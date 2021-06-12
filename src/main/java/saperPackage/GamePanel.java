package saperPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Timer;
import java.util.Date;

/**
 * Klasa reprezentujaca panel gry
 */
public class GamePanel extends JPanel {
    public static int counterPink=0;
    JPanel topPanel = new JPanel();
    public int secondTime=0;
    JPanel mainBoardPanel = new JPanel();
    public Stopwatch timeLabel;
    public JButton stopGame = new JButton("Stop");
    public JButton pauseGame = new JButton("Pause");
    public JButton startGame = new JButton("Start");
    public JButton closeGame = new JButton("Close");
    public JLabel numberOfflags = new JLabel();
    public int numflags;
    public int numBombs;
    public boolean checkStatus = false;
    public boolean isGameOver = false;
    private boolean afterFirstClick =false;
    private int numberOfFieldsSqrt;
    private int numberOfFields;
    private String playerNickname;
    private JLabel nickLabel;


    public static JLabel scoreValue = new JLabel ("Score: " + counterPink, SwingConstants.LEFT);

    GamePanel thisPanel = this;
    Field[] fields;

    /**
     * Konstuktor
     * @param parent - referencja do obiektu ramki
     * @param numberOfFields - liczba pol
     * @param bombsNumber - liczba bomb
     * @param playerNickname - nick gracza
     */
    public GamePanel(MainFrame parent, int numberOfFields, int bombsNumber, String playerNickname) {
        this.playerNickname = playerNickname;
        timeLabel = new Stopwatch();
        parent.setSize(655, 838);
        this.setBackground(Color.gray);
        this.setLayout(null);
        parent.getContentPane().add(this);
        this.numberOfFields = numberOfFields;
        numberOfFieldsSqrt = (int)Math.sqrt(numberOfFields);
        scoreValue.setText("Score: 0");
        fields = new Field[numberOfFields];
        numBombs =bombsNumber;
        numflags = bombsNumber;
        mainBoardPanel.setLayout(new GridLayout((int) Math.sqrt(numberOfFields), (int) Math.sqrt(numberOfFields)));
        nickLabel = new JLabel("Nick: " + playerNickname);
        nickLabel.setBounds(0, 10, 200, 40);
        nickLabel.setForeground(Color.white);
        nickLabel.setFont(new Font("Verdana", Font.PLAIN, 22));

        this.add(nickLabel);


        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Field(timeLabel, thisPanel, i, numberOfFields, bombsNumber);
            mainBoardPanel.add(fields[i]);
        }

        numberOfflags.setText("Flags: " + numflags);

        mainBoardPanel.setBounds(0,160, 640,640);
        mainBoardPanel.setBackground(Color.darkGray);
        this.add(mainBoardPanel);

        topPanel.setBounds(0,0,640,160);
        topPanel.setBackground(Color.darkGray);
        topPanel.setLayout(null);
        topPanel.add(scoreValue);
        topPanel.add(numberOfflags);
        pauseGame.setBounds(topPanel.getWidth()/2-50,10,100,30);
        stopGame.setBounds(topPanel.getWidth()/2-50,60,100,30);
        startGame.setBounds(topPanel.getWidth()/2-50,10,100,30);
        closeGame.setBounds(topPanel.getWidth()/2-50,110,100,30);

        startGame.setVisible(false);
        closeGame.setVisible(false);
        this.add(topPanel);
        scoreValue.setForeground(Color.white);
        scoreValue.setFont(new Font("Verdana", Font.PLAIN, 22));
        scoreValue.setBounds(0,130,200,30);

        numberOfflags.setForeground(Color.white);
        numberOfflags.setFont(new Font("Verdana", Font.PLAIN, 22));
        numberOfflags.setBounds(0,70,200,30);


        topPanel.add(timeLabel);
        topPanel.add(stopGame);
        topPanel.add(pauseGame);
        topPanel.add(startGame);
        topPanel.add(closeGame);

//        generateBombs(fields,bombsNumber); //Tymczasowo 2 parametr

//        for(int i = 0; i < fields.length; i++)
//            fields[i].setValue(checkNeighbor(fields, i));

        parent.validate();
        parent.repaint();

        pauseGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                timeLabel.tier.stop();
                pauseGame.setVisible(false);
                startGame.setVisible(true);
                checkStatus = true;
            }
        });


        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.tier.start();
                pauseGame.setVisible(true);
                startGame.setVisible(false);
                checkStatus = false;
            }
        });

        stopGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGameOver();
                setCloseGame(false);
            }
        });

        closeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getContentPane().remove(thisPanel);
                new MenuPanel(parent);
                counterPink=0;

            }
        });

    }

    /**
     * Rozmieszcza bomby na planszy
     * @param fields - tablica pol
     * @param number_of_bombs - ilosc bomb do rozmieszczenia
     */
    public static void generateBombs(Field[] fields, int number_of_bombs){
        Random random = new Random();
        int randValue;
        for(int i = 0; i < number_of_bombs; i++){
            while (true){
                randValue = random.nextInt(fields.length);
                if(fields[randValue].isBomb() || fields[randValue].isCanBeBomb() == false){
                    continue;
                }else{
                    fields[randValue].setBomb(true);
                    break;
                }
            }
        }
    }

    /**
     * Sprawdza ile bomb jest wokol danego pola
     * @param fields - tablica pol
     * @param index - index konkretnego pola w tablicy
     * @return liczba bomb wokol danego pola
     */
    public static int checkNeighbor(Field[] fields, int index){
        int lengthSqrt = (int)Math.sqrt(fields.length);
        int counter = 0;

        if(fields[index].isBomb())
            return -1;

        //Po prawej
        if( (index+1 < fields.length) && !isInLastColumn(index, fields.length) && fields[index+1].isBomb())
            counter++;

        //Po lewej
        if((index % lengthSqrt) != 0 && fields[index-1].isBomb())
            counter++;

        //Dół
        if((index + lengthSqrt) < fields.length && fields[index+lengthSqrt].isBomb())
            counter++;

        //Góra
        if((index - lengthSqrt) >= 0 && fields[index-lengthSqrt].isBomb())
            counter++;

        //Dół prawo
        if((index + lengthSqrt+1) < fields.length && !isInLastColumn(index, fields.length) && fields[index+lengthSqrt+1].isBomb())
            counter++;

        //Doł lewo
        if(index % lengthSqrt != 0  && (index + lengthSqrt-1) < fields.length && fields[index+lengthSqrt-1].isBomb())
            counter++;

        //Góra prawo
        if((index - lengthSqrt+1) >= 0 && !isInLastColumn(index, fields.length) && fields[index-lengthSqrt+1].isBomb())
            counter++;

        //Góra lewo
        if((index - lengthSqrt-1) >= 0 && index % lengthSqrt != 0  && fields[index-lengthSqrt-1].isBomb())
            counter++;

        return counter;
    }

    /**
     * Sprawdza czy index jest w ostatniej kolumnie na plancszy z polami
     * @param index - index
     * @param length - length
     * @return true - jestli index jest w ostatniej kolumnie, false jesli nie jest w ostatniej
     */
    public static boolean isInLastColumn(int index, int length){
        for(int i = (int)Math.sqrt(length)-1; i < length; i += (int)Math.sqrt(length))
            if(index == i)
                return true;
        return false;
    }

    /**
     * Sprawdza czy gra zostala zakonczona
     * @return true - jesli gra zostala zakonczona, false - jesli gra nadal trwa
     */
    public boolean isGameOver() { return isGameOver; }

    /**
     * Ustawia czy gra zostala zakonczona
     * @param gameOver - wartosc boolean informujaca o tym czy gra zostala zakonczona
     */
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setCloseGame(boolean check){

        System.out.println(timeLabel.getTimeInSeconds());

        RankingPanel.ranking.addElement(new RankingElement(playerNickname, counterPink, numberOfFields, numBombs, timeLabel.getTimeInSeconds()));
        RankingPanel.ranking.saveToFile("src/main/java/saperPackage/ranking.csv");
        System.out.println(RankingPanel.ranking.toString());


        timeLabel.tier.stop();

        closeGame.setVisible(true);
        startGame.setVisible(false);
        pauseGame.setVisible(false);
        checkStatus= true;
        System.out.println("Score: " + GamePanel.counterPink);

        if(check==false)
        {
            Field.msgbox("Koniec gry");
            drawAllFields();

        }
        if (check==true)
        {
            Field.msgbox("Koniec gry! Wygrales, twój wynik: " + GamePanel.counterPink);
            drawAllFields();

        }

    }

    /**
     * Funkcja odpowiadająca za co że pierwszym klikiem nie możemy trafić na bombe
     * @param index - index pola w które klikamy
     */
    public void firstClick(int index){
        int lengthSqrt = (int)Math.sqrt(fields.length);

        fields[index].setCanBeBomb(false);

        //Po prawej
        if( (index+1 < fields.length) && !isInLastColumn(index, fields.length))
            fields[index+1].setCanBeBomb(false);

        //Po lewej
        if((index % lengthSqrt) != 0)
            fields[index-1].setCanBeBomb(false);

        //Dół
        if((index + lengthSqrt) < fields.length)
            fields[index + lengthSqrt].setCanBeBomb(false);

        //Góra
        if((index - lengthSqrt) >= 0)
            fields[index - lengthSqrt].setCanBeBomb(false);

        //Dół prawo
        if((index + lengthSqrt+1) < fields.length && !isInLastColumn(index, fields.length))
            fields[index + lengthSqrt + 1].setCanBeBomb(false);

        //Doł lewo
        if(index % lengthSqrt != 0  && (index + lengthSqrt-1) < fields.length)
            fields[index + lengthSqrt - 1].setCanBeBomb(false);

        //Góra prawo
        if((index - lengthSqrt+1) >= 0 && !isInLastColumn(index, fields.length))
            fields[index - lengthSqrt + 1].setCanBeBomb(false);

        //Góra lewo
        if((index - lengthSqrt-1) >= 0 && index % lengthSqrt != 0)
            fields[index - lengthSqrt - 1].setCanBeBomb(false);

        generateBombs(fields, numBombs);

        for(int i = 0; i < fields.length; i++)
            fields[i].setValue(checkNeighbor(fields, i));
    }

    /**
     * Getter informujacy o tym czy nastapilo juz pierwsze klikniecie
     * @return true - jesli nastapilo, false - jesli nie nastapilo
     */
    public boolean isAfterFirstClick() {
        return afterFirstClick;
    }

    /**
     * Funkcja rekurencyjna odkrywajaca sasiednie pola
     * do momentu kiedy napotka pole ktore ma wartosc rozna od zera
     * @param index - index pola w ktore klikamy
     */
    public void selectEmptyFields(int index){

        if(fields[index].isChecked() || fields[index].isHasFlag()){
            return;
        }

        fields[index].setChecked(true);
        fields[index].drawField();

        if(fields[index].getValue() != 0){
            return;
        }

        //Po prawej
        if( (index+1 < fields.length) && !isInLastColumn(index, fields.length))
            selectEmptyFields(index+1);

        //Po lewej
        if((index % numberOfFieldsSqrt) != 0)
            selectEmptyFields(index-1);

        //Dół
        if((index + numberOfFieldsSqrt) < fields.length)
            selectEmptyFields(index + numberOfFieldsSqrt);

        //Góra
        if((index - numberOfFieldsSqrt) >= 0)
            selectEmptyFields(index - numberOfFieldsSqrt);

        //Dół prawo
        if((index + numberOfFieldsSqrt+1) < fields.length && !isInLastColumn(index, fields.length))
            selectEmptyFields(index + numberOfFieldsSqrt + 1);

        //Doł lewo
        if(index % numberOfFieldsSqrt != 0  && (index + numberOfFieldsSqrt-1) < fields.length)
            selectEmptyFields(index + numberOfFieldsSqrt - 1);

        //Góra prawo
        if((index - numberOfFieldsSqrt+1) >= 0 && !isInLastColumn(index, fields.length))
            selectEmptyFields(index - numberOfFieldsSqrt + 1);

        //Góra lewo
        if((index - numberOfFieldsSqrt-1) >= 0 && index % numberOfFieldsSqrt != 0)
            selectEmptyFields(index - numberOfFieldsSqrt - 1);
    }

    /**
     * Funkcja ktora odkrywa wszyskie pola (wywoływana po zakończeniu gry)
     */
    private void drawAllFields(){
        for(Field i : fields)
            if(i.isChecked() == false && i.isHasFlag() == false)
                i.drawField();
    }

    /**
     * Ustawia czy nastapilo juz pierwsze klikniecie
     * @param afterFirstClick
     */
    public void setAfterFirstClick(boolean afterFirstClick) {
        this.afterFirstClick = afterFirstClick;
    }
}