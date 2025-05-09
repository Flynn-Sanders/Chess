import java.awt.*;

public class Chess {
    private Piece[][] board;
    private Color white, green;

    public Chess(){
        board = new Piece[8][8];
        

        white = new Color(235,236,208);
        green = new Color(115,149,82);

    }

    public void drawBoard(Graphics g){
        int x=0; int y=0;
        for(int r=0; r<board.length; r++){
            for(int c=0; c<board[r].length; c++){
                g.setColor(green);
                if((r%2==0 && c%2==0)  || (r%2==1 && c%2==1)){
                    g.setColor(white);
                }
                g.fillRect(x, y, 95, 95);
                x+=95;
            }
            x=0;
            y+=95;
        }
    }
}
