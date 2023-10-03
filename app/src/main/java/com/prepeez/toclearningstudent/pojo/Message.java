
package com.prepeez.toclearningstudent.pojo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class Message {

    String text;
    String name;
    String photoUrl;
    String docUrl;
    HashMap<String, Object> timestampCreated;
    String userId;
    String latlng;
    String tittle;
    String desc;
    String link;
    String ext;
    String mapUrl;
    String key;
    String docTitle;
    String metaData;

    public Message() {
    }

    public Message(String text, String name, String photoUrl, String userId, String docUrl, String latlng, String tittle, String desc, String link, String ext, String mapUrl, String key, String docTitle, String metaData) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        HashMap<String, Object> timestampNow = new HashMap<>();
        timestampNow.put("timestamp", ServerValue.TIMESTAMP);
        this.timestampCreated = timestampNow;
        this.userId = userId;
        this.docUrl = docUrl;
        this.latlng = latlng;
        this.tittle = tittle;
        this.desc = desc;
        this.link = link;
        this.ext = ext;
        this.mapUrl = mapUrl;
        this.key = key;
        this.docTitle = docTitle;
        this.metaData = metaData;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public HashMap<String, Object> getTimestampCreated(){
        return timestampCreated;
    }
    @Exclude
    public long getTimestampCreatedLong(){
        return (long)timestampCreated.get("timestamp");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }
}
