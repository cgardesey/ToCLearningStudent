package com.prepeez.toclearningstudent.pojo;

/**
 * Created by 2CLearning on 12/8/2017.
 */

public class Deleted {
    String userId, timeStamp;

    public Deleted(String userId) {
        this.userId = userId;
    }
    public Deleted() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
