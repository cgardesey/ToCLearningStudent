package com.prepeez.toclearningstudent.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 2CLearning on 12/16/2017.
 */

public class Question implements Serializable {
    @Expose
    private int id;
    @Expose
    private String questionid;
    @Expose
    private String picture;
    @Expose
    private String question;
    @Expose
    private String answer;
    @Expose
    private String videopath;
    @Expose
    private int ismcq;
    @Expose
    private int isendofquestion;
    @Expose
    private int isfirstquestion;
    @Expose
    private int islastquestion;
    @Expose
    private int isfirstmcq;
    @Expose
    private int islastmcq;
    @Expose
    private String created_at;
    @Expose
    private String updated_at;


    public Question(){

    }

    public Question(int id, String questionid, String picture, String question, String answer, String videopath, int ismcq, int isendofquestion, int isfirstquestion, int islastquestion, int isfirstmcq, int islastmcq) {
        this.id = id;
        this.questionid = questionid;
        this.picture = picture;
        this.question = question;
        this.answer = answer;
        this.videopath = videopath;
        this.ismcq = ismcq;
        this.isendofquestion = isendofquestion;
        this.isfirstquestion = isfirstquestion;
        this.islastquestion = islastquestion;
        this.isfirstmcq = isfirstmcq;
        this.islastmcq = islastmcq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }

    public int getIsmcq() {
        return ismcq;
    }

    public void setIsmcq(int ismcq) {
        this.ismcq = ismcq;
    }

    public int getIsendofquestion() {
        return isendofquestion;
    }

    public void setIsendofquestion(int isendofquestion) {
        this.isendofquestion = isendofquestion;
    }

    public int getIsfirstquestion() {
        return isfirstquestion;
    }

    public void setIsfirstquestion(int isfirstquestion) {
        this.isfirstquestion = isfirstquestion;
    }

    public int getIslastquestion() {
        return islastquestion;
    }

    public void setIslastquestion(int islastquestion) {
        this.islastquestion = islastquestion;
    }

    public int getIsfirstmcq() {
        return isfirstmcq;
    }

    public void setIsfirstmcq(int isfirstmcq) {
        this.isfirstmcq = isfirstmcq;
    }

    public int getIslastmcq() {
        return islastmcq;
    }

    public void setIslastmcq(int islastmcq) {
        this.islastmcq = islastmcq;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
