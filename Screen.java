import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

public class Screen extends JPanel implements ActionListener, MouseListener{
    private BoardGame game;
    private boolean started = false, isSelecting = false;
    private JButton start,restart;
    private int moves = 0;


    private int selectedRow = -1, selectedCol=-1, turn =0;
    private ArrayList<int[]> legalMoves = new ArrayList<>();
    
    private Font titleFont = new Font("Bebas Neue", Font.BOLD, 120);  // Use Impact if preferred
    private Font descFont = new Font("Arial", Font.PLAIN, 40);  // Use Verdana if preferred
    

    public Screen(){
        addMouseListener(this);
        setLayout(null);
        game = new BoardGame();
        start= new JButton("Start");
        start.setBounds(280, 400, 200,60);
        this.add(start);
        start.addActionListener(this);
        restart= new JButton("Restart");
        restart.setBounds(280, 400, 200,60);
        this.add(restart);
        restart.addActionListener(this);
        restart.setVisible(false);
    }   

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(760, 760); 
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        start.setVisible(!started);
        if(!started){
            titleScreen(g);
        }
        else{
            drawBoard(g);
        }
    }

    private void drawBoard(Graphics g) {
        Color white = new Color(235,236,208);
        Color green = new Color(115,149,82);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                boolean light = (r + c) % 2 == 0;
                g.setColor(light ? white : green);
                g.fillRect(c*95, r*95, 95, 95);
            }
        }

        
        if (selectedRow != -1) {
            g.setColor(new Color(255,255,0,128)); 
            g.fillRect(selectedCol*95, selectedRow*95, 95, 95);
        }

        
        Piece[][] board = game.getBoardState();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p != null) p.drawMe(g, r, c);
            }
        }

        g.setColor(new Color(0,0,0,128));
        for (int[] m : legalMoves) {
            int rr = m[0], cc = m[1];
            int x = cc*95 + 95/2, y = rr*95 + 95/2;
            g.fillOval(x-10, y-10, 20, 20);
        }
    }

    public void titleScreen(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 800);
        g.setColor(Color.white);
        g.setFont(titleFont);
        FontMetrics metrics = g.getFontMetrics(titleFont);
        String text = "Chess";
        int textWidth = metrics.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;  
        int y = 150;
        g.drawString(text, x, y);
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start){
            started=true;
        }
        else if(e.getSource()==restart){
            started=false;
            moves=0;
            game=new BoardGame();
            turn=0;
            restart.setVisible(false);
        }
        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int col = e.getX() / 95;
        int row = e.getY() / 95;

       
        if (row < 0 || row > 7 || col < 0 || col > 7){
            return;
        }
        
        if (row == selectedRow && col == selectedCol) {
            selectedRow = selectedCol = -1;
            legalMoves.clear();
            isSelecting=false;
        } else {
            Piece p = game.getBoardState()[row][col];
            if (p != null&&p.getColor()==turn) {
                    selectedRow = row;
                    selectedCol = col;
                    legalMoves = p.findLegalMoves(row, col, game.getBoardState());
                    isSelecting = true;
            }
            else if (isSelecting){
                boolean isLegal = false;
                for(int[] i : legalMoves){
                    if(i[0]==row && i[1] == col){
                        isLegal=true;
                    }
                }

                if(isLegal){
                    moves++;
                    if(game.movePiece(selectedRow, selectedCol, row, col)==0)
                    {
                        turn = (turn == 0) ? 1 : 0;
                        game.flipBoard();
                    }
                    else{
                        String text = (turn==0) ? "White" : "Black";
                        System.out.println(text+" wins by capturing the king! turns played: "+moves/2);
                        turn=-1;
                        restart.setVisible(true);
                    }
                    selectedRow = selectedCol = -1;
                    legalMoves.clear();
                    isSelecting = false;
                }
            }
        }
        repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

   

    

}

    