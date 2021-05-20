package saperPackage;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){

        RankingPanel.ranking.loadFromFile("src/main/java/saperPackage/ranking.csv");

        this.setBounds(200,200,300,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Saper");
        this.setLayout(new GridLayout(1,1));

        new MenuPanel(this);

        this.setVisible(true);
    }

}