import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean newGame;
        do{
            Quiz quiz = new Quiz();
            quiz.welcome();
            if (quiz.getFileOrWeb() == 1){
                quiz.startQuizFile();
                quiz.readQuestionsFile();
            }
            else if (quiz.getFileOrWeb() == 2){
                quiz.startQuizURL();
                quiz.readQuestionsURL();
            }
            quiz.presentQuestions();
            quiz.endQuiz();

            System.out.println("Would you like to play again? Y/N");
            String playAgain = scanner.nextLine();
            newGame = playAgain.equalsIgnoreCase("Y");

        } while(newGame);
    }
}