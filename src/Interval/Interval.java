package Interval;

import GUI.Countdown;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Interval {
    protected ArrayList<String[]> schedule;
    private String name;

    public Interval(String name){
        this.name = name;
        schedule = new ArrayList<String[]>();
    }
    public void addToSchedule(String time, String message){
        String[] position = {time, message};
        schedule.add(position);
    }
    public void editPosition(int positionNumber, int positionInArray, String change){
        String[] arrayToEdit = schedule.get(positionNumber);
        arrayToEdit[positionInArray] = change;
    }
    public String toString(){
        return this.name;
    }
    public static Object[][] prepareForTable(Interval interval){
        String[][] list = new String[interval.schedule.size()][];
        for(int i = 0; i<interval.schedule.size(); i++){
            list[i] = interval.schedule.get(i);
        }
        return list;
    }


    public static void main(String[] args){
        JFrame frame = new JFrame();
        Interval interval = new Interval("First");

        interval.addToSchedule("00:45","start");
        interval.addToSchedule("00:15","break");
        interval.addToSchedule("00:45","start");
        interval.addToSchedule("00:15","break");
        interval.addToSchedule("00:30","finish");

        String[] columnNames = {"time", "message"};
        JTable table = new JTable(Interval.prepareForTable(interval),columnNames);

        GUI.Countdown countdown = new Countdown("00:10","RUN Bitch");

        frame.getContentPane().add(BorderLayout.CENTER, table);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
