package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class King extends Piece {
    public King(ChessBoard chessBoard, boolean isBlack, Position position) {
        super(chessBoard, isBlack, position);
    }

    @Override
    public Type type() {
        return Type.KING;
    }

    @Override
    public ArrayList<Ply> possiblePlies() {
        ArrayList<Ply> plies = new ArrayList<>();

        addPossiblePly(plies, position.up(1));
        addPossiblePly(plies, position.upRight(1, 1));
        addPossiblePly(plies, position.right(1));
        addPossiblePly(plies, position.upRight(1, -1));
        addPossiblePly(plies, position.up(-1));
        addPossiblePly(plies, position.upRight(-1, -1));
        addPossiblePly(plies, position.right(-1));
        addPossiblePly(plies, position.upRight(-1, 1));
        addPossibleCastling(plies);

        return plies;
    }
    
    private void addPossibleCastling(ArrayList<Ply> plies) {
        //TODO: check if empty squares are attacked by the other player
        if (isBlack) {
            if (position.compareTo(Position.E8) == 0) {
                var player = getPlayer();
                if (player.isCanCastleKingSide() && chessBoard.onSquare(Position.F8) == null && chessBoard.onSquare(Position.G8) == null)
                    addPossiblePly(plies, Position.G8);
                if (player.isCanCastleQueenSide() && chessBoard.onSquare(Position.D8) == null && chessBoard.onSquare(Position.C8) == null && chessBoard.onSquare(Position.B8) == null)
                    addPossiblePly(plies, Position.C8);
            }
        } else {
            if (position.compareTo(Position.E1) == 0) {
                var player = getPlayer();
                if (player.isCanCastleKingSide() && chessBoard.onSquare(Position.F1) == null && chessBoard.onSquare(Position.G1) == null)
                    addPossiblePly(plies, Position.G1);
                if (player.isCanCastleQueenSide() && chessBoard.onSquare(Position.D1) == null && chessBoard.onSquare(Position.C1) == null && chessBoard.onSquare(Position.B1) == null)
                    addPossiblePly(plies, Position.C1);
            }
        }
    }

    @Override
    public void playPly(Ply ply) {
        super.playPly(ply);
        if (ply.isKingCastling()) {
            var rook = chessBoard.onSquare(ply.to.right(1));
            assert rook != null && rook.type() == Type.ROOK : "Missing rook on king castling of " + this;
            rook.setPosition(ply.to.right(-1));
        }
        else if (ply.isQueenCastling()) {
            var rook = chessBoard.onSquare(ply.to.right(-2));
            assert rook != null && rook.type() == Type.ROOK : "Missing rook on queen castling of " + this;
            rook.setPosition(ply.to.right(1));
        }
    }

    @Override
    public void retractPly(Ply ply) {
        super.retractPly(ply);
        if (ply.isKingCastling()) {
            var rook = chessBoard.onSquare(ply.to.right(-1));
            assert rook != null && rook.type() == Type.ROOK : "Missing rook on retracting king castling of " + this;
            rook.setPosition(ply.to.right(1));
        }
        else if (ply.isQueenCastling()) {
            var rook = chessBoard.onSquare(ply.to.right(1));
            assert rook != null && rook.type() == Type.ROOK : "Missing rook on retracting queen castling of " + this;
            rook.setPosition(ply.to.right(-2));
        }
    }
}
