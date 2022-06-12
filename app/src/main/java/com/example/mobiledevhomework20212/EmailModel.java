package com.example.mobiledevhomework20212;

public class EmailModel {
    private String emailFrom, emailTime, emailTitle, emailContent;

    public EmailModel(String emailFrom, String emailTime, String emailTitle, String emailContent) {
        this.emailFrom = emailFrom;
        this.emailTime = emailTime;
        this.emailTitle = emailTitle;
        this.emailContent = emailContent;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTime() {
        return emailTime;
    }

    public void setEmailTime(String emailTime) {
        this.emailTime = emailTime;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }
}
