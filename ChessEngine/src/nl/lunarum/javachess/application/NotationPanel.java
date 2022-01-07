package nl.lunarum.javachess.application;

import nl.lunarum.javachess.engine.Ply;

import javax.swing.*;
import java.util.ArrayList;

public class NotationPanel extends JPanel {
    private static final int WIDTH = 200;

    private final ArrayList<Ply> game;

    public NotationPanel(ArrayList<Ply> game) {
        this.game = game;

        setSize(WIDTH, getHeight());
    }
}
