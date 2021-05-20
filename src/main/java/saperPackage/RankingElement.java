package saperPackage;

import java.util.StringTokenizer;

public class RankingElement {

    private String username;
    private int score;
    private int boardSize;
    private int numberOfBombs;
    private int seconds;

    private static final int NUMBER_OF_TOKENS = 5;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public RankingElement(String username, int score, int boardSize, int numberOfBombs, int seconds) {
        this.username = username;
        this.score = score;
        this.boardSize = boardSize;
        this.numberOfBombs = numberOfBombs;
        this.seconds = seconds;
    }

    public RankingElement() {
    }

    public static RankingElement deTokenize(String line, String separator){
        RankingElement tmp = new RankingElement();
        StringTokenizer tokenizer = new StringTokenizer(line, separator);
        tmp.setUsername(tokenizer.nextToken());
        tmp.setScore(Integer.parseInt(tokenizer.nextToken()));
        tmp.setBoardSize(Integer.parseInt(tokenizer.nextToken()));
        tmp.setNumberOfBombs(Integer.parseInt(tokenizer.nextToken()));
        tmp.setSeconds(Integer.parseInt(tokenizer.nextToken()));
        return tmp;
    }

    public static String tokenize(RankingElement rankingElement){
        return new String(rankingElement.getUsername() + ";" + rankingElement.getScore() + ";" + rankingElement.getBoardSize() + ";" +
                rankingElement.getNumberOfBombs() + ";" + rankingElement.getSeconds() + "\n");
    }

    public int getScore() {
        return score;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int getSeconds() {
        return seconds;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RankingElement{" +
                "username='" + username + '\'' +
                ", score=" + score +
                ", boardSize=" + boardSize +
                ", numberOfBombs=" + numberOfBombs +
                ", seconds=" + seconds +
                "}\n";
    }
}
