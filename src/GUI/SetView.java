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

/**
 * Class is responsible for handling particular Set by GUI. It display and can modify Set.
 */
public class SetView {

    private Set set;
    private JFormattedTextField timeField;
    private JTextField messageField;
    private JTextField nameField;
    private JTable table;
    private UserInterface userInterface;

    /**
     * SetView constructor
     * @param container the link to main GUI,
     * @param newSet is the Set to display and work on.
     */
    public SetView(UserInterface container, Set newSet){
        set = newSet;
        userInterface = container;
    }

    /**
     * Creates GUI
     * @return JPanel with GUI content
     */
    public JPanel getView(){
        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(BorderLayout.CENTER, displayInterval());
        newPanel.add(BorderLayout.EAST, createSidePanel());

        if(setIsEdited()){
            nameField.setText(set.getName());
        }
        return newPanel;
    }

    /**
     * Creates a JTable with Set data
     * @return Panel with GUI.
     */
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

    /**
     * Creates side panel with buttons
     * @return JPanel with GUI
     */
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

    /**
     * Creats JFormattedTextFields for user input.
     * @return JPanel with GUI.
     */
    public JPanel createInputFields(){
        JPanel inputPanel = new JPanel(new GridLayout(2,1,2,2));

        nameField = new JTextField(10);
        JLabel setName = new JLabel("Set name: ");
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(setName);
        namePanel.add(nameField);

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
        inputPanel.add(fieldPanel);

        return inputPanel;
    }

    /**
     * Formatter for JFormattedTextFields, for formatting input to time format.
     * @param s String with pattern for formatting
     * @return Formatter object
     */
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

    /**
     *  refresh GUI
     */
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

    /**
     * ActionListener for Add button, it add a position(time, message) to Set, and display it on Table
     */
    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            set.addToSchedule(timeField.getText(),messageField.getText());
            refreshTable();
            nameField.setText(name);

        }
    }

    /**
     * ActionListener for Lunch button, starts countdown process for particular Set
     */
    private class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            set.lunch();
        }
    }

    /**
     * ActionListener for Delete button, remove position from Set and table
     */
    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(table.getSelectedRow()!=-1){
                set.removePosition(table.getSelectedRow());
                refreshTable();
            }
        }
    }

    /**
     * Action Listener for Edit button,it enter changes in position, from usr input fields to selected position
     */
    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(table.getSelectedRow()!=-1){
                set.editPosition(table.getSelectedRow(),timeField.getText(),messageField.getText());
                refreshTable();
            }
        }
    }

    /**
     *  Move selected position by one index up in list, or on the end if it was on first position
     */
    private class UpArrowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = (table.getSelectedRow()==-1) ? 0 : table.getSelectedRow();
            set.changePosition(index,(index-1));
            refreshTable();
        }
    }

    /**
     * Move selected position by one index down in list, or on the beginning if it was on last position
     */
    private class DownArrowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = (table.getSelectedRow()==-1) ? table.getRowCount() : table.getSelectedRow();
            set.changePosition(index,(index+1));
            refreshTable();
        }
    }

    /**
     * Approve prepared Set to sets list, and display main GUI
     */
    private class OKListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(setIsEdited()){
                set.setName(nameField.getText());
                userInterface.installPanel(userInterface.getView());
            }else{
                set.setName(nameField.getText());
                if(!set.sumUpTime().equals("00:00"))userInterface.getLibrary().add(set);
                userInterface.installPanel(userInterface.getView());
            }
        }
    }
}
