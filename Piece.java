import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {
    private int team; // 0:white 1:black

    public Piece(int t){
        team=1;
    }

    public abstract ArrayList<int[]> findLegalMoves(int row, int col, Piece[][] grid);
    public abstract void drawMe(Graphics g);

    public void selectPiece(){

    }
}
