//The abstract class inherited by all pieces
public abstract class Piece {
    int x, y;
    public PieceColor color;

    // Author: Hesham
    public Piece(int x, int y, PieceColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
}
    public abstract int[][] getPossibleMoves(LinkingDict[][] link);

}