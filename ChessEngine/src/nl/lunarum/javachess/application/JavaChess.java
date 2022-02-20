package nl.lunarum.javachess.application;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class JavaChess {
    private final JFrame mainWindow;
    private final BoardPanel canvas;
    private final NotationPanel notationPanel;
    private final ButtonPanel buttonPanel;

    public static void main(String[] args) throws IOException {
        new JavaChess();
    }

    public JavaChess() {
        mainWindow = new JFrame("Java Chess");
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setSize(1000, 800);

        ChessBoard chessBoard = new ChessBoard();
        chessBoard.setup();

        notationPanel = new NotationPanel(chessBoard);
        notationPanel.setPreferredSize(new Dimension(200, 600));
        mainWindow.add(notationPanel, BorderLayout.EAST);

        canvas = new BoardPanel(notationPanel, chessBoard);
        canvas.setMinimumSize(new Dimension(400, 400));
        canvas.setPreferredSize(new Dimension(800, 600));
        mainWindow.add(canvas, BorderLayout.CENTER);

        buttonPanel = new ButtonPanel(chessBoard, canvas, notationPanel);
        canvas.setMinimumSize(new Dimension(400, 50));
        buttonPanel.setPreferredSize(new Dimension(600, 50));
        mainWindow.add(buttonPanel, BorderLayout.SOUTH);

//        mainWindow.pack();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
