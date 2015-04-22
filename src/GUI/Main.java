package GUI;

import Interval.Interval;

public class Main {
    public static void main(String[] args){
        SingleIntervalView singleIntervalView = new SingleIntervalView();
        Interval interval = new Interval("First");

        singleIntervalView.show();
        singleIntervalView.createSidePanel();
        singleIntervalView.createInputFields();
        singleIntervalView.displayInterval(interval);

        //interval.lunchInterval();
    }
}
