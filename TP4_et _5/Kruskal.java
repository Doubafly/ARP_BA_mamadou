import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {

    public static double run(int nbVertices, List<Edge> edges) {
        // Sort edges by weight
        Collections.sort(edges);

        DisjointSet disjointSet = new DisjointSet(nbVertices);
        List<Edge> spanningTree = new ArrayList<>();
        double totalWeight = 0.0;

        for (Edge e : edges) {
            if (disjointSet.find(e.src) != disjointSet.find(e.dest)) {
                spanningTree.add(e);
                totalWeight += e.weight;
                disjointSet.union(e.src, e.dest);
                if (spanningTree.size() == nbVertices - 1){
                    return totalWeight;
                }
            }
        }
    }


// classe utilisé lors du calcul du MST pour estimer si deux sommets appartiennent au même composant connexe
class DisjointSet {
    int[] parent, rank;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++)
            parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
