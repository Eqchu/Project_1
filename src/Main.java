import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        // Loome uue kasutaja objekti ja skänneri kasutaja sisendi lugemiseks
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        boolean newGame;
        // Loome tsükli, et lubada kasutajal mitu Ekorda mängida
        do {
            // Loome uue küsimustiku ja lähtestame kasutaja skoori
            Quiz quiz = new Quiz(user);
            user.setScore(0);
            // Alustame. Tutvustame küsimustikku
            quiz.welcome();
            // Alustame küsimustiku esitamisega
            quiz.startQuiz();
            //Küsime, kas kasutaja soovib küsimused failist või veebist lugeda ja loeme küsimused vastavalt sellele
            if (quiz.getFileOrWeb() == 1)
                quiz.readQuestionsFile();
            else
                quiz.readQuestionsURL();
            // Esitame küsimused ja salvestame kasutaja vastused
            quiz.presentQuestions();
            // Küsime, kas kasutaja soovib uuesti mängida
            System.out.println("Would you like to play again? Y/N");
            String playAgain = scanner.nextLine();
            newGame = playAgain.equalsIgnoreCase("Y");
        } while (newGame);

        // Näitame mängu lõpus, kui palju küsimusi kasutaja õigesti vastas
        System.out.println();
        System.out.println("Great game, " + user.getName() + "! You answered " + user.getTotalScore() + " out of " + user.getTotalAnswers() + " questions correctly.");
    }
}