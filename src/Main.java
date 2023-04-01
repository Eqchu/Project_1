import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        boolean newGame;
        do {
            Quiz quiz = new Quiz(user);
            user.setScore(0);
            quiz.welcome();
            quiz.startQuiz();
            if (quiz.getFileOrWeb() == 1)
                quiz.readQuestionsFile();
            else
                quiz.readQuestionsURL();
            quiz.presentQuestions();
            System.out.println("Would you like to play again? Y/N");
            String playAgain = scanner.nextLine();
            newGame = playAgain.equalsIgnoreCase("Y");
        } while (newGame);

        System.out.println();
        System.out.println("Great game, " + user.getName() + "! You answered " + user.getTotalScore() + " out of " + user.getTotalAnswers() + " questions correctly.");
    }
}