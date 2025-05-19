

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class King extends Piece{

    private static BufferedImage whiteImg;
    private static BufferedImage blackImg;

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
    public ArrayList<int[]> findLegalMoves(int row, int col, Piece[][] grid) {
        ArrayList<int[]> moves = new ArrayList<>();
        for(int r = -1; r<=1; r++){
            for(int c=-1; c<=1; c++){
                int newRow = row+r; int newCol = col+c;
                if(newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8){
                    Piece p = grid[newRow][newCol];
                    if(p==null || p.getColor()!=super.getColor()){
                        moves.add(new int[] {newRow,newCol});
                    }
                }
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

    @Override
    public PieceType getType(){
        return PieceType.KING;
    }
    
}