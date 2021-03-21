package chineseChecker;

public class Move {
	
	private byte oldX;
	private byte oldY;
	private byte newX;
	private byte newY;
	
	public Move(byte oldX, byte oldY, byte newX, byte newY) {
		super();
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}
	
	public byte getOldX() {
		return oldX;
	}

	public byte getOldY() {
		return oldY;
	}

	public byte getNewX() {
		return newX;
	}

	public byte getNewY() {
		return newY;
	}	

}
