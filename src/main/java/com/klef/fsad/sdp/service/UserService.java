package com.klef.fsad.sdp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
    public Object getUserByLogin(String input);
    public String changePassword(String login, String role, String newPassword);
    
    public String sendForgotPasswordOtp(String email, String role);

    public String resetPasswordWithOtp(String email, String role, String otp, String newPassword);
}