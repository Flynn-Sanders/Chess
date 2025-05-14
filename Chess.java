import java.awt.*;

public class Chess {
    private Piece[][] board;
    private Color white, green;

    public Chess(){
        board = new Piece[8][8];
        for(int i=0; i<board[1].length; i++){
            board[1][i] = new Pawn(1);
        }
        for(int i=0; i<board[6].length; i++){
            board[6][i] = new Pawn(0);
        }
        board[3][3] = new King(0);

        white = new Color(235,236,208);
        green = new Color(115,149,82);
        

    }

    public Piece[][] getBoardState(){
        return board;
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol){
        Piece piece = board[fromRow][fromCol];
        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;

        if (piece instanceof Pawn pawn) {
            pawn.setHasMoved();
            if(piece.getColor()*7==toRow){
                //board[toRow][toCol] = new Queen(piece.getColor());
                board[toRow][toCol] = new Pawn(1); // waiting for queen to be added
            }
        }
    }
}
