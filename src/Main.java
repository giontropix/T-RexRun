import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice;
        String name;
        ScoreManager scoreManager = new ScoreManager();
        do {
            System.out.println("Premi 1 per giocare, 2 per verificare i punteggi, 0 per uscire");
            Scanner scanChoice = new Scanner(System.in);
            choice = scanChoice.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Insert name");
                    Scanner scanName = new Scanner(System.in);
                    name = scanName.nextLine();
                    PrinterLevel printerLevel = new PrinterLevel();
                    printerLevel.start();
                    do {
                        Scanner scan = new Scanner(System.in);
                        String answer = scan.nextLine();
                        if (answer.equalsIgnoreCase("s")) {
                            if (printerLevel.getTrex().lookForFeetOnTheGround())
                                printerLevel.getTrex().setJump(true);
                        }
                        if(!printerLevel.getFieldOfObstacle().isInGame())
                            System.out.println("HAI PERSO!!!\nPremi INVIO per continuare...");
                    } while (printerLevel.getFieldOfObstacle().isInGame());
                    scoreManager.addScore(new Score(name, printerLevel.getFieldOfObstacle().getScore()));
                    scoreManager.store();
                    break;
                case 2:
                    scoreManager.load();
                    if (scoreManager.getListOfScore().size() == 0)
                        System.out.println("Ancora non sono presenti punteggi in elenco!");
                    else {
                        System.out.println("Ranking List");
                        for (Score score : scoreManager.getListOfScore()) {
                            System.out.println("Player Name: " + score.getPlayerName() +
                                    ", Total Score: " + score.getTotalScore());
                        }
                    }
                    break;
            }
        } while (choice != 0);
    }
}