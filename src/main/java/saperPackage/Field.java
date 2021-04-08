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


public class Field extends JPanel {
    private int intdex;
    private int value;
    private boolean isChecked = false;
    private boolean isBomb = false;
    private boolean hasFlag = false;


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

    public Field(Stopwatch timer, GamePanel parent, int index) {
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

                        if (isChecked == false) { //sprawdza czy mozemy nacisnac na to samo pole wiecej niz 1 raz
                            if (thisField.isBomb) {
                                timer.tier.stop();

                                svgCanvasBomb.setURI(urlImageBomb.toString());
                                thisField.setLayout(null);
                                svgCanvasBomb.setLocation((thisField.getWidth() / 4) / 2, (thisField.getHeight() / 4) / 2);
                                svgCanvasBomb.setSize((int) (thisField.getWidth() * 0.75), (int) (thisField.getHeight() * 0.75));
                                thisField.add(svgCanvasBomb);

                                valueText.setText("");

                                System.out.println(timer.finalTime);

                                parent.setGameOver(true);

                            } else {
                                valueText.setFont(new Font("Verdana", Font.PLAIN, thisField.getWidth() / 2));

                                if(thisField.getValue() != 0)
                                    valueText.setText(thisField.getValue() + "");
                                else
                                    valueText.setText("");
                                GamePanel.counterPink++;
                                GamePanel.scoreValue.setText("Score: " + GamePanel.counterPink);
                                thisField.setBackground(Color.lightGray);
                                thisField.setLayout(new GridLayout(1,1));
                                thisField.add(valueText);
                            }

                        }

                        isChecked = true;

                    }else if (e.getButton() == MouseEvent.BUTTON3) { //Right Mouse Button Click

                        if (parent.numflags > 0) {
                            if (thisField.isChecked == false) {

                                if(thisField.isHasFlag() == false) { //If flag is not set on the field
                                    //Set a flag as a background of field
                                    svgCanvasFlag.setURI(urlImageFlag.toString());
                                    thisField.setLayout(null);
                                    svgCanvasFlag.setLocation((thisField.getWidth() / 4) / 2, (thisField.getHeight() / 4) / 2);
                                    svgCanvasFlag.setSize((int) (thisField.getWidth() * 0.75), (int) (thisField.getHeight() * 0.75));
                                    thisField.add(svgCanvasFlag);
                                    parent.numflags--;
                                    thisField.setHasFlag(true);

                                }else{ //If flag is set on the field
                                    thisField.remove(svgCanvasFlag);
                                    parent.numflags++;
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

                    if (parent.isGameOver()) {
                        timer.tier.stop();
                        msgbox("Koniec gry");
                        parent.closeGame.setVisible(true);
                        parent.startGame.setVisible(false);
                        parent.pauseGame.setVisible(false);
                        parent.checkStatus=true;
                        System.out.println("Score: " + GamePanel.counterPink);

                    }
                }
            }
        });
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public boolean isHasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public boolean isCanBeBomb() {
        return canBeBomb;
    }

    public void setCanBeBomb(boolean canBeBomb) {
        this.canBeBomb = canBeBomb;
    }

    public int getIntdex() {
        return intdex;
    }

    public static void msgbox(String text)
    {
        JOptionPane.showMessageDialog(null, text, "Saper", JOptionPane.PLAIN_MESSAGE);
    }

}
