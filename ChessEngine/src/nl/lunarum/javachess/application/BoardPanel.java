package nl.lunarum.javachess.application;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;
import nl.lunarum.javachess.engine.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BoardPanel extends JPanel {
    private int size;
    private int borderSize;
    private int squareSize;
    private int markBorderSize;

    private static final int PIECE_FONT_SIZE = 72;
    private static final Font BORDER_FONT = new Font("LucidaSans", Font.BOLD, 16);
    private static final Font PIECE_FONT = new Font("LucidaSans", Font.PLAIN, PIECE_FONT_SIZE);
    private static final Color SAXION_GREEN = new Color(0, 156, 130);

    private final NotationPanel notationPanel;
    private final ChessBoard chessBoard;
    private final ArrayList<Ply> possiblePlies = new ArrayList<>(40);
    private Position selectedPosition = null;

    private void setSizes() {
        size = Math.min(getWidth(), getHeight());
        borderSize = size / 20;
        squareSize = (size - 2 * borderSize) / 8;
        markBorderSize = borderSize / 5;
    }

    public BoardPanel(NotationPanel notationPanel, ChessBoard chessBoard) {
        this.notationPanel = notationPanel;
        this.chessBoard = chessBoard;
        setSizes();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Nothing...
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                var point = e.getPoint();
                var position = pointToPosition(point);
                if (position != null) {
                    var piece= chessBoard.getPiece(position);
                    if (selectedPosition == null || possiblePlies.size() == 0) {
                        if (piece != null && piece.color == chessBoard.getCurrentPlayer().color) {
                            possiblePlies.clear();
                            piece.addPossiblePlies(possiblePlies, chessBoard, position);
                            if (possiblePlies.size() > 0) { // Can this piece be moved?
                                selectedPosition = position;
                                repaint();
                            }
                        }
                    } else {
                        for (var ply : possiblePlies) {
                            if (ply.to.compareTo(position) == 0) {
                                chessBoard.playPly(ply);
                                notationPanel.repaint();
                                selectedPosition = null;
                                possiblePlies.clear();
                                repaint();
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        setSizes();
        paintBoard(graphics);
        paintPieces(graphics);
        if (selectedPosition != null) {
            markSquare((Graphics2D) graphics, selectedPosition, Color.BLUE);
            for (var ply : possiblePlies)
                markSquare((Graphics2D) graphics, ply.to, Color.GREEN);
        }
    }


    private void markSquare(Graphics2D graphics2, Position position, Color color) {
        if (position != null) {
//            var oldStroke = graphics2.getStroke();
            graphics2.setStroke(new BasicStroke(markBorderSize));
            graphics2.setColor(color);
            int x = borderSize + squareSize * position.file + markBorderSize / 2;
            int x1 = x + squareSize - markBorderSize;
            int y = borderSize + squareSize * (7 - position.rank) + markBorderSize / 2;
            int y1 = y + squareSize - markBorderSize;
//            graphics2.drawRect(x, y,SQUARE_SIZE - MARK_BORDER_SIZE, SQUARE_SIZE - MARK_BORDER_SIZE);
            graphics2.drawLine(x, y1, x1, y1);
//            graphics2.setStroke(oldStroke);
        }
    }

    public void paintBoard(Graphics graphics) {
       // Paint border
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRoundRect(0, 0, size, size,/* getWidth(), getHeight(),*/ size / 20, size / 20);

        // Paint file letters
        graphics.setColor(Color.WHITE);
        graphics.setFont(BORDER_FONT);
        int xPos1 = borderSize + squareSize / 2 - 4;
        int yPos1 = borderSize / 2 + 4;
        int yPos2 = size - yPos1 + 8;
        for (int file = 0; file < 8; ++file) {
            var fileString = "ABCDEFGH".substring(file, file + 1);
            graphics.drawString(fileString, xPos1, yPos1);
            graphics.drawString(fileString, xPos1, yPos2);
            xPos1 += squareSize;
        }
        // Paint rank numbers
        xPos1 = borderSize / 2 - 4;
        int xPos2 = size - xPos1 - 8;
        yPos1 = borderSize + squareSize / 2;
        for (int rank = 0; rank < 8; ++rank) {
            var rankString = "" + (8 - rank);
            graphics.drawString(rankString, xPos1, yPos1);
            graphics.drawString(rankString, xPos2, yPos1);
            yPos1 += squareSize;
        }

        // Paint squares
        boolean isBlack = false;
        for (int file = 0; file < 8; ++file) {
            for (int rank = 0; rank < 8; ++rank) {
                if (isBlack)
                    graphics.setColor(SAXION_GREEN);
                else
                    graphics.setColor(Color.WHITE);
                graphics.fillRect(borderSize + file * squareSize, borderSize + rank * squareSize, squareSize, squareSize);
                isBlack = !isBlack;
            }
            isBlack = !isBlack;
        }
    }

    private String pieceToString(Piece piece) {
        if (piece.color == Piece.Color.BLACK) {
            return switch (piece.type()) {
                case PAWN -> "\u265f"; // Unicode black pawn
                case KING -> "\u265a"; // Unicode black king
                case QUEEN -> "\u265b"; // Unicode black queen
                case ROOK -> "\u265c"; // Unicode black rook
                case BISHOP -> "\u265d"; // Unicode black bishop
                case KNIGHT -> "\u265e"; // Unicode black knight
            };
        } else {
            return switch (piece.type()) {
                case PAWN -> "\u2659"; // Unicode white pawn
                case KING -> "\u2654"; // Unicode white king
                case QUEEN -> "\u2655"; // Unicode white queen
                case ROOK -> "\u2656"; // Unicode white rook
                case BISHOP -> "\u2657"; // Unicode white bishop
                case KNIGHT -> "\u2658"; // Unicode white knight
            };
        }
    }

    private void paintPieces(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        for(var position : Position.values()) {
            var piece = chessBoard.onSquare(position);
            if (piece != null) {
                String pieceString = pieceToString(piece);
                if (pieceString != null) {
                    int offset = 0;//(squareSize - PIECE_FONT_SIZE) / 2;
                    int x = borderSize + position.file * squareSize + offset;
                    int y = borderSize + (8 - position.rank) * squareSize + offset;
                    graphics.setFont(PIECE_FONT);
                    graphics.drawString(pieceString, x, y);
                }
            }
        }
    }

    private Position pointToPosition(Point point) {
        int file = (point.x - borderSize) / squareSize;
        if (file >= 0 && file <= 7) {
            int rank = (point.y - borderSize) / squareSize;
            if (rank >= 0 && rank <= 7) {
                return Position.fromFileRank(file, 7 - rank);
            }
        }
        return null;
    }
}
