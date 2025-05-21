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
}
