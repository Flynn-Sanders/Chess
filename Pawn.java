import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Piece{

    private boolean hasMoved;

    public Pawn(int t){
        super(t);
        hasMoved=false;
    }

    @Override
    public ArrayList<int[]> findLegalMoves(int row, int col, Piece[][] grid) {
        ArrayList<int[]> moves = new ArrayList<>();
        if(grid[row][col+1]==null){
            moves.add(new int[] {row,col+1});
        }
        if(!hasMoved && grid[row][col+2]==null){
            moves.add(new int[] {row,col+2});
        }
        return moves;
    }

    @Override
    public void drawMe(Graphics g) {
        //draw the pawn
    }

}
