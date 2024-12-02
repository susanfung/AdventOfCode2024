package org.samples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day1 {
    public int[] parseColumn(String filePath, int columnNumber) {
        List<Integer> numbers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (columnNumber > 0 && columnNumber <= parts.length) {
                    numbers.add(Integer.parseInt(parts[columnNumber - 1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }

        return numbers.stream().mapToInt(Integer::intValue).toArray();
    }

    public int part1Answer(int[] array1, int[] array2) {
        int[] sortedArray1 = array1.clone();
        int[] sortedArray2 = array2.clone();

        Arrays.sort(sortedArray1);
        Arrays.sort(sortedArray2);

        if (sortedArray1.length != sortedArray2.length) {
            throw new IllegalArgumentException("Arrays must be of equal length");
        }

        int[] differences = new int[sortedArray1.length];

        for (int i = 0; i < sortedArray1.length; i++) {
            differences[i] = Math.abs(sortedArray2[i] - sortedArray1[i]);
        }

        return Arrays.stream(differences).sum();
    }
}
