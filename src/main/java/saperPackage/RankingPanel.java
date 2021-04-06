package saperPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingPanel extends JPanel {

    JButton backToMenu = new JButton("Powrót!");

    RankingPanel thisPanel = this;

    public RankingPanel(MainFrame parent) {

        this.setBackground(Color.green);

        this.setLayout(null);
        backToMenu.setBounds(50,50,100,50);

        this.add(backToMenu);
        parent.getContentPane().add(this);

        //Ważne żeby to było po dodaniu
        parent.validate();
        parent.repaint();


        backToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                parent.getContentPane().remove(thisPanel);
                new MenuPanel(parent);
            }
        });

    }
}
