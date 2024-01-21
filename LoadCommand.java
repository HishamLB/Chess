import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoadCommand implements GameCommand {
    Controller controller;

    LoadCommand(Controller controller) {

        this.controller = controller;
    }

    // Author: Hesham
    @Override
    public void execute() {
        File file = new File("game_state.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Error: game_state.txt file not found.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        //Reads the game_state.txt files and updates the board when a saved game is loaded
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line = reader.readLine();
            controller.model.currentTurn = PieceColor.valueOf(line.split(": ")[1]);
            controller.currentTurn = PieceColor.valueOf(line.split(": ")[1]);

            line = reader.readLine();
            controller.model.turnCount = Integer.parseInt(line.split(": ")[1]);

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String pieceType = parts[0];
                PieceColor color = PieceColor.valueOf(parts[1]);
                int x = Integer.parseInt(parts[2]);
                int y = Integer.parseInt(parts[3]);

                controller.builder.addPiece(pieceType, x, y, color);
                Piece pieces[][] = controller.builder.getPieces();
                controller.link[y][x].setPiece(pieces[y][x]);
                controller.buttons[y][x].setIcon(controller.board.getIconForPiece(pieces[y][x]));

                if (pieces[y][x] instanceof Ram) {
                    Ram piece = (Ram) pieces[y][x];
                    if (parts.length > 4 && "Flipped".equals(parts[4])) {
                        if(y!=0 || y!=7){
                            piece.viewFlip = true;
                        }
                        piece.flipped = true;
                        controller.buttons[y][x].setIcon(controller.board.rotateImageIcon((ImageIcon) controller.buttons[y][x].getIcon()));
                    }
                }

                //If the player is red rotate so the board looks right
                if (controller.currentTurn == PieceColor.RED) {
                    controller.buttons[y][x].setIcon(controller.board.rotateImageIcon((ImageIcon) controller.buttons[y][x].getIcon()));
                }
                controller.link[y][x].setButton(controller.buttons[y][x]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
