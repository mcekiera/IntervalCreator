package Interval;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * It is a small class which instance holds Set class instances, it extends ArrayList to hold Sets, and implements
 * Serializable to enable save method.
 */
public class Library extends ArrayList<Set> implements Serializable{
    /**
     * Prepare data form object, to display on JTable.
     * @return
     */
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
