package saperPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingPanel extends JPanel {

    JButton backToMenu = new JButton("Powrót!");
    JTable rankingTable;
    RankingPanel thisPanel = this;
    Object[] columnsTitles = {"Nick", "Punkty", "Rozmiar", "Liczba Bomb", "Czas"};
    private JComboBox sortList;
    private String[] options;
    public static Ranking ranking = new Ranking();
    private MainFrame parent;
    private JScrollPane scrollPane;
    private JLabel sortingByLablel = new JLabel("Sortuj według: ");
    private boolean growingSort = false;
    private JCheckBox setSortOption = new JCheckBox("Sortowanie rosnące");

    public RankingPanel(MainFrame parent) {

        ranking.selectionSort(Ranking.BY_SCORE, false);
        this.parent = parent;

        this.setLayout(null);
        backToMenu.setBounds(parent.getWidth() / 2 - 50,600,100,50);
        ButtonPainter.paintButton(backToMenu);
        this.add(backToMenu);
        parent.getContentPane().add(this);

        options = new String[]{"Punkty", "Rozmiar planszy", "Liczba bomb", "Czas"};

        sortingByLablel.setBounds(80,450, 200, 50);
        sortingByLablel.setHorizontalAlignment(JLabel.CENTER);
        sortingByLablel.setFont(new Font("Verdana",Font.BOLD, 16));
        this.add(sortingByLablel);


        setSortOption.setBounds(80,500, 250, 50);
        setSortOption.setHorizontalAlignment(JLabel.CENTER);
        setSortOption.setHorizontalTextPosition(SwingConstants.LEFT);
        setSortOption.setFont(new Font("Verdana",Font.BOLD, 16));
        setSortOption.setOpaque(false);



        setSortOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               growingSort =  ((JCheckBox)e.getSource()).isSelected();
            }
        });

        this.add(setSortOption);

        sortList = new JComboBox(options);
        this.add(sortList);
        sortList.setBounds(parent.getWidth() / 2 - 70,450,140,50);

        sortList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortTable(((JComboBox)e.getSource()).getSelectedItem().toString(), growingSort);
            }
        });


        this.initializeRankingTable();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < 5; i++) {
            rankingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        scrollPane = new JScrollPane(rankingTable);
        rankingTable.setFillsViewportHeight(true);
        thisPanel.add(scrollPane);
        scrollPane.setBounds(55, 200, 530, 200);


        //Ważne żeby to było po dodaniu
        parent.validate();
        parent.repaint();

        //Powrot do glownego menu
        backToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getContentPane().remove(thisPanel);
                new MenuPanel(parent);
            }
        });
    }

    private void  sortTable(String option, boolean flag){

        if(option.equals(options[0])) {
            ranking.selectionSort(Ranking.BY_SCORE, flag);
        }else if(option.equals(options[1])){
            ranking.selectionSort(Ranking.BY_BORDER_SIZE, flag);
        }else if(option.equals(options[2])){
            ranking.selectionSort(Ranking.BY_NUMBER_OF_BOMBS, flag);
        }else if(option.equals(options[3])){
            ranking.selectionSort(Ranking.BY_TIME, flag);
        }

        rankingTable = null;
        this.initializeRankingTable();

        thisPanel.remove(scrollPane);
        scrollPane = null;

        this.initializeScrollPane();

        this.parent.validate();
        this.parent.repaint();
    }

    private void initializeRankingTable(){
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
    }

    private void initializeScrollPane(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < 5; i++) {
            rankingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPane = new JScrollPane(rankingTable);
        rankingTable.setFillsViewportHeight(true);
        thisPanel.add(scrollPane);
        scrollPane.setBounds(55, 200, 530, 200);
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
