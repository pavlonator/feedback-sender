package com.uno.feedback.gmail;

import com.uno.feedback.Conf;
import com.uno.feedback.FeedbackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public class GmailHandler implements FeedbackHandler {
    Logger log = LoggerFactory.getLogger(GmailHandler.class);
    @Override
    public void handleFeedback(String from, String subject, String message) {
        try {
            final Conf conf = Conf.getDefaultInstance();
            Session session = Session.getDefaultInstance(Conf.getDefaultInstance().getProps(),
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(conf.getGmailUsername(), conf.getGmailPassword());
                        }
                    });
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(conf.getGmailReceiver()));
            mimeMessage.setSubject(conf.getGmailSubjectPrefix() + " from " + from + " " + subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}