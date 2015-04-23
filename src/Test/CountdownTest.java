package Test;

import Interval.Countdown;
import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CountdownTest
{
    @Test
    public void parser_valid()
    {
        Assert.assertEquals("00:00", new Countdown("0:00", null).toReadableString());
        assertEquals( "10:15", new Countdown( "10:15", null ).toReadableString() );
    }

    public void parser_invalid()
    {
        // TODO
        // new Countdown( "", null ));
        // new Countdown( "0", null ));
        // new Countdown( "0:0:0", null ));
        // new Countdown( "0:70", null ));
    }

    @Test
    public void isFinished()
    {
        assertTrue( new Countdown( "0:00", null ).isFinished() );
        assertFalse( new Countdown( "0:01", null ).isFinished() );
        assertFalse( new Countdown( "1:00", null ).isFinished() );
        assertFalse( new Countdown( "3:25", null ).isFinished() );
    }

    @Test
    public void isCloseToEnd()
    {
        assertTrue( new Countdown( "0:00", null ).isCloseToEnd() );
        assertTrue( new Countdown( "0:01", null ).isCloseToEnd() );
        assertTrue( new Countdown( "0:05", null ).isCloseToEnd() );
        assertFalse( new Countdown( "0:06", null ).isCloseToEnd() );
        assertFalse( new Countdown( "1:00", null ).isCloseToEnd() );
        assertFalse( new Countdown( "3:25", null ).isCloseToEnd() );
    }

    @Test
    public void decrement()
    {
        assertEquals( "00:00",  new Countdown( "0:00", null ).decrement().toReadableString() );
        assertEquals( "00:00",  new Countdown( "0:01", null ).decrement().toReadableString() );
        assertEquals( "00:04",  new Countdown( "0:05", null ).decrement().toReadableString() );
        assertEquals( "00:59",  new Countdown( "1:00", null ).decrement().toReadableString() );
        assertEquals( "03:59",  new Countdown( "4:00", null ).decrement().toReadableString() );
        assertEquals( "04:00",  new Countdown( "4:01", null ).decrement().toReadableString() );
    }

}
