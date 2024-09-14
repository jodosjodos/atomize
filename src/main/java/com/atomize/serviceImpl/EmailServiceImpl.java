package com.atomize.serviceImpl;

import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private  String from;

    @Override
    public void sendEmailToDos(String to, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            mailSender.send(simpleMailMessage);
            log.info(" email is sent successfully");

        } catch (Exception e) {
            throw new ApiRequestException(" email failed to be sent , please try again", e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
