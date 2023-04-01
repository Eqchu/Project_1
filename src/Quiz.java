import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private User user;
    private Scanner scan = new Scanner(System.in);
    private int numOfQuestions;
    private int fileOrWeb;

    public Quiz() {
    }
    void welcome(){
        System.out.println("Welcome to the quiz game! In this quiz you can challenge your knowledge to answer different \n" +
                "questions with various topics.");
        String input;
        System.out.print("Enter your name: ");
        input = scan.nextLine();
        user = new User(input);
        System.out.print("Hello, " + user.getName() + "! ");
        System.out.println("Do you want to play quiz from file(1) or from the web(2)?");
        fileOrWeb = Integer.parseInt(scan.nextLine());
    }

    void startQuizURL() {
        System.out.println("How many questions would you like to answer?");
        numOfQuestions = scan.nextInt();
        System.out.println("Lets start game with " + numOfQuestions + " questions..");
        System.out.println();
    }
    void startQuizFile() {
        System.out.println("Maximum number of questions is 20 and minimum number of questions is 3.");
        System.out.println("How many questions would you like to answer?");
        int numberOfQuestions = Integer.parseInt(scan.nextLine());
        int rng = (int) Math.round((Math.random() * (20 - 3)) + 3); //Generating random number from 1 to 20 if user selects no of questions outside boundaries
        if(numberOfQuestions <= 20 && numberOfQuestions >= 3){
            numOfQuestions = numberOfQuestions;
            System.out.println("Lets start game with " + numOfQuestions + " questions..");
        }
        else if(numberOfQuestions < 3){
            System.out.println("Minimum number of questions is 3. Game starts with random number of questions.");
            numOfQuestions = rng;
        }
        else{
            numOfQuestions = rng;
            System.out.println("Maximum number of questions is 20. Game starts with random number of questions.");
        }
        System.out.println();
    }

    void endQuiz() {
        System.out.println();
        System.out.println("Great game, " + user.getName() + "! You answered " + user.getScore() + " out of " + numOfQuestions + " questions correctly.");
    }

    void readQuestionsFile() throws Exception {
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
            String correctAnswer = lineaslist[(lineaslist.length - 1)].substring(8); //Removing "Answer: " from start of element
            Question q = new Question(oneQuestion, answerOptions, correctAnswer);
            questions.add(q);
        }
    }

     void readQuestionsURL() throws IOException {
        String urlString = "https://the-trivia-api.com/api/questions?limit=" + numOfQuestions + "&region=EE&difficulty=easy";
        URL url = new URL(urlString);
        String jsonString;

        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder jsonText = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                jsonText.append((char) c);
            }
            jsonString =  jsonText.toString();
        }

        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String question = jsonObject.getString("question");
            String correctAnswer = jsonObject.getString("correctAnswer");
            List<String> answers = new ArrayList<>();
            answers.add(correctAnswer);
            JSONArray answersJson = jsonObject.getJSONArray("incorrectAnswers");
            if (answersJson != null) {
                for (int j = 0; j < answersJson.length(); j++) {
                    answers.add(answersJson.get(j).toString());
                }
            }
            Collections.shuffle(answers);
            Question q = new Question(question, answers, correctAnswer);
            questions.add(q);
        }
    }

    void presentQuestions() {
        int answerIndex;
        List<Question> shuffledQuestions = new ArrayList<>(questions); //Making a list of all questions to shuffle
        Collections.shuffle(shuffledQuestions); //Shuffeling questions
        shuffledQuestions = shuffledQuestions.subList(0, numOfQuestions); //Take only as much of shuffeled questions as user asked for
        for (int i = 0; i < shuffledQuestions.size(); i++) {
            Question question = shuffledQuestions.get(i);
            System.out.println(i+1 + "/" + shuffledQuestions.size() + " " + question.getQuestion());
            presentAnswers(question);
            System.out.print("Enter number of correct answer: ");
            answerIndex = scan.nextInt() - 1;

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

    public int getFileOrWeb() {
        return fileOrWeb;
    }
}
