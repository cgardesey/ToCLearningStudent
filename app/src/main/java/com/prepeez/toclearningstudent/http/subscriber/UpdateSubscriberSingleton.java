package com.prepeez.toclearningstudent.http.subscriber;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prepeez.toclearningstudent.constants.keyConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateSubscriberSingleton {
    private UpdateSubscriberInterface subscribersInterface = null;

    public UpdateSubscriberInterface updateRecordsInterface() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (subscribersInterface == null){
            Retrofit retrofit =new  Retrofit.Builder().baseUrl(keyConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            subscribersInterface = retrofit.create(UpdateSubscriberInterface.class);

        }

        return subscribersInterface;
    }
}
