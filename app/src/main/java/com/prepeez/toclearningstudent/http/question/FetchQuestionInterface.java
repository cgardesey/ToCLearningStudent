package com.prepeez.toclearningstudent.http.question;

import com.prepeez.toclearningstudent.pojo.Question;
import com.prepeez.toclearningstudent.pojo.Subscriber;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FetchQuestionInterface {
    @GET("public/api/question/{questionid}")
    Call<Question> fetch(@Path("questionid") String questionid);
}
