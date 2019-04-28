import java.util.Random;

class Board {
    
    private static final int MINIMUM_WIDTH = 0;
    private static final int MINIMUM_HEIGHT = 0;
    private static final Random RANDOM = new Random();
    private static final double DEAD_THRESHOLD = 0.5;
    private static final int DEAD = 0;
    private static final int LIVE = 1;
    private static final int PADDING = 2;
    private static final int ADJACENT_LENGTH = 1;
    private static final int UNDER_POPULATION = 1;
    private static final int REPRODUCTION = 3;
    private static final int OVER_POPULATION = 4;

    private int width;
    private int height;
    private int[][] grid;
    
    private static Board createBoard(int width, int height) {
        if (width <= MINIMUM_WIDTH || height <= MINIMUM_HEIGHT) {
            throw new IllegalArgumentException();
        }
        return new Board(width, height);
    }

    private Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new int[height][width];
    }

    private static Board deadState(int width, int height) {
        return createBoard(width, height);
    }

    /**
     * Returns a board of the specified width and height.
     * The board's cells will either be dead or live at random.
     * @param width The specified width.
     * @param height The specified height.
     * @return A randomized board with the specified width and height.
     */
    static Board randomState(int width, int height) {
        Board board = deadState(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (RANDOM.nextDouble() >= DEAD_THRESHOLD) {
                    board.grid[i][j] = LIVE;
                }
            }
        }
        return board;
    }

    /**
     * Formats the board and prints it to the terminal.
     */
    void render() {
        printHorizontalLines();
        for (int i = 0; i < height; i++) {
            printRowOfCells(i);
        }
        printHorizontalLines();
    }

    private void printHorizontalLines() {
        for (int i = 0; i < width + PADDING; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private void printRowOfCells(int row) {
        System.out.print("|");
        for (int i = 0; i < width; i++) {
            if (grid[row][i] == DEAD) {
                System.out.print(" ");
            } else {
                System.out.print("*");
            }
        }
        System.out.println("|");
    }

    Board nextBoardState() {
        Board board = deadState(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int numberOfLiveNeighbours = countLiveNeighbours(i, j);
                if (grid[i][j] == DEAD) {
                    if (numberOfLiveNeighbours == REPRODUCTION) {
                        board.grid[i][j] = LIVE;
                    }
                } else {
                    if (numberOfLiveNeighbours > UNDER_POPULATION
                        && numberOfLiveNeighbours < OVER_POPULATION) {
                        board.grid[i][j] = LIVE;
                    }
                }
            }
        }
        return board;
    }

    private int countLiveNeighbours(int row, int column) {
        int count = 0;
        for (int i = row - ADJACENT_LENGTH; i <= row + ADJACENT_LENGTH; i++) {
            for (int j = column - ADJACENT_LENGTH;
                j <= column + ADJACENT_LENGTH; j++) {
                if ((i < MINIMUM_HEIGHT || i >= height)
                    || (j < MINIMUM_WIDTH || j >= width)
                    || (i == row && j == column)) {
                    continue;
                } else if (grid[i][j] == LIVE) {
                    count++;
                }
            }
        }
        return count;
    }

}

