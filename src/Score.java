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
    public int compareTo(Score o) { //OVERRIDED TO HAVE A RANKING LIST ORDERED BY SCORE AND IN CASE OF SAME SCORE ORDERED BY NAME
        if(o.totalScore != this.totalScore)
            return Integer.compare(o.totalScore, this.totalScore);
        else return this.playerName.compareTo(o.playerName);
    }

    @Override
    public boolean equals(Object point){ //OVERRIDED BECAUSE WE NEED TO RECOGNISE THE LAST GAMER TO HAVE HIS RANKING POSITION IN GUIMANAGER
        if( point instanceof Score){
            Score p = (Score) point;
            return this.playerName.equals(p.playerName) && this.totalScore == p.totalScore;
        } else return false;
    }
}