package GUI;

import Interval.Interval;
import org.junit.Before;
import org.junit.Test;

public class SingleIntervalViewTest {
    SingleIntervalView singleIntervalView;
    Interval interval;
    @Before
    public void setUp() throws Exception {
        singleIntervalView = new SingleIntervalView();
        interval = new Interval("New");
        singleIntervalView.show();
        singleIntervalView.createSidePanel();
        singleIntervalView.createInputFields();
        singleIntervalView.displayInterval(interval);
    }
    @Test
    public void testTimeField(){
        singleIntervalView.timeField.setText("4");
        singleIntervalView.add.doClick();
        String[] test = {"40:00",""};
        org.junit.Assert.assertArrayEquals(test,interval.getSchedule().get(0));
        singleIntervalView.timeField.setText("0:4");
        singleIntervalView.add.doClick();
        String[] test2 = {"40:00",""};
        org.junit.Assert.assertArrayEquals(test2,interval.getSchedule().get(0));
    }
}
