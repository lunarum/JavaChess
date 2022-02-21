package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Pawn extends Piece {
    // Values from https://github.com/zserge/carnatus
    private static final int BASE_VALUE = 100;
    private static final int[] PST = {
              0,   0,   0,   0,   0,   0,   0,   0,
            178, 183, 186, 173, 202, 182, 185, 190,
            107, 129, 121, 144, 140, 131, 144, 107,
             83, 116,  98, 115, 114,   0, 115,  87,
             74, 103, 110, 109, 106, 101,   0,  77,
             78, 109, 105,  89,  90,  98, 103,  81,
             69, 108,  93,  63,  64,  86, 103,  69,
              0,   0,   0,   0,   0,   0,   0,   0
    };

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public Type type() {
        return Type.PAWN;
    }

    private void addPromotedPlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition, Position toPosition) {
        var player = chessBoard.getCurrentPlayer();
        plies.add(new Ply(this, fromPosition, player.queen, toPosition));
        plies.add(new Ply(this, fromPosition, player.rook, toPosition));
        plies.add(new Ply(this, fromPosition, player.bishop, toPosition));
        plies.add(new Ply(this, fromPosition, player.knight, toPosition));
    }

    protected boolean addPossibleEmptyPositionPly(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition, Position toPosition) {
        if (toPosition == null) // No position?
            return false;

        int promotionRank = color == Color.BLACK ? Position.A1.rank : Position.A8.rank;
        var piece = chessBoard.onSquare(toPosition);
        if (piece == null) { // Empty square?
            if (toPosition.rank == promotionRank)
                addPromotedPlies(plies, chessBoard, fromPosition, toPosition);
            else
                plies.add(new Ply(this, fromPosition, toPosition));
            return true;
        }
        return false;
    }

    protected void addPossibleCapturePositionPly(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition, Position toPosition) {
        if (toPosition == null) // No position?
            return;

        int promotionRank = color == Color.BLACK ? Position.A1.rank : Position.A8.rank;
        var piece = chessBoard.onSquare(toPosition);
        if (piece == null || piece.color == color) // Position empty or occupied with a piece of the same color?
            return;

        if (toPosition.rank == promotionRank)
            addPromotedPlies(plies, chessBoard, fromPosition, toPosition);
        else
            plies.add(new Ply(this, fromPosition, piece, toPosition));
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition) {
        int direction = color == Color.BLACK ? -1 : 1;
        int initialRank = color == Color.BLACK ? Position.A7.rank : Position.A2.rank;

        if (addPossibleEmptyPositionPly(plies, chessBoard, fromPosition, fromPosition.up(direction))) { // Is next position empty?
            if (fromPosition.rank == initialRank) // Initial rank and 2 square move possible?
                addPossibleEmptyPositionPly(plies, chessBoard, fromPosition, fromPosition.up(2 * direction));
        }
        addPossibleCapturePositionPly(plies, chessBoard, fromPosition, fromPosition.upRight(direction, -1));
        addPossibleCapturePositionPly(plies, chessBoard, fromPosition, fromPosition.upRight(direction, 1));
        //TODO add en-passant captures
    }

    @Override
    public int evaluate(ChessBoard chessBoard, Position position) {
        return color == Color.BLACK ? -PST[position.mirroredPosition] : PST[position.position];
    }
}
