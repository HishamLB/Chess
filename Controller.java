import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public KwazamBoard board; //The board view
    PieceButton[][] buttons; //The buttons within the board
    LinkingDict[][] link; //Custom data structure (link dictionary) that links pieces with their respective buttons
    Piece[][] pieces; //Instances of the piece models
    Piece selectedPiece = null;
    PieceButton selectedButton = null;
    public PieceColor currentTurn; //The current turn can either be BLUE or RED
    BoardBuilder builder;
    int turnCount;
    List<JButton> highlightedButtons = new ArrayList<>(); //To track highlighted buttons
    //Concrete commands for saving and loading a game
    SaveCommand save;
    LoadCommand load;
    MainMenuCommand mainMenu;

    BoardModel model;

    // Author: Hesham
    //Constructor, the boolean loading is sent to determine whether a new game or a saved game is being loaded
    Controller(boolean loading)
    {
        save = new SaveCommand(this);
        load = new LoadCommand(this);
        link = new LinkingDict[8][5];
        model = new BoardModel();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                link[i][j] = new LinkingDict();
            }
        }
        board = new KwazamBoard(this);
        mainMenu = new MainMenuCommand(board);

        //The board's 'save' button's action listener is added here
        board.saveButton.addActionListener(new ActionListener() {
            @Override
            //When clicked, execute the save command
            public void actionPerformed(ActionEvent e) {
                save.execute();
            }
        });
        //The board's 'menu' button's action listener is added here
        board.menuButton.addActionListener(new ActionListener() {
            @Override
            //When clicked, the menu button will dispose of the board and initialise a new menu
            public void actionPerformed(ActionEvent e) {
                mainMenu.execute();
            }
        });
        buttons = board.getButtons();
        pieces = new Piece[8][5];
        builder = new BoardBuilder(pieces);
        //If a previously saved game is being loaded, execute the load command
        if (loading) {
            for (int i = 0; i < KwazamBoard.ROWS; i++) {
                for (int j = 0; j < KwazamBoard.COLS; j++) {
                    buttons[i][j].setIcon(null);
                }
            }
            load.execute();
            linkElements();
        }
        //Otherwise a new game is being started
        else {
            //Initialise all pieces for a new game
            pieces = model.newGame();
            currentTurn = PieceColor.BLUE; //First turn is blue

            buttons = board.getButtons();
            linkElements();
        }
    }

    // Author: Habeba
    //Iterate over the linking dictionary to get the corresponding piece for a button
    public Piece getPieceForButton(PieceButton button) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                if (link[i][j].getButton() == button) {
                    return link[i][j].getPiece();
                }
            }
        }
        return null;
    }

    // Author: Nick
    //To switch the turn after each turn
    private void switchTurn() {
        model.switchTurn();
        currentTurn = model.getCurrentTurn();
        turnCount = model.getTurnCount();
        //When both players have moved twice (turnCount == 4), transform Xor and Tor
        if (turnCount % 4 == 0) {
            checkTransformations();
        }
        //While flipping the board, disable all buttons temporarily, this is done to prevent unwanted behaviour
        setAllButtonsEnabled(false);
        board.menuButton.setEnabled(false);
        board.saveButton.setEnabled(false);

        //A timer is implemented to ensure that once a move is made, there is a 1-second delay from when the board display flips
        //To ensure no illegal moves are made during this period, the buttons are all disabled
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Flip the board and enable the buttons after the timer
                board.flipBoard(link);
                setAllButtonsEnabled(true);
                board.menuButton.setEnabled(true);
                board.saveButton.setEnabled(true);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Author: Hesham, Nick
    //To link each piece to its button
    public void linkElements() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                link[i][j] = new LinkingDict();
                link[i][j].setButton(buttons[i][j]);
                link[i][j].setPiece(pieces[i][j]);

                buttons[i][j].addActionListener(new ActionListener() {
                    /*
                    There are 2 main cases to consider for the action listener of the pieces. The first during a turn
                    a piece is selected it will highlight the possible moves for that piece. The second case is when a
                    highlighted button/position is clicked, it will move the piece to the new position and update the board.
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Get the source button
                        PieceButton button = (PieceButton) e.getSource();
                        //If there are highlighted pieces
                        if (!highlightedButtons.isEmpty()) {
                            //If highlighted button is clicked
                            if (highlightedButtons.contains(button)) {
                                Piece piece = selectedPiece;
                                //If it is an actual piece and it's turn
                                if (piece != null && piece.color == currentTurn) {
                                    //New x and y to the clicked button
                                    int newY = button.y;
                                    int newX = button.x;
                                    //If the piece was Sau, the game is over
                                    if (link[newY][newX].getPiece() instanceof Sau) {
                                        board.gameOver(link[newY][newX].getPiece().color);
                                    }
                                    //Flip the icon of the Ram when it turns the opposite way
                                    if(selectedPiece instanceof Ram && ((Ram) selectedPiece).flipped && !(((Ram) selectedPiece).viewFlip)){
                                        selectedButton.setIcon(board.rotateImageIcon((ImageIcon) selectedButton.getIcon()));
                                        ((Ram) selectedPiece).viewFlip = true;
                                    }
                                    //Reverse the Ram icon back to its original orientation
                                    else if (selectedPiece instanceof Ram && !((Ram) selectedPiece).flipped && ((Ram) selectedPiece).viewFlip) {
                                    selectedButton.setIcon(board.rotateImageIcon((ImageIcon) selectedButton.getIcon()));
                                    ((Ram) selectedPiece).viewFlip = false;
                                    }
                                    board.updateButtonPosition(piece.y, piece.x, newY, newX);
                                    //Update link
                                    link[piece.y][piece.x].setPiece(null);
                                    link[newY][newX].setPiece(piece);
                                    piece.y = newY;
                                    piece.x = newX;
                                    //Turn ended
                                    clearHighlights();
                                    switchTurn();
                                    //Ensure UI updates before delay
                                }
                            }
                            clearHighlights();
                        }
                        //If there are no highlighted buttons
                        else {
                            Piece piece = getPieceForButton(button);
                            //Actual piece and its turn
                            if (piece != null && piece.color == currentTurn) {
                                for (int[] move : piece.getPossibleMoves(link)) {
                                    int newX = move[1];
                                    int newY = move[0];
                                    if (isValidPosition(newX, newY) && (link[newY][newX].getPiece() == null || link[newY][newX].getPiece().color != piece.color)) {
                                        JButton targetButton = buttons[newY][newX];
                                        board.highlightButton(newY, newX);
                                        highlightedButtons.add(targetButton);
                                        selectedPiece = piece;
                                        selectedButton = button;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    // Author: Nick
    //A function to enable and disable buttons, the enabled boolean determines which state the button will be in
    private void setAllButtonsEnabled(boolean enabled) {
        for (int i = 0; i < KwazamBoard.ROWS; i++) {
            for (int j = 0; j < KwazamBoard.COLS; j++) {
                buttons[i][j].setDisabledIcon(buttons[i][j].getIcon());
                buttons[i][j].setEnabled(enabled);
            }
        }
    }

    // Author: Habeba
    //Bounds checking for the board
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < KwazamBoard.COLS && y >= 0 && y < KwazamBoard.ROWS;
    }

    // Author: Hesham
    //Clear all highlights and dehighlights the board
    private void clearHighlights() {
        for (JButton button : highlightedButtons) {
            board.dehighlightButton(button.getY() / button.getHeight(), button.getX() / button.getWidth());
        }
        highlightedButtons.clear();
    }

    // Author: Hesham
    //Transform Xor into Tor and Tor into Xor
    private void checkTransformations() {
        for (int i = 0; i < KwazamBoard.ROWS; i++) {
            for (int j = 0; j < KwazamBoard.COLS; j++) {
                Piece piece = link[i][j].getPiece();
                if (piece instanceof Tor) {
                    transformTorToXor((Tor) piece, i, j);
                } else if (piece instanceof Xor) {
                    transformXorToTor((Xor) piece, i, j);
                }
            }
        }
    }

    // Author: Nick
    //Transform Tor to a Xor piece
    private void transformTorToXor(Tor tor, int row, int col) {
        Xor xor = new Xor(tor.x, tor.y, tor.color);
        link[row][col].setPiece(xor);
        buttons[row][col].setIcon(board.getIconForPiece(xor));
    }

    // Author: Hesham
    //Transform Xor to a Tor piece
    private void transformXorToTor(Xor xor, int row, int col) {
        Tor tor = new Tor(xor.x, xor.y, xor.color);
        link[row][col].setPiece(tor);
        buttons[row][col].setIcon(board.getIconForPiece(tor));
    }

}