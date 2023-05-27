package game;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Random;
public class Quiz {
    private List<Question> questions = new ArrayList<>();// küsimuste loend
    private User user;
    private Scanner scan = new Scanner(System.in);
    private int numOfQuestions; // küsimuste arv
    private int difficulty; // küsimuste raskusaste
    private String levelOfDifficulty; // tekstiline;
    private int fileOrWeb; // kasutaja valik: kas küsimused failist või veebist
    public Quiz(User user) {
        this.user = user;
    } // konstruktor, millel on kasutaja parameeter
    void welcome(){// tervitusmeetod
        if (user.getName() == null) {
            System.out.println("Welcome to the quiz game! In this quiz you can challenge your knowledge to answer different \n" +
                    "questions with various topics.");
            System.out.println();
            String input;
            System.out.print("Enter your name: ");
            input = scan.nextLine();
            this.user.setName(input);
            System.out.print("Hello, " + user.getName() + "! ");
        }
        System.out.println("Do you want to play quiz from file(1), from the web(2) or pick random(3)?");
        fileOrWeb = scan.nextInt();
    }

    void startQuiz() {// meetod, et alustada mängu
        Random random = new Random();

        if (fileOrWeb != 3) {
            if (fileOrWeb == 1)
                System.out.println("Maximum number of questions is 20 and minimum number of questions is 3.");
            else {
                System.out.println("Choose the level of question difficulty: easy (1), medium(2), hard(3)");
                difficulty = scan.nextInt();
            }
            System.out.println("How many questions would you like to answer?");
            numOfQuestions = scan.nextInt();
            if (fileOrWeb == 1) {
                int rng = (int) Math.round((Math.random() * (20 - 3)) + 3); //Generating random number from 1 to 20 if user selects no of questions outside boundaries
                if (numOfQuestions < 3) {
                    System.out.println("Minimum number of questions is 3. Game starts with random number of questions.");
                    numOfQuestions = rng;
                } else if (numOfQuestions > 20) {
                    System.out.println("Maximum number of questions is 20. Game starts with random number of questions.");
                    numOfQuestions = rng;
                }
            }
        }
        else {
            fileOrWeb = 2;
        }

        difficulty = difficulty == 0 ? random.nextInt(3-1) + 1 : difficulty;
        numOfQuestions = numOfQuestions == 0? random.nextInt(30-5) + 5 : numOfQuestions;

        if (difficulty == 1) {
            levelOfDifficulty = "easy";
        }
        else if (difficulty == 2) {
            levelOfDifficulty = "medium";
        }
        else {
            levelOfDifficulty = "hard";
        }

        if (fileOrWeb == 1) {
            System.out.println("Lets start the game from file with " + numOfQuestions + " questions..");
        }
        else {
            System.out.println("Lets start the game from web with " + numOfQuestions + " " + levelOfDifficulty + " questions..");
        }
        System.out.println();
    }

    void readQuestionsFile() throws Exception {
        File file = new File("src/Quiz");
        Scanner sc = new Scanner(file);
        

        while (sc.hasNextLine()) {//Loeme faili sisu ridade kaupa, kuni rida eksisteerib
            String line = sc.nextLine();//Salvestame praeguse rea String tüüpi muutujasse
            String[] lineaslist = line.split("; ");//Jagame rea semikoolonite kohalt massiiviks
            String oneQuestion = lineaslist[0];//Salvestame küsimuse esimese elemendi
            List<String> answerOptions = new ArrayList<>();//Loome uue ArrayListi vastusteks
            for (int i = 1; i < lineaslist.length - 1; i++) {//Loome tsükli, et lisada kõik vastused vastuste ArrayListi
                answerOptions.add(lineaslist[i]);
            }
            String correctAnswer = lineaslist[(lineaslist.length - 1)].substring(8); //Removing "Answer: " from start of element////Salvestame õige vastuse viimase elemendi ja eemaldame sellest esimesed 8 tähemärki
            Question q = new Question(oneQuestion, answerOptions, correctAnswer);
            questions.add(q);
        }
        Collections.shuffle(questions);//Segame küsimused
        questions = questions.subList(0, numOfQuestions);//Võtame nii palju küsimusi, kui kasutaja soovib
    }

     void readQuestionsURL() throws IOException, JSONException {
        String urlString = "https://the-trivia-api.com/api/questions?limit=" + numOfQuestions + "&region=EE&difficulty=" + levelOfDifficulty;
        URL url = new URL(urlString);
        String jsonString;

        try (InputStream input = url.openStream()) {//Proovime avada ühendust URL-iga
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder jsonText = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                jsonText.append((char) c);
            }
            jsonString =  jsonText.toString(); // küsimuste info stringina
        }

        JSONArray jsonArray = new JSONArray(jsonString);// Loeme küsimused JSONArray objekti

        for (int i = 0; i < jsonArray.length(); i++) {// Käime kõik küsimused läbi
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String question = jsonObject.getString("question"); // loeme sisse küsimuse
            String correctAnswer = jsonObject.getString("correctAnswer"); // loeme sisse õige vastuse
            List<String> answers = new ArrayList<>();
            answers.add(correctAnswer); // lisame õige vastuse vastuste listi
            JSONArray answersJson = jsonObject.getJSONArray("incorrectAnswers"); // loeme sisse valed vastused ja lisame vastuste listi
            if (answersJson != null) {
                for (int j = 0; j < answersJson.length(); j++) {
                    answers.add(answersJson.get(j).toString());
                }
            }
            Collections.shuffle(answers);// kuna õige vastus on vastuste listis alati esimene, siis segame vastused juhuslikku järjekorda
            Question q = new Question(question, answers, correctAnswer); // loome uue küsimuse objekti
            questions.add(q);
        }
         Collections.shuffle(questions); //Shuffeling questions
    }

    void presentQuestions() {//meetod esitab küsimused, võtab kasutajalt vastuseid vastu ja kontrollib nende õigsust.
        int answerIndex;
        Collections.shuffle(questions); //Shuffeling questions
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println(i+1 + "/" + questions.size() + " " + question.getQuestion());
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
            user.addTotalAnswers(1);
            System.out.println();
        }
    }

    void presentAnswers(Question question) {// argumendiks Question objekti ning prindib välja kõik vastusevariantid, mis selle küsimuse juurde kuuluvad
        int i = 1;
        for (String answer : question.getAnswers()) {
            System.out.println(i + ". " + answer);
            i++;
        }
    }

    public int getFileOrWeb() {
        return fileOrWeb;
    }//teave selle kohta, kas kasutaja valis küsimuste saamiseks failist või veebist

    public void setLevelOfDifficulty(String levelOfDifficulty) {
        this.levelOfDifficulty = levelOfDifficulty;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public String getLevelOfDifficulty() {
        return levelOfDifficulty;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
