

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

	JButton randomMove1 = new JButton("RANDOM AI");
	JButton manualMove1 = new JButton("MANUAL");
	JButton aStarMove1 = new JButton("A * AI");
	JButton maxMaxMove1 = new JButton("MAXMAX A * AI");
	
	JButton randomMove2 = new JButton("RANDOM AI");
	JButton manualMove2 = new JButton("MANUAL");
	JButton aStarMove2 = new JButton("A * AI");
	JButton maxMaxMove2 = new JButton("MAXMAX A * AI");

	JButton[] buttonsPlayers = new JButton[] {randomMove1, manualMove1, aStarMove1, maxMaxMove1, randomMove2, manualMove2, aStarMove2, maxMaxMove2};


	private Board board;
	private ValidMovesCalculator vmc;
	private AI ai;
	private WinCalculator wc;
	Font c = new Font("Courier", Font.PLAIN, 50);
	Font c20 = new Font("Courier", Font.PLAIN, 10); 
	AIStatus[] players = new AIStatus[] {AIStatus.MANUAL, AIStatus.MANUAL}; 
	
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
			g.setFont(c20);
			g.setColor(Color.DARK_GRAY);
			g.drawString("PLAYER ", 5, 30);
			g.drawString("CURRENT STATUS:", 5, 60);
			g.drawString("PLAYER ", 688, 30);
			g.drawString("CURRENT STATUS:", 688, 60);
			g.setColor(Color.MAGENTA);
			g.drawString(players[0].toString(), 5, 80);
			g.fillOval(55, 7, 36, 36);
			g.setColor(Color.BLUE);
			g.drawString(players[1].toString(), 688, 80);
			g.fillOval(740, 7, 36, 36);
			setButtonsPlayer1Invisible(buttonsPlayers);
			setButtonsPlayer1Visible(buttonsPlayers);
		    break;
		case 15:
			// main menu
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 800, 800);
			startGame.setVisible(true);
			break;
		default:
			// winning menu
			setButtonsPlayer1Invisible(buttonsPlayers);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 800, 800);
			g.setFont(c);
			g.setColor(Color.DARK_GRAY);
			g.drawString("PLAYER    WON", 200, 200);
			switch (wc.getWinScore()) {
				case 1:
					g.setColor(Color.MAGENTA);
					break;
				case 2:
					g.setColor(Color.BLUE);
					break;
			}
			g.fillOval(410, 166, 36, 36);
			startGame.setVisible(true);
			break;
		}
	}
	
    public static void main(String[] args) {
    	ChineseChecker cc = new ChineseChecker();
    }

    private void initGame() {
        frame.setTitle("Chinese Checker");
        frame.setSize(800, 800);
        ActionListener actionListener = new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		String str = event.getActionCommand();
        		switch (str) {
        		case "StartGame":
        			startGame.setVisible(false);
        			setButtonsPlayer1Visible(buttonsPlayers);
        			board.reset();
        			wc.setWinScore(0);
        			repaint();
        			break;
        		case "MakerandomMove1":
        			players[0] = AIStatus.RANDOM_AI;
					repaint();
        			break;
        		
				case "MakeManualMove1":
        			players[0] = AIStatus.MANUAL;
					repaint();
        			break;
        		
				case "MakeAStarMove1":
        			players[0] = AIStatus.A_STAR;
					repaint();
        			break;
        		
				case "MakeMaxMaxMove1":
        			players[0] = AIStatus.MAX_MAX_A_STAR;
					repaint();
        			break;
					case "MakerandomMove2":
        			players[1] = AIStatus.RANDOM_AI;
					repaint();
        			break;
        		
				case "MakeManualMove2":
        			players[1] = AIStatus.MANUAL;
					repaint();
        			break;
        		
				case "MakeAStarMove2":
        			players[1] = AIStatus.A_STAR;
					repaint();
        			break;
        		
				case "MakeMaxMaxMove2":
        			players[1] = AIStatus.MAX_MAX_A_STAR;
					repaint();
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
		frame.add(startGame);

		buttonSetUp(randomMove1, actionListener);
        randomMove1.setActionCommand("MakerandomMove1");
        randomMove1.setBounds(0,150,100,50);

		buttonSetUp(aStarMove1, actionListener);
        aStarMove1.setActionCommand("MakeAStarMove1");
        aStarMove1.setBounds(0,200,100,50);

		buttonSetUp(manualMove1, actionListener);
        manualMove1.setActionCommand("MakeManualMove1");
        manualMove1.setBounds(0,100,100,50);

		buttonSetUp(maxMaxMove1, actionListener);
        maxMaxMove1.setActionCommand("MakeMaxMaxMove1");
        maxMaxMove1.setBounds(0,250,100,50);

		buttonSetUp(randomMove2, actionListener);
        randomMove2.setActionCommand("MakerandomMove2");
        randomMove2.setBounds(686,150,100,50);

		buttonSetUp(aStarMove2, actionListener);
        aStarMove2.setActionCommand("MakeAStarMove2");
        aStarMove2.setBounds(686,200,100,50);

		buttonSetUp(manualMove2, actionListener);
        manualMove2.setActionCommand("MakeManualMove2");
        manualMove2.setBounds(686,100,100,50);

		buttonSetUp(maxMaxMove2, actionListener);
        maxMaxMove2.setActionCommand("MakeMaxMaxMove2");
        maxMaxMove2.setBounds(686,250,100,50);

        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }  

	private void buttonSetUp(JButton button, ActionListener actionListener){
		button.addActionListener(actionListener);
		button.setFont(c20);
        button.setForeground(Color.LIGHT_GRAY);
        button.setBackground(Color.DARK_GRAY);
        button.setVisible(false);
		frame.add(button);
	}

	private void setButtonsPlayer1Visible(JButton[] buttons){
		for (JButton b : buttons){
			b.setVisible(true);
		}
	}

	private void setButtonsPlayer1Invisible(JButton[] buttons){
		for (JButton b : buttons){
			b.setVisible(false);
		}
	}
    
    public void mousePressed(MouseEvent me) {
//    	System.out.println(me.getX() + "," + me.getY());
    	if (wc.getWinScore() == 0) {
			byte xCoord = this.getX(me.getX(), me.getY());
			byte yCoord = this.getY(me.getX(), me.getY());
			if (xCoord >= 0 && xCoord < 17 && yCoord >= 0 && yCoord < 17 && board.getEntry(xCoord, yCoord) != 9) {
				for (int i = 0; i < vmc.getNOfPlayers(); i++ ){
					if (wc.getWinScore() == 0) {
						switch (players[vmc.getTurnOfPlayer() - 1]){
							case MANUAL:
								vmc.click(board, xCoord, yCoord);
								break;
							case A_STAR:
								ai.makeGreedyMoveDepth(board, vmc, 2);
								break;
							case RANDOM_AI:
								ai.makeRandomMove(board, vmc);
								break;
							case MAX_MAX_A_STAR:
								ai.makeMinMaxMoveDepth_v2(board, vmc, 100);
								break;
						}
						wc.checkWinScore(board);
					}
					System.out.println("mouse xcord: " + xCoord + ", ycord: " + yCoord);
				}
			}
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
