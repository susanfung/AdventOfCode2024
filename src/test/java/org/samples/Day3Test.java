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
        String filePath = "src/test/java/org/samples/Day3Part1TestData.txt";

        List<String> result = day3.findValidMulCalls(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay3Part1_calculateMul() {
        Day3 day3 = new Day3();
        String filePath = "src/test/java/org/samples/Day3Part1TestData.txt";

        int result = day3.calculateMul(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay3Part1_answer() {
        Day3 day3 = new Day3();
        String filePath = "src/test/java/org/samples/Day3Data.txt";

        int result = day3.calculateMul(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay3Part2_findEnabledMulCalls() {
        Day3 day3 = new Day3();
        String filePath = "src/test/java/org/samples/Day3Part2TestData.txt";

        List<String> result = day3.findEnabledMulCalls(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay3Part2_calculateMulWithInstructions() {
        Day3 day3 = new Day3();
        String filePath = "src/test/java/org/samples/Day3Part2TestData.txt";

        int result = day3.calculateMulWithInstructions(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay3Part2_answer() {
        Day3 day3 = new Day3();
        String filePath = "src/test/java/org/samples/Day3Data.txt";

        int result = day3.calculateMulWithInstructions(filePath);

        Approvals.verify(result);
    }
}
