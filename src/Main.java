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
                    } while (printerLevel.getFieldOfObstacle().isInGame());
                    System.out.println("HAI PERSO!!!\nPremi INVIO per continuare...");
                    scoreManager.addScore(new Score(name, printerLevel.getFieldOfObstacle().getScore(), printerLevel.getFieldOfObstacle().getDistance()));
                    scoreManager.store();
                    break;
                case 2:
                    scoreManager.load();
                    if (scoreManager.getListOfScore().size() == 0)
                        System.out.println("Ancora non sono presenti punteggi in elenco!");
                    else
                        for (int i = 0; i < scoreManager.getListOfScore().size(); i++) {
                            System.out.println("Player Name: " + scoreManager.getListOfScore().get(i).getPlayerName() + ", Distance: "
                                    + scoreManager.getListOfScore().get(i).getDistance() + ", Points: "
                                    + scoreManager.getListOfScore().get(i).getPoint() + ", Total Score: " +
                                    scoreManager.getListOfScore().get(i).getTotalScore());
                        }
                    break;
            }
        } while (choice != 0);
    }
}