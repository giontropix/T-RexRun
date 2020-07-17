import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Trex trex = new Trex();
        trex.start();
        do {
            Scanner scan = new Scanner(System.in);
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("s")) {
                trex.setJump(true);
            }
        } while(trex.fieldOFObstacle.isInGame());
    }
}