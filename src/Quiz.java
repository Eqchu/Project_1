import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;




public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private User user;
    private Scanner scan = new Scanner(System.in);


    public Quiz() {
    }


    void startQuiz() {
        String input;
        System.out.print("Sisesta oma nimi: ");
        input = scan.nextLine();
        user = new User(input);
        System.out.println("Tere, " + user.getName() + "!");
        System.out.println("Alustame mängu..");
        System.out.println();
    }


    void readQuestions() throws Exception {
        File file = new File("src/Quiz");
        Scanner sc = new Scanner(file);


        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] lineaslist = line.split("; ");
            String oneQuestion = lineaslist[0];
            List<String> answerOptions = new ArrayList<>();
            for (int i = 1; i < lineaslist.length - 1; i++) {
                answerOptions.add(lineaslist[i]);
            }
            String correctAnswer = lineaslist[(lineaslist.length - 1)].substring(8);
            Question q = new Question(oneQuestion, answerOptions, correctAnswer);
            questions.add(q);
        }
    }


    void presentQuestions() {
        int answerIndex;
        List<Question> shuffledQuestions = new ArrayList<>(questions);
        Collections.shuffle(shuffledQuestions);
        for (Question question : shuffledQuestions) {
            System.out.println(question.getQuestion());
            presentAnswers(question);
            System.out.print("Sisesta vastuse number: ");
            answerIndex = Integer.parseInt(scan.nextLine()) - 1;


            if (answerIndex >= 0 && answerIndex < question.getAnswers().size() &&
                    question.getAnswers().get(answerIndex).equals(question.getCorrectAnswer())) {
                System.out.println("Õige vastus!");
                user.addScore(1);
                System.out.println("Sinu skoor on: " + user.getScore());
            } else {
                System.out.println("See on kahjuks vale vastus!");
                System.out.println("Õige vastus oleks olnud: " + question.getCorrectAnswer());
                System.out.println("Sinu skoor on: " + user.getScore());
            }
            System.out.println();
        }
    }






    void presentAnswers(Question question) {
        int i = 1;
        for (String answer : question.getAnswers()) {
            System.out.println(i + ". " + answer);
            i++;
        }
    }


    public List<Question> getQuestions() {
        return questions;
    }
}
