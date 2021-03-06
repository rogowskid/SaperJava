package saperPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Funkcja Stopwatch jest  to stoper, ktory liczy ile zajmuje graczowi dana rozgrywka (czas liczony jest do wygrania
 * gry lub przegrania poprzez natrafienie na bombe)

 Zaimplementowalismy stoper tak aby liczył:
 second_string - sekundy
 minutes_string - minuty
 hours_string - godziny

/
 */
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
/**
 * Funkcja jest wywoływana dopiero w momencie najcisniecia na pierwszy bloczek (actionPerformed)
 * @param finalTime - jest to ostateczny czas rozgrywki
 */

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
/**
 * @param Stopwatch stopuje licznik w momencie zakonczenia gry
 */

    public Stopwatch() {
        this.setFont(new Font("Verdana", Font.PLAIN, 22));
        this.setBounds(430,130,200,20);
        this.setForeground(Color.white);
        this.setBackground(Color.red);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setText("Time: " + hours_string+":"+minutes_string+":"+seconds_string);
    }

    /**
     * Zwraca ilosc sekund przez ktore uruchomiony był timer
     * @return ilosc sekund rozgrywki
     */
    public int getTimeInSeconds(){
        return hours * 3600 + minutes * 60 + seconds;
    }

}
