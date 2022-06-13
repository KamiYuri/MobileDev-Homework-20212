package com.example.mobiledevhomework20212;

import java.util.Date;

public class EmailModel {
    private String emailFrom, emailTitle, emailContent;
    private Date emailTime;

    public EmailModel(String emailFrom, Date emailTime, String emailTitle, String emailContent) {
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

    public Date getEmailTime() {
        return emailTime;
    }

    public void setEmailTime(Date emailTime) {
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
