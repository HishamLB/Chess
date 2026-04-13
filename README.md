# Overview
Kwazam Chess is a Java Swing board game inspired by chess, but with its own set of pieces and a few custom rules. It includes a main menu, a playable board, save/load support, and board-flipping gameplay so each player always views the game from their side.

## Features
- Play on an 8x5 board
- Custom pieces:
  - **Ram**
  - **Biz**
  - **Sau**
  - **Tor**
  - **Xor**
- Board flips after turns so the current player always sees the board from their perspective
- Save and load game states
- Special piece behavior:
  - **Xor** and **Tor** transform into each other every 2 turns
- Built with **Java Swing** and **AWT**

!(image one)[./images/image1.png]
!(image one)[./images/image2.png]
!(image one)[./images/image3.png]

## How it works
The project is built around a simple MVC-style structure:
- **Model** handles game state and turn tracking
- **View** handles the menu and board UI
- **Controller** connects user actions to game logic

Some parts also use design patterns like Factory Builder etc.

## Project structure

- `Main.java` — application entry point
- `MenuView.java` — main menu window
- `MenuController.java` — handles menu actions
- `KwazamBoard.java` — main game board UI
- `Controller.java` — controls gameplay and player actions
- `BoardModel.java` — stores game state and turn data
- `Piece.java` — base class for all pieces
- `PieceFactory.java` — creates pieces
- `BoardBuilder.java` — places pieces on the board
- `SaveCommand.java` — saves the game state
- `LoadCommand.java` — loads a saved game
- `MainMenuCommand.java` — returns to the main menu
- `PieceImage.java` — manages piece images
- `PieceButton.java` — custom board button class
- `LinkingDict.java` — links board buttons to pieces
- `PieceColor.java` — piece color enum

## Running the game

### Requirements
- Java must be installed on your system

### Compile
```bash
javac *.java
```

### Run
```bash
java Main
```

## Save and load

The game can save its current state to:

```text
game_state.txt
```

If the file exists, you can load a previous game from the main menu.
