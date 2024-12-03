package org.samples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public int mul(int x, int y) {
        return x * y;
    }

    public List<String> findValidMulCalls(String filePath) {
        List<String> validCalls = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    validCalls.add(matcher.group());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return validCalls;
    }

    public int calculateMul(String filePath) {
        List<Integer> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

        List<String> validMulCalls = findValidMulCalls(filePath);

        for (String call : validMulCalls) {
            Matcher matcher = pattern.matcher(call);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                results.add(mul(x, y));
            }
        }

        return results.stream().mapToInt(Integer::intValue).sum();
    }
}
