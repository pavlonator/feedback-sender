package com.uno.smtp;

import org.junit.Test;

/**
 * @author: Pavlo Cherkashyn
 */
public class SenderTest {

    @Test
    public void testEmailSend(){
        String from = "pavlocherkashyn@gmail.com";
        String text = "button does not work";
        String subject = "i found a bug on your website";
        //GmailHandler.sendEmail(from, subject, text);
    }
}
