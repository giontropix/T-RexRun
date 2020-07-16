import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Game game = new Game(100);
        Trex trex = new Trex();
        //game.start();
        trex.start();
        do {
            Scanner scan = new Scanner(System.in);
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("s")) {
                trex.setJump(true);
            }
        } while(trex.game.isInGame());
    }
}