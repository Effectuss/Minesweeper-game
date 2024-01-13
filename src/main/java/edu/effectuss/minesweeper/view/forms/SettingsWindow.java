package edu.effectuss.minesweeper.view.forms;

import edu.effectuss.minesweeper.view.listeners.GameTypeListener;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class SettingsWindow extends JDialog {
    private final Map<GameLevel, JRadioButton> radioButtonsMap = new EnumMap<>(GameLevel.class);
    private final ButtonGroup radioGroup = new ButtonGroup();
    private transient GameTypeListener gameTypeListener;

    @Getter
    private GameLevel gameLevel;

    public SettingsWindow(JFrame owner) {
        super(owner, "Settings", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        int gridY = 0;
        contentPane.add(createRadioButton("Novice (10 mines, 9х9)",
                GameLevel.NOVICE, layout, gridY++));
        contentPane.add(createRadioButton("Medium (40 mines, 16х16)",
                GameLevel.MEDIUM, layout, gridY++));
        contentPane.add(createRadioButton("Expert (99 mines, 16х30)",
                GameLevel.EXPERT, layout, gridY));

        contentPane.add(createOkButton(layout));
        contentPane.add(createCloseButton(layout));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 180));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setGameType(GameLevel.NOVICE);
    }

    public void setGameType(GameLevel gameLevel) {
        JRadioButton radioButton = radioButtonsMap.get(gameLevel);

        if (radioButton == null) {
            throw new UnsupportedOperationException("Unknown game type: " + gameLevel);
        }

        this.gameLevel = gameLevel;
        radioGroup.setSelected(radioButton.getModel(), true);
    }

    public void setGameTypeListener(GameTypeListener gameTypeListener) {
        this.gameTypeListener = gameTypeListener;
    }

    private JRadioButton createRadioButton(String radioButtonText, GameLevel gameLevel,
                                           GridBagLayout layout, int gridY) {
        JRadioButton radioButton = new JRadioButton(radioButtonText);
        radioButton.addActionListener(e -> this.gameLevel = gameLevel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        layout.setConstraints(radioButton, gbc);

        radioButtonsMap.put(gameLevel, radioButton);
        radioGroup.add(radioButton);

        return radioButton;
    }

    private JButton createOkButton(GridBagLayout layout) {
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(80, 25));
        okButton.addActionListener(e -> {
            dispose();

            if (gameTypeListener != null) {
                gameTypeListener.onGameTypeChanged(gameLevel);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(okButton, gbc);

        return okButton;
    }

    private JButton createCloseButton(GridBagLayout layout) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(80, 25));
        cancelButton.addActionListener(e -> dispose());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(cancelButton, gbc);

        return cancelButton;
    }
}
