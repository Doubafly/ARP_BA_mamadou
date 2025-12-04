import java.io.*;
import java.util.*;

public class Graphe {
    public List<Ville> villes = new ArrayList<>();
    public double[][] dist;

    public Graphe(String filename) throws Exception {
        load(filename);
        Distances();
    }

    private void load(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] t = line.trim().split("\\s+");
            int id = Integer.parseInt(t[0]);
            double lat = Double.parseDouble(t[1]);
            double lon = Double.parseDouble(t[2]);
            villes.add(new Ville(id, lat, lon));
        }
        br.close();
    }

    private void Distances() {
        int n = villes.size();
        dist = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = euclidean(villes.get(i), villes.get(j));
            }
        }
    }

    private double euclidean(Ville a, Ville b) {
        return Math.sqrt(Math.pow(a.lat - b.lat, 2) + Math.pow(a.lon - b.lon, 2));
    }
}
