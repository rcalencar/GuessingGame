package com.example.rodrigo.guessinggame.util;

import com.example.rodrigo.guessinggame.model.QuestionKt;

/**
 * Created by rodrigo on 24/11/15.
 */
public class PrintUtil {

    public static void print(QuestionKt question) {
        print(question, "");
    }

    private static void print(QuestionKt q, String s) {

        boolean fullPath = true;

        if(q.getYes() != null) {
            fullPath = false;
            String t = s + " / " + q.getQuestionText() + "? yes";
            print(q.getYes(), t);
        }
        if (q.getNo() != null) {
            fullPath = false;
            String t = s + " / " + q.getQuestionText() + "? no";
            print(q.getNo(), t);
        }

        String t = s + " / " + q.getQuestionText() + "?";
        if(fullPath) {
            System.out.println(t.substring(3));
        }
    }
}
