package org.samples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day7 {
    private long evaluateExpressionPart1(List<Integer> numbers, List<Character> operators) {
        long result = numbers.get(0);
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '+') {
                result += numbers.get(i + 1);
            } else {
                result *= numbers.get(i + 1);
            }
        }
        return result;
    }

    private boolean canEquationBeSolvedPart1(long testValue, List<Integer> numbers) {
        if (numbers.size() == 1) {
            return testValue == numbers.get(0);
        }

        int operatorsCount = numbers.size() - 1;
        int combinations = 1 << operatorsCount; // 2^n combinations

        for (int i = 0; i < combinations; i++) {
            List<Character> operators = new ArrayList<>();
            int temp = i;
            for (int j = 0; j < operatorsCount; j++) {
                operators.add((temp & 1) == 0 ? '+' : '*');
                temp >>= 1;
            }

            long result = evaluateExpressionPart1(numbers, operators);
            if (result == testValue) {
                return true;
            }
        }
        return false;
    }

    public long totalCalibrationPart1(String filePath) {
        long total = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                long testValue = Long.parseLong(parts[0]);

                String[] numberStrings = parts[1].trim().split("\\s+");
                List<Integer> numbers = new ArrayList<>();
                for (String num : numberStrings) {
                    numbers.add(Integer.parseInt(num));
                }

                if (canEquationBeSolvedPart1(testValue, numbers)) {
                    total += testValue;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        return total;
    }

    private long evaluateExpressionPart2(List<Integer> numbers, List<Character> operators) {
        long result = numbers.get(0);
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '+') {
                result += numbers.get(i + 1);
            } else if (operators.get(i) == '*') {
                result *= numbers.get(i + 1);
            } else if (operators.get(i) == '|') {
                String concatenated = String.valueOf(result) + numbers.get(i + 1);
                result = Long.parseLong(concatenated);
            }
        }
        return result;
    }

    private boolean canEquationBeSolvedPart2(long testValue, List<Integer> numbers) {
        if (numbers.size() == 1) {
            return testValue == numbers.get(0);
        }

        int operatorsCount = numbers.size() - 1;
        int combinations = (int) Math.pow(3, operatorsCount);

        for (int i = 0; i < combinations; i++) {
            List<Character> operators = new ArrayList<>();
            int temp = i;
            for (int j = 0; j < operatorsCount; j++) {
                int operatorChoice = temp % 3;
                char operator;
                if (operatorChoice == 0) {
                    operator = '+';
                } else if (operatorChoice == 1) {
                    operator = '*';
                } else if (operatorChoice == 2) {
                    operator = '|';
                } else {
                    throw new IllegalStateException("Unexpected value: " + operatorChoice);
                }
                operators.add(operator);

                temp /= 3;
            }

            long result = evaluateExpressionPart2(numbers, operators);
            if (result == testValue) {
                return true;
            }
        }
        return false;
    }

    public long totalCalibrationPart2(String filePath) {
        long total = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                long testValue = Long.parseLong(parts[0]);

                String[] numberStrings = parts[1].trim().split("\\s+");
                List<Integer> numbers = new ArrayList<>();
                for (String num : numberStrings) {
                    numbers.add(Integer.parseInt(num));
                }

                if (canEquationBeSolvedPart2(testValue, numbers)) {
                    total += testValue;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        return total;
    }
}
