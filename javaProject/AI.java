package chineseChecker;

import java.util.ArrayList;
import java.util.Random;

public class AI {
	
	Random rand = new Random();
	
	public void makeRandomMove(Board board, ValidMovesCalculator vmc) {
		ArrayList <byte[]> positions = board.listOfEntries(vmc.getTurnOfPlayer());
		ArrayList <byte[]> destinations = new ArrayList <byte[]>();
		int r1 = 0;
		int r2 = 0;
		while (destinations.isEmpty()) {
			r1 = rand.nextInt(positions.size());
			destinations = validMovesAI(board, positions.get(r1)[0], positions.get(r1)[1]);
		}
		r2 = rand.nextInt(destinations.size());
		board.setEntry(positions.get(r1)[0], positions.get(r1)[1], (byte) 0);
		board.setEntry(destinations.get(r2)[0], destinations.get(r2)[1], (byte) vmc.getTurnOfPlayer());
		vmc.increaseTurnOfPlayer();
	}
	
	public void makeGreedyMoveDepth(Board board, ValidMovesCalculator vmc, int depth) {
		ArrayList <byte[]> positions = board.listOfEntries(vmc.getTurnOfPlayer());
		ArrayList <byte[]> destinations = new ArrayList <byte[]>();
		int bestHeuristic = heuristic1(positions);
		Move m = null;
		for (int i = 0; i < positions.size(); i++) {
			destinations = validMovesAI(board, positions.get(i)[0], positions.get(i)[1]);
			for (int j = 0; j < destinations.size(); j++) {
				board.setEntry(positions.get(i)[0], positions.get(i)[1], (byte) 0);
				board.setEntry(destinations.get(j)[0], destinations.get(j)[1], (byte) vmc.getTurnOfPlayer());
				int newHeuristic = heuristic1(positions);
				int recHeuristic = RecGreedyMove(board, depth, vmc, bestHeuristic);
				if (newHeuristic < bestHeuristic) {
					bestHeuristic = newHeuristic;
					m = new Move(positions.get(i)[0], positions.get(i)[1], destinations.get(j)[0], destinations.get(j)[1]);
				}
				if (recHeuristic < bestHeuristic) {
					bestHeuristic = recHeuristic;
					m = new Move(positions.get(i)[0], positions.get(i)[1], destinations.get(j)[0], destinations.get(j)[1]);
				}
				board.setEntry(positions.get(i)[0], positions.get(i)[1], (byte) vmc.getTurnOfPlayer());
				board.setEntry(destinations.get(j)[0], destinations.get(j)[1], (byte) 0);
			}
			
		}
		//System.out.println(bestHeuristic);
		board.setEntry(m.getOldX(), m.getOldY(), (byte) 0);
		board.setEntry(m.getNewX(), m.getNewY(), (byte) vmc.getTurnOfPlayer());
		vmc.increaseTurnOfPlayer();
	}
	
	private int RecGreedyMove(Board fakeBoard, int depth, ValidMovesCalculator vmc, int bestHeuristic) {
		if (depth == 0) {
			ArrayList <byte[]> positions = fakeBoard.listOfEntries(vmc.getTurnOfPlayer());
			return heuristic1(positions);
		} else {
			Board fakeFakeBoard = fakeBoard.copy();
			ArrayList <byte[]> positions = fakeFakeBoard.listOfEntries(vmc.getTurnOfPlayer());
			ArrayList <byte[]> destinations = new ArrayList <byte[]>();
			for (int i = 0; i < positions.size(); i++) {
				destinations = validMovesAI(fakeFakeBoard, positions.get(i)[0], positions.get(i)[1]);
				for (int j = 0; j < destinations.size(); j++) {
					fakeFakeBoard.setEntry(positions.get(i)[0], positions.get(i)[1], (byte) 0);
					fakeFakeBoard.setEntry(destinations.get(j)[0], destinations.get(j)[1], (byte) vmc.getTurnOfPlayer());
					int newHeuristic = heuristic1(positions);
					int recHeuristic = RecGreedyMove(fakeFakeBoard, depth - 1, vmc, bestHeuristic);
					if (newHeuristic < bestHeuristic) {
						bestHeuristic = newHeuristic;
					}
					if (recHeuristic < bestHeuristic) {
						bestHeuristic = recHeuristic;
					}
					fakeFakeBoard.setEntry(positions.get(i)[0], positions.get(i)[1], (byte) vmc.getTurnOfPlayer());
					fakeFakeBoard.setEntry(destinations.get(j)[0], destinations.get(j)[1], (byte) 0);
				}
			}
			return bestHeuristic;
		}
	}
	
	public int heuristic1(ArrayList <byte[]> positions) {
		int result = 0;
		for (int i = 0; i < positions.size(); i++) {
			result += Math.sqrt(Math.pow(- 100 + positions.get(i)[1] * 20 + positions.get(i)[0] * 40 - 416, 2) + Math.pow(60 + positions.get(i)[1] * 34 - 700, 2));
		}
		return result;
	}
	
	public ArrayList <byte[]> validMovesAI(Board board, byte x, byte y) {
		ArrayList <byte[]> destinations = new ArrayList <byte[]>();
		validMovesWalkAI(board, x, y, destinations);
		validMovesJumpAI(board, x, y, destinations);
		board.replace((byte) 11, (byte) 0);
		return destinations;
	}
	
	public void validMovesWalkAI(Board board, byte x, byte y, ArrayList <byte[]> destinations) {
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
			    	destinations.add(new byte[] {adjacentTiles[i], adjacentTiles[i + 1]});
			    }
			} 
			catch (IndexOutOfBoundsException e) {}
		}
	}
	
	public void validMovesJumpAI(Board board, byte x, byte y, ArrayList <byte[]> destinations) {
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
		        	validMovesJumpAI(board, adjacentTiles[i + 2], adjacentTiles[i + 3], destinations);
		        	destinations.add(new byte[] {adjacentTiles[i + 2], adjacentTiles[i + 3]});
		        	
		        }
		    } 
			catch (IndexOutOfBoundsException e) {}
		}
	}
}
