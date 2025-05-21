
import java.util.ArrayList;

public class BoardGame {
    private Piece[][] board;
    private int[] enPassantSquare=null; 

    public BoardGame(){
        board = new Piece[8][8];
        for(int i=0; i<board[1].length; i++){
            board[1][i] = new Pawn(1);
        }
        for(int i=0; i<board[6].length; i++){
            board[6][i] = new Pawn(0);
        }
        board[0][4] = new King(1);
        board[7][4] = new King(0);
        board[0][0] = new Rook(1);
        board[0][7] = new Rook(1);
        board[7][0] = new Rook(0);
        board[7][7] = new Rook(0);
        board[0][2] = new Bishop(1);
        board[0][5] = new Bishop(1);
        board[7][2] = new Bishop(0);
        board[7][5] = new Bishop(0);
        board[0][3] = new Queen(1);
        board[7][3] = new Queen(0);
        board[0][1] = new Knight(1);
        board[0][6] = new Knight(1);
        board[7][1] = new Knight(0);
        board[7][6] = new Knight(0);
        

    }

    public Piece[][] getBoardState(){
        return board;
    }
    public int[] getEnPassantSquare() {
        return enPassantSquare;
    }

    public int movePiece(int fromRow, int fromCol, int toRow, int toCol){
        boolean isKingCaptured=false;
        Piece movingPiece = board[fromRow][fromCol];
        Piece capturedPiece = board[toRow][toCol];
        board[toRow][toCol] = movingPiece;
        board[fromRow][fromCol] = null;

        if (movingPiece instanceof Pawn pawn) {
            pawn.setHasMoved();
            if (Math.abs(toRow - fromRow) == 2) {
                int enPassantRow = (fromRow + toRow) / 2;
                enPassantSquare = new int[]{enPassantRow, toCol};
            } else {
                enPassantSquare = null;
            }
            if(fromCol!=toCol && capturedPiece==null){
                int dir = (movingPiece.getColor() == 0) ? -1 : 1;
                board[toRow - dir][toCol] = null;
            }
            if(movingPiece.getColor()*7==toRow){
                board[toRow][toCol] = new Queen(movingPiece.getColor());
            }
        }

        if(movingPiece instanceof King k){
            if(Math.abs(toCol-fromCol)==2){
                int rookFrom = fromCol+3;
                int rookTo = fromCol+1;
                if((toCol-fromCol) ==-4){
                    rookFrom = fromCol-4;
                    rookTo = fromCol-1;
                }
                movePiece(fromRow, rookFrom, toRow, rookTo);

            }
            k.setHasMoved();
        }
        if(movingPiece instanceof  Rook r){
            r.setHasMoved();
        }
        if(capturedPiece!=null && capturedPiece instanceof King){
            isKingCaptured=true;
        }


        return (isKingCaptured) ? 1 : 0;

    }

    public boolean isKingInCheck(int team){
        int kingRow=-1; int kingCol=-1;
        for(int r=0; r<board.length; r++){
            for(int c=0; c<board[r].length; c++){
                if(board[r][c] instanceof King k && k.getColor()==team){
                    kingRow=r; kingCol = c; break;
                }
            }
        }
        
        for(int r = 0; r<board.length; r++){
            for(int c=0; c<board[r].length; c++){
                Piece p = board[r][c];
                if(p!=null && p.getColor()!= team){
                    ArrayList<int[]> moves = p.findLegalMoves(r,c,this,false);
                    for(int[] move : moves){
                        if(move[0]==kingRow && move[1]==kingCol){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public BoardGame simulateMove(int fromRow, int fromCol, int toRow, int toCol) {
        BoardGame clone = this.deepCopy(); 
        clone.board[toRow][toCol] = clone.board[fromRow][fromCol];
        clone.board[fromRow][fromCol] = null;
    
        // clone.enPassantSquare handling in future?
        clone.enPassantSquare = null;
    
        return clone;
    }

    public BoardGame deepCopy() {
        BoardGame copy = new BoardGame();
        Piece[][] newBoard = new Piece[8][8];
    
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (this.board[r][c] != null) {
                    newBoard[r][c] = this.board[r][c].copy();
                }
            }
        }
    
        copy.board = newBoard;
    
        if (this.enPassantSquare != null) {
            copy.enPassantSquare = new int[]{this.enPassantSquare[0], this.enPassantSquare[1]};
        }
    
        return copy;
    }
}
