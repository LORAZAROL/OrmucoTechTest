package techtest.com;

import org.junit.Test;

import static org.junit.Assert.*;

public class QB_MainTest {

    @Test
    public void compare() {
        // typical test case: v1 and v2 are the same length
        assertEquals(QB_Main.compare("1.0.2", "1.0.1"), QB_Main.Comparator.Greater);
        assertEquals(QB_Main.compare("3.3.1", "4.3.5"), QB_Main.Comparator.Less);
        assertEquals(QB_Main.compare("1.0.1", "1.0.1"), QB_Main.Comparator.Equal);

        // v1, v2 with different lengths
        assertEquals(QB_Main.compare("4.2", "1.5.1"), QB_Main.Comparator.Greater);
        assertEquals(QB_Main.compare("1.0", "1.0.1"), QB_Main.Comparator.Less);

        // Invalid input: wrong delimiter
        assertEquals(QB_Main.compare("1;0;2", "1.0.1"), QB_Main.Comparator.InvalidInput);
        assertEquals(QB_Main.compare("1;0&2", "1&0.1"), QB_Main.Comparator.InvalidInput);

        // Invalid input: contains non-integer characters
        assertEquals(QB_Main.compare("23*&#", "1.0.1"), QB_Main.Comparator.InvalidInput);
        assertEquals(QB_Main.compare("1.0.2", "weqwe"), QB_Main.Comparator.InvalidInput);
        assertEquals(QB_Main.compare("asdcx", "weqwe"), QB_Main.Comparator.InvalidInput);
    }
}