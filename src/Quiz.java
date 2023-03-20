import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private User user;
    private Scanner scan = new Scanner(System.in);

    public Quiz () {
    }
    void startQuiz (){
        String input;
        System.out.print("Sisesta oma nimi: ");
        input = scan.nextLine();
        user = new User(input);
        System.out.println("Tere, " + user.getName() + "!");
        System.out.println("Alustame mängu..");
        System.out.println();
    }
    void readQuestions() {
        Question question1 = new Question("What is the capital of France?", Arrays.asList("Paris", "Berlin", "London", "Madrid"), "Paris");
        Question question2 = new Question("What is the largest planet in our solar system?", Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"), "Jupiter");
        questions.add(question1);
        questions.add(question2);
    }
    void presentQuestions() {
        String answer;
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            presentAnswers(question);
            System.out.println();
            System.out.print("Sisesta vastus: ");
            answer = scan.nextLine();
            if (answer.equals(question.getCorrectAnswer()))
            {
                System.out.println("Õige vastus!");
                user.addScore(1);
                System.out.println("Sinu skoor on: " + user.getScore());
                System.out.println();
            }
            else
            {
                System.out.println("See on kahjuks vale vastus");
                System.out.println("Sinu skoor on: " + user.getScore());
                System.out.println();
            }

        }
    }

    void presentAnswers(Question question) {
        for (String answer : question.getAnswers()) {
            System.out.println(answer);
        }

    }
}
