package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class King extends Piece {
    public King(Color color) {
        super(color);
    }

    @Override
    public Type type() {
        return Type.KING;
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition) {
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.up(1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(1, 1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.right(1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(1, -1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.up(-1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(-1, -1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.right(-1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(-1, 1));
        addPossibleCastling(plies, chessBoard, fromPosition, fromPosition);
    }
    
    private void addPossibleCastling(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition, Position toPosition) {
        //TODO: check if empty squares are attacked by the other player
        if (color == Color.BLACK) {
            if (toPosition.compareTo(Position.E8) == 0) {
                var player = chessBoard.getBlackPlayer();
                if (player.isCanCastleKingSide() && chessBoard.onSquare(Position.F8) == null && chessBoard.onSquare(Position.G8) == null) {
                    var ply = addPossiblePly(plies, chessBoard, fromPosition, Position.G8);
                    if (ply != null) ply.setAnnotation(Ply.Annotation.KingSideCastling);
                }
                if (player.isCanCastleQueenSide() && chessBoard.onSquare(Position.D8) == null && chessBoard.onSquare(Position.C8) == null && chessBoard.onSquare(Position.B8) == null) {
                    var ply = addPossiblePly(plies, chessBoard, fromPosition, Position.C8);
                    if (ply != null) ply.setAnnotation(Ply.Annotation.QueenSideCastling);
                }
            }
        } else {
            if (toPosition.compareTo(Position.E1) == 0) {
                var player = chessBoard.getWhitePlayer();
                if (player.isCanCastleKingSide() && chessBoard.onSquare(Position.F1) == null && chessBoard.onSquare(Position.G1) == null) {
                    var ply = addPossiblePly(plies, chessBoard, fromPosition, Position.G1);
                    if (ply != null) ply.setAnnotation(Ply.Annotation.KingSideCastling);
                }
                if (player.isCanCastleQueenSide() && chessBoard.onSquare(Position.D1) == null && chessBoard.onSquare(Position.C1) == null && chessBoard.onSquare(Position.B1) == null)  {
                    var ply = addPossiblePly(plies, chessBoard, fromPosition, Position.C1);
                    if (ply != null) ply.setAnnotation(Ply.Annotation.QueenSideCastling);
                }
            }
        }
    }

    @Override
    public int evaluate(ChessBoard chessBoard, Position position) {
        return color == Color.BLACK ? MAX_BLACK_VALUE : MAX_WHITE_VALUE;
    }
}
