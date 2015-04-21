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
        countdown = new Countdown();
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
    public void testDisplayTime(){
        countdown.displayTime(2,5);
        assertEquals("02:05",countdown.label.getText());
        countdown.displayTime(22,5);
        assertEquals("22:05",countdown.label.getText());
        countdown.displayTime(2,54);
        assertEquals("02:54",countdown.label.getText());
    }
    //TODO przy 00:00 się wykrzacza :P


} 
