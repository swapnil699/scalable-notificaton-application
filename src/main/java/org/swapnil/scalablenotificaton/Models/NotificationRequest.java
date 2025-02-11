//package org.swapnil.scalablenotificaton.Models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class NotificationRequest {
//    private int notificationPriority;
//    private String[] channels;
//    private Recipient recipient;
//    private Content content;
//}
package org.swapnil.scalablenotificaton.Models;

public class NotificationRequest {
    private int notificationPriority;
    private String[] channels;
    private Recipient recipient;
    private Content content;

    public NotificationRequest() {}

    public NotificationRequest(int notificationPriority, String[] channels, Recipient recipient, Content content) {
        this.notificationPriority = notificationPriority;
        this.channels = channels;
        this.recipient = recipient;
        this.content = content;
    }

    public int getNotificationPriority() {
        return notificationPriority;
    }

    public void setNotificationPriority(int notificationPriority) {
        this.notificationPriority = notificationPriority;
    }

    public String[] getChannels() {
        return channels;
    }

    public void setChannels(String[] channels) {
        this.channels = channels;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
