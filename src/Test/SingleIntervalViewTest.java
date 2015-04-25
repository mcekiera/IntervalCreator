package Test;

import GUI.SetView;
import Interval.Set;
import org.junit.Before;
import org.junit.Test;

public class SingleIntervalViewTest {
    SetView setView;
    Set set;
    @Before
    public void setUp() throws Exception {
        setView = new SetView(null,null);
        set = new Set();
        setView.createSidePanel();
        setView.createInputFields();
        setView.displayInterval(set);
    }
    @Test
    public void testTimeField(){
        setView.setTimeField("4");
        setView.getAdd().doClick();
        String[] test = {"40:00",""};
        org.junit.Assert.assertArrayEquals(test, set.getSchedule().get(0));
        setView.setTimeField("0:4");
        setView.getAdd().doClick();
        String[] test2 = {"40:00",""};
        org.junit.Assert.assertArrayEquals(test2, set.getSchedule().get(0));
    }
}
