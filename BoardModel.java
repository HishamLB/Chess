/*
   This is the model class for the board. It is responsible for dealing with the logic of the game and the state of the board.
   It follows the MVC design pattern.
 */

public class BoardModel
{
    public PieceColor currentTurn;
    public int turnCount;

    public BoardModel() {

    }

    // Author: Nick
    //Create a new game and initialise all pieces
    public Piece[][] newGame() {
        Piece[][] pieces = new Piece[8][5];
        currentTurn = PieceColor.BLUE;
        //The board builder is used to build the board model, which calls upon the Piece Factory to create each piece
        BoardBuilder builder = new BoardBuilder(pieces);
        builder.addPiece("Ram", 0, 6, PieceColor.BLUE)
                .addPiece("Ram", 1, 6, PieceColor.BLUE)
                .addPiece("Ram", 2, 6, PieceColor.BLUE)
                .addPiece("Ram", 3, 6, PieceColor.BLUE)
                .addPiece("Ram", 4, 6, PieceColor.BLUE)
                .addPiece("Biz", 1, 7, PieceColor.BLUE)
                .addPiece("Biz", 3, 7, PieceColor.BLUE)
                .addPiece("Sau", 2, 7, PieceColor.BLUE)
                .addPiece("Xor", 0, 7, PieceColor.BLUE)
                .addPiece("Tor", 4, 7, PieceColor.BLUE)
                .addPiece("Ram", 0, 1, PieceColor.RED)
                .addPiece("Ram", 1, 1, PieceColor.RED)
                .addPiece("Ram", 2, 1, PieceColor.RED)
                .addPiece("Ram", 3, 1, PieceColor.RED)
                .addPiece("Ram", 4, 1, PieceColor.RED)
                .addPiece("Biz", 1, 0, PieceColor.RED)
                .addPiece("Biz", 3, 0, PieceColor.RED)
                .addPiece("Sau", 2, 0, PieceColor.RED)
                .addPiece("Tor", 0, 0, PieceColor.RED)
                .addPiece("Xor", 4, 0, PieceColor.RED);
        pieces = builder.getPieces();
        return pieces;
    }

    // Author: Nick
    //Switch the turn, from to blue to red and vice versa
    public void switchTurn() {
        //turnCount is used to determine when Xor and Tor should change into their counterparts
        turnCount++;
        currentTurn = (currentTurn == PieceColor.BLUE) ? PieceColor.RED : PieceColor.BLUE;
    }

    // Author: Nick
    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    // Author: Nick
    public int getTurnCount() {
        return turnCount;
    }

}
