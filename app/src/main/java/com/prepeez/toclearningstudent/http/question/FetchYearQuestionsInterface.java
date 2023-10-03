package com.prepeez.toclearningstudent.http.question;

import com.prepeez.toclearningstudent.pojo.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FetchYearQuestionsInterface {
    @GET("public/api/year_questions/{search}")
    Call<List<Question>> fetch(@Path("search") String search);
}
