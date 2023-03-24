import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Quiz quiz = new Quiz();
        quiz.readQuestions();
        quiz.startQuiz();
        quiz.presentQuestions();

        System.out.println("Õiged vastused olid: ");
        List<Question> quizQuestions= quiz.getQuestions();
        for (Question el : quizQuestions) {
            System.out.println(el.getQuestion() + " - " + el.getCorrectAnswer());
        }

    }
}