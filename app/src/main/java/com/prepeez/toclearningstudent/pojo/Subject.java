package com.prepeez.toclearningstudent.pojo;



public class Subject {

    private int images;
    private String desc;
    public Subject() {

    }

    public Subject(int images, String desc) {
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
