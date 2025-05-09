import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Screen extends JPanel implements ActionListener, MouseListener{
    private Chess game;
    private boolean started = false;
    private JButton start;
    
    private Font titleFont = new Font("Bebas Neue", Font.BOLD, 120);  // Use Impact if preferred
    private Font descFont = new Font("Arial", Font.PLAIN, 40);  // Use Verdana if preferred
    

    public Screen(){
        setLayout(null);
        game = new Chess();
        start= new JButton("Start");
        start.setBounds(280, 400, 200,60);
        this.add(start);
        start.addActionListener(this);
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
            game.drawBoard(g);
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
        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
       int row = e.getX()/95; int col = e.getY()/95;
       //grid[row][col]
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

    