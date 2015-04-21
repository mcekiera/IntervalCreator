package Interval;

import java.util.ArrayList;

public class Interval implements Runnable{
    protected ArrayList<String[]> schedule;
    private String name;

    public ArrayList<String[]> getSchedule() {
        return schedule;
    }

    public Interval(String name){
        this.name = name;
        schedule = new ArrayList<String[]>();
    }
    public void addToSchedule(String time, String message){
        String[] position = {time, message};
        schedule.add(position);
    }
    public void editPosition(int positionNumber, String time, String message){
        String[] editedArray = {time,message};
        schedule.remove(positionNumber);
        schedule.add(positionNumber,editedArray);
    }
    public void removePosition(int positionNumber){
        schedule.remove(positionNumber);
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
    public void lunchInterval(){
        Thread inter = new Thread(this);
        inter.start();

    }

    @Override
    public void run() {
        String[][] temp = new String[schedule.size()][];
        for(int i=0; i<schedule.size();i++){
            temp[i] = schedule.get(i);
        }
        int index = 0;
        Countdown countdown = new Countdown();
        while(index<temp.length){
            if(index==0){
                countdown.startCountdown(temp[index][0],temp[index][1]);
                index++;
            }
            if(!countdown.isRunning()){
                countdown.startCountdown(temp[index][0],temp[index][1]);
                index++;
            }
        }
    }
}