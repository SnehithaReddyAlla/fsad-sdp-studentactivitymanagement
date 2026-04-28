package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.entity.Participant;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.MentorRepository;
import com.klef.fsad.sdp.repository.ParticipantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private ParticipantRepository participantRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    private Map<String, String> otpStorage = new HashMap<>();
    private Map<String, LocalDateTime> otpExpiryStorage = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException
    {
        Optional<Admin> adminOpt = adminRepository.findById(input);

        if (adminOpt.isPresent())
        {
            Admin admin = adminOpt.get();

            return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                List.of(new SimpleGrantedAuthority("ADMIN"))
            );
        }

        Mentor mentor = mentorRepository.findByEmail(input);

        if (mentor != null)
        {
            return new org.springframework.security.core.userdetails.User(
                mentor.getEmail(),
                mentor.getPassword(),
                List.of(new SimpleGrantedAuthority("MENTOR"))
            );
        }

        Participant participant = participantRepository.findByEmail(input);

        if (participant != null)
        {
            return new org.springframework.security.core.userdetails.User(
                participant.getEmail(),
                participant.getPassword(),
                List.of(new SimpleGrantedAuthority("PARTICIPANT"))
            );
        }

        throw new UsernameNotFoundException("User not found with input: " + input);
    }

    @Override
    public Object getUserByLogin(String input)
    {
        Optional<Admin> adminOpt = adminRepository.findById(input);
        if (adminOpt.isPresent())
        {
            return adminOpt.get();
        }

        Mentor mentor = mentorRepository.findByEmail(input);
        if (mentor != null)
        {
            return mentor;
        }

        Participant participant = participantRepository.findByEmail(input);
        if (participant != null)
        {
            return participant;
        }

        return null;
    }
    
    @Override
    public String changePassword(String login, String role, String newPassword)
    {
        if (role.equalsIgnoreCase("ADMIN"))
        {
            Optional<Admin> adminOpt = adminRepository.findById(login);

            if (adminOpt.isPresent())
            {
                Admin admin = adminOpt.get();
                admin.setPassword(newPassword);
                adminRepository.save(admin);
                return "Admin Password Changed Successfully";
            }

            return "USER_NOT_FOUND";
        }

        if (role.equalsIgnoreCase("MENTOR"))
        {
            Mentor mentor = mentorRepository.findByEmail(login);

            if (mentor == null)
            {
                mentor = mentorRepository.findByUsername(login);
            }

            if (mentor != null)
            {
                mentor.setPassword(passwordEncoder.encode(newPassword));
                mentorRepository.save(mentor);
                return "Mentor Password Changed Successfully";
            }

            return "USER_NOT_FOUND";
        }

        if (role.equalsIgnoreCase("PARTICIPANT"))
        {
            Participant participant = participantRepository.findByEmail(login);

            if (participant == null)
            {
                participant = participantRepository.findByUsername(login);
            }

            if (participant != null)
            {
                participant.setPassword(passwordEncoder.encode(newPassword));
                participantRepository.save(participant);
                return "Participant Password Changed Successfully";
            }

            return "USER_NOT_FOUND";
        }

        return "INVALID_ROLE";
    }
    
    @Override
    public String sendForgotPasswordOtp(String email, String role)
    {
        if (role == null || email == null)
        {
            return "INVALID_INPUT";
        }

        if (role.equalsIgnoreCase("MENTOR"))
        {
            Mentor mentor = mentorRepository.findByEmail(email);

            if (mentor == null)
            {
                return "USER_NOT_FOUND";
            }
        }
        else if (role.equalsIgnoreCase("PARTICIPANT"))
        {
            Participant participant = participantRepository.findByEmail(email);

            if (participant == null)
            {
                return "USER_NOT_FOUND";
            }
        }
        else
        {
            return "INVALID_ROLE";
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        String key = role.toUpperCase() + "_" + email;

        otpStorage.put(key, otp);
        otpExpiryStorage.put(key, LocalDateTime.now().plusMinutes(5));

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Password Reset OTP - Student Activity System");
        message.setText(
            "Your OTP for password reset is: " + otp + "\n\n" +
            "This OTP is valid for 5 minutes.\n\n" +
            "If you did not request this, please ignore this email."
        );

        mailSender.send(message);

        return "OTP Sent Successfully";
    }

    @Override
    public String resetPasswordWithOtp(String email, String role, String otp, String newPassword)
    {
        if (email == null || role == null || otp == null || newPassword == null)
        {
            return "INVALID_INPUT";
        }

        String key = role.toUpperCase() + "_" + email;

        if (!otpStorage.containsKey(key))
        {
            return "OTP_NOT_FOUND";
        }

        LocalDateTime expiryTime = otpExpiryStorage.get(key);

        if (expiryTime == null || LocalDateTime.now().isAfter(expiryTime))
        {
            otpStorage.remove(key);
            otpExpiryStorage.remove(key);
            return "OTP_EXPIRED";
        }

        String savedOtp = otpStorage.get(key);

        if (!savedOtp.equals(otp))
        {
            return "INVALID_OTP";
        }

        if (role.equalsIgnoreCase("MENTOR"))
        {
            Mentor mentor = mentorRepository.findByEmail(email);

            if (mentor == null)
            {
                return "USER_NOT_FOUND";
            }

            mentor.setPassword(passwordEncoder.encode(newPassword));
            mentorRepository.save(mentor);
        }
        else if (role.equalsIgnoreCase("PARTICIPANT"))
        {
            Participant participant = participantRepository.findByEmail(email);

            if (participant == null)
            {
                return "USER_NOT_FOUND";
            }

            participant.setPassword(passwordEncoder.encode(newPassword));
            participantRepository.save(participant);
        }
        else
        {
            return "INVALID_ROLE";
        }

        otpStorage.remove(key);
        otpExpiryStorage.remove(key);

        return "Password Changed Successfully";
    }
}