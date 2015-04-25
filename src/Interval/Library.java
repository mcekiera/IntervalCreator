package Interval;

import java.io.Serializable;
import java.util.ArrayList;

public class Library extends ArrayList<Set> implements Serializable{

    public String[][] extractDataForTable(){
        String[][] forTable = new String[this.size()][3];
        int index = 0;
        for(Set set: this){
            String[] strings = {set.getName(),set.getSize(), set.sumUpTime()};
            forTable[index] = strings;
            index++;
        }
        return forTable;
    }


}
