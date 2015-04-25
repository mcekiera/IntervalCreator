package GUI;

import Interval.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface{
    JFrame frame;
    Library library;
    JPanel mainPanel;
    SetView setView;

    public UserInterface(){
        library = new Library();
        setView = new SetView(this);
    }
    public void show(){
        frame = new JFrame("Interval Creator");

        mainPanel = mainView();
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setVisible(true);
        mainView();
    }
    public JPanel mainView(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, createTable());
        panel.add(BorderLayout.EAST,createButtons());
        return panel;
    }

    public JPanel createButtons(){
        JPanel buttonsPanel = new JPanel(new GridLayout(8,1,3,3));

        JButton newSet = new JButton("New");
        newSet.addActionListener(new newListener());
        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");
        JButton lunch = new JButton("Lunch");
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
        JTable table = new JTable(library.extractDataForTable(),columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }


    private class newListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            frame.remove(mainPanel);
            mainPanel = setView.getSetView();
            frame.add(mainPanel);
            frame.revalidate();
            frame.setVisible(true);
        }
    }
}
