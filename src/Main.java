import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PrinterLevel printerLevel = new PrinterLevel();
        printerLevel.start();
        do {
            Scanner scan = new Scanner(System.in);
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("s")) {
                printerLevel.getTrex().setJump(true);
            }
        } while(printerLevel.getFieldOfObstacle().isInGame());
    }
}