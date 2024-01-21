/*
   The board builder class is used to build the board by adding pieces to it. It uses the PieceFactory class to create pieces of the
   specified type and color and places them on the board at the specified position.
   The board builder class takes advantage of the creational design pattern builder to allow for the construction of  different complex objects
 */
public class BoardBuilder {

    //2D array to hold the pieces on the board
    Piece[][] pieces;

    public BoardBuilder(Piece pieces[][]){
        this.pieces = pieces;
    }

    // Author: Habeba
    public BoardBuilder addPiece(String pieceType, int x, int y, PieceColor color)
    {
        //Use Piece Factory to create a piece of the specified type and color
        Piece piece = PieceFactory.createPiece(pieceType, x, y, color);
        //Place created piece on the board at specified position
        pieces[y][x] = piece;
        return this;
    }
    public Piece[][] getPieces(){
        return pieces;
    }

}
