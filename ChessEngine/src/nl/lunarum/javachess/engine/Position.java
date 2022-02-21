package nl.lunarum.javachess.engine;

public enum Position {
    A8, B8, C8, D8, E8, F8, G8, H8,
    A7, B7, C7, D7, E7, F7, G7, H7,
    A6, B6, C6, D6, E6, F6, G6, H6,
    A5, B5, C5, D5, E5, F5, G5, H5,
    A4, B4, C4, D4, E4, F4, G4, H4,
    A3, B3, C3, D3, E3, F3, G3, H3,
    A2, B2, C2, D2, E2, F2, G2, H2,
    A1, B1, C1, D1, E1, F1, G1, H1;

    static private final Position[] cachedValues = Position.values();
    
    public final int file;
    public final int rank;
    public final int position;
    public final int mirroredPosition;
    public final String showValue;

    Position() {
        position = ordinal();
        mirroredPosition = 63 - position;
        file = position & 7;
        rank = 7 - (position >> 3);
        showValue = "abcdefgh".substring(file, file + 1) + (rank + 1);
    }

    @Override
    public String toString() {
        return showValue;
    }

    public Position next() {
        int index = position + 1;
        if (index < cachedValues.length)
            return cachedValues[index];
        return null;
    }

    public static Position fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal > cachedValues.length)
            return null;
        return cachedValues[ordinal];
    }

    public static Position fromFileRank(int file, int rank) {
        int ordinal = ((7 - (rank & 7)) << 3) | (file & 7);
        if (ordinal > cachedValues.length)
            return null;
        return cachedValues[ordinal];
    }

    public Position up(int steps) {
        int rank2 = rank + steps;
        if (rank2 < 0 || rank2 > 7)
            return null;
        return cachedValues[position + steps * -8];
    }

    public Position right(int steps) {
        int file2 = file + steps;
        if (file2 < 0 || file2 > 7)
            return null;
        return cachedValues[position + steps];
    }

    public Position upRight(int stepsUp, int stepsRight) {
        int rank2 = rank + stepsUp;
        int file2 = file + stepsRight;
        if (rank2 < 0 || rank2 > 7 || file2 < 0 || file2 > 7)
            return null;
        return cachedValues[position + stepsUp * -8 + stepsRight];
    }
}
