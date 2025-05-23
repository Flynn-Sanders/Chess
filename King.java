

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class King extends Piece{

    private static BufferedImage whiteImg;
    private static BufferedImage blackImg;

    private boolean hasMoved=false;

    static {
        try {
            whiteImg = ImageIO.read(new File("images/king-white.png"));
            blackImg = ImageIO.read(new File("images/king-black.png"));
        } catch (IOException e) {
            System.out.println("Failed to load king images: " + e.getMessage());
        }
    }
    public King(int t) {
        super(t);
    }
    
        @Override
    public ArrayList<int[]> findLegalMoves(int row, int col, BoardGame board, boolean simulate) {
        ArrayList<int[]> psuedoMoves = new ArrayList<>();
        Piece[][] grid = board.getBoardState();
        for(int r = -1; r<=1; r++){
            for(int c=-1; c<=1; c++){
                int newRow = row+r; int newCol = col+c;
                if(newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8){
                    Piece p = grid[newRow][newCol];
                    if(p==null || p.getColor()!=super.getColor()){
                        psuedoMoves.add(new int[] {newRow,newCol});
                    }
                }
            }
        }
        if(!simulate){
            return psuedoMoves;
        }
        if(!hasMoved){
            Piece rK = grid[row][col+3];
            Piece rQ = grid[row][col-4];
            if(rK instanceof Rook r){
                if(!r.getHasMoved()&&grid[row][col+1]==null&&grid[row][col+2]==null){
                    psuedoMoves.add(new int[] {row,col+2});
                }
            }
            if(rQ instanceof Rook r){
                if(!r.getHasMoved()&&grid[row][col-1]==null&&grid[row][col-2]==null&&grid[row][col-3]==null){
                    psuedoMoves.add(new int[] {row,col-2});
                }
            }
        }
        ArrayList<int[]> legalMoves = new ArrayList<>();

        for (int[] move : psuedoMoves) {
           
            if (Math.abs(move[1] - col) == 2) { 
                int step = (move[1] - col) / 2;
    
                BoardGame through1 = board.simulateMove(row, col, row, col + step);
                BoardGame through2 = board.simulateMove(row, col, row, move[1]);
    
                if (!through1.isKingInCheck(this.getColor()) && !through2.isKingInCheck(this.getColor())) {
                    legalMoves.add(move);
                }
            } 
            else{
                BoardGame simulation = board.simulateMove(row, col, move[0], move[1]);
                if (!simulation.isKingInCheck(this.getColor())) {
                    legalMoves.add(move);
                }
            }
        }

        return legalMoves;
    }

    @Override
    public void drawMe(Graphics g, int r, int c) {
        int x = c*95; int y = r*95;

        BufferedImage img = (super.getColor() == 0) ? whiteImg : blackImg;
        g.drawImage(img, x, y,95,95, null);
    }

    @Override
    public PieceType getType(){
        return PieceType.KING;
    }
    public void setHasMoved(){
        hasMoved=true;
    }
    @Override
    public Piece copy() {
        King k = new King(this.getColor());
        k.hasMoved = this.hasMoved;
        return k;
    }
}