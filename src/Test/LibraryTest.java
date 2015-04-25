package Test;

import Interval.Library;
import Interval.Set;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LibraryTest {
    Library library;

    @Before
    public void setUp(){
        library = new Library();
    }

    @Test
    public void testAddSet(){
        library.add(new Set());
        library.add(new Set());
        library.add(new Set());
        assertEquals(3,library.size());
    }
}
