

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Rook extends Piece{

    private boolean hasMoved;

    private static BufferedImage whiteImg;
    private static BufferedImage blackImg;

    static {
        try {
            whiteImg = ImageIO.read(new File("images/rook-white.png"));
            blackImg = ImageIO.read(new File("images/rook-black.png"));
        } catch (IOException e) {
            System.out.println("Failed to load rook images: " + e.getMessage());
        }
    }

    public Rook(int t){
        super(t);
    }

    @Override
    public ArrayList<int[]> findLegalMoves(int row, int col,BoardGame board, boolean simulate) {
        ArrayList<int[]> psuedoMoves = new ArrayList<>();
        Piece[][] grid = board.getBoardState();
        hasMoved=false;
       
        int[][] directions = {
            {-1, 0}, // u
            {1, 0},  // d
            {0, -1}, // l
            {0, 1}   // r
        };
    
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
    
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Piece p = grid[r][c];
                if (p == null) {
                    psuedoMoves.add(new int[] {r, c});
                } else {
                    if (p.getColor() != super.getColor()) {
                        psuedoMoves.add(new int[] {r, c});
                    }
                    break;
                }
    
                r += dir[0];
                c += dir[1];
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
        return PieceType.ROOK;
    }
    public void setHasMoved(){
        hasMoved=true;
    }
    public boolean getHasMoved(){
        return hasMoved;
    }
    @Override
    public Piece copy() {
    Rook r = new Rook(this.getColor());
    r.hasMoved = this.hasMoved;
    return r;
}
}