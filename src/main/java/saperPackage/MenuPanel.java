package saperPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

    JButton startGame;
    JButton quitGame;
    JButton rankingPanel;
    MenuPanel thisPanel = this;

    BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0,0, this);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(new Color(95, 31, 67));
        graphics2D.setFont(new Font("Arial Hebrew", Font.BOLD | Font.ITALIC, 75));
        graphics2D.drawString("Minesweper", 100, 130);
        graphics2D.drawString("Minesweper", 100, 132);
        graphics2D.drawString("Minesweper", 100, 134);


    }

    public  MenuPanel(MainFrame parent) {
        this.setLayout(null);
        this.setBackground(Color.darkGray);

        try {
            image = ImageIO.read(new File("src/main/resources/bkg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        parent.getContentPane().add(this);
        parent.setSize(655,838);
        parent.setResizable(false);
        parent.validate();
        parent.repaint();

        startGame = new JButton("Start Game");
        rankingPanel = new JButton("Ranking");
        quitGame = new JButton("Quit Game");

        startGame = ButtonPainter.paintButton(startGame);
        rankingPanel = ButtonPainter.paintButton(rankingPanel);
        quitGame = ButtonPainter.paintButton(quitGame);


        startGame.setBounds(parent.getWidth()/2-50,220,100,50);
        rankingPanel.setBounds(parent.getWidth()/2-50,300,100,50);
        quitGame.setBounds(parent.getWidth()/2-50,380,100,50);
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
