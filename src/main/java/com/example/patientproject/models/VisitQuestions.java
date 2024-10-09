package com.example.patientproject.models;

public class VisitQuestions {
    private Integer id;
    private Integer visitId;
    private String firstQuestionA;
    private String firstQuestionB;
    private String secondQuestion;
    private String thirdQuestion;
    private String boxQuestion;

    public VisitQuestions(Integer id, Integer visitId, String firstQuestionA, String firstQuestionB, String secondQuestion, String thirdQuestion, String boxQuestion) {
        this.id = id;
        this.visitId = visitId;
        this.firstQuestionA = firstQuestionA;
        this.firstQuestionB = firstQuestionB;
        this.secondQuestion = secondQuestion;
        this.thirdQuestion = thirdQuestion;
        this.boxQuestion = boxQuestion;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public VisitQuestions() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstQuestionA() {
        return firstQuestionA;
    }

    public void setFirstQuestionA(String firstQuestionA) {
        this.firstQuestionA = firstQuestionA;
    }

    public String getFirstQuestionB() {
        return firstQuestionB;
    }

    public void setFirstQuestionB(String firstQuestionB) {
        this.firstQuestionB = firstQuestionB;
    }

    public String getSecondQuestion() {
        return secondQuestion;
    }

    public void setSecondQuestion(String secondQuestion) {
        this.secondQuestion = secondQuestion;
    }

    public String getThirdQuestion() {
        return thirdQuestion;
    }

    public void setThirdQuestion(String thirdQuestion) {
        this.thirdQuestion = thirdQuestion;
    }

    public String getBoxQuestion() {
        return boxQuestion;
    }

    public void setBoxQuestion(String boxQuestion) {
        this.boxQuestion = boxQuestion;
    }
}
