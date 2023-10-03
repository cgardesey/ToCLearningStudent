package com.prepeez.toclearningstudent.http.question;


import com.prepeez.toclearningstudent.pojo.Question;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QuestionInterface {
    @FormUrlEncoded
    @POST("public/api/save_question")
    Call<Question> addRecord(
            @Field("questionid") String questionid,
            @Field("picture") String picture,
            @Field("question") String question,
            @Field("answer") String answer,
            @Field("ismcq") int ismcq,
            @Field("isendofquestion") int isendofquestion,
            @Field("isfirstquestion") int isfirstquestion,
            @Field("islastquestion") int islastquestion,
            @Field("isfirstmcq") int isfirstmcq,
            @Field("islastmcq") int islastmcq
    );
}
