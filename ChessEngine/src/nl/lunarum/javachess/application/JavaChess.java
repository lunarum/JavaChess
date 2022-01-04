package nl.lunarum.javachess.application;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class JavaChess {
    private final JFrame mainWindow;
    private final BoardCanvas canvas;

    public static void main(String[] args) throws IOException {
        JavaChess chess = new JavaChess();
    }

    public JavaChess() {
        mainWindow = new JFrame("Java Chess");
        mainWindow.setLayout(new BorderLayout());

        canvas = new BoardCanvas();
        mainWindow.add(canvas, BorderLayout.CENTER);

        mainWindow.pack();
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
