
// Dans KnightsTourProblem.java - IMPLÉMENTATION COMPLÈTE
import java.util.*;

public class KnightsTourProblem {
    private final int NB_ROWS;
    private final int NB_COLS;

    public KnightsTourProblem(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be positive.");
        }
        this.NB_ROWS = n;
        this.NB_COLS = m;
    }

    public State initialState() {
        int[][] initialBoard = new int[NB_ROWS][NB_COLS];
        // Toutes les cases à 0 sauf la position initiale du cavalier à 2
        initialBoard[0][0] = 2;
        Position initialKnight = new Position(0, 0);
        return new State(initialBoard, initialKnight, 1);
    }

    public List<Action> actions() {
        List<Action> possibleActions = new ArrayList<>();
        // Les 8 mouvements en "L" du cavalier
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
        // État terminal : toutes les cases visitées
        return state.getScore() == NB_ROWS * NB_COLS;
    }

    public State succession(State state, Action action) {
        Position newKnight = state.getKnight().move(action.dx, action.dy);

        // Vérifier si le mouvement est valide
        if (!state.isValidPosition(newKnight) || state.isVisited(newKnight)) {
            return null;
        }

        // Copie profonde de la grille
        int[][] newBoard = new int[NB_ROWS][NB_COLS];
        int[][] currentBoard = state.getBoard();
        for (int i = 0; i < NB_ROWS; i++) {
            newBoard[i] = currentBoard[i].clone();
        }

        // Mettre à jour les positions
        Position oldKnight = state.getKnight();
        newBoard[oldKnight.x][oldKnight.y] = 1; // Ancienne position → visitée
        newBoard[newKnight.x][newKnight.y] = 2; // Nouvelle position → cavalier

        int newScore = state.getScore() + 1;

        return new State(newBoard, newKnight, newScore);
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