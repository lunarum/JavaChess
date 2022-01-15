package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.*;

import java.util.Arrays;

public class ChessBoard {
    private final Piece[] squares = new Piece[64];
    private final Player blackPlayer = new Player(this, true);
    private final Player whitePlayer = new Player(this, false);
    private Player player = whitePlayer;
    private int halfMoves = 0;
    private int move = 1;
    private Position enPassant = null;

    public int getHalfMoves() {
        return halfMoves;
    }

    public int getMove() {
        return move;
    }

    public void addPiece(Piece piece, Position position) {
        assert position != null : "Piece " + piece + " is not on the board";
        int index = position.index();
        assert squares[index] == null : "Position " + position + "is already occupied";
        squares[index] = piece;
    }

    public void movePiece(Piece piece, Position fromPosition, Position toPosition) {
        assert fromPosition != null : "Piece " + piece + " is not on the board";
        squares[fromPosition.index()] = null;
        assert toPosition != null : "Piece " + piece + " destination is unknown";
        squares[toPosition.index()] = piece;
    }

    public Piece getPiece(Position position) {
        return position == null ? null : squares[position.index()];
    }

    public void clear() {
        Arrays.fill(squares, null);
        player = whitePlayer;
        enPassant = null;
        halfMoves = 0;
        move = 1;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public Piece onSquare(Position position) {
        if (position == null)
            return null;
        return squares[position.index()];
    }

    public void playPly(Ply ply) {
        movePiece(ply.piece, ply.from, ply.to);
        if (ply.isKingCastling()) {
            var rookPosition = ply.to.right(1);
            var rook = onSquare(rookPosition);
            assert rook != null && rook.type() == Piece.Type.ROOK : "Missing rook on king castling of " + ply.piece;
            movePiece(rook, rookPosition, ply.to.right(-1));
        }
        else if (ply.isQueenCastling()) {
            var rookPosition = ply.to.right(-2);
            var rook = onSquare(rookPosition);
            assert rook != null && rook.type() == Piece.Type.ROOK : "Missing rook on queen castling of " + ply.piece;
            movePiece(rook, rookPosition, ply.to.right(1));
        }

        ply.setMove(move);
        if (ply.piece.isBlack) {
            player = whitePlayer;
        } else {
            player = blackPlayer;
            ++move;
        }

        ply.setPreviousHalfMoves(halfMoves);
        if (ply.piece.type() == Piece.Type.PAWN || ply.capturedPiece != null)
            halfMoves = 0;
        else
            ++halfMoves;
    }

    public void retractPly(Ply ply) {
        halfMoves = ply.getPreviousHalfMoves();
        if (ply.piece.isBlack) {
            player = blackPlayer;
        } else {
            player = whitePlayer;
            --move;
        }

        movePiece(ply.piece, ply.from, ply.to);
        if (ply.isKingCastling()) {
            var rookPosition = ply.to.right(-1);
            var rook = onSquare(rookPosition);
            assert rook != null && rook.type() == Piece.Type.ROOK : "Missing rook on king castling of " + ply.piece;
            movePiece(rook, rookPosition, ply.to.right(1));
        }
        else if (ply.isQueenCastling()) {
            var rookPosition = ply.to.right(1);
            var rook = onSquare(rookPosition);
            assert rook != null && rook.type() == Piece.Type.ROOK : "Missing rook on queen castling of " + ply.piece;
            movePiece(rook, rookPosition, ply.to.right(-2));
        }
    }

    public void setup() {
        setup("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    private void addPiece(char type, Position position) {
        switch (type) {
            case 'k' -> addPiece(blackPlayer.king, position);
            case 'q' -> addPiece(blackPlayer.queen, position);
            case 'r' -> addPiece(blackPlayer.rook, position);
            case 'b' -> addPiece(blackPlayer.bishop, position);
            case 'n' -> addPiece(blackPlayer.knight, position);
            case 'p' -> addPiece(blackPlayer.pawn, position);
            case 'K' -> addPiece(whitePlayer.king, position);
            case 'Q' -> addPiece(whitePlayer.queen, position);
            case 'R' -> addPiece(whitePlayer.rook, position);
            case 'B' -> addPiece(whitePlayer.bishop, position);
            case 'N' -> addPiece(whitePlayer.knight, position);
            case 'P' -> addPiece(whitePlayer.pawn, position);
        }
    }

    public void setup(String Fen) {
        clear();

        // Pieces setup
        var position = Position.A8;
        var firstPosition = Position.A8;
        int index = 0;
        while(index < Fen.length()) {
            char ch = Fen.charAt(index);
            if (Character.isDigit(ch)) {
                position = position.right(ch - '0');
            } else if (ch != '/') {
                addPiece(ch, position);
                position = position.right(1);
            }
            ++index;
            if (position == null) {
                position = firstPosition = firstPosition.up(-1);
                if (position == null)
                    break;
            }
        }

        // Skip space
        ++index;

        // Player to move next
        if (index + 2 < Fen.length()) {
            char ch = Fen.charAt(index++);
            if (ch == 'b') // Black to move next? (default is white)
                player = blackPlayer;
        }

        // Skip space
        ++index;

        if (index + 4 < Fen.length()) {
            whitePlayer.setCanCastleKingSide(Fen.charAt(index++) == 'K');
            whitePlayer.setCanCastleQueenSide(Fen.charAt(index++) == 'Q');
            blackPlayer.setCanCastleKingSide(Fen.charAt(index++) == 'k');
            blackPlayer.setCanCastleQueenSide(Fen.charAt(index++) == 'q');
        }

        // Skip space
        ++index;

        // en-passant
        if (index  < Fen.length()) {
            int file = Fen.charAt(index++) - 'a';
            if (file >= 0 && file <= 7) {
                if (index  < Fen.length()) {
                    int rank = Fen.charAt(index++) - '0' - 1;
                    if (rank == 2 || rank == 5) {
                        enPassant = new Position(file, rank);
                    }
                }
            }
        }

        // Skip space
        ++index;

        // halfMoves (=0)
        while(index < Fen.length()) {
            char ch = Fen.charAt(index++);
            if (!Character.isDigit(ch))
                break;
            halfMoves *= 10;
            halfMoves += ch - '0';
        }


        // move (=1)
        move = 0;
        while(index < Fen.length()) {
            char ch = Fen.charAt(index++);
            if (!Character.isDigit(ch))
                break;
            move *= 10;
            move += ch - '0';
        }
    }
}
