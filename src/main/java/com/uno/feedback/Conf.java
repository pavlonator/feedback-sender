package com.uno.feedback;

import com.uno.feedback.gmail.GmailHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.Properties;

/**
 * @author: Pavlo Cherkashyn
 */
public class Conf {
    Logger log = LoggerFactory.getLogger(Conf.class);

    private final Properties props = new Properties();
    private static Conf instance;
    public Conf(){
        try {
            props.load(GmailHandler.class.getResourceAsStream("/smtp.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Conf getDefaultInstance() {
        if(instance==null){
            instance = new Conf();
        }
        return instance;
    }

    public boolean isGmailEnabled(){
        return "true".equals(props.getProperty("gmail.enabled", "false"));
    }

    public String getGmailUsername() {
        return props.getProperty("gmail.username");
    }

    public String getGmailPassword() {
        try {
            return new String(new BASE64Decoder().decodeBuffer(props.getProperty("gmail.password")), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public boolean isJiraEnabled(){
        return "true".equals(props.getProperty("jira.enabled", "false"));
    }

    public String getJiraUrl(){
        return props.getProperty("jira.url");
    }

    public String getJiraProject(){
        return props.getProperty("jira.project");
    }

    public String getJiraUsername(){
        return props.getProperty("jira.username");
    }

    public String getJiraPassword(){
        try {
            return new String(new BASE64Decoder().decodeBuffer(props.getProperty("jira.password")), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Properties getProps() {
        return props;
    }

    public String getGmailReceiver() {
        return props.getProperty("gmail.receiver");
    }

    public String getGmailSubjectPrefix() {
        return props.getProperty("gmail.subject.prefix");
    }
}
