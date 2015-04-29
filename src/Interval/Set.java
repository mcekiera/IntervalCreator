package Interval;

import GUI.CountdownFrame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class contains an information about set of intervals created by user. It have a name, and array containing
 * a set of String: time to countdown and message displayed in a meantime.
 */
public class Set implements Runnable,Serializable {
    protected ArrayList<String[]> schedule;
    private String name;

    /**
     * Constructor don't have parameters, but it declares an ArrayList to containing data.
     */
    public Set(){
        schedule = new ArrayList<String[]>();
    }

    /** Class getter for name field.
     * @return Set name;
     */
    public String getName(){
        return name;
    }

    /** Class setter for name field.
     * @param name is an user input String object.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @return an ArrayList length, the number of its elements - number of steps in set.
     */
    public String getSize(){
        return String.valueOf(schedule.size());
    }

    /**
     * @return ArrayList containing data.
     */
    public ArrayList<String[]> getSchedule() {
        return schedule;
    }

    /**
     * Add a position to ArrayList.
     * @param time to countdown,
     * @param message to display,
     */
    public void addToSchedule(String time, String message){
        String[] position = {time, message};
        schedule.add(position);
    }

    /**
     * Method edit position already in ArrayList with data.
     * @param positionNumber index of object in ArrayList,
     * @param time edited
     * @param message edited
     */
    public void editPosition(int positionNumber, String time, String message){
        String[] editedArray = {time,message};
        schedule.remove(positionNumber);
        schedule.add(positionNumber,editedArray);
    }

    /**
     * remove a position from ArrayList
     * @param positionNumber index of object to remove
     */
    public void removePosition(int positionNumber){
        schedule.remove(positionNumber);
    }

    /**
     * Change a position of particular object, but not object itself.
     * @param positionNumber from which object is replaced
     * @param direction to which object is replaced
     */
    public void changePosition(int positionNumber, int direction){
        ArrayList<String[]> temp = new ArrayList<String[]>();
        for(String[] a:schedule){
            temp.add(a);
        }
        try{
            if(direction==-1){
                System.out.println(positionNumber+direction);
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
        }catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
        }

    }

    /**
     * Method sums up a time value from all Strings in ArrayList.
     * @return String with summed value in format "mm:ss"
     */
    public String sumUpTime(){
        if(schedule.isEmpty()) return "00:00";


        int min = 0;
        int sec = 0;


        for(String[] part: schedule){
            String[] time = part[0].split(":");
            min += Integer.valueOf(time[0]);
            sec += Integer.valueOf(time[1]);
        }
        sec += (min+sec==0) ? 0 : schedule.size()-1;
        // This line adds to summed time of schedule an additional value
        // of seconds, which equals schedule.size decremented by 1. Because of that,
        // it is possible to display partly countdown to 00:00 value, and maintaining
        // a compatibility of partial time and summed time.

        min += sec/60;
        sec = sec%60;

        String minStr = (min<10)? "0"+min : String.valueOf(min);
        String secStr = (sec<10)? "0"+sec : String.valueOf(sec);

        return minStr+":"+secStr;
    }

    /**
     * @return name for display
     */
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

    /**
     * Method starts countdown process in new Thread
     */
    public void lunch(){
        new Thread(this).start();
    }

    @Override
    public void run() {
        new CountdownFrame(this,new Countdown(sumUpTime(),""));
    }
}