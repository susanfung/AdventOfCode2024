package org.samples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day6 {
    private static final char GUARD_UP = '^';
    private static final char GUARD_RIGHT = '>';
    private static final char GUARD_DOWN = 'v';
    private static final char GUARD_LEFT = '<';
    private static final char OBSTACLE = '#';
    private static final char NEW_OBSTACLE = 'O';
    private static final char VERTICAL = '|';
    private static final char HORIZONTAL = '-';
    private static final char BOTH = '+';

    private static class GuardState {
        Position pos;
        char direction;

        GuardState(Position pos, char direction) {
            this.pos = pos;
            this.direction = direction;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof GuardState)) {
                return false;
            }
            GuardState that = (GuardState) o;
            return pos.row == that.pos.row &&
                    pos.col == that.pos.col &&
                    direction == that.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos.row, pos.col, direction);
        }
    }

    public int calculatePatrolPositions(String filePath) {
        char[][] map = readMap(filePath);
        Position guardPos = findGuard(map);
        if (guardPos == null) {
            return 0;
        }

        Set<Position> visitedPositions = new HashSet<>();
        Map<GuardState, Integer> stateHistory = new HashMap<>();

        char currentDirection = map[guardPos.row][guardPos.col];
        visitedPositions.add(new Position(guardPos.row, guardPos.col));

        char[][] debugMap = new char[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                debugMap[i][j] = map[i][j];
            }
        }
        debugMap[guardPos.row][guardPos.col] = 'X';

        int steps = 0;
        while (true) {
            Position nextPos = getNextPosition(guardPos, currentDirection);

            if (!isInBounds(nextPos, map)) {
                break;
            }

            if (map[nextPos.row][nextPos.col] == OBSTACLE) {
                currentDirection = turnRight(currentDirection);
            } else {
                guardPos = nextPos;
                visitedPositions.add(new Position(guardPos.row, guardPos.col));
                debugMap[guardPos.row][guardPos.col] = 'X';
            }

            GuardState currentState = new GuardState(guardPos, currentDirection);
            if (stateHistory.containsKey(currentState)) {
                break;
            }
            stateHistory.put(currentState, steps);
            steps++;
        }

        return visitedPositions.size();
    }

    public static class Position {
        int row, col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return row == position.row && col == position.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    private Position findGuard(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (isGuard(map[i][j])) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    private boolean isGuard(char c) {
        return c == GUARD_UP || c == GUARD_RIGHT || c == GUARD_DOWN || c == GUARD_LEFT;
    }

    private char turnRight(char currentDirection) {
        switch (currentDirection) {
            case GUARD_UP:
                return GUARD_RIGHT;
            case GUARD_RIGHT:
                return GUARD_DOWN;
            case GUARD_DOWN:
                return GUARD_LEFT;
            case GUARD_LEFT:
                return GUARD_UP;
            default:
                return currentDirection;
        }
    }

    private Position getNextPosition(Position current, char direction) {
        switch (direction) {
            case GUARD_UP:
                return new Position(current.row - 1, current.col);
            case GUARD_RIGHT:
                return new Position(current.row, current.col + 1);
            case GUARD_DOWN:
                return new Position(current.row + 1, current.col);
            case GUARD_LEFT:
                return new Position(current.row, current.col - 1);
            default:
                return current;
        }
    }

    private boolean isInBounds(Position pos, char[][] map) {
        return pos.row >= 0 && pos.row < map.length &&
                pos.col >= 0 && pos.col < map[0].length;
    }

    private char[][] readMap(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new char[0][0];
        }

        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }
        return map;
    }

    public List<Position> findLoopingObstructions(String filePath) {
        char[][] originalMap = readMap(filePath);
        List<Position> validObstructions = new ArrayList<>();
        Position guardStart = findGuard(originalMap);

        for (int row = 0; row < originalMap.length; row++) {
            for (int col = 0; col < originalMap[0].length; col++) {
                if (originalMap[row][col] == '.' &&
                        (row != guardStart.row || col != guardStart.col)) {

                    char[][] testMap = copyMap(originalMap);
                    testMap[row][col] = OBSTACLE;

                    if (isValidLoop(testMap)) {
                        validObstructions.add(new Position(row, col));
                    }
                }
            }
        }

        return validObstructions;
    }

    private boolean isValidLoop(char[][] map) {
        Position guardPos = findGuard(map);
        char currentDirection = map[guardPos.row][guardPos.col];
        Map<GuardState, Integer> stateHistory = new HashMap<>();
        int steps = 0;

        while (true) {
            GuardState currentState = new GuardState(guardPos, currentDirection);

            if (stateHistory.containsKey(currentState)) {
                int loopStart = stateHistory.get(currentState);
                int loopSize = steps - loopStart;

                return loopSize > 1;
            }

            Position nextPos = getNextPosition(guardPos, currentDirection);
            if (!isInBounds(nextPos, map)) {
                return false;
            }

            stateHistory.put(currentState, steps);

            if (map[nextPos.row][nextPos.col] == OBSTACLE) {
                currentDirection = turnRight(currentDirection);
            } else {
                guardPos = nextPos;
            }

            steps++;
        }
    }

    private char[][] copyMap(char[][] original) {
        char[][] copy = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
}
