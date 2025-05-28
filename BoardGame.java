
import java.util.ArrayList;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BoardGame {
    private Piece[][] board;
    private int[] enPassantSquare=null; 
    private Notation notation = new Notation();

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
    public void playMoveSound(boolean isCapture) {

        if(isCapture){
            try {
                URL url = this.getClass().getClassLoader().getResource("capture.wav");
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(url));
                clip.start();
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
        }
        else{
            try {
                URL url = this.getClass().getClassLoader().getResource("move.wav");
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(url));
                clip.start();
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
        }
    }

    public int movePiece(int fromRow, int fromCol, int toRow, int toCol){

        Piece movingPiece = board[fromRow][fromCol];
        Piece capturedPiece = board[toRow][toCol];
        String moveNotation = notation.getAlgebraicNotation(fromRow, fromCol, toRow, toCol, this);

        playMoveSound(capturedPiece!=null);

        if(movingPiece.getColor()==0){
            System.out.println();
        }
        int length = moveNotation.length();
        moveNotation+="         ";
        for(int i=0; i<length; i++){
            moveNotation=moveNotation.substring(0,moveNotation.length()-1);
        }
        System.out.print(moveNotation);
        
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
                int rookTo;
                int rookFrom;
                if (toCol < fromCol) {
                    rookFrom = 0;
                    rookTo = fromCol - 1;
                } else {
                    rookFrom = 7;
                    rookTo = fromCol + 1;
                }
                board[toRow][rookTo] = board[fromRow][rookFrom];
                board[fromRow][rookFrom]=null;


            }
            k.setHasMoved();
        }
        if(movingPiece instanceof  Rook r){
            r.setHasMoved();
        }
        
        

        ArrayList<int[]> moves = new ArrayList<>();
        for(int r=0; r<board.length; r++){
            for(int c=0; c<board[r].length; c++){
                Piece p = board[r][c];
                if(p!=null && p.getColor()!=movingPiece.getColor()){
                    for(int[] move : p.findLegalMoves(r, c, this, true)){
                        moves.add(new int[] {move[0],move[1]});
                    }
                }
            }
        }
        int otherTeam = (movingPiece.getColor()==0) ? 1 : 0;
        if(moves.isEmpty()){
            if(isKingInCheck(otherTeam)){
                return 1;
            }
            return 2;
        }
        return 0;

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
