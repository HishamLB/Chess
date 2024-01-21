import javax.swing.*;

public class Sau extends Piece {
    public Sau(int x, int y, PieceColor color) {
        super(x, y, color);
    }

    // Author: Habeba
    //Sau can move anywhere (diagonally or orthogonally) with a distance of 1
    public int[][] getPossibleMoves(LinkingDict[][] link) {
        return new int[][]{
                {y - 1, x - 1}, {y - 1, x}, {y - 1, x + 1}, // Above row
                {y, x - 1},                 {y, x + 1},     // Same row
                {y + 1, x - 1}, {y + 1, x}, {y + 1, x + 1}  // Below row
        };
    }


}