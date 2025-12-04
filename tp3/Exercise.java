import java.util.List;

public class Exercise {
    public static void main(String[] args) {
        System.out.println("Hello, this program should solve the Knights Tour!");
        int NB_ROWS = 5;
        int NB_COLS = 5;
        KnightsTourProblem problem = new KnightsTourProblem(NB_ROWS, NB_COLS);

        // Question 5 - Tous les parcours
        // List<List<int[]>> toutesLesSolutions = problem.findAllTours();
        // System.out.println("Nombre de parcours trouvés : " +
        // toutesLesSolutions.size());

        // Question 6 - Tours fermes seulement
        // System.out.println("\nQuestion 6 - Tours fermes:");
        // KnightsTourProblem problem6 = new KnightsTourProblem(NB_ROWS, NB_COLS, true);
        // List<List<int[]>> closedTours = problem6.findAllTours();
        // System.out.println("Nombre de tours fermes trouves : " + closedTours.size());

        // if (closedTours.size() > 0) {
        // System.out.println("\nExemple de tour ferme :");
        // printSolution(closedTours.get(0), NB_ROWS, NB_COLS);
        // verifyClosedTour(closedTours.get(0));
        // }
        // pour tester la questions 3 il faut juste remplacer search par DFS
        Node solutionNode = ResearchAlgorithm.search(problem);
        if (solutionNode != null) {
            solutionNode.printSolution();
        } else {
            System.out.println("Could not find a solution.");
        }
    }

    // private static void printSolution(List<int[]> solution, int rows, int cols) {
    // int[][] grid = new int[rows][cols];
    // for (int step = 0; step < solution.size(); step++) {
    // int[] pos = solution.get(step);
    // grid[pos[0]][pos[1]] = step + 1;
    // }

    // for (int i = 0; i < rows; i++) {
    // for (int j = 0; j < cols; j++) {
    // System.out.printf("%3d ", grid[i][j]);
    // }
    // System.out.println();
    // }
    // }

    // private static void verifyClosedTour(List<int[]> tour) {
    // int[] start = tour.get(0);
    // int[] end = tour.get(tour.size() - 1);

    // System.out.println("Position de depart: (" + start[0] + "," + start[1] +
    // ")");
    // System.out.println("Position finale: (" + end[0] + "," + end[1] + ")");

    // // Vérifier le retour au départ
    // int[][] moves = { { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }, { 2, 1 }, { 2,
    // -1 }, { -2, 1 }, { -2, -1 } };
    // for (int[] move : moves) {
    // int testX = end[0] + move[0];
    // int testY = end[1] + move[1];
    // if (testX == start[0] && testY == start[1]) {
    // System.out.println(
    // "Le cavalier peut revenir au depart avec le mouvement: (" + move[0] + "," +
    // move[1] + ")");
    // return;
    // }
    // }
    // System.out.println("ERREUR: Le cavalier ne peut pas revenir au depart");
    // }
}
