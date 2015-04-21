package GUI;

import javax.swing.*;
import java.awt.*;

public class UserInterface {
    private JPanel panel;

    public void show(){
        JFrame frame = new JFrame("Interval Creator");
        panel = new JPanel(new BorderLayout());
        panel.setSize(300,300);
        panel.setBackground(Color.WHITE);
        frame.getContentPane().add(BorderLayout.CENTER,panel);

        /*String[] columnNames = {"time", "message"};
        Interval o1 = new Interval("o1");
        for(int i = 0; i<10; i++){
            o1.addToSchedule("12"+i,"go"+i);
        }
        JTable table = new JTable(Interval.prepareForTable(o1),columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(BorderLayout.CENTER,scrollPane); */



        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);
    }

}
