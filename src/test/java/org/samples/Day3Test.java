package org.samples;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day3Test {
    @Test
    public void testDay3Part1_mul() {
        Approvals.verify(new Day3().mul(1, 2));
    }

    @Test
    public void testDay3Part1_findValidMulCalls() {
        Day3 day3 = new Day3();
        String filePath = "src/test/java/org/samples/Day3TestData.txt";

        List<String> result = day3.findValidMulCalls(filePath);

        Approvals.verify(result);
    }
}
