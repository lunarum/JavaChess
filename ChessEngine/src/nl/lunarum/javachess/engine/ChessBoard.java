package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard {
    private final Piece[] squares = new Piece[64];
    private final Player blackPlayer = new Player();
    private final Player whitePlayer = new Player();
    private Player player = whitePlayer;
    private int halfMoves = 0;
    private int move = 1;
    private Position enPassant = null;

    public ArrayList<Piece> getBlackPieces() {
        return blackPlayer.getPieces();
    }

    public ArrayList<Piece> getWhitePieces() {
        return whitePlayer.getPieces();
    }

    public int getHalfMoves() {
        return halfMoves;
    }

    public int getMove() {
        return move;
    }

    public void addPiece(Piece piece) {
        var position = piece.getPosition();
        assert position != null : "Piece " + piece + " is not on the board";
        int index = position.index();
        assert squares[index] == null : "Position " + position + "is already occupied";
        squares[index] = piece;
        if (piece.isBlack)
            blackPlayer.addPiece(piece);
        else
            whitePlayer.addPiece(piece);
    }

    public void setPiece(Piece piece) {
        var position = piece.getPosition();
        assert position != null : "Piece " + piece + " is not on the board";
        squares[position.index()] = piece;
    }

    public void clearSquare(Position position) {
        int index = position.index();
        assert squares[index] != null : "Position is already empty";
        squares[index] = null;
    }

    public void clear() {
        Arrays.fill(squares, null);
        blackPlayer.clearPieces();
        whitePlayer.clearPieces();
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
        ply.piece.playPly(ply);
        if (ply.piece.isBlack) {
            player = whitePlayer;
        } else {
            player = blackPlayer;
            ++move;
        }
        ply.setMove(move);
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

        ply.piece.retractPly(ply);
    }

    public void setup() {
        setup("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    private Piece newPiece(char type, Position position) {
        char type1 = Character.toLowerCase(type);
        return switch (type1) {
            case 'k' -> new King(this, type == type1, position);
            case 'q' -> new Queen(this, type == type1, position);
            case 'r' -> new Rook(this, type == type1, position);
            case 'b' -> new Bishop(this, type == type1, position);
            case 'n' -> new Knight(this, type == type1, position);
            case 'p' -> new Pawn(this, type == type1, position);
            default -> null;
        };
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
            } else if (ch != '/'){
                newPiece(ch, position);
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
