import java.io.File;
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
    void readQuestions() throws Exception { //Meetod lugemaks küsimused sisse failist.
        File file = new File("src/Quiz");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine(); //Loe rida sisse
            String[] lineaslist = line.split("; "); //Tükelda ; kohtade pealt
            String oneQuestion = lineaslist[0]; //Küsimus
            List<String> answerOptions = new ArrayList<>(); //Vastused
            for(int i = 1; i < lineaslist.length - 1; i++) {
                answerOptions.add(lineaslist[i]);
            }
            String correctAnswer = lineaslist[(lineaslist.length -1)].substring(8); //Valime õige vastuse ning lõikame eest ära "Answer: " osa
            Question q = new Question(oneQuestion, answerOptions, correctAnswer);
            questions.add(q);
        }
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
                System.out.println("See on kahjuks vale vastus!");
                System.out.println("Õige vastus oleks olnud: " + question.getCorrectAnswer());
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
    public List<Question> getQuestions() {
        return questions;
    }
}
