package org.samples;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day6Test {
    @Test
    public void testDay6Part1_calculatePatrolPosition() {
        Day6 day6 = new Day6();
        String filePath = "src/test/java/org/samples/Day6TestData.txt";

        int result = day6.calculatePatrolPositions(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay6Part1_answer() {
        Day6 day6 = new Day6();
        String filePath = "src/test/java/org/samples/Day6Data.txt";

        int result = day6.calculatePatrolPositions(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay6Part2_findLoopingObstructions() {
        Day6 day6 = new Day6();
        String filePath = "src/test/java/org/samples/Day6TestData.txt";

        List<Day6.Position> result = day6.findLoopingObstructions(filePath);

        Approvals.verify(result.size());
    }

    @Test
    public void testDay6Part2_answer() {
        Day6 day6 = new Day6();
        String filePath = "src/test/java/org/samples/Day6Data.txt";

        List<Day6.Position> result = day6.findLoopingObstructions(filePath);

        Approvals.verify(result.size());
    }
}
