package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Queen extends Piece {
    // Values from https://github.com/zserge/carnatus
    private static final int BASE_VALUE = 929;
    private static final int[] PST = {
            935, 930, 921, 825,  998,  953, 1017, 955,
            943, 961, 989, 919,  949, 1005,  986, 953,
            927, 972, 961, 989, 1001,  992,  972, 931,
            930, 913, 951, 946,  954,  949,  916, 923,
            915, 914, 927, 924,  928,  919,  909, 907,
            899, 923, 916, 918,  913,  918,  913, 902,
            893, 911,   0, 910,  914,  914,  908, 891,
            890, 899, 898, 916,  898,  893,  895, 887,
    };

    public Queen(Color color) {
        super(color);
    }

    @Override
    public Type type() {
        return Type.QUEEN;
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition) {
        addPossibleStraightRayPlies(plies, chessBoard, fromPosition);
        addPossibleDiagonalRayPlies(plies, chessBoard, fromPosition);
    }

    @Override
    public int evaluate(ChessBoard chessBoard, Position position) {
        return color == Color.BLACK ? -PST[position.mirroredPosition] : PST[position.position];
    }
}
