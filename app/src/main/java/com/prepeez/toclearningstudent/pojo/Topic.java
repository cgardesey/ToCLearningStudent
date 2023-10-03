package com.prepeez.toclearningstudent.pojo;



public class Topic {

    private int images;
    private String desc;
    public Topic() {

    }

    public Topic(int images, String desc) {
        this.images = images;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
