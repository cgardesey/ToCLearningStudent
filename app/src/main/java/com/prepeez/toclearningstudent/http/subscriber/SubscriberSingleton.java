package com.prepeez.toclearningstudent.http.subscriber;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prepeez.toclearningstudent.constants.keyConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubscriberSingleton {
    private SubscriberInterface subscriberInterface = null;

    public SubscriberInterface getSubscriberInterface() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (subscriberInterface == null){
            Retrofit retrofit =new  Retrofit.Builder().baseUrl(keyConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            subscriberInterface = retrofit.create(SubscriberInterface.class);

        }

        return subscriberInterface;
    }
}
