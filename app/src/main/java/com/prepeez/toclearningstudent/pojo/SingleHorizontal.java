package com.prepeez.toclearningstudent.pojo;



public class SingleHorizontal {

    private int images;
    private String desc;

    public SingleHorizontal() {

    }

    public SingleHorizontal(int images, String desc) {
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
