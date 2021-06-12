package saperPackage;

import java.util.StringTokenizer;

/** Reprezentuje pojedynczy element rankingu
 * @author Adrian Rubak
 */
public class RankingElement {

    private String username;
    private int score;
    private int boardSize;
    private int numberOfBombs;
    private int seconds;


    /**
     * Ustawia nazwe uzytkowniaka
     * @param username - nowe imie uzytkownika
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Ustawia ilosc punktow gracza
     * @param score - ilosc punktów
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Ustawia rozmiar planczy na ktorej gral gracz
     * @param boardSize - rozmiar planszy
     */
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    /**
     * Ustawia liczbe bomb ktore mial do odkrycia gracz
     * @param numberOfBombs - liczba bomb
     */
    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }

    /**
     * Ustawia czas w jakim gracz zakonczyl rozgrywke
     * @param seconds - sekundy
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Konstruktor tworzacy element rankingu i inicjujacy pola
     * @param username - nazwa gracza
     * @param score - punkty
     * @param boardSize - rozmair planszy
     * @param numberOfBombs - liczba bomb
     * @param seconds - czas
     */
    public RankingElement(String username, int score, int boardSize, int numberOfBombs, int seconds) {
        this.username = username;
        this.score = score;
        this.boardSize = boardSize;
        this.numberOfBombs = numberOfBombs;
        this.seconds = seconds;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public RankingElement() {
    }

    /**
     * Wykorzystywana podczas odczytywania z pliku
     * Zapisuje pojedynczy wiersz do rankingu
     * Detokenizujac pobrana z pliku linie
     * @param line - linia odczytana z pliku
     * @param separator - separator rozdzielajacy wartosci
     * @return element rankingu
     */
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

    /**
     * Tokenizuje pojedynczy element rankinu (Pojedyncze wartosci rozdziela separatorem)
     * wykorzystywana podczas zapisu do pliku
     * @param rankingElement - pojedynczy element rankingu
     * @return linie ktora bedzie zapisywana do pliku
     */
    public static String tokenize(RankingElement rankingElement){
        return new String(rankingElement.getUsername() + ";" + rankingElement.getScore() + ";" + rankingElement.getBoardSize() + ";" +
                rankingElement.getNumberOfBombs() + ";" + rankingElement.getSeconds() + "\n");
    }

    /**
     * Zwraca liczbe punktow danego gracza
     * @return - liczba punktow
     */
    public int getScore() {
        return score;
    }

    /**
     * Zwraca rozmiar planczy danego elementu z rankinngu
     * @return - rozmiar planczy (ilosc pol)
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Zwraca liczbe bomb
     * @return - liczba bomb
     */
    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    /**
     * Zwraca czas
     * @return - czas w sekundach
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Zwraca nazwe gracza
     * @return - nazwa gracza
     */
    public String getUsername() {
        return username;
    }

}
