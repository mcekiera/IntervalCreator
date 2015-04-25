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
    private UserInterface userInterface;


    public SetView(UserInterface container, Set newSet){
        set = newSet;
        userInterface = container;
        panel = new JPanel(new BorderLayout());
    }
    public JPanel getSetView(){
        panel.add(BorderLayout.CENTER, displayInterval());
        panel.add(BorderLayout.EAST, createSidePanel());
        return panel;
    }

    public JPanel displayInterval(){
        String[] columnNames = {"Time","Comment"};
        table = new JTable(set.prepareForTable(),columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(BorderLayout.PAGE_START,createInputFields());
        displayPanel.add(BorderLayout.CENTER,scrollPane);
        return displayPanel;
    }

    public JPanel createSidePanel(){
        JPanel sidePanel = new JPanel(new GridLayout(8,1,2,2));

        JButton ok = new JButton("OK");
        ok.addActionListener(new OKListener());
        JButton add = new JButton("ADD");
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

        return sidePanel;
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
        getSetView();
        panel.revalidate();

    }
    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(timeField.getText()+","+messageField.getText());
            set.addToSchedule(timeField.getText(),messageField.getText());
            refreshTable();

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
