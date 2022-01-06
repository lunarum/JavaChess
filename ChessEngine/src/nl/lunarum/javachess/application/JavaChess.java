package nl.lunarum.javachess.application;

import nl.lunarum.javachess.engine.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class JavaChess {
    private final JFrame mainWindow;
    private final BoardCanvas canvas;

    public static void main(String[] args) throws IOException {
        new JavaChess();
    }

    public JavaChess() {
        mainWindow = new JFrame("Java Chess");
        mainWindow.setLayout(new BorderLayout());

        ChessBoard chessBoard = new ChessBoard();
        chessBoard.setup();
        canvas = new BoardCanvas(chessBoard);
        mainWindow.add(canvas, BorderLayout.CENTER);

        mainWindow.pack();
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
