//Custom data structure (Linking Dictionary) to link pieces to their corresponding buttons. It stores
//a piece and a button in a single object.

public class LinkingDict {
    private PieceButton button;
    private Piece piece;

    // Author: Hesham
    public PieceButton getButton() {
        return button;
    }

    // Author: Hesham
    public void setButton(PieceButton button) {
        this.button = button;
    }

    // Author: Hesham
    public Piece getPiece() {
        return piece;
    }

    // Author: Hesham
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}