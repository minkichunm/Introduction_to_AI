package chineseChecker;

public class WinCalculator {
	
	private int winScore = 15;
	
	public void setWinScore(int winScore) {
		this.winScore = winScore;
	}

	private int winCheck(Board board) {
		if ( board.getEntry((byte) 4, (byte) 13) == (byte) 1 &&
		     board.getEntry((byte) 5, (byte) 13) == (byte) 1 &&
		     board.getEntry((byte) 6, (byte) 13) == (byte) 1 &&
		     board.getEntry((byte) 7, (byte) 13) == (byte) 1 &&
		     board.getEntry((byte) 4, (byte) 14) == (byte) 1 &&
		     board.getEntry((byte) 5, (byte) 14) == (byte) 1 &&
		     board.getEntry((byte) 6, (byte) 14) == (byte) 1 &&
		     board.getEntry((byte) 4, (byte) 15) == (byte) 1 &&
		     board.getEntry((byte) 5, (byte) 15) == (byte) 1 &&
		     board.getEntry((byte) 4, (byte) 16) == (byte) 1 ) {
			return 1;
		} else if ( board.getEntry((byte) 12, (byte) 0) == (byte) 2 &&
			        board.getEntry((byte) 12, (byte) 1) == (byte) 2 &&
			        board.getEntry((byte) 12, (byte) 2) == (byte) 2 &&
			        board.getEntry((byte) 12, (byte) 3) == (byte) 2 &&
			        board.getEntry((byte) 11, (byte) 1) == (byte) 2 &&
			        board.getEntry((byte) 11, (byte) 2) == (byte) 2 &&
			        board.getEntry((byte) 11, (byte) 3) == (byte) 2 &&
			        board.getEntry((byte) 10, (byte) 2) == (byte) 2 &&
			        board.getEntry((byte) 10, (byte) 3) == (byte) 2 &&
			        board.getEntry((byte) 9, (byte) 3) == (byte) 2 ) {
			return 2;
		} else {
			return 0;
		}
	}

	public int getWinScore() {
		return winScore;
	}

	public void checkWinScore(Board board) {
		this.winScore = winCheck(board);
	}
}
