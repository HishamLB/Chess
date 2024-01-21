/*
    This class is responsible for creating pieces. It is used by the BoardBuilder class to create pieces for the board.
    It follows the creational design pattern factory method to create pieces of the specified type and color.
 */

public class PieceFactory {

    // Author: Hesham
    public static Piece createPiece(String pieceType, int x, int y, PieceColor color) {
        switch (pieceType) {
            case "Biz":
                return new Biz(x, y, color);
            case "Ram":
                return new Ram(x, y, color);
            case "Sau":
                return new Sau(x, y, color);
            case "Tor":
                return new Tor(x, y, color);
            case "Xor":
                return new Xor(x, y, color);
            default:
                throw new IllegalArgumentException("Unknown piece type: " + pieceType);
        }
    }
}