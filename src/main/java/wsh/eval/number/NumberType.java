package wsh.eval.number;

public enum NumberType {
    LONG(1),
    DOUBLE(2),
    BIG_DECIMAL(3);

    /**
     * When doing binary operator on two numbers.
     * The result number type will be the one with higher rank between two.
     */
    private final int rank;

    NumberType(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

}
