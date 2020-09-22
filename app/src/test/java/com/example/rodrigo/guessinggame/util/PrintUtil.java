package com.example.rodrigo.guessinggame.util;

import com.example.rodrigo.guessinggame.model.GameQuestion;
import com.example.rodrigo.guessinggame.model.Parent;
import com.example.rodrigo.guessinggame.model.Question;

/**
 * Created by rodrigo on 24/11/15.
 */
public class PrintUtil {

    public static void print(Question question) {
        print(question, "");
    }

    private static void print(Question q, String s) {

        boolean fullPath = true;

        if (q instanceof Parent) {
            Parent p = (Parent) q;
            fullPath = false;
            String t = s + " / " + q.getQuestionText() + "? yes";
            print(p.getYes(), t);

            t = s + " / " + q.getQuestionText() + "? no";
            print(p.getNo(), t);
        }

        String t = s + " / " + q.getQuestionText();
        if(fullPath) {
            System.out.println(t.substring(3));
        }
    }
}
