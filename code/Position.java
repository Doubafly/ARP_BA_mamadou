public class Position {
    // a position is immutable : all fields are final
    public final int x;
    public final int y;

    /** ------------------ constructeurs ------------------ */
    /** Default constructor at (0,0) */
    public Position() {
        this(0, 0);
    }

    /** Constructor with given coordinates. Can only be accessed via move method */
    Position(int i, int j) {
        this.x = i;
        this.y = j;
    }

    /** ------------------ builders ------------------ */
    /** Retourne une nouvelle position après un déplacement */
    public Position move(int nb_lignes, int nb_colonnes) {
        return new Position(this.x + nb_lignes, this.y + nb_colonnes);
    }
}
