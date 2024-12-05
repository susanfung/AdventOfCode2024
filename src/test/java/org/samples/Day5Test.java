package org.samples;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day5Test {
    @Test
    public void testDay5Part1_parseRules() throws IOException {
        Day5 day5 = new Day5();
        String filePath = "src/test/java/org/samples/Day5TestData.txt";

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        day5.parseRules(reader);

        Approvals.verify(day5.rules);
    }

    @Test
    public void testDay5Part1_isValidOrder() throws IOException {
        Day5 day5 = new Day5();
        String filePath = "src/test/java/org/samples/Day5TestData.txt";

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        day5.parseRules(reader);

        String update = "75,47,61,53,29";

        int[] pages = Arrays.stream(update.split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();

        Approvals.verify(day5.isValidOrder(pages));
    }

    @Test
    public void testDay5Part1_solvePart1() {
        Day5 day5 = new Day5();
        String filePath = "src/test/java/org/samples/Day5TestData.txt";

        int result = day5.solvePart1(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay5Part1_answer() {
        Day5 day5 = new Day5();
        String filePath = "src/test/java/org/samples/Day5Data.txt";

        int result = day5.solvePart1(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay5Part2_solvePart2() {
        Day5 day5 = new Day5();
        String filePath = "src/test/java/org/samples/Day5TestData.txt";

        int result = day5.solvePart2(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay5Part2_answer() {
        Day5 day5 = new Day5();
        String filePath = "src/test/java/org/samples/Day5Data.txt";

        int result = day5.solvePart2(filePath);

        Approvals.verify(result);
    }
}
