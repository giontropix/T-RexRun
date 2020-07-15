import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(100);
        game.start();
        Character test = game.generatePalm();
        game.paintCharacter(test);
        System.out.println(game.toString());
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        /*if (answer.equalsIgnoreCase("s")) {
            game.moveObstacle();
            System.out.println(game.toString());
        }*/
    }
}
