package com.ideaas.services.service.interfaces;

import com.ideaas.services.bean.Email;

import javax.mail.MessagingException;

public interface EmailService {

    void send(Email email) throws MessagingException;
}
