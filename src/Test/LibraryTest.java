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
        library.addSet(new Set("One"));
        library.addSet(new Set("Two"));
        library.addSet(new Set("Three"));
        assertEquals(3,library.getSize());

    }
}
