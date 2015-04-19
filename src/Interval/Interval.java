package Interval;

import java.util.ArrayList;

public class Interval {
    protected ArrayList<String[]> schedule;
    private String name;


    public Interval(String name){
        this.name = name;
        schedule = new ArrayList<String[]>();
    }
    public void addToSchedule(String time, String message, String alarm){
        String[] position = {time, message, alarm};
        schedule.add(position);

    }

    public String toString(){
        return this.name;
    }
}
