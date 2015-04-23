package GUI;

import Interval.Interval;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class SingleIntervalView {
    private JPanel panel;
    private Interval interval;
    private JFormattedTextField timeField;
    private JTextField messageField;
    private JTable table;
    private JPanel displayPanel;
    private JButton add;

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

    public JButton getAdd() {
        return add;
    }
    public void setTimeField(String text) {
        timeField.setText(text);
    }

    public void displayInterval(Interval interval){
        String[] columnNames = {"Time","Comment"};
        this.interval = interval;
        table = new JTable(interval.prepareForTable(),columnNames);
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

        add = new JButton("ADD");
        add.addActionListener(new AddListener());
        JButton edit = new JButton("EDIT");
        edit.addActionListener(new EditListener());
        JButton delete = new JButton("DEL");
        delete.addActionListener(new DeleteListener());
        JButton start = new JButton("START");
        start.addActionListener(new StartListener());
        JButton up = new JButton("^");
        up.addActionListener(new UpArrowListener());
        JButton down = new JButton("v");
        down.addActionListener(new DownArrowListener());
        JPanel upDown = new JPanel(new GridLayout(2,1));

        upDown.add(up);
        upDown.add(down);

        sidePanel.add(add);
        sidePanel.add(edit);
        sidePanel.add(delete);
        sidePanel.add(upDown);
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
            formatter.setPlaceholderCharacter('0');
        }catch (ParseException ex){
            ex.printStackTrace();
        }
        return formatter;
    }
    public void refreshTable(){
        panel.remove(displayPanel);
        displayInterval(interval);
    }
    public class AddListener implements ActionListener {
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
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(table.getSelectedRow()!=-1){
                interval.removePosition(table.getSelectedRow());
                refreshTable();
            }
        }
    }

    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(table.getSelectedRow()!=-1){
                interval.editPosition(table.getSelectedRow(),timeField.getText(),messageField.getText());
                refreshTable();
            }
        }
    }

    private class UpArrowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = (table.getSelectedRow()==-1) ? 0 : table.getSelectedRow();
            interval.changePosition(index,(index-1));
            refreshTable();
        }
    }

    private class DownArrowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = (table.getSelectedRow()==-1) ? table.getRowCount() : table.getSelectedRow();
            interval.changePosition(index,(index+1));
            refreshTable();
        }
    }
}
