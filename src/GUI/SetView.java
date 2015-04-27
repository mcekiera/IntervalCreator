package GUI;

import Interval.Set;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

public class SetView {

    private Set set;
    private JFormattedTextField timeField;
    private JTextField messageField;
    private JTextField nameField;
    private JTable table;
    private UserInterface userInterface;


    public SetView(UserInterface container, Set newSet){
        set = newSet;
        userInterface = container;
    }
    public JPanel getView(){
        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(BorderLayout.CENTER, displayInterval());
        newPanel.add(BorderLayout.EAST, createSidePanel());

        if(setIsEdited()){
            nameField.setText(set.getName());
        }
        return newPanel;
    }

    public JPanel displayInterval(){
        String[] columnNames = {"Time","Message"};
        table = new JTable(set.prepareForTable(),columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table.getColumnModel().getColumn(0).setMaxWidth(35);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
        JPanel inputPanel = new JPanel(new GridLayout(2,1,2,2));

        nameField = new JTextField(10);
        JLabel setName = new JLabel("Set name: ");
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(setName);
        namePanel.add(nameField);
        //////////////////////////////////////////////////////
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));
        JCheckBox closeToEnd = new JCheckBox("Change color");
        JTextField toEnd = new JTextField(2);
        JLabel before = new JLabel("before end.   ");
        options.add(closeToEnd);
        options.add(toEnd);
        options.add(before);

        JPanel repeat = new JPanel();
        repeat.setLayout(new BoxLayout(repeat,BoxLayout.X_AXIS));
        JRadioButton bySet = new JRadioButton("Set");
        JRadioButton byPosition = new JRadioButton("Position");
        JLabel repeats = new JLabel("Repeat by");
        repeat.add(repeats);
        repeat.add(bySet);
        repeat.add(byPosition);
        ///////////////////////////////////////////////////////////
        JPanel mid = new JPanel(new GridLayout(1,2,2,2));
        mid.add(options);
        mid.add(repeat);

        timeField = new JFormattedTextField(createFormatter("##:##"));
        timeField.setColumns(3);
        timeField.setText("0000");
        messageField = new JTextField("Message",20);
        messageField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                messageField.setText("");
            }
        });

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel,BoxLayout.X_AXIS));
        fieldPanel.add(timeField);
        fieldPanel.add(messageField);

        inputPanel.add(namePanel);
        //inputPanel.add(mid);
        inputPanel.add(fieldPanel);

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
        userInterface.installPanel(getView());

    }

    public boolean setIsEdited(){
        for(Set editedSet: userInterface.getLibrary()){
            if(editedSet.equals(set)){
                return true;
            }
        }
        return false;
    }

    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            set.addToSchedule(timeField.getText(),messageField.getText());
            refreshTable();
            nameField.setText(name);

        }
    }

    private class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            set.lunch();
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
            if(setIsEdited()){
                set.setName(nameField.getText());
                userInterface.installPanel(userInterface.getView());
            }else{
                set.setName(nameField.getText());
                userInterface.getLibrary().add(set);
                userInterface.installPanel(userInterface.getView());
            }
        }
    }
}
