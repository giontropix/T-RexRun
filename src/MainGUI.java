import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MainGUI extends Application {
    Group root = new Group();
    Canvas canvas = new Canvas(500, 500);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    String name = "";
    PrinterLevel game = new PrinterLevel(this.name);
    ScoreManager score = new ScoreManager();
    private final double GAME_HEIGHT = game.getFieldOfObstacle().getFieldHeight()*35;
    private final double GAME_WIDTH = game.getFieldOfObstacle().getFieldWidth()*30;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("T-Rex Run!");
        score.load();
        createContent(stage);
    }

    private VBox menu(){
        final Menu menuOptions = new Menu("Options");
        MenuItem subMenuNewGame = new MenuItem("New Game");
        MenuItem subMenuRanking = new MenuItem("Ranking");
        subMenuRanking.setOnAction(e -> {
            int ranking = 0;
            for (Score score : score.getListOfScore()) {
                gc.fillText(ranking++ + "° Player, Name: " + score.getPlayerName().toUpperCase() +
                        ", Total Score: " + score.getTotalScore(), 50, 50);
            }
        });
        menuOptions.getItems().add(subMenuNewGame);
        menuOptions.getItems().add(subMenuRanking);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuOptions);
        return new VBox(menuBar);
    }

    private void createContent(Stage stage) {
        game.start();

        root.getChildren().add(canvas);

        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle);
        root.getChildren().addAll(keyboardNode);

        root.getChildren().add(menu());




        Vector<Circle> tRexBody = new Vector<>();
        Vector<Rectangle> fieldOfPalms = new Vector<>();
        Vector<Circle> birds = new Vector<>();
        Vector<Rectangle> grounds = new Vector<>();

        for (int i = 0; i < this.game.getTrex().getTrex().size(); i++) {
            Circle trex = new Circle(20);
            trex.setCenterX(game.getTrex().getTrex().get(i).getY()*30+20);//COME SE COMPENSASSIMO IL RAGGIO
            trex.setCenterY(game.getTrex().getTrex().get(i).getX()*30+20);
            trex.setFill(Color.RED);
            tRexBody.add(trex);
        }
        for (Circle trex:tRexBody) {
            root.getChildren().addAll(trex);
        }

        for (int i = 0; i < this.game.getFieldOfObstacle().getPalm().size(); i++) {
            Rectangle palm = new Rectangle(10, 40);
            palm.setX(game.getFieldOfObstacle().getPalm().get(i).getX() * 30 + 20);
            palm.setY(game.getFieldOfObstacle().getPalm().get(i).getY() * 30 + 20);
            palm.setFill(Color.GREEN);
            fieldOfPalms.add(palm);
        }
        for (Rectangle palm : fieldOfPalms) {
            root.getChildren().addAll(palm);
        }

        for (int i = 0; i < this.game.getFieldOfObstacle().getBird().size(); i++) {
            Circle bird = new Circle(15);
            bird.setCenterX(game.getFieldOfObstacle().getBird().get(i).getX() * 30 + 10);
            bird.setCenterY(game.getFieldOfObstacle().getBird().get(i).getY() * 30 + 10);
            bird.setFill(Color.CYAN);
            birds.add(bird);
        }
        for (Circle bird : birds) {
            root.getChildren().addAll(bird);
        }

        for (int i = 0; i < this.game.getFieldOfObstacle().getGround().size(); i++) {
            Rectangle ground = new Rectangle(30, 10);
            ground.setX(game.getFieldOfObstacle().getGround().get(i).getX() * 30 + 20);
            ground.setY(game.getFieldOfObstacle().getGround().get(i).getY() * 30 + 20);
            ground.setFill(Color.BROWN);
            grounds.add(ground);
        }
        for (Rectangle ground : grounds) {
            root.getChildren().addAll(ground);
        }

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, 512,512);
                if(game.getFieldOfObstacle().isInGame()) {
                    gc.setFont(Font.font(20));
                    gc.fillText("PLAYER NAME: ", 20, 50);
                    gc.strokeText("PLAYER NAME: ", 20, 50);
                    gc.fillText("SCORE: " + game.getFieldOfObstacle().getScore(), 20, 70);
                    gc.strokeText("SCORE: " + game.getFieldOfObstacle().getScore(), 20, 70);

                    for (int i = 0; i < game.getTrex().getTrex().size(); i++) {
                        tRexBody.get(i).setCenterX(game.getTrex().getTrex().get(i).getY()*30+20);
                        tRexBody.get(i).setCenterY(game.getTrex().getTrex().get(i).getX()*30+20);
                    }

                    if (fieldOfPalms.size() < game.getFieldOfObstacle().getPalm().size()) {
                        while (fieldOfPalms.size() < game.getFieldOfObstacle().getPalm().size()) {
                            Rectangle newPalm = new Rectangle(10, 40);
                            newPalm.setFill(Color.GREEN);
                            fieldOfPalms.add(newPalm);
                            root.getChildren().add(newPalm);
                        }
                    }
                    for (int i = 0; i < game.getFieldOfObstacle().getPalm().size(); i++) {
                        fieldOfPalms.get(i).setX(game.getFieldOfObstacle().getPalm().get(i).getY() * 30 + 20);
                        fieldOfPalms.get(i).setY(game.getFieldOfObstacle().getPalm().get(i).getX() * 30 + 20);
                    }

                    if(birds.size() < game.getFieldOfObstacle().getBird().size()) {
                        Circle newBird = new Circle(15);
                        newBird.setFill(Color.CYAN);
                        birds.add(newBird);
                        root.getChildren().add(newBird);
                    }
                    for (int i = 0; i < game.getFieldOfObstacle().getBird().size(); i++) {
                        birds.get(i).setCenterX(game.getFieldOfObstacle().getBird().get(i).getY() * 30 + 20);
                        birds.get(i).setCenterY(game.getFieldOfObstacle().getBird().get(i).getX() * 30 + 20);
                    }

                    if(grounds.size() < game.getFieldOfObstacle().getGround().size()) {
                        Rectangle newGround = new Rectangle(30, 10);
                        newGround.setFill(Color.BROWN);
                        grounds.add(newGround);
                        root.getChildren().add(newGround);
                    }
                    for (int i = 0; i < game.getFieldOfObstacle().getGround().size(); i++) {
                        grounds.get(i).setX(game.getFieldOfObstacle().getGround().get(i).getY() * 30 + 20);
                        grounds.get(i).setY(game.getFieldOfObstacle().getGround().get(i).getX() * 30 + 20);
                    }

                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    gc.fillText("HAI PERSO!!!", GAME_HEIGHT/2, GAME_WIDTH/2);
                    gc.strokeText("HAI PERSO!!!", GAME_HEIGHT/2, GAME_WIDTH/2);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scene scene = new Scene(root, this.GAME_WIDTH, this.GAME_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void handle(KeyEvent arg0) {
        if (game.getFieldOfObstacle().isInGame() && game.getTrex().lookForFeetOnTheGround()) {
            game.getTrex().setJump(arg0.getCode() == KeyCode.SPACE);
        }
    }
}
