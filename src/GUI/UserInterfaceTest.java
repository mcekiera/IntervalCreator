package GUI;

import Interval.Interval;
import org.junit.Before;
import org.junit.Test;

public class UserInterfaceTest {
    UserInterface userInterface;
    Interval interval;
    @Before
    public void setUp() throws Exception {
        userInterface = new UserInterface();
        interval = new Interval("New");
        userInterface.show();
        userInterface.createSidePanel();
        userInterface.createInputFields();
        userInterface.displayInterval(interval);
    }
    @Test
    public void testTimeField(){
        userInterface.timeField.setText("4");
        userInterface.add.doClick();
        String[] test = {"40:00",""};
        org.junit.Assert.assertArrayEquals(test,interval.getSchedule().get(0));
        userInterface.timeField.setText("0:4");
        userInterface.add.doClick();
        String[] test2 = {"40:00",""};
        org.junit.Assert.assertArrayEquals(test2,interval.getSchedule().get(0));
    }
}
