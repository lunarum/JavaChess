package nl.lunarum.javachess.engine;

public enum Position {
    A1, A2, A3, A4, A5, A6, A7, A8,
    B1, B2, B3, B4, B5, B6, B7, B8,
    C1, C2, C3, C4, C5, C6, C7, C8,
    D1, D2, D3, D4, D5, D6, D7, D8,
    E1, E2, E3, E4, E5, E6, E7, E8,
    F1, F2, F3, F4, F5, F6, F7, F8,
    G1, G2, G3, G4, G5, G6, G7, G8,
    H1, H2, H3, H4, H5, H6, H7, H8;

    static private final Position[] cachedValues = Position.values();

    @Override
    public String toString() {
        int rank = ordinal();
        int file = rank >> 3;
        rank &= 7;
        return "ABCDEFGH".substring(file, file+1) + (rank + 1);
    }

    /**
     * Return the file number
     * @return file as number 0..7
     */
    public int file() {
        return ordinal() >> 3; // No need to mask this, because file will never be anything else than 0..7
    }
    /**
     * Return the rank number
     * @return rank as number 0..7
     */
    public int rank() {
        return ordinal() & 7; // Mask of file bits
    }

    public Position next() {
        int index = ordinal() + 1;
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
        int ordinal = ((file & 7) << 3) | (rank & 7);
        if (ordinal < 0 || ordinal > cachedValues.length)
            return null;
        return cachedValues[ordinal];
    }

    public Position up(int steps) {
        int rank = ordinal();
        int file = rank & 56;
        rank &= 7;
        rank += steps;
        if (rank < 0 || rank > 7)
            return null;
        return cachedValues[file | rank];
    }

    public Position right(int steps) {
        int rank = ordinal();
        int file = rank >> 3;
        rank &= 7;
        file += steps;
        if (file < 0 || file > 7)
            return null;
        return cachedValues[file << 3 | rank];
    }

    public Position upRight(int stepsUp, int stepsRight) {
        int rank = ordinal();
        int file = rank >> 3;
        file += stepsRight;
        rank &= 7;
        rank += stepsUp;
        if (rank < 0 || rank > 7 || file < 0 || file > 7)
            return null;
        return cachedValues[(file << 3) | rank];
    }
}
