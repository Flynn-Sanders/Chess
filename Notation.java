public class Notation {
    public String getNotation(Piece p){
        String s = "";
        switch(p.getType()){
            case PAWN -> s="";
            case KNIGHT -> s="N";
            case BISHOP -> s="B";
            case ROOK -> s="R";
            case QUEEN -> s="Q";
            case KING -> s="K";
        }
        return s;
    }
    public String getAlgebraicNotation(int fromRow, int fromCol, int toRow, int toCol, BoardGame b) {
        Piece[][] board = b.getBoardState();
        Piece movingPiece = board[fromRow][fromCol];
        Piece targetPiece = board[toRow][toCol];
        boolean isCapture = targetPiece != null || (movingPiece instanceof Pawn && fromCol != toCol);
    
        StringBuilder notation = new StringBuilder();
    
        // castle
        if (movingPiece instanceof King && Math.abs(toCol - fromCol) == 2) {
            return toCol > fromCol ? "O-O" : "O-O-O";
        }
    
        // piece letter
        String pieceLetter = "";
        if (!(movingPiece instanceof Pawn)) {
            pieceLetter = getNotation(movingPiece);
        }
    
        // disambiguation 
        boolean needsDisambiguation = false;
        boolean disambiguateFile = false;
        boolean disambiguateRank = false;
    
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (r == fromRow && c == fromCol) continue;
                Piece p = board[r][c];
                if (p != null && p.getClass() == movingPiece.getClass() && p.getColor() == movingPiece.getColor()) {
                    for (int[] move : p.findLegalMoves(r, c, b, true)) {
                        if (move[0] == toRow && move[1] == toCol) {
                            needsDisambiguation = true;
                            if (fromCol != c) disambiguateFile = true;
                            if (fromRow != r) disambiguateRank = true;
                        }
                    }
                }
            }
        }
    
        notation.append(pieceLetter);
    
        if (needsDisambiguation) {
            if (disambiguateFile && !disambiguateRank) {
                // file
                notation.append((char) ('a' + fromCol));
            } else if (!disambiguateFile && disambiguateRank) {
                // rank
                notation.append(8 - fromRow);
            } else if (disambiguateFile && disambiguateRank) {
                // both
                notation.append((char) ('a' + fromCol));
                notation.append(8 - fromRow);
            }
}
    
        // pawn capture
        if (movingPiece instanceof Pawn && isCapture) {
            notation.append((char) ('a' + fromCol));
        }
    
        if (isCapture) {
            notation.append('x');
        }
    
        // to square 
        notation.append((char) ('a' + toCol));
        notation.append(8 - toRow);
    
        // promotion to queen
        if (movingPiece instanceof Pawn && (toRow == 0 || toRow == 7)) {
            notation.append("=Q");
        }
    
        // check / mate
        BoardGame simulated = b.simulateMove(fromRow, fromCol, toRow, toCol);
        int opponentColor = 1 - movingPiece.getColor();
        if (simulated.isKingInCheck(opponentColor)) {
            boolean hasEscape = false;
            outer:
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    Piece p = simulated.getBoardState()[r][c];
                    if (p != null && p.getColor() == opponentColor) {
                        for (int[] move : p.findLegalMoves(r, c, simulated, true)) {
                            BoardGame next = simulated.simulateMove(r, c, move[0], move[1]);
                            if (!next.isKingInCheck(opponentColor)) {
                                hasEscape = true;
                                break outer;
                            }
                        }
                    }
                }
            }
            if (hasEscape) {
                notation.append('+');
            } else {
                notation.append('#');
            }
        }
    
        return notation.toString(); 
    }
}
