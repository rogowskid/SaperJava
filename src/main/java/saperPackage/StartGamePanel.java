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
    JLabel valueSlider = new JLabel("Liczba kratek: " + fieldSlider.getValue());
    JSlider numberBombs = new JSlider(0,100,0);
    JLabel bombChoose = new JLabel("Wybierz ilosc bomb");
    JLabel valueBomb = new JLabel("Ilosc bomb: " + numberBombs.getValue());
    JLabel textofStartGame = new JLabel("Ilosc bomb jest wieksza od ilosci pol");

    private JButton startGame = new JButton("Start Game");
    private int numberOfFields;

    public StartGamePanel(MainFrame parent){

        //this.setBackground(Color.blue);
        this.setLayout(null);
        backToMenu.setBounds(500,50,100,50);
        fieldChoose.setBounds(150,10,250,50);
        fieldSlider.setBounds(150,60,100,20);
        valueSlider.setBounds(150,80,250,20);

        fieldChoose.setFont(new Font("Verdana",Font.PLAIN, 12));
        valueSlider.setFont(new Font("Verdana",Font.PLAIN, 12));


        numberBombs.setBounds(350,60,100,20);
        bombChoose.setBounds(350,10,250,50);
        valueBomb.setBounds(350, 80, 250,20);
        bombChoose.setFont(new Font("Verdana",Font.PLAIN, 12));
        valueBomb.setFont(new Font("Verdana",Font.PLAIN, 12));
        startGame.setBounds(250, 400, 100,50);


        textofStartGame.setBounds(250,500,400,50);

        this.add(startGame);


        this.add(fieldSlider);
        this.add(fieldChoose);
        this.add(valueSlider);

        this.add(numberBombs);
        this.add(bombChoose);
        this.add(valueBomb);

        this.add(backToMenu);
        this.add(textofStartGame);
        textofStartGame.setVisible(false);
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
                numberOfFields = fieldSlider.getValue()*fieldSlider.getValue();
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

                }

            }
        });



    }


}