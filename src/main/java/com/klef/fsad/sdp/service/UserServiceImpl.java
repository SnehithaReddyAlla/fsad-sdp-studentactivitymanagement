package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.entity.Participant;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.MentorRepository;
import com.klef.fsad.sdp.repository.ParticipantRepository;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private ParticipantRepository participantRepository;

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
}