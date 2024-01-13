package edu.effectuss.minesweeper.view.forms;

import edu.effectuss.minesweeper.view.listeners.CellEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {
    private static final int IMAGE_SIZE = 20;
    private final Container contentPane;
    private final GridBagLayout mainLayout;

    private JMenuItem newGameMenu;
    private JMenuItem highScoresMenu;
    private JMenuItem settingsMenu;
    private JMenuItem exitMenu;

    private transient CellEventListener cellEventListener;

    private JButton[][] cellButtons;
    private JLabel timerLabel;
    private JLabel bombsCounterLabel;

    public MainWindow() {
        super("Miner");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        createMenu();

        contentPane = getContentPane();
        mainLayout = new GridBagLayout();
        contentPane.setLayout(mainLayout);

        contentPane.setBackground(new Color(144, 158, 184));
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        newGameMenu = new JMenuItem("New Game");
        gameMenu.add(newGameMenu);

        gameMenu.addSeparator();

        highScoresMenu = new JMenuItem("High Scores");
        gameMenu.add(highScoresMenu);

        settingsMenu = new JMenuItem("Settings");
        gameMenu.add(settingsMenu);

        gameMenu.addSeparator();

        exitMenu = new JMenuItem("Exit");
        gameMenu.add(exitMenu);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    public void setNewGameMenuAction(ActionListener listener) {
        newGameMenu.addActionListener(listener);
    }

    public void setHighScoresMenuAction(ActionListener listener) {
        highScoresMenu.addActionListener(listener);
    }

    public void setSettingsMenuAction(ActionListener listener) {
        settingsMenu.addActionListener(listener);
    }

    public void setExitMenuAction(ActionListener listener) {
        exitMenu.addActionListener(e -> {
            listener.actionPerformed(e);
            System.exit(0);
        });
    }

    public void setCellListener(CellEventListener listener) {
        this.cellEventListener = listener;
    }

    public void setCellImage(int x, int y, GameImage gameImage) {
        cellButtons[y][x].setIcon(gameImage.getImageIcon());
    }

    public void setBombsCount(int bombsCount) {
        bombsCounterLabel.setText(String.valueOf(bombsCount));
    }

    public void setTimerValue(int value) {
        timerLabel.setText(String.valueOf(value));
    }

    public void createGameField(int rowsCount, int colsCount) {
        contentPane.removeAll();
        setPreferredSize(new Dimension(IMAGE_SIZE * colsCount + 70,
                IMAGE_SIZE * rowsCount + 110));

        addButtonsPanel(createButtonsPanel(rowsCount, colsCount));
        addTimerImage();

        timerLabel = new JLabel("0");
        addTimerLabel(timerLabel);

        bombsCounterLabel = new JLabel("0");
        addBombCounter(bombsCounterLabel);
        addBombCounterImage();

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createButtonsPanel(int numberOfRows, int numberOfCols) {
        cellButtons = new JButton[numberOfRows][numberOfCols];
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(IMAGE_SIZE * numberOfCols,
                IMAGE_SIZE * numberOfRows));
        buttonsPanel.setLayout(new GridLayout(numberOfRows, numberOfCols, 0, 0));

        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfCols; col++) {
                final int x = col;
                final int y = row;

                cellButtons[y][x] = new JButton(GameImage.CLOSED.getImageIcon());
                cellButtons[y][x].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (cellEventListener == null) {
                            return;
                        }

                        switch (e.getButton()) {
                            case MouseEvent.BUTTON1:
                                cellEventListener.onLeftButtonMouseClick(x, y);
                                break;
                            case MouseEvent.BUTTON2:
                                cellEventListener.onMiddleButtonMouseClick(x, y);
                                break;
                            case MouseEvent.BUTTON3:
                                cellEventListener.onRightButtonMouseClick(x, y);
                                break;
                            default:
                                // Other mouse buttons are ignored
                        }
                    }
                });
                buttonsPanel.add(cellButtons[y][x]);
            }
        }

        return buttonsPanel;
    }

    private void addButtonsPanel(JPanel buttonsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.insets = new Insets(20, 20, 5, 20);
        mainLayout.setConstraints(buttonsPanel, gbc);
        contentPane.add(buttonsPanel);
    }

    private void addTimerImage() {
        JLabel label = new JLabel(GameImage.TIMER.getImageIcon());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        mainLayout.setConstraints(label, gbc);
        contentPane.add(label);
    }

    private void addTimerLabel(JLabel timerLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 0, 0);
        mainLayout.setConstraints(timerLabel, gbc);
        contentPane.add(timerLabel);
    }

    private void addBombCounter(JLabel bombsCounterLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.7;
        mainLayout.setConstraints(bombsCounterLabel, gbc);
        contentPane.add(bombsCounterLabel);
    }

    private void addBombCounterImage() {
        JLabel label = new JLabel(GameImage.BOMB_ICON.getImageIcon());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 3;
        gbc.insets = new Insets(0, 5, 0, 20);
        gbc.weightx = 0.1;
        mainLayout.setConstraints(label, gbc);
        contentPane.add(label);
    }
}
