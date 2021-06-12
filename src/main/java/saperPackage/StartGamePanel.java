package saperPackage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Panel przed startem gry
 */
public class StartGamePanel extends JPanel {

    JButton backToMenu = new JButton("Powrot");
    StartGamePanel thisPanel = this;
    JLabel fieldChoose = new JLabel("Wybierz rozmiar pola");
    JSlider fieldSlider = new JSlider(6,25, 6);

    JLabel valueSlider = new JLabel("Liczba kratek: " + fieldSlider.getValue()*6);
    JSlider numberBombs = new JSlider(0,100,0);
    JLabel bombChoose = new JLabel("Wybierz ilosc bomb");
    JLabel valueBomb = new JLabel("Ilosc bomb: " + numberBombs.getValue());
    JLabel textofStartGame = new JLabel("Liczba bomb jest za duża!");
    JLabel emptyNicknameLabel = new JLabel("Nick gracza nie może być pusty!");

    JLabel nickInputText = new JLabel("Wprowadź nick: ");
    JTextField nickField = new JTextField();
    String playerNickname;

    private JButton startGame = new JButton("Start Game");
    private int numberOfFields=36;

    /**
     * Ustawia tlo w panelu
     * @param g - obiekt grafiki
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MenuPanel.image, 0,0, this);

    }

    /**
     * Konstruktor
     * @param parent - referencja do glownej ramki
     */
    public StartGamePanel(MainFrame parent){
        this.setBackground(Color.darkGray);
        this.setLayout(null);

        backToMenu = ButtonPainter.paintButton(backToMenu);
        startGame = ButtonPainter.paintButton(startGame);

        fieldChoose.setBounds(50,10,250,50);
        fieldSlider.setBounds(50,60,100,20);
        valueSlider.setBounds(50,80,250,20);


        fieldChoose.setFont(new Font("Verdana",Font.PLAIN, 18));
        valueSlider.setFont(new Font("Verdana",Font.PLAIN, 18));


        valueSlider.setForeground(Color.white);
        fieldChoose.setForeground(Color.white);
        fieldSlider.setBackground(new Color(227,84,138));

        numberBombs.setBounds(405,60,100,20);
        bombChoose.setBounds(405,10,250,50);
        valueBomb.setBounds(405, 80, 250,20);


        bombChoose.setFont(new Font("Verdana",Font.PLAIN, 18));
        valueBomb.setFont(new Font("Verdana",Font.PLAIN, 18));

        bombChoose.setForeground(Color.white);
        valueBomb.setForeground(Color.white);
        numberBombs.setBackground(new Color(217,83,134));

        startGame.setBounds(parent.getWidth()/2-50, 400, 100,50);
        backToMenu.setBounds(parent.getWidth()/2-50,500,100,50);

        textofStartGame.setBounds(0, 150, parent.getWidth(), 50);
        textofStartGame.setHorizontalAlignment(JLabel.CENTER);
        textofStartGame.setFont(new Font("Verdana",Font.BOLD, 16));

        //Empty nickname label
        emptyNicknameLabel.setBounds(0, 210, parent.getWidth(), 50);
        emptyNicknameLabel.setHorizontalAlignment(JLabel.CENTER);
        emptyNicknameLabel.setFont(new Font("Verdana",Font.BOLD, 16));

        //Input nickname label
        nickInputText.setBounds(parent.getWidth()/2-100, 250, 200,40);
        nickInputText.setHorizontalAlignment(JLabel.CENTER);
        nickInputText.setFont(new Font("Verdana",Font.BOLD, 16));
        this.add(nickInputText);

        //Nickname field
        nickField.setBounds(parent.getWidth()/2-100, 300, 200,40);
        nickField.setHorizontalAlignment(JLabel.CENTER);
        nickField.setFont(new Font("Verdana",Font.BOLD, 16));
        this.add(nickField);
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

                numberOfFields = fieldSlider.getValue()*fieldSlider.getValue(); //?
                valueSlider.setText("Liczba kratek: " + numberOfFields);
            }
        });

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(nickField.getText().equals("")){
                    thisPanel.add(emptyNicknameLabel);
                    parent.repaint();
                    parent.validate();
                    return;
                }

                if (numberBombs.getValue()<=numberOfFields - 9) //9 bo pierwszy klik
                {
                    parent.getContentPane().remove(thisPanel);
                    playerNickname = nickField.getText();
                    new GamePanel(parent, numberOfFields, numberBombs.getValue(), playerNickname);
                }else{
                    thisPanel.remove(emptyNicknameLabel);
                    textofStartGame.setVisible(true);
                }
            }
        });
    }
}