package Interval;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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
public void testRegisterAndExtractTime() throws Exception {
    String time = "00:45:23";
    interval.registerTime(time);
    assertEquals(time,interval.extractTime(0));
} 

@Test
public void testCheckStringFormat() throws Exception {
    assertTrue(Interval.checkStringFormat("00:00:00"));
    assertFalse(Interval.checkStringFormat("000000"));
} 


} 
