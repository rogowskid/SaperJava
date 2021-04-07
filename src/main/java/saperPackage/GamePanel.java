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

public class GamePanel extends JPanel {
    public static int counterPink=0;
    JPanel topPanel = new JPanel();
    public int secondTime=0;
    JPanel mainBoardPanel = new JPanel();
    public String userName;
    public Stopwatch timeLabel;
    public JButton stopGame = new JButton("Stop");
    public JButton pauseGame = new JButton("Pause");
    public JButton startGame = new JButton("Start");
    public JButton closeGame = new JButton("Close");
    public JLabel numberOfflags = new JLabel();
    public int numflags;
    public boolean checkStatus = false;
    public boolean isGameOver = false;


    public static JLabel scoreValue = new JLabel ("Score: " + counterPink, SwingConstants.LEFT);

    GamePanel thisPanel = this;
    Field[] fields;

    public GamePanel(MainFrame parent, int numberOfFields, int bombsNumber) {

        timeLabel = new Stopwatch();
        parent.setSize(655, 838);
        this.setBackground(Color.gray);
        this.setLayout(null);
        parent.getContentPane().add(this);

        fields = new Field[numberOfFields];

        numflags = bombsNumber;



        mainBoardPanel.setLayout(new GridLayout((int) Math.sqrt(numberOfFields), (int) Math.sqrt(numberOfFields)));

        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Field(i, timeLabel, thisPanel);
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

        generateBombs(fields,bombsNumber); //Tymczasowo 2 parametr

        for(int i = 0; i < fields.length; i++)
            fields[i].setValue(checkNeighbor(fields, i));

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
                timeLabel.tier.stop();
                Field.msgbox("Koniec gry");
                checkStatus=true;
                closeGame.setVisible(true);
            }
        });

        closeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getContentPane().remove(thisPanel);
                new MenuPanel(parent);

            }
        });

    }




    public static void generateBombs(Field[] fields, int number_of_bombs){
        Random random = new Random();
        int randValue;
        for(int i = 0; i < number_of_bombs; i++){
            while (true){
                randValue = random.nextInt(fields.length);
                if(fields[randValue].isBomb()){
                    continue;
                }else{
                    fields[randValue].setBomb(true);
                    break;
                }
            }
        }
    }

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

    public static boolean isInLastColumn(int index, int length){
        for(int i = (int)Math.sqrt(length)-1; i < length; i += (int)Math.sqrt(length))
            if(index == i)
                return true;
        return false;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }




}