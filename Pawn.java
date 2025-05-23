

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Pawn extends Piece{

    private boolean hasMoved;

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
    public ArrayList<int[]> findLegalMoves(int row, int col, BoardGame board, boolean simulate) {
        ArrayList<int[]> psuedoMoves = new ArrayList<>();
        Piece[][] grid = board.getBoardState();
        int dir = (super.getColor()==0) ? -1 : 1;
        int nextRow = row + dir ;
        int twoForward = row + 2*dir ;
        
        if (inBounds(nextRow, col) && grid[nextRow][col] == null) {
            psuedoMoves.add(new int[]{nextRow, col});
    
            
            if (!hasMoved && inBounds(twoForward, col) && grid[twoForward][col] == null) {
                psuedoMoves.add(new int[]{twoForward, col});
            }
        }
    
        for (int dc = -1; dc<=1; dc+=2) {
            int diagCol = col + dc;
            if (super.inBounds(nextRow, diagCol)) {
                Piece target = grid[nextRow][diagCol];
                if (target != null && target.getColor() != getColor()) {
                    psuedoMoves.add(new int[]{nextRow, diagCol});
                }
            }
        }

        int[] enPassantSquare = board.getEnPassantSquare();
        if (enPassantSquare != null) {
            int epRow = enPassantSquare[0];
            int epCol = enPassantSquare[1];
    
            if (nextRow == epRow && Math.abs(col - epCol) == 1) {
                int behindRow = epRow - dir;
                Piece passedPawn = board.getBoardState()[behindRow][epCol];

                if (passedPawn instanceof Pawn && passedPawn.getColor() != this.getColor()) {
                    psuedoMoves.add(new int[]{epRow, epCol});
            }
            }   
        }
        if(!simulate){
            return psuedoMoves;
        }
        ArrayList<int[]> legalMoves = new ArrayList<>();

        for (int[] move : psuedoMoves) {
            BoardGame simulation = board.simulateMove(row, col, move[0], move[1]);
            if (!simulation.isKingInCheck(this.getColor())) {
                legalMoves.add(move);
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
        return PieceType.PAWN;
    }

    public void setHasMoved(){
        hasMoved=true;
    }
    @Override
    public Piece copy() {
        Pawn p = new Pawn(this.getColor());
        p.hasMoved = this.hasMoved;
        return p;
    }
}
