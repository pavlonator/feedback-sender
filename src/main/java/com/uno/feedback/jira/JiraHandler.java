package com.uno.feedback.jira;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.JiraRestClientFactory;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.input.IssueInput;
 import com.atlassian.jira.rest.client.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.uno.feedback.Conf;
import com.uno.feedback.FeedbackHandler;
import com.uno.feedback.gmail.GmailHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * @author: Pavlo Cherkashyn
 */
public class JiraHandler implements FeedbackHandler {
    Logger log = LoggerFactory.getLogger(GmailHandler.class);

    @Override
    public void handleFeedback(String from, String subject, String message) {
        try {
            Conf conf = Conf.getDefaultInstance();
            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            final URI jiraServerUri = new URI(conf.getJiraUrl());
            final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, conf.getJiraUsername(), conf.getJiraPassword());
            com.atlassian.jira.rest.client.domain.input.IssueInputBuilder issueBuilder = new IssueInputBuilder(conf.getJiraProject(), 2L);
            IssueInput issueInput = issueBuilder.setDescription("issue description").setSummary("issue summary").build();
            Promise<BasicIssue> bIssue = restClient.getIssueClient().createIssue(issueInput);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}
