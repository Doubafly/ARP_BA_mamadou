import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ResearchAlgorithm {

    public static Node DFS(KnightsTourProblem problem) {
        int counter = 0;
        List<Node> frontier = new LinkedList<>();
        Node root = new Node(problem.initialState(), null, null);
        // mémoire pour éviter les états déjà explorés
        HashSet<State> visited = new HashSet<>();
        frontier.add(root);
        while (!frontier.isEmpty()) {
            Node currentNode = frontier.remove(frontier.size() - 1); // LIFO
            counter++;
            // test d’état but
            if (problem.isGoalState(currentNode.getState())) {
                System.out.println("DFS found a solution after evaluating " + counter + " nodes.");
                return currentNode;
            }
            // marquer état visité
            visited.add(currentNode.getState());
            // expansion
            for (Node child : currentNode.expand(problem)) {
                if (!visited.contains(child.getState())) {
                    frontier.add(child);
                }
            }
        }

        return null;
    }

    public static Node search(KnightsTourProblem problem) {
        int counter = 0;
        List<Node> frontier = new LinkedList<>();
        Node root = new Node(problem.initialState(), null, null);
        frontier.add(root);
        while (!frontier.isEmpty()) {
            Node currentNode = frontier.remove(0);
            counter += 1;
            if (problem.isGoalState(currentNode.getState())) {
                System.out.println("solution trouver : " + counter + " nodes.");
                return currentNode;
            }
            frontier.addAll(currentNode.expand(problem));
        }
        return null;
    }

    public static Node DFS_Iterer(KnightsTourProblem problem) {
        int[] counter = new int[] { 0 };
        Node root = new Node(problem.initialState(), null, null);
        int maxDepth = problem.NB_ROWS * problem.NB_COLS;

        for (int limit = 1; limit <= maxDepth; limit++) {
            HashSet<State> visited = new HashSet<>();

            Node result = DFS_Limiter(root, problem, limit, visited, counter);
            if (result != null) {
                System.out.println("solution à la profondeur " + limit +
                        " apres " + counter[0] + " nœuds.");

                return result;
            }
        }

        System.out.println("pas de solutions.");
        return null;
    }

    private static Node DFS_Limiter(Node node, KnightsTourProblem problem,
            int limit, HashSet<State> pathVisited, int[] counter) {
        counter[0]++;
        if (problem.isGoalState(node.getState())) {
            return node;
        }
        if (limit == 0) {
            return null; // limite de profondeur atteinte
        }
        pathVisited.add(node.getState());
        for (Node child : node.expand(problem)) {
            if (!pathVisited.contains(child.getState())) {
                Node result = DFS_Limiter(child, problem, limit - 1, pathVisited, counter);
                if (result != null) {
                    return result;
                }
            }
        }

        pathVisited.remove(node.getState());
        return null;
    }

}
