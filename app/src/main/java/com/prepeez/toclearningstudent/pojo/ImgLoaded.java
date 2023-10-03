package com.prepeez.toclearningstudent.pojo;

/**
 * Created by 2CLearning on 12/8/2017.
 */

public class ImgLoaded {
    String userId, timeStamp;

    public ImgLoaded(String userId) {
        this.userId = userId;
    }
    public ImgLoaded() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
