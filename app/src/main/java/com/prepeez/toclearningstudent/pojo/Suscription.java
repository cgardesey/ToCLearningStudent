package com.prepeez.toclearningstudent.pojo;

/**
 * Created by 2CLearning on 12/30/2017.
 */

public class Suscription {
    String amount;
    String primary_description;
    String secondary_description;

    public Suscription(){

    }

    public Suscription(String amount, String primary_description, String secondary_description) {
        this.amount = amount;
        this.primary_description = primary_description;
        this.secondary_description = secondary_description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrimary_description() {
        return primary_description;
    }

    public void setPrimary_description(String primary_description) {
        this.primary_description = primary_description;
    }

    public String getSecondary_description() {
        return secondary_description;
    }

    public void setSecondary_description(String secondary_description) {
        this.secondary_description = secondary_description;
    }
}

