import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/*
   KwazamBoard is the main view class for the game. It is responsible for displaying the game board and pieces.
   It takes advantage of the MVC design pattern by acting as the view in the MVC pattern.
 */

public class KwazamBoard extends JFrame {

    static final int ROWS = 8;
    static final int COLS = 5;
    PieceButton[][] buttons;  // To keep track of the buttons
    public JButton saveButton, menuButton;

    // Author: Nick, Hesham, Habiba
    KwazamBoard(Controller controller) {
        //Frame Initialisation
        saveButton = new JButton("Save Game");
        menuButton = new JButton("Main Menu");
        this.setTitle("Kwazam Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 800);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        //Wrapper panel for resizing
        JPanel wrapper = new JPanel(new GridBagLayout());
        this.add(wrapper, BorderLayout.CENTER);

        //Board panel (grid layout)
        JPanel board = new JPanel(new GridLayout(ROWS, COLS));
        wrapper.add(board);

        //Top panel for save and menu button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton.setPreferredSize(new Dimension(200, 50));
        menuButton.setPreferredSize(new Dimension(200, 50));
        topPanel.add(saveButton);
        topPanel.add(menuButton);
        this.add(topPanel, BorderLayout.NORTH);

        PieceImage pieceImage = new PieceImage();
        //Buttons array
        buttons = new PieceButton[ROWS][COLS];
        //Add buttons
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                PieceButton square = new PieceButton(row, col);
                square.setBackground(Color.WHITE);

                //Set icon if present
                if (pieceImage.getBoardLayout()[row][col] != null) {
                    square.setIcon(pieceImage.getBoardLayout()[row][col]);
                }

                //Store button reference in the array
                buttons[row][col] = square;

                //Add button to the panel
                board.add(square);
            }
        }

        //Resizing dynamically
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int wrapperWidth = wrapper.getWidth();
                int wrapperHeight = wrapper.getHeight();
                int squareSize = Math.min(wrapperWidth / COLS, wrapperHeight / ROWS);

