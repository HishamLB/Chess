import java.util.ArrayList;
import java.util.List;

public class Xor extends Piece {

    public Xor(int x, int y, PieceColor color) {
        super(x, y, color);
    }

    // Author: Nick, Hesham, Habeba
    // Get all possible moves for a Xor piece
    public int[][] getPossibleMoves(LinkingDict[][] link) {
        List<int[]> moves = new ArrayList<>();

        // Directions for diagonal moves
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] direction : directions) {
            int currentX = this.x;
            int currentY = this.y;

            // Continue moving diagonally
            while (true) {
                currentX += direction[0];
                currentY += direction[1];

                // Check if valid position
                if (isValidPosition(currentX, currentY)) {
                    Piece targetPiece = link[currentY][currentX].getPiece();

                    if (targetPiece == null) {
                        // Add empty square
                        moves.add(new int[]{currentY, currentX});
                    } else {
                        // If the piece is of opposite color, add it as a move and stop
                        if (targetPiece.color != this.color) {
                            moves.add(new int[]{currentY, currentX});
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return moves.toArray(new int[0][]);
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < KwazamBoard.COLS && y >= 0 && y < KwazamBoard.ROWS;
    }
}