package com.prepeez.toclearningstudent.http.subscriber;

import com.prepeez.toclearningstudent.pojo.Subscriber;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UpdateSubscriberInterface {

    //@FormUrlEncoded
    @POST("public/api/update_subscriber/{subscriptionid}")
    Call<Subscriber> update(@Path("subscriptionid") String subscriptionid,
                            @Body Subscriber subscriber
    );
}
