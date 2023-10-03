package com.prepeez.toclearningstudent.pojo;

/**
 * Created by 2CLearning on 12/30/2017.
 */

public class CreditHours {
    private int min;
    private int max;

    public CreditHours(){

    }

    public CreditHours(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}

