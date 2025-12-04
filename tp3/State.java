public class State {
    /**
    Un état est décrit par une grille NxM d'entiers. 
    O indique le fait que la case n'a pas encore été visité.
    1 indique le fait que la case a déjà été visité
    2 indique le fait que le cavalier se trouve sur la case
    */

    /* -------------------- attributs -------------------- */
    // a state is immutable : all fields are final
    private final int NB_LIGNES;
    private final int NB_COLONNES;
    private final int score;
    private final Position knight;
    private final int[][] board;


    /* ------------------ constructeurs ------------------ */

    public State(int[][] grille, Position knightPosition, int score){
        this.NB_LIGNES = grille.length;
        this.NB_COLONNES = grille[0].length;
        if (knightPosition.x < 0 || knightPosition.x >= NB_LIGNES || knightPosition.y < 0 || knightPosition.y >= NB_COLONNES){
            throw new IllegalArgumentException("Invalid knight position");
        }
        if (score < 0 || score > NB_LIGNES * NB_COLONNES){
            throw new IllegalArgumentException("Invalid score");
        }

        this.board = new int[NB_LIGNES][];
        for (int i = 0; i < NB_LIGNES; i++) {
            this.board[i] = grille[i].clone();  // deep copy
        }
        this.score = score;
        this.knight = knightPosition;
    }

    /* -------------------- getteurs -------------------- */
    public int[][] getBoard() {
        return this.board;
    }

    public int getScore() {
        return this.score;
    }

    public Position getKnight() {
        return this.knight;
    }

    /* ------------------ query methods------------------- */
    /** Vérifie si la case a déjà été visité. */
    public boolean isVisited(Position pos){
        return this.board[pos.x][pos.y] != 0;
    }

    /** Vérifie si la position est à l'intérieur de la grille */
    public boolean isValidPosition(Position pos){
        return pos.x >= 0 && pos.x < NB_LIGNES && pos.y >= 0 && pos.y < NB_COLONNES;
    }

    /** Fournit une description de l'état */
    @Override
    public String toString(){
        StringBuffer description = new StringBuffer();
        description.append("Nb moves : ").append(this.getScore()).append("\n");
        
        for (int i = 0; i < NB_LIGNES; i++) {
            for (int j = 0; j < NB_COLONNES; j++) {
                description.append(this.board[i][j]).append(" ");               
            }
            description.append("\n");
        }
        return description.toString();
    }

    


    
}