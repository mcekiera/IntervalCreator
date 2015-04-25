package Interval;

import java.util.ArrayList;

public class Library {
    private ArrayList<Set> data;

    public Library(){
        data = new ArrayList<Set>();
    }

    public int getSize(){
        return data.size();
    }

    public void addSet(Set set){
        data.add(set);
    }

    public void removeSet(int position){
        data.remove(position);
    }

    public String[][] extractDataForTable(){
        String[][] forTable = new String[data.size()][3];
        int index = 0;
        for(Set set: data){
            String[] strings = {set.getName(),set.getSize(), set.sumUpTime()};
            forTable[index] = strings;
            index++;
        }
        return forTable;
    }


}
