package chineseChecker;

import java.util.ArrayList;

public class ValidMovesCalculator {
	private byte turnOfPlayer = 2;
	private boolean selected = false;
	private byte nOfPlayers = 2;
	
	public byte getTurnOfPlayer() {
		return turnOfPlayer;
	}

	public void increaseTurnOfPlayer() {
		turnOfPlayer++;
		if (this.nOfPlayers < turnOfPlayer) {
			turnOfPlayer = 1;
		}
	}
	
	public void click(Board board, byte x, byte y) {
		if (turnOfPlayer == board.getEntry(x, y)) {
			if (selected) {
				board.replace((byte) 12, turnOfPlayer);
				board.replace((byte) 11, (byte) 0);
				board.setEntry(x, y, (byte) 12);
				validMovesWalk(board, x, y);
				validMovesJump(board, x, y);
			} else {
				board.setEntry(x, y, (byte) 12);
				selected = true;
				validMovesWalk(board, x, y);
				validMovesJump(board, x, y);
			}
		}
		if ((byte) 11 == board.getEntry(x, y)) {
			board.setEntry(x, y, (byte) turnOfPlayer);
			board.replace((byte) 12, (byte) 0);
			board.replace((byte) 11, (byte) 0);
			selected = false;
			increaseTurnOfPlayer();
		}
	}
	

	public void validMovesWalk(Board board, byte x, byte y) {
		byte x1 = (byte) (x + 1);
		byte x_1 = (byte) (x - 1);
		byte y1 = (byte) (y + 1);
		byte y_1 = (byte) (y - 1);
		byte[] adjacentTiles = new byte[] {x1, y,
										   x_1, y,
										   x1, y_1,
										   x_1, y1,
										   x, y1,
										   x, y_1 }; 
		
		for (int i = 0; i < 12; i += 2) {
			try {
			    if (board.getEntry(adjacentTiles[i], adjacentTiles[i + 1]) == 0) {
			    	board.setEntry(adjacentTiles[i], adjacentTiles[i + 1], (byte) 11);
			    }
			} 
			catch (IndexOutOfBoundsException e) {}
		}
	}
	
	public void validMovesJump(Board board, byte x, byte y) {
		byte x1 = (byte) (x + 1);
		byte x_1 = (byte) (x - 1);
		byte y1 = (byte) (y + 1);
		byte y_1 = (byte) (y - 1);
		byte x2 = (byte) (x + 2);
		byte x_2 = (byte) (x - 2);
		byte y2 = (byte) (y + 2);
		byte y_2 = (byte) (y - 2);
		byte[] adjacentTiles = new byte[] {x1, y, x2, y,
										   x_1, y, x_2, y,
										   x1, y_1, x2, y_2,
										   x_1, y1, x_2, y2,
										   x, y1, x, y2,
										   x, y_1, x, y_2 };
		for (int i = 0; i < 24; i += 4) {
			try {
		        if (board.getEntry(adjacentTiles[i], adjacentTiles[i + 1]) != 9 && 
		        	board.getEntry(adjacentTiles[i], adjacentTiles[i + 1]) != 11 && 
		        	board.getEntry(adjacentTiles[i], adjacentTiles[i + 1]) != 0 && 
		        	board.getEntry(adjacentTiles[i + 2], adjacentTiles[i + 3]) == 0) 
		        {
		        	board.setEntry(adjacentTiles[i + 2], adjacentTiles[i + 3], (byte) 11);
		        	validMovesJump(board, adjacentTiles[i + 2], adjacentTiles[i + 3]);
		        	
		        }
		    } 
			catch (IndexOutOfBoundsException e) {}
		}
	}
}
	
