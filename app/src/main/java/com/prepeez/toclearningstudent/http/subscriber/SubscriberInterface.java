package com.prepeez.toclearningstudent.http.subscriber;


import com.prepeez.toclearningstudent.pojo.Question;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SubscriberInterface {
    @FormUrlEncoded
    @POST("public/api/save_suscriber")
    Call<Question> addRecord(
            @Field("subscriptionid") String subscriptionid,
            @Field("subscriptiontype") String subscriptiontype,
            @Field("networkprovider") String networkprovider,
            @Field("number") String number
    );
}
