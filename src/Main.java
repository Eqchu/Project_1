import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
public class Main {
    static List<Question> questions = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);
    static User user;
    public static void main(String[] args) {
        String input;


        System.out.print("Sisesta oma nimi: ");
        input = scan.nextLine();
        user = new User(input);
        System.out.println("Tere, " + user.getName() + "!");
        System.out.println("Alustame mängu..");
        System.out.println();

        readQuestions();
        presentQuestions();
    }

    static void readQuestions() {
        Question question1 = new Question("What is the capital of France?", Arrays.asList("Paris", "Berlin", "London", "Madrid"), "Paris");
        Question question2 = new Question("What is the largest planet in our solar system?", Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"), "Jupiter");
        questions.add(question1);
        questions.add(question2);
    }

    static void presentQuestions() {
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

    static void presentAnswers(Question question) {
        for (String answer : question.getAnswers()) {
            System.out.println(answer);
        }

    }

}