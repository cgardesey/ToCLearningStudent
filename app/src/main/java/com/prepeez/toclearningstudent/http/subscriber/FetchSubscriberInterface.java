package com.prepeez.toclearningstudent.http.subscriber;

import com.prepeez.toclearningstudent.pojo.Subscriber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FetchSubscriberInterface {
    @GET("public/api/subscriber/{number}")
    Call<Subscriber> fetch(@Path("number") String number);
}
