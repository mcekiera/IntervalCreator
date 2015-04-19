package Interval;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class IntervalTest {
    Interval interval;

    @Before
    public void before() throws Exception {
        interval = new Interval("int");
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testToString(){
        assertEquals("int",interval.toString());
    }


} 
