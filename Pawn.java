

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Pawn extends Piece{

    private boolean hasMoved;
    private boolean twoSqauresMovedLastTurn = false;

    private static BufferedImage whiteImg;
    private static BufferedImage blackImg;

    static {
        try {
            whiteImg = ImageIO.read(new File("images/pawn-white.png"));
            blackImg = ImageIO.read(new File("images/pawn-black.png"));
        } catch (IOException e) {
            System.out.println("Failed to load pawn images: " + e.getMessage());
        }
    }
    public Pawn(int t){
        super(t);
        hasMoved=false;
    }

    @Override
    public ArrayList<int[]> findLegalMoves(int row, int col, Piece[][] grid) {
        ArrayList<int[]> moves = new ArrayList<>();
        int nextRow = row -1 ;
    
        
        if (inBounds(nextRow, col) && grid[nextRow][col] == null) {
            moves.add(new int[]{nextRow, col});
    
            int twoForward = row - 2 ;
            if (!hasMoved && inBounds(twoForward, col) && grid[twoForward][col] == null) {
                moves.add(new int[]{twoForward, col});
            }
        }
    
        for (int dc = -1; dc<=1; dc+=2) {
            int diagCol = col + dc;
            if (super.inBounds(nextRow, diagCol)) {
                Piece target = grid[nextRow][diagCol];
                if (target != null && target.getColor() != getColor()) {
                    moves.add(new int[]{nextRow, diagCol});
                }
            }
        }

        for(int dc = -1; dc<=1; dc+=2){
            if(super.inBounds(row,col+dc)){
                Piece p = grid[row][col+dc];
                if(p instanceof Pawn pawn && pawn.isEnPassantable()){
                    moves.add(new int[] {row-1, col+dc});
                }
            }
        }
    
        return moves;
    }
    


    public void move(int fromRow, int toRow){
        int dist = Math.abs(toRow - fromRow);
        twoSqauresMovedLastTurn = (dist == 2 && !hasMoved);
        hasMoved = true;
    }

    public void clearEnPassant(){
        twoSqauresMovedLastTurn = false;
    }

    @Override
    public void drawMe(Graphics g, int r, int c) {
        
        int x = c*95; int y = r*95;

        BufferedImage img = (super.getColor() == 0) ? whiteImg : blackImg;
        g.drawImage(img, x, y,95,95, null);
    }

    public boolean isEnPassantable() {
        return twoSqauresMovedLastTurn;
    }
    @Override
    public PieceType getType(){
        return PieceType.PAWN;
    }

}
