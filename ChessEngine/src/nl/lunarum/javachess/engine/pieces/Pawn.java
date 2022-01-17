package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(ChessBoard chessBoard, boolean isBlack) {
        super(chessBoard, isBlack);
    }

    @Override
    public Type type() {
        return Type.PAWN;
    }

    private void addPromotedPlies(ArrayList<Ply> plies, Position fromPosition, Position toPosition) {
        plies.add(new Ply(this, fromPosition, toPosition, Type.QUEEN));
        plies.add(new Ply(this, fromPosition, toPosition, Type.ROOK));
        plies.add(new Ply(this, fromPosition, toPosition, Type.BISHOP));
        plies.add(new Ply(this, fromPosition, toPosition, Type.KNIGHT));
    }

    protected boolean addPossibleEmptyPositionPly(ArrayList<Ply> plies, Position fromPosition, Position toPosition) {
        if (toPosition == null) // No position?
            return false;

        int promotionRank = isBlack ? Position.A1.rank() : Position.A8.rank();
        var piece = chessBoard.onSquare(toPosition);
        if (piece == null) { // Empty square?
            if (toPosition.rank() == promotionRank)
                addPromotedPlies(plies, fromPosition, toPosition);
            else
                plies.add(new Ply(this, fromPosition, toPosition));
            return true;
        }
        return false;
    }

    protected void addPossibleCapturePositionPly(ArrayList<Ply> plies, Position fromPosition, Position toPosition) {
        if (toPosition == null) // No position?
            return;

        int promotionRank = isBlack ? Position.A1.rank() : Position.A8.rank();
        var piece = chessBoard.onSquare(toPosition);
        if (piece == null || piece.isBlack == isBlack) // Position empty or occupied with a piece of the same color?
            return;

        if (toPosition.rank() == promotionRank)
            addPromotedPlies(plies, fromPosition, toPosition);
        else
            plies.add(new Ply(this, fromPosition, toPosition, piece));
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, Position fromPosition) {
        int direction = isBlack ? -1 : 1;
        int initialRank = isBlack ? Position.A7.rank() : Position.A2.rank();

        if (addPossibleEmptyPositionPly(plies, fromPosition, fromPosition.up(direction))) { // Is next position empty?
            if (fromPosition.rank() == initialRank) // Initial rank and 2 square move possible?
                addPossibleEmptyPositionPly(plies, fromPosition, fromPosition.up(2 * direction));
        }
        addPossibleCapturePositionPly(plies, fromPosition, fromPosition.upRight(direction, -1));
        addPossibleCapturePositionPly(plies, fromPosition, fromPosition.upRight(direction, 1));
        //TODO add en-passant captures
    }
}