                //Update board size for square cells
                board.setPreferredSize(new Dimension(squareSize * COLS, squareSize * ROWS));
                wrapper.revalidate();
            }
        });
    }

    // Author: Hesham
    public void flipBoard(LinkingDict[][] link) {
        for (int i = 0; i < KwazamBoard.ROWS / 2; i++) {
            for (int j = 0; j < KwazamBoard.COLS; j++) {
                //Swap pieces
                Piece topPiece = link[i][j].getPiece();
                Piece bottomPiece = link[KwazamBoard.ROWS - 1 - i][j].getPiece();

                //Update coordinates for the pieces being swapped
                if (topPiece != null) {
                    topPiece.y = KwazamBoard.ROWS - 1 - i;
                    topPiece.x = j;
                }
                if (bottomPiece != null) {
                    bottomPiece.y = i;
                    bottomPiece.x = j;
                }

                //Perform the swap in the linking dict
                link[i][j].setPiece(bottomPiece);
                link[KwazamBoard.ROWS - 1 - i][j].setPiece(topPiece);

                //Only ram and sau pieces need to have their image flipped when the board is flipped
                if (topPiece instanceof Ram) {
                    buttons[i][j].setIcon(rotateImageIcon((ImageIcon) buttons[i][j].getIcon()));
                } else if (topPiece instanceof Sau) {
                    buttons[i][j].setIcon(rotateImageIcon((ImageIcon) buttons[i][j].getIcon()));
                }

                if (bottomPiece instanceof Ram) {
                    buttons[KwazamBoard.ROWS - 1 - i][j].setIcon(rotateImageIcon((ImageIcon) buttons[KwazamBoard.ROWS - 1 - i][j].getIcon()));
                } else if (bottomPiece instanceof Sau) {
                    buttons[KwazamBoard.ROWS - 1 - i][j].setIcon(rotateImageIcon((ImageIcon) buttons[KwazamBoard.ROWS - 1 - i][j].getIcon()));
                }

                // Swap icons on buttons
                Icon tempIcon = buttons[i][j].getIcon();
                buttons[i][j].setIcon(buttons[KwazamBoard.ROWS - 1 - i][j].getIcon());
                buttons[KwazamBoard.ROWS - 1 - i][j].setIcon(tempIcon);
            }
        }
    }

    // Author: Habeba
    //Return the image (with colour) of a piece
    public ImageIcon getIconForPiece(Piece piece) {
        PieceImage pieceImage = new PieceImage();
        if (piece instanceof Biz) {
            return piece.color == PieceColor.BLUE ? pieceImage.getBlueBiz() : pieceImage.getRedBiz();
        } else if (piece instanceof Ram) {
            return piece.color == PieceColor.BLUE ? pieceImage.getBlueRam() : pieceImage.getRedRam();
        } else if (piece instanceof Sau) {
            return piece.color == PieceColor.BLUE ? pieceImage.getBlueSau() : pieceImage.getRedSau();
        } else if (piece instanceof Tor) {
            return piece.color == PieceColor.BLUE ? pieceImage.getBlueTor() : pieceImage.getRedTor();
        } else if (piece instanceof Xor) {
            return piece.color == PieceColor.BLUE ? pieceImage.getBlueXor() : pieceImage.getRedXor();
        }
        return null;
    }


    // Author: Nick
    //Rotate the Image icon, used when board is flipped and when ram turns around
    public ImageIcon rotateImageIcon(ImageIcon icon) {
        if (icon == null) {
            return null;  //Return null if the icon is not initialized
        }

        //Convert ImageIcon to Image
        Image image = icon.getImage();

        //Create a BufferedImage from the Image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        //Rotate the image by 180 degrees
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.rotate(Math.PI, image.getWidth(null) / 2, image.getHeight(null) / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        //Return the rotated image as an ImageIcon
        return new ImageIcon(bufferedImage);
    }

    // Author: Hesham
    //Highlight a button based on x, y position
    public void highlightButton(int x, int y) {
        if (x >= 0 && x < ROWS && y >= 0 && y < COLS) {
            PieceButton button = buttons[x][y];
            button.setBackground(Color.YELLOW);
        }
    }

    // Author: Habeba
    //De-highlight a button based on x, y position
    public void dehighlightButton(int x, int y) {
        if (x >= 0 && x < ROWS && y >= 0 && y < COLS) {
            PieceButton button = buttons[x][y];
            button.setBackground(Color.WHITE);
        }
    }

    // Author: Habeba
    public PieceButton[][] getButtons() {
        return buttons;
    }

    // Author: Nick
    //Used for a piece moves, this visually updates the board
    public void updateButtonPosition(int oldRow, int oldCol, int newRow, int newCol) {
        if (oldRow < 0 || oldRow >= ROWS || oldCol < 0 || oldCol >= COLS) return;
        if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS) return;

        PieceButton sourceButton = buttons[oldRow][oldCol];
        PieceButton targetButton = buttons[newRow][newCol];
        if (targetButton != null) {
            killPiece(targetButton);
        }
        //Swap icons
        Icon tempIcon = sourceButton.getIcon();
        sourceButton.setIcon(targetButton.getIcon());
        targetButton.setIcon(tempIcon);

        //Update the button background
        targetButton.setBackground(Color.WHITE);
    }

    // Author: Hesham
    private void killPiece(PieceButton targetButton) {
        //Kill the piece
        targetButton.setIcon(null);
    }

    // Author: Hesham
    public void gameOver(PieceColor color) {
        PieceColor oppositeColor = (color == PieceColor.RED) ? PieceColor.BLUE : PieceColor.RED;
        JOptionPane.showMessageDialog(this, "Game Over! Winning team is " + oppositeColor.name());

        //Hide and dispose the current game board
        this.setVisible(false);
        this.dispose();

        //Show the main menu
        new MenuController(new MenuView());
    }
}