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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
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
}
