package Interval;

import GUI.UserInterface;

import java.util.ArrayList;

public class Interval {
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
    public void lunchInterval(){
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
        countdown.message.setText("koniec");
    }


    public static void main(String[] args){
        UserInterface userInterface = new UserInterface();
        Interval interval = new Interval("First");

        //interval.addToSchedule("00:10","start");
        interval.addToSchedule("00:10","break");
        interval.addToSchedule("00:10","finish");

        String[] columnNames = {"time", "message"};
        userInterface.show();
        userInterface.createSidePanel();
        userInterface.createInputFields();
        userInterface.displayInterval(interval);

        //interval.lunchInterval();
    }
}