import java.util.*;

public class MissionnairesCannibales {

    static class State {
        int mL, cL;   // missionnaires et cannibales à gauche
        int boat;    // 0 = gauche, 1 = droite

        State(int mL, int cL, int boat) {
            this.mL = mL;
            this.cL = cL;
            this.boat = boat;
        }

        // On choisis le BFS comme Algo
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
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

    // Vérifie si un état est valide selon les contraintes
    static boolean valid(State s) {
        int mL = s.mL, cL = s.cL;
        int mR = 3 - mL, cR = 3 - cL;

        // bornes
        if (mL < 0 || mL > 3 || cL < 0 || cL > 3) return false;

        // sécurité rive gauche
        if (mL > 0 && mL < cL) return false;

        // sécurité rive droite
        if (mR > 0 && mR < cR) return false;

        return true;
    }

    // Calcule les états successeurs possibles
    static List<State> successors(State s) {
        int[][] moves = {
                {1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}
        };
        List<State> succ = new ArrayList<>();

        for (int[] mv : moves) {
            int dm = mv[0], dc = mv[1];
            State next;

            if (s.boat == 0) { // bateau gauche -> droite
                next = new State(s.mL - dm, s.cL - dc, 1);
            } else {           // bateau droite -> gauche
                next = new State(s.mL + dm, s.cL + dc, 0);
            }

            if (valid(next)) succ.add(next);
        }

        return succ;
    }

    // BFS pour trouver la solution minimale
    static List<State> bfs(State start, State goal) {
        Queue<State> q = new LinkedList<>();
        Map<State, State> parent = new HashMap<>();

        q.add(start);
        parent.put(start, null);

        while (!q.isEmpty()) {
            State current = q.poll();

            if (current.equals(goal)) {
                // reconstruction du chemin
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (State nxt : successors(current)) {
                if (!parent.containsKey(nxt)) {
                    parent.put(nxt, current);
                    q.add(nxt);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {

        State start = new State(3, 3, 0);
        State goal = new State(0, 0, 1);

        List<State> solution = bfs(start, goal);

        if (solution == null) {
            System.out.println("Aucune solution trouvée !");
        } else {
            System.out.println("Solution trouvée en " + (solution.size() - 1) + " actions :");
            int step = 0;
            for (State s : solution) {
                System.out.println(step + " : " + s);
                step++;
            }
        }
    }
}
