package nl.lunarum.javachess.application;

import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NotationPanel extends JScrollPane {
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font NOTATION_FONT = new Font("Arial", Font.BOLD, 12);

    private final ArrayList<Ply> game;

    public NotationPanel(ArrayList<Ply> game) {
        this.game = game;
        //TODO Why doesn't this work?
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(Color.BLACK);
        graphics.setFont(TITLE_FONT);
        graphics.drawString("GAME", 2, 20);

        graphics.setFont(NOTATION_FONT);
        int yPosition = 36;
        String move = "...";
        for (var ply : game) {
            if (ply.piece.color == Piece.Color.BLACK) {
                move += ", " + ply;
                graphics.drawString(move, 4, yPosition);
                yPosition += 14;
                move = null;
            } else {
                move = ply.getMove() + ". " + ply;
            }
        }
        if (move != null) {
            graphics.drawString(move, 4, yPosition);
        }
    }
}
