package saperPackage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonPainter {

    public static JButton paintButton(JButton button){
        button.setBackground(new Color(164, 83, 129));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.darkGray));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Border line = new LineBorder(Color.BLACK);
                Border margin = new EmptyBorder(5, 15, 5, 15);
                Border compound = new CompoundBorder(line, margin);
                button.setForeground(Color.BLACK);
                button.setBorder(compound);
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                Border line = new LineBorder(Color.DARK_GRAY);
                Border margin = new EmptyBorder(5, 15, 5, 15);
                Border compound = new CompoundBorder(line, margin);
                button.setBorder(compound);
                button.setForeground(Color.WHITE);
            }


        });
        return button;
    }

}
