package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class King extends Piece {
    // Values from https://github.com/zserge/carnatus
    private static final int BASE_VALUE = 60000;
    private static final int[] PST = {
            60004, 60054, 60047, 59901, 59901, 60060, 60083, 59938,
            59968, 60010, 60055, 60056, 60056, 60055, 60010, 60003,
            59938, 60012, 59943, 60044, 59933, 60028, 60037, 59969,
            59945, 60050, 60011, 59996, 59981, 60013,     0, 59951,
            59945, 59957, 59948, 59972, 59949, 59953, 59992, 59950,
            59953, 59958, 59957, 59921, 59936, 59968, 59971, 59968,
            59996, 60003, 59986, 59950, 59943, 59982, 60013, 60004,
            60017, 60030, 59997, 59986, 60006, 59999, 60040, 60018
    };

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
        return color == Color.BLACK ? -PST[position.mirroredPosition] : PST[position.position];
    }
}
