package term_project_final;

public class RankingEntry {
    public String name;
    public int moveCount;
    public int playSeconds;

    public RankingEntry(String name, int moveCount, int playSeconds) {
        this.name = name;
        this.moveCount = moveCount;
        this.playSeconds = playSeconds;
    }
}