import java.util.*;

public class KnightsTourProblem {
    private final int NB_ROWS;
    private final int NB_COLS;

    private static final int[] DX = { 1, 1, -1, -1, 2, 2, -2, -2 };
    private static final int[] DY = { 2, -2, 2, -2, 1, -1, 1, -1 };

    private List<List<int[]>> allSolutions;
    private boolean closedTour;

    public KnightsTourProblem(int n, int m) {
        this(n, m, false);
    }

    public KnightsTourProblem(int n, int m, boolean closedTour) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be positive.");
        }
        this.NB_ROWS = n;
        this.NB_COLS = m;
        this.allSolutions = new ArrayList<>();
        this.closedTour = closedTour;
    }

    public State initialState() {
        int[][] initialBoard = new int[NB_ROWS][NB_COLS];
        initialBoard[0][0] = 2;
        Position initialKnight = new Position(0, 0);
        return new State(initialBoard, initialKnight, 1);
    }

    public List<Action> actions() {
        List<Action> possibleActions = new ArrayList<>();
        int[][] moves = {
                { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 },
                { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }
        };

        for (int[] move : moves) {
            possibleActions.add(new Action(move[0], move[1]));
        }
        return possibleActions;
    }

    public boolean isGoalState(State state) {
        return state.getScore() == NB_ROWS * NB_COLS;
    }

    public State succession(State state, Action action) {
        Position newKnight = state.getKnight().move(action.dx, action.dy);

        if (!state.isValidPosition(newKnight) || state.isVisited(newKnight)) {
            return null;
        }

        int[][] newBoard = new int[NB_ROWS][NB_COLS];
        int[][] currentBoard = state.getBoard();
        for (int i = 0; i < NB_ROWS; i++) {
            newBoard[i] = currentBoard[i].clone();
        }

        Position oldKnight = state.getKnight();
        newBoard[oldKnight.x][oldKnight.y] = 1;
        newBoard[newKnight.x][newKnight.y] = 2;

        int newScore = state.getScore() + 1;

        return new State(newBoard, newKnight, newScore);
    }

    public List<List<int[]>> findAllTours() {
        allSolutions.clear();
        boolean[][] visited = new boolean[NB_ROWS][NB_COLS];
        List<int[]> currentPath = new ArrayList<>();
        backtrackAll(0, 0, 1, visited, currentPath);
        return allSolutions;
    }

    private void backtrackAll(int x, int y, int moveCount, boolean[][] visited, List<int[]> currentPath) {
        visited[x][y] = true;
        currentPath.add(new int[] { x, y });

        if (moveCount == NB_ROWS * NB_COLS) {
            if (!closedTour || canReturnToStart(x, y)) {
                allSolutions.add(new ArrayList<>(currentPath));
            }
        } else {
            for (int i = 0; i < 8; i++) {
                int newX = x + DX[i];
                int newY = y + DY[i];

                if (isValidPosition(newX, newY) && !visited[newX][newY]) {
                    backtrackAll(newX, newY, moveCount + 1, visited, currentPath);
                }
            }
        }

        visited[x][y] = false;
        currentPath.remove(currentPath.size() - 1);
    }

    private boolean canReturnToStart(int x, int y) {
        for (int i = 0; i < 8; i++) {
            int testX = x + DX[i];
            int testY = y + DY[i];
            if (testX == 0 && testY == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < NB_ROWS && y >= 0 && y < NB_COLS;
    }

    class Action {
        public final int dx;
        public final int dy;

        public Action(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int cout() {
            return 1;
        }

        @Override
        public String toString() {
            return "(" + dx + "," + dy + ")";
        }
    }
}