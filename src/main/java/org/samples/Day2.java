package org.samples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    public int numberOfSafeReports(String filePath) {
        List<int[]> reports = new ArrayList<>();
        int numberofReportsIsSafe = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int[] numbers = new int[parts.length];

                for (int i = 0; i < parts.length; i++) {
                    numbers[i] = Integer.parseInt(parts[i]);
                }

                if (isSafe(numbers)) {
                    numberofReportsIsSafe++;
                }

                reports.add(numbers);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }

        return numberofReportsIsSafe;
    }

    private boolean isSafe(int[] numbers) {
        if (numbers.length <= 1) {
            return true;
        }

        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 1; i < numbers.length; i++) {
            int diff = Math.abs(numbers[i] - numbers[i - 1]);

            boolean validDiff = (diff >= 1 && diff <= 3);

            if (numbers[i] <= numbers[i - 1] || !validDiff) {
                increasing = false;
            }
            if (numbers[i] >= numbers[i - 1] || !validDiff) {
                decreasing = false;
            }
            if (!increasing && !decreasing) {
                return false;
            }
        }

        return increasing || decreasing;
    }

}
