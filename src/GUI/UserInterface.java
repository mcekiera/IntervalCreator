package GUI;

import Interval.Library;
import Interval.Set;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface{
    private JFrame frame;
    private Library library;
    private JPanel mainPanel;
    private JTable table;

    public UserInterface(){
        library = new Library();

    }
    public Library getLibrary(){
        return library;
    }
    public void show(){
        frame = new JFrame("Interval Creator");

        mainPanel = getView();
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setVisible(true);
    }
    public JPanel getView(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, createTable());
        panel.add(BorderLayout.EAST,createButtons());
        return panel;
    }

    public JPanel createButtons(){
        JPanel buttonsPanel = new JPanel(new GridLayout(8,1,3,3));

        JButton newSet = new JButton("New");
        newSet.addActionListener(new NewListener());
        JButton edit = new JButton("Edit");
        edit.addActionListener(new EditListener());
        JButton delete = new JButton("Delete");
        JButton lunch = new JButton("Lunch");
        lunch.addActionListener(new LunchListener());
        JButton save = new JButton("Save");
        JButton load = new JButton("Load");

        buttonsPanel.add(newSet);
        buttonsPanel.add(edit);
        buttonsPanel.add(delete);
        buttonsPanel.add(new JPanel());
        buttonsPanel.add(lunch);
        buttonsPanel.add(new JPanel());
        buttonsPanel.add(save);
        buttonsPanel.add(load);

        return buttonsPanel;
    }

    public JScrollPane createTable(){
        String[] columnNames = {"Name","Steps","Duration"};
        table = new JTable(library.extractDataForTable(),columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    public void installPanel(JPanel panel){
        frame.setVisible(false);
        frame.remove(mainPanel);
        mainPanel = panel;
        frame.add(mainPanel);
        frame.revalidate();
        frame.setVisible(true);
    }


    private class NewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            SetView setView = new SetView(UserInterface.this, new Set());
            installPanel(setView.getView());
        }
    }

    private class LunchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            library.get(table.getSelectedRow()).lunch();
        }
    }

    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Set selectedSet = library.get(table.getSelectedRow());
            SetView setView = new SetView(UserInterface.this, selectedSet);
            installPanel(setView.getView());
        }
    }
}
