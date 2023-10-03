package com.prepeez.toclearningstudent.http.subscriber;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prepeez.toclearningstudent.constants.keyConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchSubscriberSingleton {
    private FetchSubscriberInterface subscriberInterface = null;

    public FetchSubscriberInterface getSubscriberInterface() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (subscriberInterface == null){
            Retrofit retrofit =new  Retrofit.Builder().baseUrl(keyConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            subscriberInterface = retrofit.create(FetchSubscriberInterface.class);

        }

        return subscriberInterface;
    }
}
