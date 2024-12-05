package org.samples;

import java.io.*;
import java.util.*;

public class Day5 {
    public Map<Integer, Set<Integer>> rules = new HashMap<>();

    public int solvePart1(String filePath) {
        List<String> updates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            parseRules(reader);

            while ((line = reader.readLine()) != null) {
                updates.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return 0;
        }

        int sum = 0;
        for (String update : updates) {
            int[] pages = Arrays.stream(update.split(","))
                               .mapToInt(Integer::parseInt)
                               .toArray();
            if (isValidOrder(pages)) {
                sum += getMiddlePage(pages);
            }
        }
        return sum;
    }

    public void parseRules(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] parts = line.split("\\|");
            int before = Integer.parseInt(parts[0]);
            int after = Integer.parseInt(parts[1]);
            rules.computeIfAbsent(before, k -> new HashSet<>()).add(after);
        }
    }

    public boolean isValidOrder(int[] pages) {
        for (int i = 0; i < pages.length; i++) {
            for (int j = i + 1; j < pages.length; j++) {
                int page1 = pages[i];
                int page2 = pages[j];

                if (rules.containsKey(page2) && rules.get(page2).contains(page1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getMiddlePage(int[] pages) {
        return pages[pages.length / 2];
    }

    public int solvePart2(String filePath) {
        List<String> updates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            parseRules(reader);
            while ((line = reader.readLine()) != null) {
                updates.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return 0;
        }

        int sum = 0;
        for (String update : updates) {
            int[] pages = Arrays.stream(update.split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
            if (!isValidOrder(pages)) {
                int[] correctOrder = getCorrectOrder(pages);
                sum += getMiddlePage(correctOrder);
            }
        }
        return sum;
    }

    private int[] getCorrectOrder(int[] pages) {
        Map<Integer, Set<Integer>> localRules = new HashMap<>();
        Set<Integer> pageSet = new HashSet<>();

        for (int page : pages) {
            pageSet.add(page);
            localRules.put(page, new HashSet<>());
        }

        for (int page1 : pages) {
            if (rules.containsKey(page1)) {
                for (int page2 : rules.get(page1)) {
                    if (pageSet.contains(page2)) {
                        localRules.get(page1).add(page2);
                    }
                }
            }
        }

        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int page : pages) {
            inDegree.put(page, 0);
        }
        for (Map.Entry<Integer, Set<Integer>> entry : localRules.entrySet()) {
            for (int after : entry.getValue()) {
                inDegree.merge(after, 1, Integer::sum);
            }
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        for (int page : pages) {
            if (inDegree.get(page) == 0) {
                queue.offer(page);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int page = queue.poll();
            result.add(page);

            for (int next : localRules.getOrDefault(page, new HashSet<>())) {
                inDegree.merge(next, -1, Integer::sum);
                if (inDegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
