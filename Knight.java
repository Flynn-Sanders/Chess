
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
public class Knight extends Piece{
    private static BufferedImage whiteImg;
    private static BufferedImage blackImg;

    static {
        try {
            whiteImg = ImageIO.read(new File("images/knight-white.png"));
            blackImg = ImageIO.read(new File("images/knight-black.png"));
        } catch (IOException e) {
            System.out.println("Failed to load knight images: " + e.getMessage());
        }
    }

    public Knight(int t){
        super(t);
    }

    @Override
    public ArrayList<int[]> findLegalMoves(int row, int col, BoardGame board, boolean simulate) {
        ArrayList<int[]> psuedoMoves = new ArrayList<>();
        Piece[][] grid = board.getBoardState();
       
        int[][] directions = {
            {-2, -1}, // uul
            {-2, 1},  // uur
            {-1, -2}, // ull
            {-1, 2},   //urr
            {2, -1}, // ddl
            {2, 1},  // ddr
            {1, -2}, // dll
            {1, 2}   //drr

        };
    
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
    
            if (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Piece p = grid[r][c];
                if (p == null || p.getColor()!=super.getColor()) {
                    psuedoMoves.add(new int[] {r, c});
                } 
            }
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
        return PieceType.KNIGHT;
    }
    @Override
    public Piece copy() {
        Knight n = new Knight(this.getColor());
        return n;
    }
}
