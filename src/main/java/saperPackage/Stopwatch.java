package saperPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JLabel {
    JLabel timeLabel = new JLabel();
    int elapsedTime = 0;
    int seconds =0;
    int minutes =0;
    int hours =0;
    boolean started = false;
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    String hours_string = String.format("%02d", hours);
    String finalTime;


    Stopwatch thisLabel = this;

    public Timer tier = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime=elapsedTime+1000;
            hours = (elapsedTime/3600000);
            minutes = (elapsedTime/60000) % 60;
            seconds = (elapsedTime/1000) % 60;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            thisLabel.setText("Time: " + hours_string+":"+minutes_string+":"+seconds_string);
            finalTime = hours_string+":"+minutes_string+":"+seconds_string;

        }
    });

    public Stopwatch() {
        this.setFont(new Font("Verdana", Font.PLAIN, 22));
        this.setBounds(430,130,200,20);
        this.setForeground(Color.white);
        this.setBackground(Color.red);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setText("Time: " + hours_string+":"+minutes_string+":"+seconds_string);
    }

    public int getTimeInSeconds(){
        return hours * 3600 + minutes * 60 + seconds;
    }

}
