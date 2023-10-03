package com.prepeez.toclearningstudent.pojo;

/**
 * Created by Cyril on 7/26/2017.
 */

public class ListItem {
    private int imgId;
    private String title;
    public ListItem()
    {

    }
    public ListItem(int imgId, String title) {
        this.imgId = imgId;
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
