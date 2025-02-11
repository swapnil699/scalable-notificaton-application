//package org.swapnil.scalablenotificaton.Models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Recipient {
//    private String userId;
//    private String userEmail;
//}
package org.swapnil.scalablenotificaton.Models;

public class Recipient {
    private String userId;
    private String userEmail;

    public Recipient() {}

    public Recipient(String userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
