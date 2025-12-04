import java.util.*;

public class DFS {

    private Graphe g;
    private boolean[] visited;
    private List<Integer> path;
    private double totalCost;

    public DFS(Graphe g) {
        this.g = g;
        visited = new boolean[g.villes.size()];
        path = new ArrayList<>();
        totalCost = 0;
    }

    public void run(int start) {
        dfs(start);
    }

    private void dfs(int u) {
        visited[u] = true;
        path.add(u);

        // si toutes les villes sont visitées → on arrête
        if (path.size() == g.villes.size()) {
            return;
        }

        // sinon : explorer naive
        for (int v = 0; v < g.villes.size(); v++) {
            if (!visited[v]) {
                totalCost += g.dist[u][v];
                dfs(v);
                return; // on prend seulement le premier chemin
            }
        }
    }

    public List<Integer> getPath() {
        return path;
    }

    public double getCost() {
        return totalCost;
    }
}
