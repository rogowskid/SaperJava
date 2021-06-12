import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import saperPackage.ButtonPainter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ButtonPainterTest {
    @Test
    @DisplayName("Button painting")
    void shouldPaintButton(){
        JButton button = new JButton("Test");
        button = ButtonPainter.paintButton(button);
        Assertions.assertTrue(new Color(164, 83, 129).equals((Color) button.getBackground()));
    }
}
