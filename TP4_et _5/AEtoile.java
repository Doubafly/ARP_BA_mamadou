import java.util.*;

// A* NODE
class AStarNode implements Comparable<AStarNode> {

    public int villes;
    public boolean[] visited;
    public double g;
    public double f;
    public List<Integer> path;

    public AStarNode(int villes, boolean[] visited, double g, double f, List<Integer> path) {
        this.villes = villes;
        this.visited = visited;
        this.g = g;
        this.f = f;
        this.path = path;
    }

    @Override
    public int compareTo(AStarNode other) {
        return Double.compare(this.f, other.f);
    }
}

// Heuristique
class HeuristicMST {

    public static double mst(Graphe g, boolean[] visited) {

        List<Edge> edges = new ArrayList<>();
        List<Integer> remaining = new ArrayList<>();

        // villes non visitées
        for (int i = 0; i < g.villes.size(); i++) {
            if (!visited[i])
                remaining.add(i);
        }
        // si tous les villes ont ete visiter on retourne 0 fin
        int n = remaining.size();
        if (n <= 1)
            return 0;

        // ajout des arêtes entre les villes restantes
        Map<Integer, Integer> localIndex = new HashMap<>();
        // je recuper les ids des villes
        for (int i = 0; i < n; i++) {
            localIndex.put(remaining.get(i), i);
        }
        // j'applique Kruskal
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int u = remaining.get(i);
                int v = remaining.get(j);
                int lu = localIndex.get(u);
                int lv = localIndex.get(v);
                edges.add(new Edge(lu, lv, g.dist[u][v]));
            }
        }

        return new Kruskal().run(n, edges);
    }
}

// A*
class AStar {

    private Graphe g;

    public AStar(Graphe g) {
        this.g = g;
    }

    public AStarNode run(int start) {

        PriorityQueue<AStarNode> open = new PriorityQueue<>();
        // la je visite un a un en tenant bien compte la premier ville sera a distance 0
        // de tout le monde
        boolean[] initVisited = new boolean[g.villes.size()];
        initVisited[start] = true;

        double h0 = HeuristicMST.mst(g, initVisited);

        AStarNode root = new AStarNode(
                start,
                initVisited,
                0.0,
                h0,
                new ArrayList<>(List.of(start)));

        open.add(root);

        while (!open.isEmpty()) {

            AStarNode current = open.poll();

            // BUT : toutes les villess visitées
            if (current.path.size() == g.villes.size()) {
                return current;
            }

            for (int nxt = 0; nxt < g.villes.size(); nxt++) {
                if (!current.visited[nxt]) {
                    boolean[] nextVisited = current.visited.clone();
                    nextVisited[nxt] = true;
                    double gNext = current.g + g.dist[current.villes][nxt];
                    double hNext = HeuristicMST.mst(g, nextVisited);
                    List<Integer> nextPath = new ArrayList<>(current.path);
                    nextPath.add(nxt);
                    AStarNode child = new AStarNode(
                            nxt,
                            nextVisited,
                            gNext,
                            gNext + hNext,
                            nextPath);
                    open.add(child);
                }
            }
        }

        return null;
    }
}

// MAIN
public class AEtoile {

    public static void main(String[] args) throws Exception {

        Graphe g = new Graphe("us_capitals.txt");
        int start = 0;
        AStar astar = new AStar(g);
        AStarNode result = astar.run(start);

        System.out.println("Solution A* ");
        System.out.println("Chemin : " + result.path);
        System.out.println("Coût total : " + result.g);
    }
}
