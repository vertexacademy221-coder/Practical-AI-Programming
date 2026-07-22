package com.vertexacademy.neuralnetwork;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class DigitalComparatorTest {

    @Test
    public void testDigitalComparator() {

        DigitalComparator dc = new DigitalComparator();

        assertArrayEquals("a should be equal to b",     dc.compute(new double[]{0, 0}), new double[]{0, 1, 0}, 0.0);
        assertArrayEquals("a should be less than b",    dc.compute(new double[]{0, 1}), new double[]{0, 0, 1}, 0.0);
        assertArrayEquals("a should be greater than b", dc.compute(new double[]{1, 0}), new double[]{1, 0, 0}, 0.0);
        assertArrayEquals("a should be equal to b",     dc.compute(new double[]{1, 1}), new double[]{0, 1, 0}, 0.0);
    }
}