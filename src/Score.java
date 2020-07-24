import java.io.Serializable;

public class Score implements Serializable, Comparable<Score> {
    private final String playerName;
    private final int totalScore;

    public Score(String playerName, int totalScore) {
        this.playerName = playerName;
        this.totalScore = totalScore;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    @Override
    public int compareTo(Score o) {
        if(o.totalScore != this.totalScore)
            return Integer.compare(o.totalScore, this.totalScore);
        else return this.playerName.compareTo(o.playerName);
    }
}