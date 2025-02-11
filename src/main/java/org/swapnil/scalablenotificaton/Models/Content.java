//package org.swapnil.scalablenotificaton.Models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Map;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Content {
//    private boolean usingTemplates;
//    private String templateName;
//    private Map<String, String> placeholders;
//    private String message;
//    private String emailSubject;
//    private String[] emailAttachments;
//    private PushNotification pushNotification;
//}

package org.swapnil.scalablenotificaton.Models;

import java.util.Map;

public class Content {
    private boolean usingTemplates;
    private String templateName;
    private Map<String, String> placeholders;
    private String message;
    private String emailSubject;
    private String[] emailAttachments;
    private PushNotification pushNotification;

    public Content() {}

    public Content(boolean usingTemplates, String templateName, Map<String, String> placeholders,
                   String message, String emailSubject, String[] emailAttachments, PushNotification pushNotification) {
        this.usingTemplates = usingTemplates;
        this.templateName = templateName;
        this.placeholders = placeholders;
        this.message = message;
        this.emailSubject = emailSubject;
        this.emailAttachments = emailAttachments;
        this.pushNotification = pushNotification;
    }

    public boolean isUsingTemplates() {
        return usingTemplates;
    }

    public void setUsingTemplates(boolean usingTemplates) {
        this.usingTemplates = usingTemplates;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String[] getEmailAttachments() {
        return emailAttachments;
    }

    public void setEmailAttachments(String[] emailAttachments) {
        this.emailAttachments = emailAttachments;
    }

    public PushNotification getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(PushNotification pushNotification) {
        this.pushNotification = pushNotification;
    }
}
