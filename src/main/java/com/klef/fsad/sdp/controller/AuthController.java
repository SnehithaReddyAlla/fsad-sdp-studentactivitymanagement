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

            // ADMIN (plain check)
            if (role.equalsIgnoreCase("ADMIN"))
            {
                isValid = request.getPassword().equals(userDetails.getPassword());
            }
            // MENTOR / PARTICIPANT (BCrypt)
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

            // Generate JWT
            String token = jwtUtil.generateToken(userDetails);

            // Fetch full user object
            Object userObj = service.getUserByLogin(request.getLogin());

            // RETURN user also
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
}