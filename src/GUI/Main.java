package GUI;

import Interval.Interval;

public class Main {
    public static void main(String[] args){
        UserInterface userInterface = new UserInterface();
        Interval interval = new Interval("First");

        interval.addToSchedule("00:02","start");
        interval.addToSchedule("00:02","break");
        interval.addToSchedule("00:02","finish");

        userInterface.show();
        userInterface.createSidePanel();
        userInterface.createInputFields();
        userInterface.displayInterval(interval);

        //interval.lunchInterval();
    }
}
