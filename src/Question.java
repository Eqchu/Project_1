import java.util.List;
import java.util.ArrayList;
public class Question {
    private String question;
    private List<String> answers;
    private String correctAnswer;


    Question (String question, List<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
    public String getQuestion() {
        return question;
    }


    public List<String> getAnswers() {
        return answers;
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
