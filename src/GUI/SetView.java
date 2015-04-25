package GUI;

import Interval.Set;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class SetView {
    private JPanel panel;
    private Set set;
    private JFormattedTextField timeField;
    private JTextField messageField;
    private JTable table;
    private JPanel displayPanel;
    private JButton add;
    private UserInterface userInterface;


    public SetView(UserInterface container, Set newSet){
        set = newSet;
        userInterface = container;
    }
    public JPanel getSetView(){
        panel = new JPanel(new BorderLayout());
        displayInterval(set);
        createSidePanel();
        createInputFields();
        return panel;
    }

    public JButton getAdd() {
        return add;
    }
    public void setTimeField(String text) {
        timeField.setText(text);
    }

    public void displayInterval(Set set){
        String[] columnNames = {"Time","Comment"};
        this.set = set;
        table = new JTable(set.prepareForTable(),columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

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
        JPanel sidePanel = new JPanel(new GridLayout(8,1,2,2));

        JButton ok = new JButton("OK");
        ok.addActionListener(new OKListener());
        add = new JButton("ADD");
        add.addActionListener(new AddListener());
        JButton edit = new JButton("EDIT");
        edit.addActionListener(new EditListener());
        JButton delete = new JButton("DEL");
        delete.addActionListener(new DeleteListener());
        JButton lunch = new JButton("Lunch");
        lunch.addActionListener(new StartListener());
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
        sidePanel.add(new JPanel());
        sidePanel.add(new JPanel());
        sidePanel.add(ok);
        sidePanel.add(lunch);

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
        displayInterval(set);
    }
    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            set.addToSchedule(timeField.getText(),messageField.getText());
            panel.remove(displayPanel);
            displayInterval(set);
        }
    }

    private class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            set.lunchInterval();
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(table.getSelectedRow()!=-1){
                set.removePosition(table.getSelectedRow());
                refreshTable();
            }
        }
    }

    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(table.getSelectedRow()!=-1){
                set.editPosition(table.getSelectedRow(),timeField.getText(),messageField.getText());
                refreshTable();
            }
        }
    }

    private class UpArrowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = (table.getSelectedRow()==-1) ? 0 : table.getSelectedRow();
            set.changePosition(index,(index-1));
            refreshTable();
        }
    }

    private class DownArrowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = (table.getSelectedRow()==-1) ? table.getRowCount() : table.getSelectedRow();
            set.changePosition(index,(index+1));
            refreshTable();
        }
    }

    private class OKListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userInterface.getLibrary().add(set);
            userInterface.installPanel();

        }
    }
}
