package org.swapnil.scalablenotificaton.dtos;

public class SmsRequest {
    private String phoneNumber;
    private String messageBody;

    public SmsRequest() {}

    public SmsRequest(String phoneNumber, String messageBody) {
        this.phoneNumber = phoneNumber;
        this.messageBody = messageBody;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}

