package org.samples;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class Day2Test {
    @Test
    public void testDay2Part1_numberOfSafeReports() {
        Day2 day2 = new Day2();
        String filePath = "src/test/java/org/samples/Day2TestData.txt";
        int result = day2.numberOfSafeReports(filePath);

        Approvals.verify(result);
    }
}
