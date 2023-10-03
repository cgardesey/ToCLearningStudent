package com.prepeez.toclearningstudent.pojo;

/**
 * Created by Cyril on 7/26/2017.
 */

public class SubTopic {
    private int images;
    private String desc;
    public SubTopic() {

    }
    public SubTopic(int images, String desc) {
        this.images = images;
        this.desc = desc;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
