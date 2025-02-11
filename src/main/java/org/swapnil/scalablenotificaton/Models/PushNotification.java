//package org.swapnil.scalablenotificaton.Models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor @AllArgsConstructor
//public class PushNotification {
//    private String title;
//    private Action action;
//}
package org.swapnil.scalablenotificaton.Models;

public class PushNotification {
    private String title;
    private Action action;

    public PushNotification() {}

    public PushNotification(String title, Action action) {
        this.title = title;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Action getAction() {
        return action;
    }
    public void setAction(Action action) {
        this.action = action;
    }
}
