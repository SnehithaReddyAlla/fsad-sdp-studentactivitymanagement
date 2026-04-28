package com.klef.fsad.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.dto.AuthRequestDTO;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.UserService;
import com.klef.fsad.sdp.dto.ChangePasswordDTO;
import com.klef.fsad.sdp.dto.ForgotPasswordRequestDTO;
import com.klef.fsad.sdp.dto.ResetPasswordDTO;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController
{
    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request)
    {
        try
        {
            UserDetails userDetails = service.loadUserByUsername(request.getLogin());

            String role = userDetails.getAuthorities()
                    .iterator().next().getAuthority();

            if (!role.equalsIgnoreCase(request.getRole()))
            {
                return ResponseEntity.status(403).body("Invalid Role");
            }

            boolean isValid = false;

            if (role.equalsIgnoreCase("ADMIN"))
            {
                isValid = request.getPassword().equals(userDetails.getPassword());
            }
            else if (role.equalsIgnoreCase("MENTOR") || role.equalsIgnoreCase("PARTICIPANT"))
            {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                isValid = encoder.matches(request.getPassword(), userDetails.getPassword());
            }
            else
            {
                return ResponseEntity.status(403).body("Invalid Role Type");
            }

            if (!isValid)
            {
                return ResponseEntity.status(401).body("Login Invalid");
            }

            String token = jwtUtil.generateToken(userDetails);

            Object userObj = service.getUserByLogin(request.getLogin());

            return ResponseEntity.ok(
                Map.of(
                    "token", token,
                    "role", role,
                    "user", userObj
                )
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    
    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto)
    {
        try
        {
            String output = service.changePassword(
                dto.getLogin(),
                dto.getRole(),
                dto.getNewPassword()
            );

            if (output.equals("USER_NOT_FOUND"))
            {
                return ResponseEntity.status(404).body("User Not Found");
            }

            if (output.equals("INVALID_ROLE"))
            {
                return ResponseEntity.status(400).body("Invalid Role");
            }

            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Password Change Failed");
        }
    }
    
    @PostMapping("/forgotpassword/sendotp")
    public ResponseEntity<String> sendOtp(@RequestBody ForgotPasswordRequestDTO dto)
    {
        try
        {
            String output = service.sendForgotPasswordOtp(
                dto.getEmail(),
                dto.getRole()
            );

            if (output.equals("USER_NOT_FOUND"))
            {
                return ResponseEntity.status(404).body("User Not Found");
            }

            if (output.equals("INVALID_ROLE"))
            {
                return ResponseEntity.status(400).body("Invalid Role");
            }

            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Send OTP");
        }
    }

    @PostMapping("/forgotpassword/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO dto)
    {
        try
        {
            String output = service.resetPasswordWithOtp(
                dto.getEmail(),
                dto.getRole(),
                dto.getOtp(),
                dto.getNewPassword()
            );

            if (output.equals("OTP_NOT_FOUND"))
            {
                return ResponseEntity.status(400).body("Please generate OTP first");
            }

            if (output.equals("OTP_EXPIRED"))
            {
                return ResponseEntity.status(400).body("OTP Expired");
            }

            if (output.equals("INVALID_OTP"))
            {
                return ResponseEntity.status(400).body("Invalid OTP");
            }

            if (output.equals("USER_NOT_FOUND"))
            {
                return ResponseEntity.status(404).body("User Not Found");
            }

            if (output.equals("INVALID_ROLE"))
            {
                return ResponseEntity.status(400).body("Invalid Role");
            }

            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Password Reset Failed");
        }
    }
}