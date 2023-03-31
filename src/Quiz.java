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
        System.out.print("Enter your name: ");
        input = scan.nextLine();
        user = new User(input);
        System.out.println("Hello, " + user.getName() + "!");
        System.out.println("Lets start..");
        System.out.println();
    }

    void endQuiz() {
        System.out.println();
        System.out.println("Great game, " + user.getName() + "! Your total score is: " + user.getScore());
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
        for (int i = 0; i < shuffledQuestions.size(); i++) {
            Question question = shuffledQuestions.get(i);
            System.out.println(i+1 + "/" + shuffledQuestions.size() + " " + question.getQuestion());
            presentAnswers(question);
            System.out.print("Enter number of correct answer: ");
            answerIndex = Integer.parseInt(scan.nextLine()) - 1;


            if (answerIndex >= 0 && answerIndex < question.getAnswers().size() &&
                    question.getAnswers().get(answerIndex).equals(question.getCorrectAnswer())) {
                user.addScore(1);
                System.out.println("Correct answer! Your score is: " + user.getScore());
            } else {
                System.out.println("Incorrect answer! Your score is: " + user.getScore());
                System.out.println("Correct answer was: " + question.getCorrectAnswer());
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
