// MainMenuCommand which implements GameCommand interface
public class MainMenuCommand implements GameCommand {

    private KwazamBoard board;
    MainMenuCommand(KwazamBoard board) {
        this.board = board;
    }

    // Author: Nick
    @Override
    public void execute() {
        board.setVisible(false);
        board.dispose();  // Close the game window
        new MenuController(new MenuView());
    }
}
