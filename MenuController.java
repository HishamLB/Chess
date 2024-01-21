public class MenuController {
    private MenuView menuView;
    private boolean loading = false;

    // Author: Nick
    //The menu controller adds listeners to the menu's buttons
    public MenuController(MenuView menuView) {
        this.menuView = menuView;
        this.menuView.addStartButtonListener(e -> startGame());
        this.menuView.addLoadButtonListener(e -> loadGame());
    }

    // Author: Habeba
    //When a game is started, the main menu is disposed
    private void startGame() {
        menuView.setVisible(false);
        menuView.dispose();
        new Controller(loading);
    }

    // Author: Habeba
    //When a game is loaded, do the same, the main menu is disposed
    private void loadGame() {
        loading = true;
        new Controller(loading);
        menuView.setVisible(false);
        menuView.dispose();
    }


}