package Test;

import GUI.SingleIntervalView;
import Interval.Set;
import org.junit.Before;
import org.junit.Test;

public class SingleIntervalViewTest {
    SingleIntervalView singleIntervalView;
    Set set;
    @Before
    public void setUp() throws Exception {
        singleIntervalView = new SingleIntervalView();
        set = new Set("New");
        singleIntervalView.show();
        singleIntervalView.createSidePanel();
        singleIntervalView.createInputFields();
        singleIntervalView.displayInterval(set);
    }
    @Test
    public void testTimeField(){
        singleIntervalView.setTimeField("4");
        singleIntervalView.getAdd().doClick();
        String[] test = {"40:00",""};
        org.junit.Assert.assertArrayEquals(test, set.getSchedule().get(0));
        singleIntervalView.setTimeField("0:4");
        singleIntervalView.getAdd().doClick();
        String[] test2 = {"40:00",""};
        org.junit.Assert.assertArrayEquals(test2, set.getSchedule().get(0));
    }
}
