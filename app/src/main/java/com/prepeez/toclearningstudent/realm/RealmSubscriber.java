package com.prepeez.toclearningstudent.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 2CLearning on 12/16/2017.
 */

public class RealmSubscriber extends RealmObject {
    @PrimaryKey
    private int id;
    private String subscriptionid;
    private String subscriptiontype;
    private String networkprovider;
    private String number;
    private String created_at;
    private String updated_at;

    public RealmSubscriber(){

    }

    public RealmSubscriber(String subscriptionid, String subscriptiontype, String networkprovider, String number, String created_at, String updated_at) {
        this.subscriptionid = subscriptionid;
        this.subscriptiontype = subscriptiontype;
        this.networkprovider = networkprovider;
        this.number = number;
        this.created_at = created_at;
        this.updated_at = updated_at;
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
