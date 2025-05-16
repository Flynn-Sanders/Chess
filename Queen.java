

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Queen extends Piece{


    private static BufferedImage whiteImg;
    private static BufferedImage blackImg;

    static {
        try {
            whiteImg = ImageIO.read(new File("images/queen-white.png"));
            blackImg = ImageIO.read(new File("images/queen-black.png"));
        } catch (IOException e) {
            System.out.println("Failed to load queen images: " + e.getMessage());
        }
    }

    public Queen(int t){
        super(t);
    }

    @Override
    public ArrayList<int[]> findLegalMoves(int row, int col, Piece[][] grid) {
        ArrayList<int[]> moves = new ArrayList<>();
    
       
        int[][] directions = {
            //ROOK
            {-1, 0}, // u
            {1, 0},  // d
            {0, -1}, // l
            {0, 1},   // r
            //BISHOP
            {-1, -1}, // ul
            {-1, 1},  // ur
            {1, -1}, // dl
            {1, 1}   // dr
        };
    
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
    
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Piece p = grid[r][c];
                if (p == null) {
                    moves.add(new int[] {r, c});
                } else {
                    if (p.getColor() != super.getColor()) {
                        moves.add(new int[] {r, c});
                    }
                    break;
                }
    
                r += dir[0];
                c += dir[1];
            }
        }
    
        return moves;
    }

    @Override
    public void drawMe(Graphics g, int r, int c) {
        int x = c*95; int y = r*95;

        BufferedImage img = (super.getColor() == 0) ? whiteImg : blackImg;
        g.drawImage(img, x, y,95,95, null);
    }
    
}