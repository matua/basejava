package com.matuageorge.webapp;

import junit.framework.TestCase;

import static com.matuageorge.webapp.Streams.minValue;

public class StreamsTest extends TestCase {

    public void testMinValue() {
        assertEquals(89, minValue(new int[]{9, 8}));
        assertEquals(123, minValue(new int[]{1, 2, 3, 3, 2, 3}));
        assertEquals(1246, minValue(new int[]{0, 1, 6, 4, 2, 4}));
    }
}