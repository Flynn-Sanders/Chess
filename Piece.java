
import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {
    private int team; // 0:white 1:black

    public Piece(int t){
        team=t;

    }

    public enum PieceType{
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
    }

    public abstract ArrayList<int[]> findLegalMoves(int row, int col, BoardGame board, boolean simulate);
    public abstract void drawMe(Graphics g, int r, int c);
    public abstract PieceType getType();

    public int getColor(){
        return team;
    }

    protected boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }
    public abstract Piece copy();
}
