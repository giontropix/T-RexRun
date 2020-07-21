import java.io.*;
import java.util.TreeSet;

public class ScoreManager {
    private TreeSet<Score> listOfScore = new TreeSet<>();
    private final String path = "\\src\\score.txt";

    public ScoreManager(){
    }

    private String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }

    public TreeSet<Score> getListOfScore() {
        return listOfScore;
    }

    public void store() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.createFilePath(this.path)));
            outputStream.writeObject(this.listOfScore);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.createFilePath(this.path)));
            this.listOfScore.clear();
            this.listOfScore = (TreeSet<Score>)inputStream.readObject();
            inputStream.close();
        } catch (EOFException eofException) {
            System.out.println("Fine della lettura del file");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
