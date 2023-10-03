package com.prepeez.toclearningstudent.http.question;

import com.prepeez.toclearningstudent.pojo.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FetchQuestionsInterface {
    @GET("public/api/questions")
    Call<List<Question>> fetchAll();
}
