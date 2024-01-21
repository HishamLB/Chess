import javax.swing.*;

//Custom button class extending from JButton. This is the button pieces will use
public class PieceButton extends JButton {
    public int x, y;

    // Author: Hesham
    public PieceButton(int y, int x) {
        this.x = x;
        this.y = y;
    }
}