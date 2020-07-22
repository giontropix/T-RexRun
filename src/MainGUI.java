import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MainGUI extends Application {
    private AnchorPane pane;
    String name = "";
    PrinterLevel game = new PrinterLevel(this.name);
    private final String bgPath = "\\src\\JurassicPark.png";
    private final int GAME_HEIGHT = game.getGridForGUI().length*35;
    private final int GAME_WIDTH = game.getGridForGUI()[0].length*30;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("T-Rex Run!");
        createContent(stage);
    }

    private void createContent(Stage stage) {
        game.start();
        Group root = new Group(); //ammucchiata di oggetti
        this.pane = new AnchorPane();
        createBackground();
        this.pane.getChildren().addAll(root);

        Vector<Circle> tRexBody = new Vector<>();
        Vector<Circle> fieldOfPalms = new Vector<>();
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
            Circle palm = new Circle(20);
            palm.setCenterX(game.getFieldOfObstacle().getPalm().get(i).getX() * 30 + 20);
            palm.setCenterY(game.getFieldOfObstacle().getPalm().get(i).getY() * 30 + 20);
            palm.setFill(Color.GREEN);
            fieldOfPalms.add(palm);
        }
        for (Circle palm : fieldOfPalms) {
            root.getChildren().addAll(palm);
        }

        for (int i = 0; i < this.game.getFieldOfObstacle().getBird().size(); i++) {
            Circle bird = new Circle(20);
            bird.setCenterX(game.getFieldOfObstacle().getBird().get(i).getX() * 30 + 20);
            bird.setCenterY(game.getFieldOfObstacle().getBird().get(i).getY() * 30 + 20);
            bird.setFill(Color.CYAN);
            birds.add(bird);
        }
        for (Circle bird : birds) {
            root.getChildren().addAll(bird);
        }

        for (int i = 0; i < this.game.getFieldOfObstacle().getGround().size(); i++) {
            Rectangle ground = new Rectangle(30, 20);
            ground.setX(game.getFieldOfObstacle().getGround().get(i).getX() * 30 + 20);
            ground.setY(game.getFieldOfObstacle().getGround().get(i).getY() * 30 + 20);
            ground.setFill(Color.BROWN);
            grounds.add(ground);
        }
        for (Rectangle ground : grounds) {
            root.getChildren().addAll(ground);
        }


        /*for (int i = 0; i < this.game.getGridForGUI().length; i++) {
            for (int j = 0; j < this.game.getGridForGUI()[i].length; j++) {
                if(game.getGridForGUI()[i][j] == 1){
                    Rectangle rectPalm = new Rectangle(10,50);
                    rectPalm.setX(j*30);
                    rectPalm.setY(i*30);
                    rectPalm.setFill(Color.RED);
                    root.getChildren().add(rectPalm);
                }
                if(this.game.getGridForGUI()[i][j] == 2){
                    Rectangle rectPalm = new Rectangle(50,50);
                    rectPalm.setX(j*30);
                    rectPalm.setY(i*30);
                    rectPalm.setFill(Color.GREEN);
                    root.getChildren().add(rectPalm);
                }
                else if(this.game.getGridForGUI()[i][j] == 3){
                    Rectangle rectBird = new Rectangle(50,50);
                    rectBird.setX(j*30);
                    rectBird.setY(i*30);
                    rectBird.setFill(Color.CYAN);
                    root.getChildren().add(rectBird);
                }
                else if(this.game.getGridForGUI()[i][j] == 4){
                    Rectangle rectGround = new Rectangle(50,50);
                    rectGround.setX(j*30);
                    rectGround.setY(i*30);
                    rectGround.setFill(Color.BROWN);
                    root.getChildren().add(rectGround);
                }
            }
        }*/

        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle);
        root.getChildren().addAll(keyboardNode);
        Scene scene = new Scene(this.pane, this.GAME_WIDTH, this.GAME_HEIGHT);
        stage.setScene(scene);
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);



        new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, 512,512);
                if(game.getFieldOfObstacle().isInGame()) {
                    gc.setFont(Font.font(40));
                    String points = "SCORE: " + game.getFieldOfObstacle().getScore();
                    gc.fillText(points, 20, 50);
                    gc.strokeText(points, 20, 50);
                    /*Text scoreDisplay = new Text();
                    scoreDisplay.setFont(Font.font("Verdana", 30));
                    scoreDisplay.setX(20);
                    scoreDisplay.setY(50);
                    scoreDisplay.setFill(Color.BLACK);
                    scoreDisplay.setFontSmoothingType(FontSmoothingType.LCD);
                    scoreDisplay.setText("SCORE: " + game.getFieldOfObstacle().getScore());
                    root.getChildren().add(scoreDisplay);*/

                    for (int i = 0; i < game.getTrex().getTrex().size(); i++) {
                        tRexBody.get(i).setCenterX(game.getTrex().getTrex().get(i).getY()*30+20);
                        tRexBody.get(i).setCenterY(game.getTrex().getTrex().get(i).getX()*30+20);
                    }

                    if (fieldOfPalms.size() < game.getFieldOfObstacle().getPalm().size()) {
                        Circle newPalm = new Circle(20);
                        newPalm.setFill(Color.GREEN);
                        fieldOfPalms.add(newPalm);
                        root.getChildren().add(newPalm);
                    }
                    for (int i = 0; i < game.getFieldOfObstacle().getPalm().size(); i++) {
                        fieldOfPalms.get(i).setCenterX(game.getFieldOfObstacle().getPalm().get(i).getY() * 30 + 20);
                        fieldOfPalms.get(i).setCenterY(game.getFieldOfObstacle().getPalm().get(i).getX() * 30 + 20);
                    }

                    if(birds.size() < game.getFieldOfObstacle().getBird().size()) {
                        Circle newBird = new Circle(20);
                        newBird.setFill(Color.CYAN);
                        birds.add(newBird);
                        root.getChildren().add(newBird);
                    }
                    for (int i = 0; i < game.getFieldOfObstacle().getBird().size(); i++) {
                        birds.get(i).setCenterX(game.getFieldOfObstacle().getBird().get(i).getY() * 30 + 20);
                        birds.get(i).setCenterY(game.getFieldOfObstacle().getBird().get(i).getX() * 30 + 20);
                    }

                    if(grounds.size() < game.getFieldOfObstacle().getGround().size()) {
                        Rectangle newGround = new Rectangle(30, 20);
                        newGround.setFill(Color.BROWN);
                        grounds.add(newGround);
                        root.getChildren().add(newGround);
                    }
                    for (int i = 0; i < game.getFieldOfObstacle().getGround().size(); i++) {
                        grounds.get(i).setX(game.getFieldOfObstacle().getGround().get(i).getY() * 30 + 20);
                        grounds.get(i).setY(game.getFieldOfObstacle().getGround().get(i).getX() * 30 + 20);
                    }

                    /*for (int i = 0; i < game.getGridForGUI().length; i++) {
                        for (int j = 0; j < game.getGridForGUI()[i].length; j++) {
                            if(game.getGridForGUI()[i][j] == 1){
                                Rectangle rectPalm = new Rectangle(10,50);
                                rectPalm.setX(j*30);
                                rectPalm.setY(i*30);
                                rectPalm.setFill(Color.RED);
                                root.getChildren().add(rectPalm);
                            }
                            else if(game.getGridForGUI()[i][j] == 2){
                                Rectangle rectPalm = new Rectangle(50,50);
                                rectPalm.setX(j*30);
                                rectPalm.setY(i*30);
                                rectPalm.setFill(Color.GREEN);
                                root.getChildren().add(rectPalm);
                            }
                            else if(game.getGridForGUI()[i][j] == 3){
                                Rectangle rectBird = new Rectangle(50,50);
                                rectBird.setX(j*30);
                                rectBird.setY(i*30);
                                rectBird.setFill(Color.CYAN);
                                root.getChildren().add(rectBird);
                            }
                            else if(game.getGridForGUI()[i][j] == 4){
                                Rectangle rectGround = new Rectangle(50,50);
                                rectGround.setX(j*30);
                                rectGround.setY(i*30);
                                rectGround.setFill(Color.BROWN);
                                root.getChildren().add(rectGround);
                            }
                            else {
                                Rectangle rectGround = new Rectangle(50,50);
                                rectGround.setX(j*30);
                                rectGround.setY(i*30);
                                rectGround.setFill(Color.TRANSPARENT);
                                root.getChildren().add(rectGround);
                            }
                        }
                    }*/



                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Text youlose = new Text();
                    youlose.setText("HAI PERSO :)");
                    youlose.setFont(Font.font("Verdana", 40));
                    Random rand = new Random();
                    youlose.setX(rand.nextInt(GAME_WIDTH));
                    youlose.setY(rand.nextInt(GAME_HEIGHT));
                    youlose.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    root.getChildren().add(youlose);
                }
            }
        }.start();
        stage.show();
    }

    public void handle(KeyEvent arg0) {
        if (game.getFieldOfObstacle().isInGame()) {
            game.getTrex().setJump(arg0.getCode() == KeyCode.SPACE);
        }
    }

    private String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }

    private void createBackground(){
        System.out.println(bgPath);
        System.out.println(createFilePath(bgPath));
        File file = new File(createFilePath(bgPath));
        javafx.scene.image.Image img = new Image(file.getAbsoluteFile().toURI().toString());
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        //pane.setBackground(new Background(bgImg));
    }
}
