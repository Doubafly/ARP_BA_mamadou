import java.util.*;

public class MissionnairesCannibales {

    static class State {
        int mL, cL; // missionnaires et cannibales à gauche
        int boat; // 0 = gauche, 1 = droite

        State(int mL, int cL, int boat) {
            this.mL = mL;
            this.cL = cL;
            this.boat = boat;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State))
                return false;
            State s = (State) o;
            return mL == s.mL && cL == s.cL && boat == s.boat;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mL, cL, boat);
        }

        @Override
        public String toString() {
            return "(" + mL + "," + cL + "," + boat + ")";
        }
    }

    static boolean valid(State s) {
        int mL = s.mL, cL = s.cL;
        int mR = 3 - mL, cR = 3 - cL;

        if (mL < 0 || mL > 3 || cL < 0 || cL > 3)
            return false;
        if (mL > 0 && cL > mL)
            return false;
        if (mR > 0 && cR > mR)
            return false;

        return true;
    }

    static List<State> successors(State s) {
        int[][] moves = { { 1, 0 }, { 2, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } };
        List<State> succ = new ArrayList<>();

        for (int[] mv : moves) {
            int dm = mv[0], dc = mv[1];

            if (s.boat == 0) {
                if (dm > s.mL || dc > s.cL)
                    continue;
            } else {
                int mR = 3 - s.mL, cR = 3 - s.cL;
                if (dm > mR || dc > cR)
                    continue;
            }

            State next = (s.boat == 0)
                    ? new State(s.mL - dm, s.cL - dc, 1)
                    : new State(s.mL + dm, s.cL + dc, 0);

            if (valid(next))
                succ.add(next);
        }

        return succ;
    }

    static int bfs_noeud = 0;
    static int dfs_noeud = 0;
    static int idfs_noeud = 0;

    static List<State> bfs(State debut, State goal) {
        Queue<State> frontier = new LinkedList<>();
        Map<State, State> parent = new HashMap<>();
        frontier.add(debut);
        parent.put(debut, null);

        while (!frontier.isEmpty()) {
            State current = frontier.poll();
            bfs_noeud++;

            if (current.equals(goal)) {
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (State nxt : successors(current)) {
                parent.put(nxt, current);
                frontier.add(nxt);
            }
        }
        return null;
    }

    static List<State> dfs(State debut, State goal) {
        Stack<State> frontier = new Stack<>();
        Map<State, State> parent = new HashMap<>();
        frontier.push(debut);
        parent.put(debut, null);

        while (!frontier.isEmpty()) {
            State current = frontier.pop();
            dfs_noeud++;

            if (current.equals(goal)) {
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (State nxt : successors(current)) {
                parent.put(nxt, current);
                frontier.push(nxt);
            }
        }
        return null;
    }

    static List<State> idfs(State debut, State goal) {
        for (int depth = 0; depth <= 20; depth++) {
            List<State> res = dls(debut, goal, depth, new HashSet<>());
            if (res != null)
                return res;
        }
        return null;
    }

    static List<State> dls(State current, State goal, int limit, Set<State> visited) {
        idfs_noeud++;
        if (current.equals(goal)) {
            List<State> path = new ArrayList<>();
            path.add(current);
            return path;
        }
        if (limit == 0)
            return null;

        visited.add(current);
        for (State nxt : successors(current)) {
            List<State> sub = dls(nxt, goal, limit - 1, visited);
            if (sub != null) {
                sub.add(0, current);
                return sub;
            }
        }
        visited.remove(current);
        return null;
    }

    static int bfsMem_noeud = 0;
    static int dfsMem_noeud = 0;
    static int iddfsMem_noeud = 0;

    static List<State> bfsMem(State debut, State goal) {
        Queue<State> frontier = new LinkedList<>();
        Map<State, State> parent = new HashMap<>();
        Set<State> visited = new HashSet<>();

        frontier.add(debut);
        parent.put(debut, null);
        visited.add(debut);

        while (!frontier.isEmpty()) {
            State current = frontier.poll();
            bfsMem_noeud++;

            if (current.equals(goal)) {
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (State nxt : successors(current)) {
                if (!visited.contains(nxt)) {
                    parent.put(nxt, current);
                    frontier.add(nxt);
                    visited.add(nxt);
                }
            }
        }
        return null;
    }

    static List<State> dfsMem(State debut, State goal) {
        Stack<State> frontier = new Stack<>();
        Map<State, State> parent = new HashMap<>();
        Set<State> visited = new HashSet<>();

        frontier.push(debut);
        parent.put(debut, null);
        visited.add(debut);

        while (!frontier.isEmpty()) {
            State current = frontier.pop();
            dfsMem_noeud++;

            if (current.equals(goal)) {
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (State nxt : successors(current)) {
                if (!visited.contains(nxt)) {
                    parent.put(nxt, current);
                    frontier.push(nxt);
                    visited.add(nxt);
                }
            }
        }
        return null;
    }

    static List<State> iddfsMem(State debut, State goal) {
        for (int depth = 0; depth <= 20; depth++) {
            Set<State> visited = new HashSet<>();
            List<State> res = dlsMem(debut, goal, depth, visited);
            if (res != null)
                return res;
        }
        return null;
    }

    static List<State> dlsMem(State current, State goal, int limit, Set<State> visited) {
        iddfsMem_noeud++;
        if (current.equals(goal)) {
            List<State> path = new ArrayList<>();
            path.add(current);
            return path;
        }
        if (limit == 0)
            return null;

        visited.add(current);
        for (State nxt : successors(current)) {
            if (!visited.contains(nxt)) {
                List<State> sub = dlsMem(nxt, goal, limit - 1, visited);
                if (sub != null) {
                    sub.add(0, current);
                    return sub;
                }
            }
        }
        visited.remove(current);
        return null;
    }

    public static void main(String[] args) {
        State debut = new State(3, 3, 0);
        State goal = new State(0, 0, 1);

        /*
         * // Sans memoire j'ai pas pu identifier exactement le probleme mais on dirait
         * que je suis dans une sorte de boucle infinie
         * List<State> solB = bfs(debut, goal);
         * System.out.println("\nBFS : noeuds = " + bfs_noeud + ", chemin = " + solB);
         * 
         * List<State> solD = dfs(debut, goal);
         * System.out.println("DFS : noeuds = " + dfs_noeud + ", chemin = " + solD);
         * 
         * List<State> solI = idfs(debut, goal);
         * System.out.println("IDDFS : noeuds = " + idfs_noeud + ", chemin = " + solI);
         */

        // Avec memoire
        List<State> solBM = bfsMem(debut, goal);
        System.out.println("\nBFS (memoire) : noeuds = " + bfsMem_noeud + ", chemin = " + solBM);

        List<State> solDM = dfsMem(debut, goal);
        System.out.println("DFS (memoire) : noeuds = " + dfsMem_noeud + ", chemin = " + solDM);

        List<State> solIM = iddfsMem(debut, goal);
        System.out.println("IDDFS (memoire) : noeuds = " + iddfsMem_noeud + ", chemin = " + solIM);
    }
}