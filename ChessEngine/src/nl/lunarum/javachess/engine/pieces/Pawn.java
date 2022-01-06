package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(ChessBoard chessBoard, boolean isBlack, Position position) {
        super(chessBoard, isBlack, position);
    }

    @Override
    public Type type() {
        return Type.PAWN;
    }

    private void addPromotedPlies(ArrayList<Ply> plies, Position position) {
        plies.add(new Ply(this, position, null, Type.QUEEN));
        plies.add(new Ply(this, position, null, Type.ROOK));
        plies.add(new Ply(this, position, null, Type.BISHOP));
        plies.add(new Ply(this, position, null, Type.KNIGHT));
    }

    protected boolean addPossibleEmptyPositionPly(ArrayList<Ply> plies, Position position) {
        if (position == null) // No position?
            return false;

        int promotionRank = isBlack ? Position.A1.rank() : Position.A8.rank();
        var piece = chessBoard.onSquare(position);
        if (piece == null) { // Empty square?
            if (position.rank() == promotionRank)
                addPromotedPlies(plies, position);
            else
                plies.add(new Ply(this, position));
            return true;
        }
        return false;
    }

    protected void addPossibleCapturePositionPly(ArrayList<Ply> plies, Position position) {
        if (position == null) // No position?
            return;

        int promotionRank = isBlack ? Position.A1.rank() : Position.A8.rank();
        var piece = chessBoard.onSquare(position);
        if (piece == null || piece.isBlack == isBlack) // Position empty or occupied with a piece of the same color?
            return;

        if (position.rank() == promotionRank)
            addPromotedPlies(plies, position);
        else
            plies.add(new Ply(this, position, piece));
    }

    @Override
    public ArrayList<Ply> possiblePlies() {
        ArrayList<Ply> plies = new ArrayList<>();

        int direction = isBlack ? -1 : 1;
        int initialRank = isBlack ? Position.A7.rank() : Position.A2.rank();

        if (addPossibleEmptyPositionPly(plies, position.up(direction))) { // Is next position empty?
            if (position.rank() == initialRank) // Initial rank and 2 square move possible?
                addPossibleEmptyPositionPly(plies, position.up(2 * direction));
        }
        addPossibleCapturePositionPly(plies, position.upRight(direction, -1));
        addPossibleCapturePositionPly(plies, position.upRight(direction, 1));
        //TODO add en-passant captures

        return plies;
    }
}
