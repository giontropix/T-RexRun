import java.io.Serializable;

public class Score implements Serializable {
    private String playerName;
    private int distance;
    private int point;

    public Score(String playerName, int distance, int point) {
        this.playerName = playerName;
        this.distance = distance;
        this.point = point;
    }

    public int getTotalScore(){
        return this.distance + this.point;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getDistance() {
        return distance;
    }

    public int getPoint() {
        return point;
    }
}