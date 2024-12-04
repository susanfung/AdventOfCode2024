package org.samples;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day4Test {
    @Test
    public void testDay4Part1_findXmasWithMarkers() throws IOException {
        Day4 day4 = new Day4();
        String filePath = "src/test/java/org/samples/Day4TestData.txt";

        String result = day4.findXmasWithMarkers(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay4Part1_answer() throws IOException {
        Day4 day4 = new Day4();
        String filePath = "src/test/java/org/samples/Day4Data.txt";

        String result = day4.findXmasWithMarkers(filePath);

        Approvals.verify(result);
    }

    @Test
    public void testDay4Part2_findXMas() throws IOException {
        Day4 day4 = new Day4();
        String filePath = "src/test/java/org/samples/Day4TestData.txt";

        String result = day4.findXMas(filePath);

        Approvals.verify(result);
    }
}
