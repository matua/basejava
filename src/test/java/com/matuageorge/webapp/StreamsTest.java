package com.matuageorge.webapp;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.matuageorge.webapp.Streams.minValue;
import static com.matuageorge.webapp.Streams.oddOrEven;

public class StreamsTest extends TestCase {

    public void testMinValue() {
        assertEquals(89, minValue(new int[]{9, 8}));
        assertEquals(123, minValue(new int[]{1, 2, 3, 3, 2, 3}));
        assertEquals(12359, minValue(new int[]{3, 2, 0, 5, 2, 9, 1, 0}));
    }

    public void testOddOrEven() {
        List<Integer> many = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            many.add((int) (Math.random() * 10));
        }

        List<Integer> tooMany = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            tooMany.add((int) (Math.random() * 10));
        }

        List<Integer> extremelyMany = new ArrayList<>();
        for (int i = 0; i < 100_000_000; i++) {
            extremelyMany.add((int) (Math.random() * 10));
        }

        List<Integer> expectedResult1 = Arrays.asList(8);
        List<Integer> expectedResult2 = Arrays.asList(1, 3, 3, 3);
        List<Integer> expectedResult3 = Arrays.asList(3, 5, 9, 1);
        assertEquals(expectedResult1, oddOrEven(Arrays.asList(8, 9)));
        assertEquals(expectedResult2, oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3)));
        assertEquals(expectedResult3, oddOrEven(Arrays.asList(3, 2, 0, 5, 2, 9, 1, 0)));


        long start = System.currentTimeMillis();
        oddOrEven(many);
        System.out.format("Many: %d%n", System.currentTimeMillis() - start);


        long start1 = System.currentTimeMillis();
        oddOrEven(tooMany);
        System.out.format("Too many: %d%n", System.currentTimeMillis() - start1);


        long start2 = System.currentTimeMillis();
        oddOrEven(extremelyMany);
        System.out.format("Extremely many: %d%n", System.currentTimeMillis() - start2);

    }
}