package nl.lunarum.javachess.engine;


public record Position(int file, int rank, String display) {
    public static Position A1 = new Position(0, 0);
    public static Position A2 = new Position(0, 1);
    public static Position A7 = new Position(0, 6);
    public static Position A8 = new Position(0, 7);
    public static Position H1 = new Position(7, 0);
    public static Position H8 = new Position(7, 7);

    public Position(int file, int rank) {
        this(file, rank, "ABCDEFGH".substring(Math.max(Math.min(file, 7), 0), 1) + (rank + 1));
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
