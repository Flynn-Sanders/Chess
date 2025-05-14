import java.awt.Graphics;
import java.util.ArrayList;

public class King extends Piece{

    public King(int t) {
        super(t);
    }
    
        @Override
    public ArrayList<int[]> findLegalMoves(int row, int col, Piece[][] grid) {
        ArrayList<int[]> moves = new ArrayList<>();
        for(int r = -1; r<=1; r++){
            for(int c=-1; c<=1; c++){
                int newRow = row+r; int newCol = col+c;
                if(newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8){
                    Piece p = grid[r][c];
                    if(p==null || p.getColor()!=super.getColor()){
                        moves.add(new int[] {r,c});
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public void drawMe(Graphics g, int r, int c) {
        
    }
    
}