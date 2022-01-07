package nl.lunarum.javachess.application;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class JavaChess {
    private final JFrame mainWindow;
    private final BoardCanvas canvas;
    private final NotationPanel notationPanel;

    public static void main(String[] args) throws IOException {
        new JavaChess();
    }

    public JavaChess() {
        mainWindow = new JFrame("Java Chess");
        mainWindow.setLayout(new BorderLayout());

        ArrayList<Ply> game = new ArrayList<>();
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.setup();
        canvas = new BoardCanvas(chessBoard, game);
        mainWindow.add(canvas, BorderLayout.CENTER);

        notationPanel = new NotationPanel(game);
        mainWindow.add(notationPanel, BorderLayout.EAST);

        mainWindow.pack();
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
