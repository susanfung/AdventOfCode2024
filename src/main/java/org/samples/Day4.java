package org.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day4 {
    private static final String TARGET = "XMAS";

    public String findXmasWithMarkers(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        char[][] grid = linesToGrid(lines);
        List<List<Point>> allXmasWords = new ArrayList<>();

        int rows = grid.length;
        int cols = grid[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (col + 3 < cols) {
                    checkWordAndAdd(grid, row, col, 0, 1, allXmasWords);  // right
                }
                if (col - 3 >= 0) {
                    checkWordAndAdd(grid, row, col, 0, -1, allXmasWords);   // left
                }
                if (row + 3 < rows) {
                    checkWordAndAdd(grid, row, col, 1, 0, allXmasWords);  // down
                }
                if (row - 3 >= 0) {
                    checkWordAndAdd(grid, row, col, -1, 0, allXmasWords);   // up
                }
                if (row + 3 < rows && col + 3 < cols) {
                    checkWordAndAdd(grid, row, col, 1, 1, allXmasWords);     // down-right
                }
                if (row - 3 >= 0 && col - 3 >= 0) {
                    checkWordAndAdd(grid, row, col, -1, -1, allXmasWords);   // up-left
                }
                if (row + 3 < rows && col - 3 >= 0) {
                    checkWordAndAdd(grid, row, col, 1, -1, allXmasWords);    // down-left
                }
                if (row - 3 >= 0 && col + 3 < cols) {
                    checkWordAndAdd(grid, row, col, -1, 1, allXmasWords);    // up-right
                }
            }
        }

        char[][] outputGrid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                outputGrid[i][j] = '.';
            }
        }

        for (int i = 0; i < allXmasWords.size(); i++) {
            List<Point> xmasPoints = allXmasWords.get(i);
            char marker = (char) ('0' + (i + 1));
            if (i >= 9) {
                marker = (char) ('A' + (i - 9));
            }
            for (Point p : xmasPoints) {
                outputGrid[p.x][p.y] = marker;
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Found ").append(allXmasWords.size()).append(" XMAS words:\n\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.append(outputGrid[i][j]);
            }
            result.append('\n');
        }
        return result.toString();
    }

    private void checkWordAndAdd(char[][] grid, int startRow, int startCol,
                                 int rowDelta, int colDelta, List<List<Point>> allXmasWords) {
        List<Point> currentPoints = new ArrayList<>();
        boolean isMatch = true;

        for (int i = 0; i < TARGET.length(); i++) {
            int newRow = startRow + (i * rowDelta);
            int newCol = startCol + (i * colDelta);

            if (grid[newRow][newCol] != TARGET.charAt(i)) {
                isMatch = false;
                break;
            }
            currentPoints.add(new Point(newRow, newCol));
        }

        if (isMatch) {
            allXmasWords.add(currentPoints);
        }
    }

    public String findXMas(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        char[][] grid = linesToGrid(lines);
        List<List<Point>> allXMasPatterns = new ArrayList<>();

        int rows = grid.length;
        int cols = grid[0].length;

        // Check each position as potential center of X
        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                if (grid[row][col] == 'A') {  // Center must be 'A'
                    checkXPattern(grid, row, col, allXMasPatterns);
                }
            }
        }

        // Create output grid
        char[][] outputGrid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                outputGrid[i][j] = '.';
            }
        }

        // Mark found patterns
        for (List<Point> pattern : allXMasPatterns) {
            for (Point p : pattern) {
                outputGrid[p.x][p.y] = grid[p.x][p.y];
            }
        }

        // Create output string
        StringBuilder result = new StringBuilder();
        result.append("Found ").append(allXMasPatterns.size()).append(" X-MAS patterns:\n\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.append(outputGrid[i][j]);
            }
            result.append('\n');
        }
        return result.toString();
    }

    private void checkXPattern(char[][] grid, int centerRow, int centerCol,
                               List<List<Point>> allPatterns) {
        // Check first diagonal (top-left to bottom-right)
        List<Point> diagonal1Forward = checkDiagonal(grid, centerRow - 1, centerCol - 1, centerRow + 1, centerCol + 1, false);
        List<Point> diagonal1Backward = checkDiagonal(grid, centerRow - 1, centerCol - 1, centerRow + 1, centerCol + 1, true);

        // Check second diagonal (top-right to bottom-left)
        List<Point> diagonal2Forward = checkDiagonal(grid, centerRow - 1, centerCol + 1, centerRow + 1, centerCol - 1, false);
        List<Point> diagonal2Backward = checkDiagonal(grid, centerRow - 1, centerCol + 1, centerRow + 1, centerCol - 1, true);

        // Try all valid combinations
        tryAddPattern(grid, centerRow, centerCol, diagonal1Forward, diagonal2Forward, allPatterns);
        tryAddPattern(grid, centerRow, centerCol, diagonal1Forward, diagonal2Backward, allPatterns);
        tryAddPattern(grid, centerRow, centerCol, diagonal1Backward, diagonal2Forward, allPatterns);
        tryAddPattern(grid, centerRow, centerCol, diagonal1Backward, diagonal2Backward, allPatterns);
    }

    private List<Point> checkDiagonal(char[][] grid, int startRow, int startCol,
                                      int endRow, int endCol, boolean reverse) {
        List<Point> points = new ArrayList<>();

        // Check if positions are valid
        if (startRow < 0 || startRow >= grid.length || startCol < 0 || startCol >= grid[0].length ||
                endRow < 0 || endRow >= grid.length || endCol < 0 || endCol >= grid[0].length) {
            return points;
        }

        char first = reverse ? 'S' : 'M';
        char last = reverse ? 'M' : 'S';

        if (grid[startRow][startCol] == first && grid[endRow][endCol] == last) {
            points.add(new Point(startRow, startCol));
            points.add(new Point(endRow, endCol));
        }

        return points;
    }

    private void tryAddPattern(char[][] grid, int centerRow, int centerCol,
                               List<Point> diagonal1, List<Point> diagonal2,
                               List<List<Point>> allPatterns) {
        if (!diagonal1.isEmpty() && !diagonal2.isEmpty()) {
            List<Point> pattern = new ArrayList<>();
            pattern.addAll(diagonal1);
            pattern.add(new Point(centerRow, centerCol));  // Add center A
            pattern.addAll(diagonal2);
            allPatterns.add(pattern);
        }
    }

    private char[][] linesToGrid(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    private static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }

}
