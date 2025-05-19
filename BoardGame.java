public class BoardGame {
    private Piece[][] board;

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

    public void flipBoard(){
        Piece[][] newBoard = new Piece[8][8];
        for(int r = 0 ; r<board.length; r++){
            for(int c=0; c<board[r].length; c++){
                newBoard[7-r][7-c] = board[r][c];
            }
        }
        board=newBoard;
    }
    public void clearEnPassantFlags(int currentColor) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p instanceof Pawn && p.getColor() != currentColor) {
                    ((Pawn)p).clearEnPassant();
                }
            }
        }
    }

    public int movePiece(int fromRow, int fromCol, int toRow, int toCol){
        boolean isKingCaptured=false;
        Piece movingPiece = board[fromRow][fromCol];
        Piece capturedPiece = board[toRow][toCol];
        board[toRow][toCol] = movingPiece;
        board[fromRow][fromCol] = null;

        if (movingPiece instanceof Pawn pawn) {
            if(fromCol!=toCol && capturedPiece==null){
                board[toRow+1][toCol] = null;
            }
            pawn.move(fromRow, toRow);
            
            if(movingPiece.getColor()*7==toRow){
                board[toRow][toCol] = new Queen(movingPiece.getColor());
            }
        }
        if(capturedPiece!=null && capturedPiece instanceof King){
            isKingCaptured=true;
        }


        if(isKingCaptured){
            return 1;
        }
        return 0;

    }
}
