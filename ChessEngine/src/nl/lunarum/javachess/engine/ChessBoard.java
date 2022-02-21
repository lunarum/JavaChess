package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard {
    private final Piece[] squares = new Piece[64];
    private final Player blackPlayer = new Player(Piece.Color.BLACK);
    private final Player whitePlayer = new Player(Piece.Color.WHITE);
    private Player currentPlayer = whitePlayer;
    private int halfMoves = 0;
    private int move = 1;
    private Position enPassant = null;
    private final ArrayList<Ply> game = new ArrayList<>();
    private int score = 0;

    public int getHalfMoves() {
        return halfMoves;
    }

    public int getMove() {
        return move;
    }

    public void addPiece(Piece piece, Position position) {
        assert position != null : "Piece " + piece + " is not on the board";
        int index = position.position;
        assert squares[index] == null : "Position " + position + "is already occupied";
        squares[index] = piece;
    }

    public void movePiece(Piece fromPiece, Position fromPosition, Piece toPiece, Position toPosition, Piece captured, boolean isForward) {
        assert fromPosition != null : "Piece " + toPiece + " is not on the board";
        assert toPosition != null : "Piece " + toPiece + " destination is unknown";

        if (isForward) {
            squares[fromPosition.position] = null;
            squares[toPosition.position] = toPiece;
            score += toPiece.evaluate(this, toPosition) - fromPiece.evaluate(this, fromPosition);
            if (captured != null)
                score -= captured.evaluate(this, toPosition);
        } else {
            squares[fromPosition.position] = fromPiece;
            squares[toPosition.position] = captured;
            score += fromPiece.evaluate(this, fromPosition) - toPiece.evaluate(this, toPosition);
            if (captured != null)
                score += captured.evaluate(this, toPosition);
        }
    }

    private void moveCastlingRook(boolean isKingSide, boolean isForward, Ply ply) {
        if (isKingSide) {
            var rookPosition = isForward ? ply.to.right(1) : ply.to.right(-1);
            assert rookPosition != null : "King position wrong on king castling: " + ply.to;
            var rook = onSquare(rookPosition);
            assert rook != null && rook.type() == Piece.Type.ROOK : "Missing rook on king castling of " + ply.piece;
            var newRookPosition = isForward ? ply.to.right(-1) : ply.to.right(1);
            assert newRookPosition != null : "Rook position wrong on king castling: " + ply.to;
            movePiece(rook, rookPosition, rook, newRookPosition, null, true);
        }
        else if (ply.isQueenCastling()) {
            var rookPosition = isForward ? ply.to.right(-2) : ply.to.right(1);
            assert rookPosition != null : "King position wrong on queen castling: " + ply.to;
            var rook = onSquare(rookPosition);
            assert rook != null && rook.type() == Piece.Type.ROOK : "Missing rook on queen castling of " + ply.piece;
            var newRookPosition = isForward ? ply.to.right(1) : ply.to.right(-2);
            assert newRookPosition != null : "Rook position wrong on queen castling: " + ply.to;
            movePiece(rook, rookPosition, rook, newRookPosition, null, true);
        }
    }

    public Piece getPiece(Position position) {
        return position == null ? null : squares[position.position];
    }

    public void clear() {
        Arrays.fill(squares, null);
        currentPlayer = whitePlayer;
        enPassant = null;
        halfMoves = 0;
        move = 1;
        game.clear();
        score = 0;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Ply> getGame() {
        return game;
    }

    public Piece onSquare(Position position) {
        if (position == null)
            return null;
        return squares[position.position];
    }

    public ArrayList<Ply> getPossiblePlies() {
        ArrayList<Ply> plies = new ArrayList<>(40); // average of 35 plies per player move

        for(int index = 0; index < squares.length; ++index) {
            var piece = squares[index];
            if (piece != null && piece.color == currentPlayer.color) {
                piece.addPossiblePlies(plies, this, Position.fromOrdinal(index));
            }
        }

        return plies;
    }

    public int getScore() {
        return score;
    }

    public void playPly(Ply ply) {
        movePiece(ply.piece, ply.from, (ply.promotedPiece == null) ? ply.piece : ply.promotedPiece, ply.to, ply.capturedPiece, true);
        boolean isKingCastling = ply.isKingCastling();
        boolean isQueenCastling = !isKingCastling && ply.isQueenCastling();
        if (isKingCastling || isQueenCastling) moveCastlingRook(isKingCastling, true, ply);
        ply.setMove(move);

        if (ply.piece.color == Piece.Color.BLACK) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = blackPlayer;
            ++move;
        }

        ply.setPreviousHalfMoves(halfMoves);
        if (ply.piece.type() == Piece.Type.PAWN || ply.capturedPiece != null)
            halfMoves = 0;
        else
            ++halfMoves;

        game.add(ply);
    }

    public void retractPly(Ply ply) {
        game.remove(ply);

        halfMoves = ply.getPreviousHalfMoves();
        if (ply.piece.color == Piece.Color.BLACK) {
            currentPlayer = blackPlayer;
        } else {
            currentPlayer = whitePlayer;
            --move;
        }

        boolean isKingCastling = ply.isKingCastling();
        boolean isQueenCastling = !isKingCastling && ply.isQueenCastling();
        if (isKingCastling || isQueenCastling) moveCastlingRook(isKingCastling, false, ply);
        movePiece(ply.piece, ply.from, (ply.promotedPiece == null) ? ply.piece : ply.promotedPiece, ply.to, ply.capturedPiece, false);
    }

    public void setup() {
        setup("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    private void addPiece(char type, Position position) {
        Piece piece = switch(type) {
            case 'k': yield blackPlayer.king;
            case 'q': yield blackPlayer.queen;
            case 'r': yield blackPlayer.rook;
            case 'b': yield blackPlayer.bishop;
            case 'n': yield blackPlayer.knight;
            case 'p': yield blackPlayer.pawn;
            case 'K': yield whitePlayer.king;
            case 'Q': yield whitePlayer.queen;
            case 'R': yield whitePlayer.rook;
            case 'B': yield whitePlayer.bishop;
            case 'N': yield whitePlayer.knight;
            case 'P': yield whitePlayer.pawn;
            default: assert false : "Unknown piece type"; yield whitePlayer.pawn;
        };
        squares[position.position] = piece;
        score += piece.evaluate(this, position);
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
                currentPlayer = blackPlayer;
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
                        enPassant = Position.fromFileRank(file, rank);
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
