package com.example.rodrigo.guessinggame.model;

/**
 * Created by rodrigo on 22/11/15.
 */
public class Question {

    private Question parent;
    private String questionText;
    private Question yes;
    private Question no;
    private boolean guessing;

    public Question(String questionText, Question yes, Question no) {
        setQuestionText(questionText);
        setYes(yes);
        setNo(no);
        setGuessing(false);
    }

    public Question(String questionText) {
        setQuestionText(questionText);
        setGuessing(true);
    }

    public void setYes(Question question) {
        question.setParent(this);
        this.yes = question;
    }

    public void setNo(Question question) {
        question.setParent(this);
        this.no = question;
    }

    public Question getYes() {
        return yes;
    }

    public Question getNo() {
        return no;
    }

    public Question getParent() {
        return parent;
    }

    public void setParent(Question parent) {
        this.parent = parent;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean isGuessing() {
        return guessing;
    }

    public void setGuessing(boolean guessing) {
        this.guessing = guessing;
    }
}
