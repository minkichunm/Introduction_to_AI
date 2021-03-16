package chineseChecker;

import java.util.ArrayList;

public class HeuristicTrial {
	
	public static void main(String[] args) {
		ArrayList <byte[]> positions = new ArrayList <byte[]>();
		positions.add(new byte[] {7, 12});
		positions.add(new byte[] {7, 13});
		positions.add(new byte[] {6, 14});
		positions.add(new byte[] {5, 15});
		positions.add(new byte[] {4, 16});
		positions.add(new byte[] {4, 15});
		positions.add(new byte[] {4, 14});
		positions.add(new byte[] {4, 13});
		positions.add(new byte[] {5, 13});
		positions.add(new byte[] {5, 14});
	System.out.print(heuristic1(positions));
	}
	
	public static int heuristic1(ArrayList <byte[]> positions) {
		int result = 0;
		for (int i = 0; i < positions.size(); i++) {
			result += Math.sqrt(Math.pow(- 100 + positions.get(i)[1] * 20 + positions.get(i)[0] * 40 - 416, 2) + Math.pow(60 + positions.get(i)[1] * 34 - 700, 2));
		}
		return result;
	}
}
