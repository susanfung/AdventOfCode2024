package org.samples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    public int numberOfSafeReports(String filePath, int part) {
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

                if (part == 1) {
                    if (isSafePart1(numbers)) {
                        numberofReportsIsSafe++;
                    }
                } else {
                    if (isSafePart2(numbers)) {
                        numberofReportsIsSafe++;
                    }
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

    private boolean isSafePart2(int[] numbers) {
        if (isSafePart1(numbers)) {
            return true;
        }

        for (int i = 0; i < numbers.length; i++) {
            int[] shortened = new int[numbers.length - 1];
            for (int j = 0, k = 0; j < numbers.length; j++) {
                if (j != i) {
                    shortened[k++] = numbers[j];
                }
            }

            if (isSafePart1(shortened)) {
                return true;
            }
        }

        return false;
    }
}
