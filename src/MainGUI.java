import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainGUI extends Application {
    private AnchorPane pane;
    String name = "";
    PrinterLevel game = new PrinterLevel(this.name);
    private final String bgPath = "\\src\\JurassicPark.png";
    private final int GAMEHEIGHT = game.getGridForGUI().length*30;
    private final int GAMEWIDTH = game.getGridForGUI()[0].length*30;


    @Override
    public void start(Stage stage) {
        stage.setTitle("T-Rex Run!");
        createContent(stage);
    }

    private Scene createContent(Stage stage) {
        Group root = new Group(); //ammucchiata di oggetti
        this.pane = new AnchorPane();
        createBackground();
        this.pane.getChildren().addAll(root);
        Scene scene = new Scene(this.pane, this.GAMEWIDTH, this.GAMEHEIGHT);
        ArrayList<Circle> tRexBody = new ArrayList<>();
        ArrayList<Circle> fieldOfPalms = new ArrayList<>();

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

        /*for (int i = 0; i < this.game.getFieldOfObstacle().getPalm().size(); i++) {
            Circle palm = new Circle(20);
            palm.setCenterX(game.getFieldOfObstacle().getPalm().get(i).getX()*50+25);
            palm.setCenterY(game.getFieldOfObstacle().getPalm().get(i).getY()*50+25);
            palm.setFill(Color.GREEN);
            fieldOfPalms.add(palm);
        }
        for (Circle palm:fieldOfPalms) {
            root.getChildren().addAll(palm);
        }*/


        for (int i = 0; i < this.game.getGridForGUI().length; i++) {
            for (int j = 0; j < this.game.getGridForGUI()[i].length; j++) {
                if(this.game.getGridForGUI()[i][j] == 2){
                    Rectangle rectPalm = new Rectangle(50,50);
                    rectPalm.setX(j*50);
                    rectPalm.setY(i*50);
                    rectPalm.setFill(Color.GREEN);
                    root.getChildren().add(rectPalm);
                }
                else if(this.game.getGridForGUI()[i][j] == 3){
                    Rectangle rectBird = new Rectangle(50,50);
                    rectBird.setX(j*50);
                    rectBird.setY(i*50);
                    rectBird.setFill(Color.CYAN);
                    root.getChildren().add(rectBird);
                }
                else if(this.game.getGridForGUI()[i][j] == 4){
                    Rectangle rectGround = new Rectangle(50,50);
                    rectGround.setX(j*50);
                    rectGround.setY(i*50);
                    rectGround.setFill(Color.BROWN);
                    root.getChildren().add(rectGround);
                }
            }
        }
        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle);
        root.getChildren().addAll(keyboardNode);
        stage.setScene(scene);
        stage.show();


        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(game.getFieldOfObstacle().isInGame()) {
                    game.getFieldOfObstacle().generateObstacle();
                    game.getFieldOfObstacle().moveObstacle();
                    for (int i = 0; i < game.getTrex().getTrex().size(); i++) {
                        tRexBody.get(i).setCenterX(game.getTrex().getTrex().get(i).getY()*30+20);
                        tRexBody.get(i).setCenterY(game.getTrex().getTrex().get(i).getX()*30+20);
                    }
                    for (int i = 0; i < game.getFieldOfObstacle().getPalm().size(); i++) {
                        fieldOfPalms.get(i).setCenterX(game.getFieldOfObstacle().getPalm().get(i).getX()*30+20);
                        fieldOfPalms.get(i).setCenterY(game.getFieldOfObstacle().getPalm().get(i).getY()*30+20);
                    }
                }
                else {
                    Text youlose = new Text();
                    youlose.setText("HAI PERSO :)");
                    youlose.setFont(Font.font("Verdana", 40));
                    Random rand = new Random();
                    youlose.setX(rand.nextInt(GAMEWIDTH));
                    youlose.setY(rand.nextInt(GAMEHEIGHT));
                    youlose.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    root.getChildren().add(youlose);
                }
            }
        };
        animator.start();
        return scene;
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
        pane.setBackground(new Background(bgImg));
    }
}
