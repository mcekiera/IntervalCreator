package GUI;

import Interval.Library;
import Interval.Set;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Provide main GUI, with JTable of all Sets available.
 */
public class UserInterface{
    private JFrame frame;
    private Library library;
    private JPanel mainPanel;
    private JTable table;

    /**
     * constructor without parameters, however it declares Library object
     */
    public UserInterface(){
        library = new Library();

    }

    /**
     * @return UserInterfaces Library object
     */
    public Library getLibrary(){
        return library;
    }

    /**
     * create and display GUI
     */
    public void show(){
        frame = new JFrame("Interval Creator");

        mainPanel = getView();
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(350,250);
        frame.setVisible(true);
    }

    /**
     * creates JPanel with JTable and JButton. This part is replaced if change view for SetView
     * @return JPanel with GUI
     */
    public JPanel getView(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, createTable());
        panel.add(BorderLayout.EAST,createButtons());
        return panel;
    }

    /**
     * creates buttons
     * @return JPanel with buttons
     */
    public JPanel createButtons(){
        JPanel buttonsPanel = new JPanel(new GridLayout(8,1,3,3));

        JButton newSet = new JButton("New");
        newSet.addActionListener(new NewListener());
        JButton edit = new JButton("Edit");
        edit.addActionListener(new EditListener());
        JButton delete = new JButton("Delete");
        delete.addActionListener(new DeleteListener());
        JButton lunch = new JButton("Lunch");
        lunch.addActionListener(new LunchListener());
        JButton save = new JButton("Save");
        save.addActionListener(new SaveListener());
        JButton load = new JButton("Load");
        load.addActionListener(new LoadListener());

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

    /**
     * Creates JTable with Sets list.
     * @return JScrollPane with JTable
     */
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

    /**
     * Display a SetView.
     * @param panel to add to frame
     */
    public void installPanel(JPanel panel){
        frame.setVisible(false);
        frame.remove(mainPanel);
        mainPanel = panel;
        frame.add(mainPanel);
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * Method for dealing with FileNotFoundException;
     * @param ex cached exception
     */
    public void fileNotFound(Exception ex){
        ex.printStackTrace();
        JOptionPane.showMessageDialog(frame,"File not found","Error",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * method checks if any row is selected.
     * @return boolean, true if yes.
     */
    public boolean rowIsNotSelected(){
        return table.getSelectedRow()==-1;
    }

    /**
     * New button ActionListener, open SetView and creates new Set object
     */
    private class NewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            SetView setView = new SetView(UserInterface.this, new Set());
            installPanel(setView.getView());
        }
    }

    /**
     * Lunch button ActionListener, lunch countdown process of selected Set.
     */
    private class LunchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(rowIsNotSelected()) return;
            library.get(table.getSelectedRow()).lunch();
        }
    }

    /**
     * Edit button Action Listener, open SetView for already created Set object.
     */
    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(rowIsNotSelected()) return;
            Set selectedSet = library.get(table.getSelectedRow());
            SetView setView = new SetView(UserInterface.this, selectedSet);
            installPanel(setView.getView());
        }
    }

    /**
     * Delete button Action Listener, remove Set form list delete reference to Set object.
     */
    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(rowIsNotSelected()) return;
            library.remove(table.getSelectedRow());
            installPanel(getView());
        }
    }

    /**
     * Save button Action Listener, save Sets list to chosen file
     */
    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showSaveDialog(frame);
                File file = fileChooser.getSelectedFile().getAbsolutePath().endsWith(".ser") ?
                        fileChooser.getSelectedFile() : new File(fileChooser.getSelectedFile() + ".ser");
                OutputStream stream = new FileOutputStream(file);
                OutputStream buffer = new BufferedOutputStream(stream);
                ObjectOutput object = new ObjectOutputStream(buffer);
                object.writeObject(library);
                object.close();
                JOptionPane.showMessageDialog(frame,"Saving completed","Save",JOptionPane.INFORMATION_MESSAGE);

            }catch (IOException ex){
                fileNotFound(ex);
            }
        }
    }

    /**
     * Load button Action Listener, load chosen file
     */
    private class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(frame);
                InputStream file = new FileInputStream(fileChooser.getSelectedFile());
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput object = new ObjectInputStream(buffer);
                library = (Library)(object.readObject());
                object.close();
                JOptionPane.showMessageDialog(frame,"Loading completed","Load",JOptionPane.INFORMATION_MESSAGE);
            }catch (IOException ex){
                fileNotFound(ex);
            }catch (ClassNotFoundException ex){
                ex.printStackTrace();
                library = null;
            }
            installPanel(getView());
        }
    }
}
