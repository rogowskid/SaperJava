package saperPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.batik.swing.*;

/**
 * Reorezebtuje pojedyncze pole na planszy gry
 */
public class Field extends JPanel {
    private int intdex;
    private int value;
    private boolean isChecked = false;


    private boolean isBomb = false;
    private boolean hasFlag = false;
    private int closeGameScore ;

    private boolean canBeBomb = true;
    private Field thisField = this;
    private JLabel valueText = new JLabel(thisField.getValue()+"", SwingConstants.CENTER);

    //Flag SVG Image
    JSVGCanvas svgCanvasFlag = new JSVGCanvas();
    File flagImgFile = new File("src/main/resources/flag.svg");
    URL urlImageFlag;
    //Bomb SVG Image
    JSVGCanvas svgCanvasBomb = new JSVGCanvas();
    File bombImgFile = new File("src/main/resources/bomb.svg");
    URL urlImageBomb;

    /**
     * Konstruktor pojedynczego pola
     * @param timer - licznik czasu
     * @param parent - rodzin (Panel)
     * @param index - index  pola na planszy
     * @param numberOfFields - ogolna liczba pol na planszy
     * @param numberBombs - liczba bomb na planszy
     */
    public Field(Stopwatch timer, GamePanel parent, int index, int numberOfFields, int numberBombs) {
        this.setBackground(Color.gray);
        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setLayout(new GridLayout(1,1));

        this.intdex = index;

        //Flag Icon
        svgCanvasFlag.setBackground(Color.GRAY);
        try {
            urlImageFlag = flagImgFile.toURI().toURL();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }
        svgCanvasFlag.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("flaga kliked!");
                    thisField.remove(svgCanvasFlag);
                    parent.numflags++;

                    thisField.setHasFlag(false);
                    parent.numberOfflags.setText("Flags: " + parent.numflags);
                    repaint();
                    validate();
                }
            }
        });

        //Bomb Icon
        svgCanvasBomb.setBackground(Color.GRAY);
        try {
            urlImageBomb = bombImgFile.toURI().toURL();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


                if (parent.checkStatus == false) {

                    if (e.getButton() == MouseEvent.BUTTON1){ //Left Mouse Button
                        timer.tier.start();

                        if(parent.isAfterFirstClick() == false){
                            parent.firstClick(thisField.getIntdex());
                            parent.setAfterFirstClick(true);
                        }
                            System.out.println(thisField.canBeBomb + " Index: " + thisField.getIntdex());

                        if (isChecked == false && thisField.isHasFlag() == false) { //sprawdza czy mozemy nacisnac na to samo pole wiecej niz 1 raz
                            if (thisField.isBomb) {
                                timer.tier.stop();
                                thisField.drawBomb();
                                valueText.setText("");
                                System.out.println(timer.finalTime);
                                GamePanel.counterPink--;
                                parent.setGameOver(true);
                            } else {

                                if(thisField.getValue() == 0) {
                                    parent.selectEmptyFields(thisField.getIntdex());
                                }
                                GamePanel.scoreValue.setText("Score: " + GamePanel.counterPink);

                                if(thisField.getValue() != 0)
                                    thisField.drawField();
                            }

                        }

                        isChecked = true;

                    }else if (e.getButton() == MouseEvent.BUTTON3) { //Right Mouse Button Click
                        if (parent.numflags > 0) {
                            if (thisField.isChecked == false) {
                                if(thisField.isHasFlag() == false) { //If flag is not set on the field
                                    //Set a flag as a background of field
                                    drawFlag();
                                    parent.numflags--;
                                    GamePanel.counterPink++;
                                    thisField.setHasFlag(true);

                                }else{ //If flag is set on the field
                                    thisField.remove(svgCanvasFlag);
                                    parent.numflags++;
                                    GamePanel.counterPink--;
                                    thisField.setHasFlag(false);
                                }
                                parent.numberOfflags.setText("Flags: " + parent.numflags);
                            }
                        }else{
                            parent.numberOfflags.setText("Flags: " + 0);
                        }
                    }

                    validate();
                    repaint();

                    if(GamePanel.counterPink-parent.numflags==numberOfFields)
                    {
                        parent.setCloseGame(true); //true wygrana
                    }

                    if (parent.isGameOver()) {
                        System.out.println("end");
                        parent.setCloseGame(false); //false przegrana
                    }
                }
            }
        });
    }

    /**
     * Rysuje pole na planszy
     */
    public void drawField(){
        valueText.setFont(new Font("Verdana", Font.PLAIN, thisField.getWidth() / 2));
        if(thisField.getValue() != 0)
            valueText.setText(thisField.getValue() + "");
        else
            valueText.setText("");

        if(thisField.getValue() == -1){
            thisField.drawBomb();
            return;
        }
        GamePanel.counterPink++;
        thisField.setBackground(Color.lightGray);
        thisField.setLayout(new GridLayout(1,1));
        thisField.add(valueText);
        validate();
        repaint();
    }

    /**
     * Zwraca wartosc pola (iloe wokol jest bomb)
     * @return ilosc bomb wokol pola
     */
    public int getValue() {
        return value;
    }

    /**
     * Ustawia wartosc danego pola (ilosc bomb wokol)
     * @param value - liczba bomb wokol pola
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sprawdza czy dane pole jest bomba
     * @return jesli pole jest bomba zwraca true jesli nie false
     */
    public boolean isBomb() {
        return isBomb;
    }

    /**
     * Ustawia pole jako te ktore bedzie bomba
     * @param bomb - true jest pole ma byc bomba, false jesli nie bedzie bomba
     */
    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    /**
     * Sprawdza czy pole ma flage
     * @return jesli na polu jest oznaczona flaga zwraca true jesli nie zwraca false
     */
    public boolean isHasFlag() {
        return hasFlag;
    }

    /**
     * Ustawia flage na polu
     * @param hasFlag - true na polu jest ustawiona flaga, false na polu nie ma ustawionej flagi
     */
    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    /**
     * Sprawdza czy pole moze byc bomba
     * @return - true jesli pole moze byc bomba, false jesli nie moze
     */
    public boolean isCanBeBomb() {
        return canBeBomb;
    }

    /**
     * Ustawia czy pole moze byc bomba
     * @param canBeBomb - true jesli pole moze byc bomba, false jesli nie moze
     */
    public void setCanBeBomb(boolean canBeBomb) {
        this.canBeBomb = canBeBomb;
    }

    /**
     * Zwraca index pola (wzgledem planszy)
     * @return index pola na planszy
     */
    public int getIntdex() {
        return intdex;
    }

    /**
     * Ustawia na polu czy bylo juz sprawdzone czy nie
     * @param checked - true jesli pole bylo sprawdzone, false jesli nie bylo sprawdzone
     */
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    /**
     * Sprawdza czy pole bylo juz sprawdzone czy nie
     * @return true jesli pole bylo sprawdzone false jesli nie
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Wyswietla komunikat
     * @param text - tresc komunikatu
     */
    public static void msgbox(String text)
    {
        JOptionPane.showMessageDialog(null, text, "Saper", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Rysuje bombe na polu
     */
    private void drawBomb(){
        svgCanvasBomb.setURI(urlImageBomb.toString());
        thisField.setLayout(null);
        svgCanvasBomb.setLocation((thisField.getWidth() / 4) / 2, (thisField.getHeight() / 4) / 2);
        svgCanvasBomb.setSize((int) (thisField.getWidth() * 0.75), (int) (thisField.getHeight() * 0.75));
        thisField.add(svgCanvasBomb);
    }

    /**
     * Rysuje flage na polu
     */
    private void drawFlag(){
        svgCanvasFlag.setURI(urlImageFlag.toString());
        thisField.setLayout(null);
        svgCanvasFlag.setLocation((thisField.getWidth() / 4) / 2, (thisField.getHeight() / 4) / 2);
        svgCanvasFlag.setSize((int) (thisField.getWidth() * 0.75), (int) (thisField.getHeight() * 0.75));
        thisField.add(svgCanvasFlag);
    }

}
