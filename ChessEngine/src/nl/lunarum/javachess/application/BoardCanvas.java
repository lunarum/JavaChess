package nl.lunarum.javachess.application;

import java.awt.*;

public class BoardCanvas extends Canvas {
    private static final int SIZE = 800;
    private static final int BORDER_SIZE = SIZE / 20;
    private static final Font BORDER_FONT = new Font("LucidaSans", Font.BOLD, 16);
    private static final Font PIECE_FONT = new Font("LucidaSans", Font.PLAIN, 72);
    private static final Color SAXION_GREEN = new Color(0,156,130);

    public BoardCanvas() {
        setSize(SIZE, SIZE);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        int squareSize = (getWidth() - 2 * BORDER_SIZE) / 8;

        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), SIZE/20, SIZE/20);

        graphics.setColor(Color.WHITE);
        graphics.setFont(BORDER_FONT);
        int xPos1 = BORDER_SIZE + squareSize / 2 - 4;
        int yPos1 = BORDER_SIZE / 2 + 4;
        int yPos2 = getHeight() - yPos1 + 8;
        for(int file = 0; file < 8; ++file) {
            var fileString = "ABCDEFGH".substring(file, file + 1);
            graphics.drawString(fileString, xPos1, yPos1);
            graphics.drawString(fileString, xPos1, yPos2);
            xPos1 += squareSize;
        }
        xPos1 = BORDER_SIZE / 2 - 4;
        int xPos2 = getWidth() - xPos1 - 8;
        yPos1 = BORDER_SIZE + squareSize / 2;
        for(int rank = 0; rank < 8; ++rank) {
            var rankString = "" + (8 - rank);
            graphics.drawString(rankString, xPos1, yPos1);
            graphics.drawString(rankString, xPos2, yPos1);
            yPos1 += squareSize;
        }

        boolean isBlack = false;
        for(int file = 0; file < 8; ++file) {
            for(int rank = 0; rank < 8; ++rank) {
                if (isBlack)
                    graphics.setColor(SAXION_GREEN);
                else
                    graphics.setColor(Color.WHITE);
                graphics.fillRect(BORDER_SIZE + file * squareSize, BORDER_SIZE + rank * squareSize, squareSize, squareSize);
                isBlack = !isBlack;
            }
            isBlack = !isBlack;
        }

        char chessChar =  '\u2654'; // Unicode white king

        graphics.setFont(PIECE_FONT);
        for(int rank = 1; rank < 3; ++rank) {
            for(int file = 0; file < 6; ++file) {
                var ch = Character.toString(chessChar++);
                graphics.setColor(Color.BLACK);
                graphics.drawString(ch,
                        BORDER_SIZE + file * squareSize + 8,
                        BORDER_SIZE + rank * squareSize - 20);
            }
        }
    }
}
