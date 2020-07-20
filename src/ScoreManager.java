import java.io.*;
import java.util.ArrayList;

public class ScoreManager {
    private ArrayList<Score> listOfScore = new ArrayList<>();
    private final String path = "\\src\\score.txt";

    public ScoreManager(){

    }

    public void addScore(Score score){
        this.listOfScore.add(score);
    }

    public ArrayList<Score> getListOfScore() {
        return listOfScore;
    }

    private String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }

    public void store() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.createFilePath(this.path)));
            outputStream.writeObject(listOfScore);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.createFilePath(this.path)));
            this.listOfScore = (ArrayList<Score>) inputStream.readObject();
            inputStream.close();
        } catch (EOFException eofException) {
            System.out.println("Fine della lettura del file");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
