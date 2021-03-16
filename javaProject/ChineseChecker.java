package chineseChecker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChineseChecker extends JPanel implements MouseListener{
	
	JFrame frame = new JFrame();
	JButton startGame = new JButton("START GAME");
	JButton randomMove = new JButton("RANDOM AI");
	private Board board;
	private ValidMovesCalculator vmc;
	private AI ai;
	private WinCalculator wc;
	Font c = new Font("Courier", Font.PLAIN, 50);  
	
   public ChineseChecker() {
      board = new Board();
      vmc = new ValidMovesCalculator();
      ai = new AI();
      wc = new WinCalculator();
      addMouseListener(this);
      initGame();
   }
	
	public void paint(Graphics g){
		switch (wc.getWinScore()) {
		case 0:
			// gameplay
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 800, 800);
			int initX = -100;
		    
		    for(byte i = 0; i < 17; i ++) {
		        for(byte j = 0; j < 17; j ++) {
		        	if (board.getEntry(j, i) != 9) {
		        		switch (board.getEntry(j, i)) {
		        		case 0:
		        			g.setColor(Color.DARK_GRAY);
		        			break;
		        		case 1:
		        			g.setColor(Color.MAGENTA);
		        			break;
		        		case 2:
		        			g.setColor(Color.BLUE);
		        			break;
		        		case 11:
		        			g.setColor(Color.YELLOW);
		        			break;
		        		case 12:
		        			g.setColor(Color.ORANGE);
		        			break;
		        		default:
		        			System.out.println("Color Not Defined Tile");
		        			break;
		        		}
		        		g.fillOval(initX + j * 40, 60 + i * 34, 36, 36);
		        	} 
		        }
		        initX += 20;
		    }
		    randomMove.setVisible(false);
		    randomMove.setVisible(true);
		    break;
		case 15:
			// main menu
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 800, 800);
			startGame.setVisible(true);
			break;
		default:
			// winning menu
			randomMove.setVisible(false);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 800, 800);
			g.setFont(c);
			g.setColor(Color.DARK_GRAY);
			g.drawString("PLAYER " + wc.getWinScore() + " WON", 200, 200);
			startGame.setVisible(true);
			break;
		}
	}
	
    public static void main(String[] args) {
    	ChineseChecker cc = new ChineseChecker();
    }
    
    
    private void initGame() {
        frame.setTitle("Cinese Checker");
        frame.setSize(800, 800);
        ActionListener actionListener = new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		String str = event.getActionCommand();
        		switch (str) {
        		case "StartGame":
        			startGame.setVisible(false);
        			randomMove.setVisible(true);
        			board.reset();
        			wc.setWinScore(0);
        			repaint();
        			break;
        		case "MakeRandomMove":
        			System.out.print("Random Move");
        			break;
        		}
        	}
        };
        startGame.setActionCommand("StartGame");
        startGame.addActionListener(actionListener);
        startGame.setBounds(200,300,400,150);
        startGame.setFont(c);
        startGame.setForeground(Color.LIGHT_GRAY);
        startGame.setBackground(Color.DARK_GRAY);
        startGame.setVisible(false);
        randomMove.setActionCommand("MakeRandomMove");
        randomMove.addActionListener(actionListener);
        randomMove.setBounds(0,0,40,50);
        randomMove.setFont(c);
        randomMove.setForeground(Color.LIGHT_GRAY);
        randomMove.setBackground(Color.DARK_GRAY);
        randomMove.setVisible(false);
        frame.add(randomMove);
        frame.add(startGame);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }  
    
    public void mousePressed(MouseEvent me) {
    	//System.out.println(me.getX() + "," + me.getY());
    	if (wc.getWinScore() == 0) {
			byte xCoord = this.getX(me.getX(), me.getY());
			byte yCoord = this.getY(me.getX(), me.getY());
			if (xCoord >= 0 && xCoord < 17 && yCoord >= 0 && yCoord < 17 && board.getEntry(xCoord, yCoord) != 9) {
				vmc.click(board, xCoord, yCoord);
				wc.checkWinScore(board);
				if (vmc.getTurnOfPlayer() == 1 && wc.getWinScore() == 0)
					ai.makeGreedyMoveDepth(board, vmc, 2);
				System.out.println(xCoord + ", " + yCoord);
			}
			wc.checkWinScore(board);
		    repaint(); 
    	}
     }
    
     public void mouseClicked(MouseEvent me) {}
     public void mouseEntered(MouseEvent me) {}
     public void mouseExited(MouseEvent me) {}
     public void mouseReleased(MouseEvent me) {}
     
     public byte getX(int x, int y) { 
    	 return  (byte) ((x + 100 - this.getY(x, y) * 20) / 40);
     }
     
     public byte getY(int x, int y) {
    	 
    	 return (byte) ((y- 60) / 34);
     }
     
     
}
