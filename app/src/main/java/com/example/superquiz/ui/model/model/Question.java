package com.example.superquiz.ui.model.model;

import java.util.List;

public class Question {
    private final String mQuestion;
    private final List<String> mChoiceList;
    private final int mAnswerIndex;

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public Question(String question, List<String> choiceList, int answerIndex) {
        mQuestion = question;
        mChoiceList = choiceList;
        mAnswerIndex = answerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }




}
