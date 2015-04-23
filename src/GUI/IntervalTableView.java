package GUI;

import javax.swing.*;
import java.awt.*;

public class IntervalTableView {
    JPanel panel;
    public void show(){
        JFrame frame = new JFrame("Interval Creator");
        panel = new JPanel(new BorderLayout());
        panel.setSize(300,300);
        panel.setBackground(Color.WHITE);
        frame.getContentPane().add(BorderLayout.CENTER,panel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setVisible(true);
    }
}
