package com.melitaltd.services;

import com.melitaltd.config.EmailProperty;
import com.melitaltd.exception.ServiceError;
import com.melitaltd.model.Email;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final EmailProperty emailProperty;

    public EmailService(JavaMailSender javaMailSender,
                              EmailProperty emailProperty) {
        this.javaMailSender = javaMailSender;
        this.emailProperty = emailProperty;
    }

    public void sendSimpleEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailProperty.getFrom());
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            javaMailSender.send(message);
        } catch (Exception e) {
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException();
        }
    }
}
