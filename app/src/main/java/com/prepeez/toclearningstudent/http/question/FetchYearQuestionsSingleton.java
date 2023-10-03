package com.prepeez.toclearningstudent.http.question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prepeez.toclearningstudent.constants.keyConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchYearQuestionsSingleton {
    private FetchYearQuestionsInterface yearQuestionsInterface = null;

    public FetchYearQuestionsInterface getYearQuestionsInterface() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (yearQuestionsInterface == null){
            Retrofit retrofit =new  Retrofit.Builder().baseUrl(keyConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            yearQuestionsInterface = retrofit.create(FetchYearQuestionsInterface.class);

        }

        return yearQuestionsInterface;
    }
}
