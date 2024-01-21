public class Biz extends Piece {

    public Biz(int x, int y, PieceColor color) {
        super(x, y, color);
    }

    // Author: Hesham
    // Get all possible moves for a Biz piece
    public int[][] getPossibleMoves(LinkingDict[][] link) {
        return new int[][]{
                {y - 1, x - 2}, {y + 1, x - 2}, // Up-left, Up-right
                {y - 1, x + 2}, {y + 1, x + 2}, // Down-left, Down-right
                {y - 2, x - 1}, {y + 2, x - 1}, // Left-up, Right-up
                {y - 2, x + 1}, {y + 2, x + 1}  // Left-down, Right-down
        };
    }
}