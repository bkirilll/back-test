package ru.dictation.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender sender;

    public void sendMessage(String to, String subject, String path) throws MessagingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        messageHelper.setFrom(username);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText("text!");

        FileSystemResource file = new FileSystemResource(new File(path));

        messageHelper.addInline("Image.jpg", file);

        sender.send(message);
    }
}
