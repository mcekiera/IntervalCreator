package Interval;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;



public class IntervalTest {
    Interval interval;
    Countdown countdown;

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
        Assert.assertArrayEquals(array, interval.prepareForTable());
    }
    @Test
    public void testEditPosition(){

    }
    @Test
    public void testConvertTimeToInt(){
        String[] nums = {"23","02"};
        int[] ints = {23,2};
        Assert.assertArrayEquals(ints,countdown.convertTimeToInt(nums));
        String[] nums2 = {"05","56"};
        int[] ints2 = {5,56};
        Assert.assertArrayEquals(ints2,countdown.convertTimeToInt(nums2));
    }
    @Test
    public void testSumUpTime(){
        interval.schedule.removeAll(interval.schedule);
        assertEquals("00:00",interval.sumUpTime());
        interval.addToSchedule("00:01","one");
        interval.addToSchedule("00:01","one");
        interval.addToSchedule("00:01","one");
        interval.addToSchedule("00:01","one");
        assertEquals("00:04",interval.sumUpTime());
        interval.addToSchedule("01:01","one");
        interval.addToSchedule("00:59","one");
        interval.addToSchedule("01:20","one");
        interval.addToSchedule("00:01","one");
        assertEquals("03:25",interval.sumUpTime());
    }


} 
