package com.melitaltd.services;

import com.melitaltd.config.EmailProperty;
import com.melitaltd.entity.OrderEntity;
import com.melitaltd.model.Email;
import org.springframework.stereotype.Service;

@Service
public class OrderEmailService {

    private final EmailService emailService;
    private final EmailProperty emailProperty;

    public OrderEmailService(EmailService emailService, EmailProperty emailProperty) {

        this.emailService = emailService;
        this.emailProperty = emailProperty;
    }

    public void generateOrderEmail(OrderEntity order, boolean manualApproval) {

        Email email = manualApproval ? createManualApprovalEmail(order) : createAutoApprovalEmail(order);
        emailService.sendSimpleEmail(email);
    }

    private Email createManualApprovalEmail(OrderEntity order) {
        String subject = emailProperty.getSubjectManualApprove();
        subject = String.format(subject, order.getId());
        String body = emailProperty.getManualApprovalBody();
        return new Email(emailProperty.getTo(), subject, body);
    }

    private Email createAutoApprovalEmail(OrderEntity order) {
        String subject = emailProperty.getSubjectAutoApprove();
        subject = String.format(subject, order.getId());
        String body = emailProperty.getAutoApprovalBody();
        return new Email(emailProperty.getTo(), subject, body);
    }
}
