package org.swapnil.scalablenotificaton.Models;

public class BroadcastOfferRequest {
    private String emailSubject;
    private String message;

    public BroadcastOfferRequest() {}

    public BroadcastOfferRequest(String emailSubject, String message) {
        this.emailSubject = emailSubject;
        this.message = message;
    }

    public String getEmailSubject() {
        return emailSubject;
    }
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
