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

    private int index;
    private int value;
    private boolean isChecked = false;
    private boolean isBomb = false;


    private Field thisField = this;
    private JLabel valueText  = new JLabel(thisField.getValue()+"", SwingConstants.CENTER);

    //Flag SVG Image
    JSVGCanvas svgCanvasFlag = new JSVGCanvas();
    File flagImgFile = new File("src/main/resources/flag.svg");
    URL urlImageFlag;
    //Bomb SVG Image
    JSVGCanvas svgCanvasBomb = new JSVGCanvas();
    File bombImgFile = new File("src/main/resources/bomb.svg");
    URL urlImageBomb;

    public Field(int index, Stopwatch timer, GamePanel parent) {
        this.index = index;
        this.setBackground(Color.gray);

        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setLayout(new GridLayout(1,1));

        //Flag Icon
        svgCanvasFlag.setBackground(Color.GRAY);
        try {
            urlImageFlag = flagImgFile.toURI().toURL();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        //Bomb Icon
        svgCanvasBomb.setBackground(Color.darkGray);
        try {
            urlImageBomb = bombImgFile.toURI().toURL();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getButton() == MouseEvent.BUTTON1) //Left Mouse Button
                {
                    timer.tier.start();

                    if (isChecked == false)
                    {
                        if(thisField.isBomb) {
                            timer.tier.stop();

                            svgCanvasBomb.setURI(urlImageBomb.toString());
                            thisField.add(svgCanvasBomb);

                            valueText.setText("");

                            System.out.println(timer.finalTime);

                            parent.setGameOver(true);

                        }else {
                            valueText.setFont(new Font("Verdana", Font.PLAIN, thisField.getWidth()/2));
                            valueText.setText(thisField.getValue()+"");
                            GamePanel.counterPink++;
                            GamePanel.scoreValue.setText("Score: " + GamePanel.counterPink);
                            thisField.add(valueText);
                        }

                    }

                    isChecked=true;

                }else if (e.getButton() == MouseEvent.BUTTON3){ //Right Mouse Button Click

                    if(thisField.isChecked == false) {
                        //Set a flag as a background of field
                        svgCanvasFlag.setURI(urlImageFlag.toString());
                        thisField.setLayout(null);
                        svgCanvasFlag.setLocation((thisField.getWidth()/4)/2, (thisField.getHeight()/4)/2);
                        svgCanvasFlag.setSize((int)(thisField.getWidth()*0.75), (int)(thisField.getHeight()*0.75));
                        thisField.add(svgCanvasFlag);
                    }
                }
                validate();
                repaint();

                if(parent.isGameOver()) {
                    msgbox("Koniec gry");
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

    public int getIndex() {
        return index;
    }

    private void msgbox(String text)
    {
        JOptionPane.showMessageDialog(null, text, "Saper", JOptionPane.PLAIN_MESSAGE);
    }

}
