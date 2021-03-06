package no.itverket;

public enum Face {
    J(11), Q(12), K(13), A(14, 1);

    public final int rank;
    public final int extraRank;

    Face(int rank) {
        this.rank = rank;
        extraRank = 0;
    }

    Face(int rank, int extraRank) {
        this.rank = rank;
        this.extraRank = extraRank;
    }

    public static Face getFace(int rank) {
        for (Face f : Face.values()) {
            if (f.rank == rank || f.extraRank == rank) {
                return f;
            }
        }
        return null;
    }
}
