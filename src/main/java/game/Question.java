package game;
import java.util.List;
public class Question {
    // Defineerime küsimuse, vastusevariantide ja õige vastuse muutujad
    private String question;// küsimus
    private List<String> answers;//vastused
    private final String correctAnswer;//õige vastus


    // Konstruktor, mida kasutatakse uue küsimuse loomisel
    Question (String question, List<String> answers, String correctAnswer) {
        // Määrame antud küsimuse ja vastusevariandid
        this.question = question;
        this.answers = answers;
        // Määrame õige vastuse
        this.correctAnswer = correctAnswer;
    }
    //meetod küsimuse tagastamiseks
    public String getQuestion() {
        return question;
    }


    //meetod vastusevariantide tagastamiseks
    public List<String> getAnswers() {
        return answers;
    }

    //meetod õige vastuse tagastamiseks
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
