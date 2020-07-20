import java.io.Serializable;

public class Score implements Serializable, Comparable<Score> {
    private final String playerName;
    private final int totalScore;

    public Score(String playerName, int totalScore) {
        this.playerName = playerName;
        this.totalScore = totalScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getTotalScore() {
        return totalScore;
    }

    @Override
    public int compareTo(Score o) {
        return Integer.compare(o.totalScore, this.totalScore);
    }
}