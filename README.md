# Minesweeper Game

This project is an implementation of the classic Minesweeper game using the Model-View-Controller (MVC) architecture pattern. The game adheres to the behavior described in the original game's specifications.

### Features

- Left-clicking on a closed cell reveals its content:
    - If a mine is present, the game ends (loss).
    - If no mine is present but there are adjacent mines, a number indicating the number of surrounding mines is displayed.
    - If there are no surrounding mines, all adjacent cells are opened recursively.
- Right-clicking on a closed cell flags/unflags it.
- Left-clicking on a flagged cell has no effect.
- The first click on the board never hits a mine; mines are placed after the first click.
- Flags can be placed and removed before the first click.
- Winning condition: all non-mine cells are opened.
- Displays the remaining number of mines (actual mines minus flagged cells).
- Middle-clicking on a cell with a number opens all surrounding cells if the number of flagged cells matches the number on the cell.
- Supports customizable board sizes and mine counts: Beginner (9x9, 10 mines), Intermediate (16x16, 40 mines), and Expert (16x30, 99 mines).
- High scores table persists between sessions.
- You can change path in file **records.properties** for records.
- User-accessible menu with commands: Exit, About, New Game, High Scores.

### Implementation Details

- Built with Java Swing (javax.swing.*).
- Mines and flags are represented with images.
- Uses GridBagLayout for arranging UI elements.
- Utilizes GridLayout for arranging cells on the game board.

### How to Play

1. Clone the repository.
2. Compile the Java files.
3. Run the compiled application.
4. Use the left mouse button to open cells, the right mouse button to flag/unflag cells, and the middle mouse button (or left and right buttons simultaneously) to open surrounding cells if conditions are met.
5. Enjoy the game!

### Demonstration
