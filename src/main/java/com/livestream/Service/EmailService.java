package com.livestream.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    JavaMailSender mailSender;

    public String requestPasswordReset(String id, String email, String pass) {
        String token = UUID.randomUUID().toString() + "hrm" + LocalDateTime.now().plusHours(1) + "hrm" + pass;
        this.sendPasswordResetEmail(id, email, token);

        return "Password reset email sent";
    }

    public void sendPasswordResetEmail(String id, String to, String token) {
        String resetUrl = "localhost:3000/users/reset-password/" + id + "?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("You requested a password reset. Click the following link to reset your password: " + resetUrl);

        mailSender.send(message);
    }

    public void sendNotificationSalary(String email, String content, String title) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            messageHelper.setTo(email);
            messageHelper.setSubject(title);
            messageHelper.setText(content, true);

            // Gửi email
            mailSender.send(mimeMessage);
        } catch (MessagingException ignored) {

        }
    }
}
