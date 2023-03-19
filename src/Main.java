import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String input;
        System.out.println("Alustan mängu!");
        Scanner scan = new Scanner(System.in);
        System.out.println("Sisesta oma nimi: ");
        input = scan.nextLine();
        User user1 = new User(input);
        System.out.println("Tere, " + user1.getName() + "!");
        System.out.println("Hello world!");
    }
}