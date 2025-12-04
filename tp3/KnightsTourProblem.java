import java.util.*;

public class KnightsTourProblem {
    public final int NB_ROWS;
    public final int NB_COLS;

    private static final int[] DEPLAC_X = { 1, 1, -1, -1, 2, 2, -2, -2 };
    private static final int[] DEPLAC_Y = { 2, -2, 2, -2, 1, -1, 1, -1 };

    private List<List<int[]>> toutesLesSolutions;
    private boolean tourFerme;

    public KnightsTourProblem(int n, int m) {
        this(n, m, false);
    }

    public KnightsTourProblem(int n, int m, boolean tourFerme) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be positive.");
        }
        this.NB_ROWS = n;
        this.NB_COLS = m;
        this.toutesLesSolutions = new ArrayList<>();
        this.tourFerme = tourFerme;
    }

    /** Retourne l'état initial : une grille vide avec le cavalier sur un coin */
    public State initialState() {
        int[][] plateauInitial = new int[NB_ROWS][NB_COLS];
        plateauInitial[0][0] = 2;

        Position cavalierDepart = new Position(0, 0);
        return new State(plateauInitial, cavalierDepart, 1);
    }

    /** Retourne la liste des actions possibles */
    public List<Action> actions() {
        List<Action> actionsPossibles = new ArrayList<>();

        int[][] mouvements = {
                { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 },
                { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }
        };

        for (int[] mov : mouvements) {
            actionsPossibles.add(new Action(mov[0], mov[1]));
        }
        return actionsPossibles;
    }

    /** Vérifie si l'état est terminal */
    public boolean isGoalState(State etat) {
        return etat.getScore() == NB_ROWS * NB_COLS;
    }

    /** Retourne l'état successeur après avoir appliqué une action */
    public State succession(State etat, Action action) {
        Position nouveauCavalier = etat.getKnight().move(action.dx, action.dy);

        if (!etat.isValidPosition(nouveauCavalier) || etat.isVisited(nouveauCavalier)) {
            return null;
        }

        int[][] nouveauPlateau = new int[NB_ROWS][NB_COLS];
        int[][] plateauActuel = etat.getBoard();

        for (int i = 0; i < NB_ROWS; i++) {
            nouveauPlateau[i] = plateauActuel[i].clone();
        }

        Position ancienCavalier = etat.getKnight();
        nouveauPlateau[ancienCavalier.x][ancienCavalier.y] = 1;
        nouveauPlateau[nouveauCavalier.x][nouveauCavalier.y] = 2;

        int nouveauScore = etat.getScore() + 1;

        return new State(nouveauPlateau, nouveauCavalier, nouveauScore);
    }

    public List<List<int[]>> findAllTours() {
        toutesLesSolutions.clear();

        boolean[][] visite = new boolean[NB_ROWS][NB_COLS];
        List<int[]> cheminActuel = new ArrayList<>();

        backtrackAll(0, 0, 1, visite, cheminActuel);
        return toutesLesSolutions;
    }

    private void backtrackAll(int x, int y, int etape,
            boolean[][] visite, List<int[]> cheminActuel) {

        visite[x][y] = true;
        cheminActuel.add(new int[] { x, y });

        if (etape == NB_ROWS * NB_COLS) {
            if (!tourFerme || peutRevenirDepart(x, y)) {
                toutesLesSolutions.add(new ArrayList<>(cheminActuel));
            }
        } else {
            for (int i = 0; i < 8; i++) {
                int nx = x + DEPLAC_X[i];
                int ny = y + DEPLAC_Y[i];

                if (estPositionValide(nx, ny) && !visite[nx][ny]) {
                    backtrackAll(nx, ny, etape + 1, visite, cheminActuel);
                }
            }
        }

        visite[x][y] = false;
        cheminActuel.remove(cheminActuel.size() - 1);
    }

    private boolean peutRevenirDepart(int x, int y) {
        for (int i = 0; i < 8; i++) {
            int tx = x + DEPLAC_X[i];
            int ty = y + DEPLAC_Y[i];

            if (tx == 0 && ty == 0)
                return true;
        }
        return false;
    }

    private boolean estPositionValide(int x, int y) {
        return x >= 0 && x < NB_ROWS && y >= 0 && y < NB_COLS;
    }

    /* ------------------- ACTION INTERN ---------------------- */

    class Action {
        public final int dx;
        public final int dy;

        public Action(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        /** Coût de l'action (toujours 1 dans ce problème) */
        public int cout() {
            return 1;
        }

        @Override
        public String toString() {
            return "(" + dx + "," + dy + ")";
        }
    }
}
