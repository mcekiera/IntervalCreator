package Interval;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class IntervalTest {
    Interval interval;

    @Before
    public void before() throws Exception {
        interval = new Interval("int");
        interval.addToSchedule("1","2");
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testToString(){
        assertEquals("int",interval.toString());
    }
    @Test
    public void testPrepareForTable(){
        String[][] array = new String[1][];
        array[0] = new String[] {"1","2"};
        Assert.assertArrayEquals(array, Interval.prepareForTable(interval));
    }
    @Test
    public void testEditPosition(){
        interval.editPosition(0,1,"4");
        String[] test = interval.schedule.get(0);
        assertEquals("4",test[1]);
        interval.editPosition(0,1,"2");
        assertNotSame("4",test[1]);
    }


} 
