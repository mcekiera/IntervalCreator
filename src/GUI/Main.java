package GUI;

import Interval.Set;

public class Main {
    public static void main(String[] args){
        SingleIntervalView singleIntervalView = new SingleIntervalView();
        Set set = new Set("First");

        singleIntervalView.show();
        singleIntervalView.createSidePanel();
        singleIntervalView.createInputFields();
        singleIntervalView.displayInterval(set);

        //set.lunchInterval();
    }
}
