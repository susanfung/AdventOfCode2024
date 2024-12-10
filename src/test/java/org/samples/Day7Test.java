package org.samples;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class Day7Test {
    @Test
    public void testDay7Part1_totalCalibration() {
        Day7 day7 = new Day7();
        String filePath = "src/test/java/org/samples/Day7TestData.txt";

        long result = day7.totalCalibrationPart1(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay7Part1_answer() {
        Day7 day7 = new Day7();
        String filePath = "src/test/java/org/samples/Day7Data.txt";

        long result = day7.totalCalibrationPart1(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay7Part2_totalCalibration() {
        Day7 day7 = new Day7();
        String filePath = "src/test/java/org/samples/Day7TestData.txt";

        long result = day7.totalCalibrationPart2(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay7Part2_answer() {
        Day7 day7 = new Day7();
        String filePath = "src/test/java/org/samples/Day7Data.txt";

        long result = day7.totalCalibrationPart2(filePath);

        Approvals.verify(result);
    }
}
