package saperPackage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/** Reprezentuje ranking graczy
 * @author Adrian Rubak
 */
public class Ranking {
    private List<RankingElement> listaRankingowa;

    /**
     * Stala uzywana podczas wykonywania sortownia (sortownia wg. punktow)
     */
    public static final int BY_SCORE = 1;
    /**
     * Stala uzywana podczas wykonywania sortownia (sortownia wg. rozmiaru planszy)
     */
    public static final int BY_BORDER_SIZE = 2;
    /**
     * Stala uzywana podczas wykonywania sortownia (sortownia wg. ilosci bomb)
     */
    public static final int BY_NUMBER_OF_BOMBS = 3;
    /**
     * Stala uzywana podczas wykonywania sortownia (sortownia wg. czasu)
     */
    public static final int BY_TIME = 4;

    /**
     * Konstruktor bezparametrowy
     */
    public Ranking() {
        listaRankingowa = new LinkedList<RankingElement>();
    }

    /**
     * Dodaje element do rankingu
     * @param rankingElement element rankingu (obiekt klasy ranking)
     */
    public void addElement(RankingElement rankingElement){
        listaRankingowa.add(rankingElement);
    }

    /**
     * Czyta ranking z pliku o podanej nazwie
     * @param filename nazwa pliku
     */
    public void loadFromFile(String filename){
        FileReader fileReader;
        Scanner scanner;
        try {
            fileReader = new FileReader(filename);
            scanner = new Scanner(fileReader);

            while (scanner.hasNextLine()){
                this.addElement(RankingElement.deTokenize(scanner.nextLine(), ";"));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zapisuje ranking do pliku o podanej nazwie
     * @param fileName - nazwa pliku
     * @return FileWriter objekt
     */
    public FileWriter saveToFile(String fileName){
        FileWriter file = null;
        try {
            file = new FileWriter(fileName);
            for(int i = 0; i < this.getListaRankingowa().size(); i++){
                RankingElement listElement = this.listaRankingowa.get(i);
                file.write(RankingElement.tokenize(listElement));
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Sortownie rankingu
     * @param option pole po którym będziemy sortować
     * @param growing true - sortowanie rosnące, false - sortowanie malejąca
     */
    public void selectionSort(int option, boolean growing){
        int tmp;
        for(int i = 0; i < listaRankingowa.size(); i++){
            tmp = i;
            for(int j = i + 1; j < listaRankingowa.size(); j++) {

                if(!growing) {
                    if (option == this.BY_SCORE && listaRankingowa.get(j).getScore() > listaRankingowa.get(tmp).getScore())
                        tmp = j;
                    if (option == this.BY_BORDER_SIZE && listaRankingowa.get(j).getBoardSize() > listaRankingowa.get(tmp).getBoardSize())
                        tmp = j;
                    if (option == this.BY_NUMBER_OF_BOMBS && listaRankingowa.get(j).getNumberOfBombs() > listaRankingowa.get(tmp).getNumberOfBombs())
                        tmp = j;
                    if (option == BY_TIME && listaRankingowa.get(j).getSeconds() > listaRankingowa.get(tmp).getSeconds())
                        tmp = j;
                }else{
                    if (option == this.BY_SCORE && listaRankingowa.get(j).getScore() < listaRankingowa.get(tmp).getScore())
                        tmp = j;
                    if (option == this.BY_BORDER_SIZE && listaRankingowa.get(j).getBoardSize() < listaRankingowa.get(tmp).getBoardSize())
                        tmp = j;
                    if (option == this.BY_NUMBER_OF_BOMBS && listaRankingowa.get(j).getNumberOfBombs() < listaRankingowa.get(tmp).getNumberOfBombs())
                        tmp = j;
                    if (option == BY_TIME && listaRankingowa.get(j).getSeconds() < listaRankingowa.get(tmp).getSeconds())
                        tmp = j;
                }
            }
            swap(tmp, i);
        }
    }

    /**
     * Zamiana elementow w rankingu (wykorzystywane w sortowaniu)
     * @param indexA index pierwszego elementu
     * @param indexB index drugiego elementu
     */
    private void swap(int indexA, int indexB){
        RankingElement tmp;
        tmp = listaRankingowa.get(indexA);
        listaRankingowa.set(indexA, listaRankingowa.get(indexB));
        listaRankingowa.set(indexB, tmp);
    }

    /**
     * Getter zwracajacy liste rankingowa
     * @return zwraca ranking
     */
    public List<RankingElement> getListaRankingowa() {
        return listaRankingowa;
    }
}
