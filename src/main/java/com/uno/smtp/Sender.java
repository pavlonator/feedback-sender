package com.uno.smtp;

import sun.misc.BASE64Decoder;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class Sender {
    public static void sendEmail(String from, String subject, String text) {
        try {

            final Properties props = new Properties();
            props.load(Sender.class.getResourceAsStream("/smtp.properties"));

            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            try {
                                return new PasswordAuthentication(props.getProperty("gmail.username"), new String(new BASE64Decoder().decodeBuffer(props.getProperty("gmail.password")), "UTF-8"));
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            }
                        }
                    });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(props.getProperty("gmail.receiver")));
            message.setSubject(props.getProperty("gmail.subject.prefix") + " " + subject);
            message.setText(text);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}