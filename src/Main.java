import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(100);
        game.start();
        do {
            Scanner scan = new Scanner(System.in);
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("s")) {
                game.tRexJump();
            }
        } while(game.isInGame);
    }
}
