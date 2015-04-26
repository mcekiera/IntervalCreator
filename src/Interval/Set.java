package Interval;

import GUI.CountdownFrame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class contains an information about set of intervals created by user. It have a name filed for identification
 * in UserInterface table, and schedule ArrayList with set of String, for time to count and message to display.
 */
public class Set implements Runnable,Serializable {
    protected ArrayList<String[]> schedule;
    private String name;

    public Set(){
        schedule = new ArrayList<String[]>();
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getSize(){
        return String.valueOf(schedule.size());
    }
    public void setSchedule(ArrayList<String[]> schedule){
        this.schedule = schedule;
    }
    public ArrayList<String[]> getSchedule() {
        return schedule;
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

    public void changePosition(int positionNumber, int direction){
        ArrayList<String[]> temp = new ArrayList<String[]>();
        for(String[] a:schedule){
            temp.add(a);
        }
        if(direction==-1){
            schedule.remove(0);
            schedule.add(temp.get(0));
        }else if(direction==schedule.size()+1){
            schedule.remove(schedule.size()-1);
            schedule.add(0,temp.get(temp.size()-1));
        }else{
            temp.remove(positionNumber);
            temp.add(positionNumber, schedule.get(direction));
            temp.remove(direction);
            temp.add(direction,schedule.get(positionNumber));
            schedule = temp;

        }

    }
    public String sumUpTime(){
        if(schedule.isEmpty()) return "00:00";

        int min = 0;
        int sec = 0;

        for(String[] part: schedule){
            String[] time = part[0].split(":");
            min += Integer.valueOf(time[0]);
            sec += Integer.valueOf(time[1]);
        }
        min += sec/60;
        sec = sec%60;

        String minStr = (min<10)? "0"+min : String.valueOf(min);
        String secStr = (sec<10)? "0"+sec : String.valueOf(sec);

        return minStr+":"+secStr;
    }

    public String toString(){
        return this.name;
    }

    public Object[][] prepareForTable(){
        String[][] list = new String[this.schedule.size()][];
        for(int i = 0; i<this.schedule.size(); i++){
            list[i] = this.schedule.get(i);
        }
        return list;
    }

    public void lunch(){
        new Thread(this).start();
    }

    public ArrayList<String[]> copySchedule(){
        ArrayList<String[]> copy = new ArrayList<String[]>();
        for(String[] element: schedule){
            copy.add(element);
        }
        return copy;
    }

    @Override
    public void run() {
        int index = 0;
        Countdown totalSetTime = new Countdown(sumUpTime(),"END");
        CountdownFrame countdownFrame = null;
        while(index<schedule.size()){
            if(countdownFrame==null || !countdownFrame.isBusy()){
                countdownFrame = new CountdownFrame(this,totalSetTime);
                index++;
            }
        }
    }
}