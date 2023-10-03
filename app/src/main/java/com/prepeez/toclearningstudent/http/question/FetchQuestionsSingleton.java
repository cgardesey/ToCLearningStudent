package com.prepeez.toclearningstudent.http.question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prepeez.toclearningstudent.constants.keyConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionsSingleton {
    private FetchQuestionsInterface questionsInterface = null;

    public FetchQuestionsInterface getQuestionsInterface() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (questionsInterface == null){
            Retrofit retrofit =new  Retrofit.Builder().baseUrl(keyConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            questionsInterface = retrofit.create(FetchQuestionsInterface.class);

        }

        return questionsInterface;
    }
}
