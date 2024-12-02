package org.samples;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Day1Test {
    @Test
    public void testDay1Part1_parseColumn() {
        Day1 day1 = new Day1();
        String filePath = "src/test/java/org/samples/Day1TestData.txt";

        int[] column1 = day1.parseColumn(filePath, 1);
        int[] column2 = day1.parseColumn(filePath, 2);

        String result = "Column 1: " + Arrays.toString(column1) + "\n" +
                "Column 2: " + Arrays.toString(column2);

        Approvals.verify(result);
    }

    @Test
    public void testDay1Part1_part1Answer() {
        Day1 day1 = new Day1();
        String filePath = "./src/test/java/org/samples/Day1TestData.txt";

        int[] column1 = day1.parseColumn(filePath, 1);
        int[] column2 = day1.parseColumn(filePath, 2);

        int result = day1.part1Answer(column1, column2);

        Approvals.verify(result);
    }

    @Test
    public void testDay1Part1_answer() {
        Day1 day1 = new Day1();
        String filePath = "./src/test/java/org/samples/Day1Part1Data.txt";

        int[] column1 = day1.parseColumn(filePath, 1);
        int[] column2 = day1.parseColumn(filePath, 2);

        int result = day1.part1Answer(column1, column2);

        Approvals.verify(result);
    }

    @Test
    public void testDay1Part2_similarityScore() {
        Day1 day1 = new Day1();
        String filePath = "./src/test/java/org/samples/Day1TestData.txt";

        int[] column1 = day1.parseColumn(filePath, 1);
        int[] column2 = day1.parseColumn(filePath, 2);

        int result = day1.similarityScore(column1, column2);

        Approvals.verify(result);
    }

    @Test
    public void testDay1Part2_answer() {
        Day1 day1 = new Day1();
        String filePath = "./src/test/java/org/samples/Day1Data.txt";

        int[] column1 = day1.parseColumn(filePath, 1);
        int[] column2 = day1.parseColumn(filePath, 2);

        int result = day1.similarityScore(column1, column2);

        Approvals.verify(result);
    }
}
