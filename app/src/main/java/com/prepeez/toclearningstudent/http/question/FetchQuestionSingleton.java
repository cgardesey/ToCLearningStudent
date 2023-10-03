package com.prepeez.toclearningstudent.http.question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prepeez.toclearningstudent.constants.keyConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionSingleton {
    private FetchQuestionInterface questionInterface = null;

    public FetchQuestionInterface getQuestionInterface() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (questionInterface == null){
            Retrofit retrofit =new  Retrofit.Builder().baseUrl(keyConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            questionInterface = retrofit.create(FetchQuestionInterface.class);

        }

        return questionInterface;
    }
}
