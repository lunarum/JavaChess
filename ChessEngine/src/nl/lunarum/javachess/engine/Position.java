package nl.lunarum.javachess.engine;


public record Position(int file, int rank, String display) {
    public static Position A1 = new Position(0, 0);
    public static Position A2 = new Position(0, 1);
    public static Position A7 = new Position(0, 6);
    public static Position A8 = new Position(0, 7);
    public static Position B1 = new Position(1, 0);
    public static Position B2 = new Position(1, 1);
    public static Position B7 = new Position(1, 6);
    public static Position B8 = new Position(1, 7);
    public static Position C1 = new Position(2, 0);
    public static Position C2 = new Position(2, 1);
    public static Position C7 = new Position(2, 6);
    public static Position C8 = new Position(2, 7);
    public static Position D1 = new Position(3, 0);
    public static Position D2 = new Position(3, 1);
    public static Position D7 = new Position(3, 6);
    public static Position D8 = new Position(3, 7);
    public static Position E1 = new Position(4, 0);
    public static Position E2 = new Position(4, 1);
    public static Position E7 = new Position(4, 6);
    public static Position E8 = new Position(4, 7);
    public static Position F1 = new Position(5, 0);
    public static Position F2 = new Position(5, 1);
    public static Position F7 = new Position(5, 6);
    public static Position F8 = new Position(5, 7);
    public static Position G1 = new Position(6, 0);
    public static Position G2 = new Position(6, 1);
    public static Position G7 = new Position(6, 6);
    public static Position G8 = new Position(6, 7);
    public static Position H1 = new Position(7, 0);
    public static Position H2 = new Position(7, 1);
    public static Position H7 = new Position(7, 6);
    public static Position H8 = new Position(7, 7);

    public Position(int file, int rank) {
        this(file, rank, "ABCDEFGH".substring(Math.max(Math.min(file, 7), 0), 1 + Math.max(Math.min(file, 7), 0)) + (rank + 1));
        assert file < 0 || file > 7 || rank < 0 || rank > 7 : "Invalid position";
    }

    public Position up(int steps) {
        return new Position(this.file, this.rank + steps);
    }

    public Position right(int steps) {
        return new Position(this.file + steps, this.rank);
    }

    public Position upRight(int stepsUp, int stepsRight) {
        return new Position(this.file + stepsRight, this.rank + stepsUp);
    }

    public int index() {
        return (file << 3) + rank;
    }

    @Override
    public String toString() {
        return display;
    }
}
