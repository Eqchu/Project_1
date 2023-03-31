import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Quiz quiz = new Quiz();
        quiz.startQuiz();
        //quiz.readQuestions();
        quiz.readQuestionsURL();
        quiz.presentQuestions();
        quiz.endQuiz();
    }
}