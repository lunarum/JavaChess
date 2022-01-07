package nl.lunarum.javachess.application;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;
import nl.lunarum.javachess.engine.pieces.Piece;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BoardCanvas extends Canvas {
    private static final int SIZE = 800;
    private static final int BORDER_SIZE = SIZE / 20;
    private static final int SQUARE_SIZE = (SIZE - 2 * BORDER_SIZE) / 8;
    private static final int MARK_BORDER_SIZE = BORDER_SIZE / 5;
    private static final Font BORDER_FONT = new Font("LucidaSans", Font.BOLD, 16);
    private static final Font PIECE_FONT = new Font("LucidaSans", Font.PLAIN, 72);
    private static final Color SAXION_GREEN = new Color(0, 156, 130);

    private final ChessBoard chessBoard;
    private final ArrayList<Ply> game;
    private ArrayList<Ply> possiblePlies = null;
    private Position selectedPosition = null;

    public BoardCanvas(ChessBoard chessBoard, ArrayList<Ply> game) {
        this.chessBoard = chessBoard;
        this.game = game;
        setSize(SIZE, SIZE);
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
                    var piece = getPiece(position);
                    if (selectedPosition == null || possiblePlies.size() == 0) {
                        if (piece != null && piece.getPlayer() == chessBoard.getPlayer()) {
                            selectedPosition = position;
                            possiblePlies = piece.possiblePlies();
                            repaint();
                        }
                    } else {
                        for(var ply : possiblePlies) {
                            if (ply.to.compareTo(position) == 0) {
                                chessBoard.playPly(ply);
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
        paintBoard(graphics);
        paintPieces(graphics, chessBoard.getBlackPieces());
        paintPieces(graphics, chessBoard.getWhitePieces());
        if (selectedPosition != null) {
            markSquare((Graphics2D) graphics, selectedPosition, Color.BLUE);
            for (var ply : possiblePlies)
                markSquare((Graphics2D) graphics, ply.to, Color.GREEN);
        }
    }

    private Piece getPiece(Position position) {
        for (Piece piece : chessBoard.getBlackPieces()) {
            if (position.compareTo(piece.getPosition()) == 0)
                return piece;
        }
        for (Piece piece : chessBoard.getWhitePieces()) {
            if (position.compareTo(piece.getPosition()) == 0)
                return piece;
        }
        return null;
    }

    private void markSquare(Graphics2D graphics2, Position position, Color color) {
        if (position != null) {
//            var oldStroke = graphics2.getStroke();
            graphics2.setStroke(new BasicStroke(MARK_BORDER_SIZE));
            graphics2.setColor(color);
            int x = BORDER_SIZE + SQUARE_SIZE * position.file() + MARK_BORDER_SIZE / 2;
            int x1 = x + SQUARE_SIZE - MARK_BORDER_SIZE;
            int y = BORDER_SIZE + SQUARE_SIZE * (7 - position.rank()) + MARK_BORDER_SIZE / 2;
            int y1 = y + SQUARE_SIZE - MARK_BORDER_SIZE;
//            graphics2.drawRect(x, y,SQUARE_SIZE - MARK_BORDER_SIZE, SQUARE_SIZE - MARK_BORDER_SIZE);
            graphics2.drawLine(x, y1, x1, y1);
//            graphics2.setStroke(oldStroke);
        }
    }

    public void paintBoard(Graphics graphics) {
        // Paint border
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), SIZE / 20, SIZE / 20);

        // Paint file letters
        graphics.setColor(Color.WHITE);
        graphics.setFont(BORDER_FONT);
        int xPos1 = BORDER_SIZE + SQUARE_SIZE / 2 - 4;
        int yPos1 = BORDER_SIZE / 2 + 4;
        int yPos2 = getHeight() - yPos1 + 8;
        for (int file = 0; file < 8; ++file) {
            var fileString = "ABCDEFGH".substring(file, file + 1);
            graphics.drawString(fileString, xPos1, yPos1);
            graphics.drawString(fileString, xPos1, yPos2);
            xPos1 += SQUARE_SIZE;
        }
        // Paint rank numbers
        xPos1 = BORDER_SIZE / 2 - 4;
        int xPos2 = getWidth() - xPos1 - 8;
        yPos1 = BORDER_SIZE + SQUARE_SIZE / 2;
        for (int rank = 0; rank < 8; ++rank) {
            var rankString = "" + (8 - rank);
            graphics.drawString(rankString, xPos1, yPos1);
            graphics.drawString(rankString, xPos2, yPos1);
            yPos1 += SQUARE_SIZE;
        }

        // Paint squares
        boolean isBlack = false;
        for (int file = 0; file < 8; ++file) {
            for (int rank = 0; rank < 8; ++rank) {
                if (isBlack)
                    graphics.setColor(SAXION_GREEN);
                else
                    graphics.setColor(Color.WHITE);
                graphics.fillRect(BORDER_SIZE + file * SQUARE_SIZE, BORDER_SIZE + rank * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                isBlack = !isBlack;
            }
            isBlack = !isBlack;
        }
    }

    private String pieceToString(Piece piece) {
        if (piece.isBlack) {
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

    private void paintPieces(Graphics graphics, ArrayList<Piece> pieces) {
        graphics.setColor(Color.BLACK);
        for (Piece piece : pieces) {
            String pieceString = pieceToString(piece);
            if (pieceString != null) {
                var position = piece.getPosition();
                int x = BORDER_SIZE + position.file() * SQUARE_SIZE + 8;
                int y = BORDER_SIZE + (8 - position.rank()) * SQUARE_SIZE - 20;
                graphics.setFont(PIECE_FONT);
                graphics.drawString(pieceString, x, y);
            }
        }
    }

    private Position pointToPosition(Point point) {
        int file = (point.x - BORDER_SIZE) / SQUARE_SIZE;
        if (file >= 0 && file <= 7) {
            int rank = (point.y - BORDER_SIZE) / SQUARE_SIZE;
            if (rank >= 0 && rank <= 7) {
                return new Position(file, 7 - rank);
            }
        }
        return null;
    }
}
