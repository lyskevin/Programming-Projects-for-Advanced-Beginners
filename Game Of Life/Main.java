public class Main {

    private static Board board;

    public static void main(String[] args) {
        initializeBoard();
        while (true) {
            try {
                renderBoard();
                updateBoardState();
                Thread.sleep(100);
                clearScreen();
            } catch (InterruptedException e) {};
        }
    }

    private static void initializeBoard() {
        try {
            board = Board.randomState(10, 10);
        } catch (IllegalArgumentException e) {
            System.err.println("Please provide valid board parameters.");
            System.exit(0);
        }
    }

    private static void updateBoardState() {
        board = board.nextBoardState();
    }

    private static void renderBoard() {
        board.render();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

