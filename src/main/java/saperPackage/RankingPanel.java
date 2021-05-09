package saperPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingPanel extends JPanel {

    JButton backToMenu = new JButton("Powrót!");
    JTable rankingTable;
    RankingPanel thisPanel = this;
    Object[] columnsTitles = {"Nick", "Punkty", "Rozmiar", "Liczba Bomb", "Czas"};
    Ranking ranking = new Ranking();

    public RankingPanel(MainFrame parent) {

        this.setBackground(Color.green);

        this.setLayout(null);
        backToMenu.setBounds(parent.getWidth() / 2 - 50,600,100,50);
        ButtonPainter.paintButton(backToMenu);
        this.add(backToMenu);
        parent.getContentPane().add(this);

        ranking.loadFromFile("src/main/java/saperPackage/ranking.csv");

        Object[][] rowData = new Object[ranking.getListaRankingowa().size()][5];


        for(int i = 0; i < ranking.getListaRankingowa().size(); i++){
            rowData[i][0] = ranking.getListaRankingowa().get(i).getUsername();
            rowData[i][1] = ranking.getListaRankingowa().get(i).getScore();
            rowData[i][2] = ranking.getListaRankingowa().get(i).getBoardSize();
            rowData[i][3] = ranking.getListaRankingowa().get(i).getNumberOfBombs();
            rowData[i][4] = ranking.getListaRankingowa().get(i).getSeconds();
        }
        rankingTable = new JTable(rowData, columnsTitles){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < 5; i++) {
            rankingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(rankingTable);
        rankingTable.setFillsViewportHeight(true);

        thisPanel.add(scrollPane);
        scrollPane.setBounds(55, 200, 530, 200);

//        thisPanel.add(rankingTable);


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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MenuPanel.image, 0,0, this);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(new Color(95, 31, 67));
        graphics2D.setFont(new Font("Arial Hebrew", Font.BOLD | Font.ITALIC, 75));
        graphics2D.drawString("Minesweper", 100, 130);
        graphics2D.drawString("Minesweper", 100, 132);
        graphics2D.drawString("Minesweper", 100, 134);


    }

}
