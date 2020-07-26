import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice = 0;
        String name;
        ScoreManager scoreManager = new ScoreManager();
        scoreManager.load();
        do {
            System.out.println("\n\t\t\t\t--------> T-REX RUN! <--------");
            try {
                System.out.println("Press: \n - 1 to play\n - 2 for ranking list\n - 0 to exit");
                Scanner scanChoice = new Scanner(System.in);
                choice = scanChoice.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Wrong value inserted");
            }
            switch (choice) {
                case 1:
                    System.out.println("Insert name");
                    Scanner scanName = new Scanner(System.in);
                    name = scanName.nextLine();
                    PrinterLevel printerLevel = new PrinterLevel(name);
                    printerLevel.start();
                    do {
                        Scanner scan = new Scanner(System.in);
                        String answer = "";
                        try {
                            answer = scan.nextLine();
                        } catch (InputMismatchException e){
                            System.out.println("Wrong value inserted");
                        }
                        if (answer.equalsIgnoreCase("s")) {
                            if (printerLevel.getTrex().lookForFeetOnTheGround()) //TREX CAN JUMP ONLY IF IT'S ON THE GROUND
                                printerLevel.getTrex().setJump(true);
                        }
                        if(!printerLevel.getObstacle().isInGame())
                            System.out.println("HAI PERSO!!!\nPremi INVIO per continuare...");
                    } while (printerLevel.getObstacle().isInGame());
                    scoreManager.getListOfScore().add(new Score(name.toUpperCase(), printerLevel.getObstacle().getScore()));
                    scoreManager.store();
                    break;
                case 2:
                    if (scoreManager.getListOfScore().size() == 0)
                        System.out.println("Ancora non sono presenti punteggi in elenco!");
                    else {
                        System.out.println("Ranking List");
                        int ranking = 1;
                        for (Score score : scoreManager.getListOfScore()) {
                            System.out.println(ranking++ + "° Player, Name: " + score.getPlayerName().toUpperCase() +
                                    ", Total Score: " + score.getTotalScore());
                        }
                    }
                    break;
                case 0:
                    System.out.println("See you for the next game, good bye!");
                    return;
                default:
                    System.out.println("Wrong choose");
            }
        } while (true);
    }
}