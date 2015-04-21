package GUI;

import Interval.Interval;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class UserInterface {
    private JPanel panel;
    private Interval interval;
    JFormattedTextField timeField;
    JTextField messageField;
    JTable table;
    JPanel displayPanel;

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
    public void displayInterval(Interval interval){
        String[] columnNames = {"Time","Message"};
        this.interval = interval;
        table = new JTable(Interval.prepareForTable(interval),columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(BorderLayout.PAGE_START,createInputFields());
        displayPanel.add(BorderLayout.CENTER,scrollPane);
        panel.add(BorderLayout.CENTER,displayPanel);
        panel.revalidate();
    }

    public void createSidePanel(){
        JPanel sidePanel = new JPanel(new GridLayout(5,1,2,2));

        JButton add = new JButton("ADD");
        add.addActionListener(new AddListener());
        JButton edit = new JButton("EDIT");
        edit.addActionListener(new EditListener());
        JButton delete = new JButton("DEL");
        delete.addActionListener(new DeleteListener());
        JButton start = new JButton("START");
        start.addActionListener(new StartListener());

        sidePanel.add(add);
        sidePanel.add(edit);
        sidePanel.add(delete);
        sidePanel.add(new JPanel());
        sidePanel.add(start);

        panel.add(BorderLayout.EAST,sidePanel);
    }

    public JPanel createInputFields(){
        JPanel inputPanel = new JPanel();

        timeField = new JFormattedTextField(createFormatter("##:##"));
        timeField.setColumns(3);
        timeField.setText("0000");
        messageField = new JTextField(15);

        inputPanel.add(timeField);
        inputPanel.add(messageField);

        return inputPanel;
    }

    public MaskFormatter createFormatter(String s){
        MaskFormatter formatter = null;
        try{
            formatter = new MaskFormatter(s);
        }catch (ParseException ex){
            ex.printStackTrace();
        }
        return formatter;
    }

    private class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            interval.addToSchedule(timeField.getText(),messageField.getText());
            panel.remove(displayPanel);
            displayInterval(interval);
        }
    }

    private class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            interval.lunchInterval();
            for(String[] a: interval.getSchedule()){
                System.out.println(a[0]+","+a[1]);
            }
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            interval.removePosition(table.getSelectedRow());
            panel.remove(displayPanel);
            displayInterval(interval);
        }

    }

    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            interval.editPosition(table.getSelectedRow(),timeField.getText(),messageField.getText());
            panel.remove(displayPanel);
            displayInterval(interval);
        }
    }
}
