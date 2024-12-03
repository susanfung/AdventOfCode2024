package org.samples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

    public List<String> findEnabledMulCalls(String filePath) {
        List<String> enabledCalls = new ArrayList<>();
        Pattern mulPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Pattern doPattern = Pattern.compile("do\\(\\)");
        Pattern dontPattern = Pattern.compile("don't\\(\\)");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean enabled = true;

            while ((line = reader.readLine()) != null) {
                List<Instruction> instructions = new ArrayList<>();

                Matcher mulMatcher = mulPattern.matcher(line);
                while (mulMatcher.find()) {
                    instructions.add(new Instruction(mulMatcher.start(), "mul", mulMatcher.group()));
                }

                Matcher doMatcher = doPattern.matcher(line);
                while (doMatcher.find()) {
                    instructions.add(new Instruction(doMatcher.start(), "do", null));
                }

                Matcher dontMatcher = dontPattern.matcher(line);
                while (dontMatcher.find()) {
                    instructions.add(new Instruction(dontMatcher.start(), "dont", null));
                }

                instructions.sort(Comparator.comparingInt(i -> i.position));

                for (Instruction inst : instructions) {
                    switch (inst.type) {
                        case "do":
                            enabled = true;
                            break;
                        case "dont":
                            enabled = false;
                            break;
                        case "mul":
                            if (enabled) {
                                enabledCalls.add(inst.value);
                            }
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return enabledCalls;
    }

private static class Instruction {
    int position;
    String type;
    String value;

    Instruction(int position, String type, String value) {
        this.position = position;
        this.type = type;
        this.value = value;
    }
}

    public int calculateMulWithInstructions(String filePath) {
        List<String> enabledCalls = findEnabledMulCalls(filePath);
        return calculateMul(enabledCalls);
    }

    public int calculateMul(List<String> mulCalls) {
        List<Integer> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

        for (String call : mulCalls) {
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
