package Interval;

import java.util.ArrayList;

public class Interval {
    private ArrayList<String> schedule;
    private String name;
    private String message;

    public Interval(String name){
        this.name = name;
        schedule = new ArrayList<String>();
    }

    public void registerTime(String time){
        if(!checkStringFormat(time)) System.out.println("Wrong input format");
        this.schedule.add(time);
    }
    public String extractTime(int index){
        return schedule.get(index);
    }
    static boolean checkStringFormat(String toCheck){
        return (toCheck.matches("^(\\d{2}:\\d{2}:\\d{2}$)"));

        //TODO: ta metoda powinna sprawdzać a później zmianiać format;
    }
}
