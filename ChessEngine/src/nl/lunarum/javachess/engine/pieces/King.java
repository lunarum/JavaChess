package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class King extends Piece {
    public King(ChessBoard chessBoard, boolean isBlack) {
        super(chessBoard, isBlack);
    }

    @Override
    public Type type() {
        return Type.KING;
    }

    @Override
    public ArrayList<Ply> possiblePlies(Position position) {
        ArrayList<Ply> plies = new ArrayList<>();

        addPossiblePly(plies, position, position.up(1));
        addPossiblePly(plies, position, position.upRight(1, 1));
        addPossiblePly(plies, position, position.right(1));
        addPossiblePly(plies, position, position.upRight(1, -1));
        addPossiblePly(plies, position, position.up(-1));
        addPossiblePly(plies, position, position.upRight(-1, -1));
        addPossiblePly(plies, position, position.right(-1));
        addPossiblePly(plies, position, position.upRight(-1, 1));
        addPossibleCastling(plies, position, position);

        return plies;
    }
    
    private void addPossibleCastling(ArrayList<Ply> plies, Position fromPosition, Position toPosition) {
        //TODO: check if empty squares are attacked by the other player
        if (isBlack) {
            if (toPosition.compareTo(Position.E8) == 0) {
                var player = chessBoard.getBlackPlayer();
                if (player.isCanCastleKingSide() && chessBoard.onSquare(Position.F8) == null && chessBoard.onSquare(Position.G8) == null)
                    addPossiblePly(plies, fromPosition, Position.G8);
                if (player.isCanCastleQueenSide() && chessBoard.onSquare(Position.D8) == null && chessBoard.onSquare(Position.C8) == null && chessBoard.onSquare(Position.B8) == null)
                    addPossiblePly(plies, fromPosition, Position.C8);
            }
        } else {
            if (toPosition.compareTo(Position.E1) == 0) {
                var player = chessBoard.getWhitePlayer();
                if (player.isCanCastleKingSide() && chessBoard.onSquare(Position.F1) == null && chessBoard.onSquare(Position.G1) == null)
                    addPossiblePly(plies, fromPosition, Position.G1);
                if (player.isCanCastleQueenSide() && chessBoard.onSquare(Position.D1) == null && chessBoard.onSquare(Position.C1) == null && chessBoard.onSquare(Position.B1) == null)
                    addPossiblePly(plies, fromPosition, Position.C1);
            }
        }
    }
}
