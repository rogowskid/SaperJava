package saperPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    JButton startGame;
    JButton quitGame;
    JButton rankingPanel;
    MenuPanel thisPanel = this;


    public MenuPanel(MainFrame parent) {
        this.setLayout(null);
        this.setBackground(Color.darkGray);

        parent.getContentPane().add(this);
        parent.setSize(655,838);
        parent.setResizable(false);
        parent.validate();
        parent.repaint();

        startGame = new JButton("Start Game");
        rankingPanel = new JButton("Ranking");
        quitGame = new JButton("Quit Game");

        startGame.setBounds(parent.getWidth()/2-50,50,100,50);
        rankingPanel.setBounds(parent.getWidth()/2-50,120,100,50);
        quitGame.setBounds(parent.getWidth()/2-50,190,100,50);
        this.add(startGame);
        this.add(rankingPanel);
        this.add(quitGame);


        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getContentPane().remove(thisPanel);
                new StartGamePanel(parent);
            }
        });

        rankingPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getContentPane().remove(thisPanel);
                new RankingPanel(parent);
            }
        });
        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
}
