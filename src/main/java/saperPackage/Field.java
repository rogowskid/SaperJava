package saperPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.batik.swing.*;



public class Field extends JPanel {

    private int index;
    private int value;

    private boolean isChecked = false;

    private boolean isBomb = false;


    private void msgbox(String text)
    {
        JOptionPane.showMessageDialog(null, text, "Saper", JOptionPane.PLAIN_MESSAGE);
    }

    private Field thisField = this;
    private JLabel valueText  = new JLabel(thisField.getValue()+"", SwingConstants.CENTER);

    public int getIndex() {
        return index;
    }

    public Field(int index, Stopwatch timer) {
        this.index = index;
        this.setBackground(Color.gray);

        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setLayout(new GridLayout(1,1));



        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(thisField.getIndex());


                if (e.getButton() == MouseEvent.BUTTON1) //Left Mouse Button
                {
                    timer.tier.start();

                    if (isChecked == false)
                    {
                        if(thisField.isBomb) {
                            timer.tier.stop();
                            thisField.setBackground(Color.red);
                            valueText.setText("");
                            msgbox("Koniec gry");

                            System.out.println(timer.finalTime);



                        }else {
                            thisField.setBackground(Color.PINK);
                            valueText.setText(thisField.getValue()+"");
                            GamePanel.counterPink++;
                            GamePanel.scoreValue.setText("Score: " + GamePanel.counterPink);


                        }

                    }

                    isChecked=true;
                    thisField.add(valueText);

                }else if (e.getButton() == MouseEvent.BUTTON3) //Right Mouse Button
                {

                    JSVGCanvas svgCanvas = new JSVGCanvas();
                    svgCanvas.setBackground(Color.GRAY);
                    File f = new File("src/main/resources/flag.svg");

                    thisField.add(svgCanvas);

                    try {
                        svgCanvas.setURI(f.toURL().toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }

                validate();
                repaint();
            }


        });


    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }


}