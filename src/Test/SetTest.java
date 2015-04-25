package Test;

import Interval.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SetTest {
    Set set;

    @Before
    public void before() throws Exception {
        set = new Set("int");
        set.addToSchedule("1","2");
    }

    @Test
    public void testToString(){
        assertEquals("int", set.toString());
    }
    @Test
    public void testPrepareForTable(){
        String[][] array = new String[1][];
        array[0] = new String[] {"1","2"};
        Assert.assertArrayEquals(array, set.prepareForTable());
    }
    @Test
    public void testSumUpTime(){
        set.getSchedule().removeAll(set.getSchedule());
        assertEquals("00:00", set.sumUpTime());
        set.addToSchedule("00:01","one");
        set.addToSchedule("00:01","one");
        set.addToSchedule("00:01","one");
        set.addToSchedule("00:01","one");
        assertEquals("00:04", set.sumUpTime());
        set.addToSchedule("01:01","one");
        set.addToSchedule("00:59","one");
        set.addToSchedule("01:20","one");
        set.addToSchedule("00:01","one");
        assertEquals("03:25", set.sumUpTime());
    }


} 
