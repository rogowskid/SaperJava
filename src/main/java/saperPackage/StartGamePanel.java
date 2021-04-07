package saperPackage;
import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGamePanel extends JPanel {

    JButton backToMenu = new JButton("Powrot");
    StartGamePanel thisPanel = this;
    JLabel fieldChoose = new JLabel("Wybierz rozmiar pola");
    JSlider fieldSlider = new JSlider(6,25, 6);

    JLabel valueSlider = new JLabel("Liczba kratek: " + fieldSlider.getValue()*6);
    JSlider numberBombs = new JSlider(0,100,0);
    JLabel bombChoose = new JLabel("Wybierz ilosc bomb");
    JLabel valueBomb = new JLabel("Ilosc bomb: " + numberBombs.getValue());
    JLabel textofStartGame = new JLabel("The number of bombs is greater than the number of fields");
    JLabel textofStarGame2 = new JLabel("The number of fields must be greater");


    private JButton startGame = new JButton("Start Game");
    private int numberOfFields=36;

    public StartGamePanel(MainFrame parent){

        this.setBackground(Color.darkGray);
        this.setLayout(null);

        fieldChoose.setBounds(50,10,250,50);
        fieldSlider.setBounds(50,60,100,20);
        valueSlider.setBounds(50,80,250,20);


        fieldChoose.setFont(new Font("Verdana",Font.PLAIN, 18));
        valueSlider.setFont(new Font("Verdana",Font.PLAIN, 18));


        valueSlider.setForeground(Color.white);
        fieldChoose.setForeground(Color.white);
        fieldSlider.setBackground(Color.darkGray);

        numberBombs.setBounds(405,60,100,20);
        bombChoose.setBounds(405,10,250,50);
        valueBomb.setBounds(405, 80, 250,20);


        bombChoose.setFont(new Font("Verdana",Font.PLAIN, 18));
        valueBomb.setFont(new Font("Verdana",Font.PLAIN, 18));

        bombChoose.setForeground(Color.white);
        valueBomb.setForeground(Color.white);
        numberBombs.setBackground(Color.darkGray);

        startGame.setBounds(parent.getWidth()/2-50, 400, 100,50);
        backToMenu.setBounds(parent.getWidth()/2-50,500,100,50);

        textofStartGame.setBounds(parent.getWidth()/2-200,150,400,50);
        textofStartGame.setFont(new Font("Verdana",Font.PLAIN, 12));
        textofStartGame.setForeground(Color.white);

        textofStarGame2.setBounds(parent.getWidth()/2-140,210,400,50);
        textofStarGame2.setFont(new Font("Verdana",Font.PLAIN, 12));
        textofStarGame2.setForeground(Color.white);

        this.add(startGame);


        this.add(fieldSlider);
        this.add(fieldChoose);
        this.add(valueSlider);

        this.add(numberBombs);
        this.add(bombChoose);
        this.add(valueBomb);

        this.add(backToMenu);
        this.add(textofStartGame);
        this.add(textofStarGame2);
        textofStartGame.setVisible(false);
        textofStarGame2.setVisible(false);
        parent.getContentPane().add(this);
        parent.validate();
        parent.repaint();


        backToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getContentPane().remove(thisPanel);
                new MenuPanel(parent);
            }
        });

        numberBombs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                valueBomb.setText("Liczba bomb: " + numberBombs.getValue());
            }
        });

        fieldSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                numberOfFields = fieldSlider.getValue()*fieldSlider.getValue(); //?
                valueSlider.setText("Liczba kratek: " + numberOfFields);
            }
        });




        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (numberBombs.getValue()<=numberOfFields)
                {
                    parent.getContentPane().remove(thisPanel);
                    new GamePanel(parent, numberOfFields, numberBombs.getValue());
                }
                if(numberBombs.getValue()>numberOfFields)
                {
                    textofStartGame.setVisible(true);
                    textofStarGame2.setVisible(true);

                }

            }
        });



    }


}