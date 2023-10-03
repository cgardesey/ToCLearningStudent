package com.prepeez.toclearningstudent.http.subscriber;

import com.prepeez.toclearningstudent.pojo.Question;
import com.prepeez.toclearningstudent.pojo.Subscriber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FetchSubscribersInterface {
    @GET("public/api/subscribers")
    Call<List<Subscriber>> fetchAll();
}
