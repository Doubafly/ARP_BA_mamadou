public class Main {
    public static void main(String[] args) throws Exception {

        Graphe g = new Graphe("us_capitals.txt");

        int start = 0; // tu peux changer

        DFS dfs = new DFS(g);
        dfs.run(start);

        System.out.println("Chemin trouvé par DFS :");
        for (Integer id : dfs.getPath())
            System.out.print(id + " ");

        System.out.println("\nCoût total : " + dfs.getCost());
    }
}
