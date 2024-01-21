import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveCommand implements GameCommand {


    Controller controller;

    SaveCommand(Controller controller) {
        this.controller = controller;
    }

    // Author: Hesham
    @Override
    public void execute() {
        //This writes the game state into a file called game_state.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_state.txt"))) {
            writer.write("Turn: " + controller.currentTurn.name() + "\n");
            writer.write("TurnCount: " + controller.model.turnCount + "\n");
            for (int i = 0; i < KwazamBoard.ROWS; i++) {
                for (int j = 0; j < KwazamBoard.COLS; j++) {
                    Piece piece = controller.link[i][j].getPiece();
                    if (piece != null) {
                        writer.write(piece.getClass().getSimpleName() + "," + piece.color.name() + "," + piece.x + "," + piece.y);
                        //If a ram is flipped
                        if (piece instanceof Ram && ((Ram) piece).viewFlip) {
                            writer.write(",Flipped");
                        }
                        writer.write("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
