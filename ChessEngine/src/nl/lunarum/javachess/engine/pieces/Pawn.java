package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.Board;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Board board, boolean isBlack, Position position) {
        super(board, isBlack, position);
    }

    @Override
    public ArrayList<Ply> possiblePlies() {
        ArrayList<Ply> plies = new ArrayList<>();
        if (isBlack) {
            plies.add(new Ply(this, position.up(-1)));
            // Initial rank?
            if (position.rank() == Position.A7.rank())
                plies.add(new Ply(this, position.up(-2)));
        } else {
            plies.add(new Ply(this, position.up(1)));
            // Initial rank?
            if (position.rank() == Position.A2.rank())
                plies.add(new Ply(this, position.up(2)));
        }
        return null;
    }
}
