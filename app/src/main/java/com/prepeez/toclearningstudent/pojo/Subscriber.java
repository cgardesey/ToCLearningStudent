package com.prepeez.toclearningstudent.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by 2CLearning on 12/16/2017.
 */

public class Subscriber implements Serializable {
    @Expose
    private int id;
    @Expose
    private String subscriptionid;
    @Expose
    private String subscriptiontype;
    @Expose
    private String networkprovider;
    @Expose
    private String number;
    @Expose
    private String created_at;
    @Expose
    private String updated_at;

    public Subscriber(){

    }

    public Subscriber(int id, String subscriptionid, String subscriptiontype, String networkprovider, String number) {
        this.id = id;
        this.subscriptionid = subscriptionid;
        this.subscriptiontype = subscriptiontype;
        this.networkprovider = networkprovider;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubscriptionid() {
        return subscriptionid;
    }

    public void setSubscriptionid(String subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

    public String getSubscriptiontype() {
        return subscriptiontype;
    }

    public void setSubscriptiontype(String subscriptiontype) {
        this.subscriptiontype = subscriptiontype;
    }

    public String getNetworkprovider() {
        return networkprovider;
    }

    public void setNetworkprovider(String networkprovider) {
        this.networkprovider = networkprovider;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
