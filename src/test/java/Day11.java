import java.util.LinkedList;
import java.util.Queue;

public class Day11 {
	public static void main(String[] args) {
		String[] input = { "7777838353", "2217272478", "3355318645", "2242618113", "7182468666", "5441641111",
				"4773862364", "5717125521", "7542127721", "4576678341" };
		String[] input1 = { "5483143223", "2745854711", "5264556173", "6141336146", "6357385478", "4167524645",
				"2176841721", "6882881134", "4846848554", "5283751526" };

		int[][] matrix = new int[input.length][input[0].length()];
		for (int i = 0; i < input.length; i++) {
			String s = input[i];
			for (int j = 0; j < s.length(); j++) {
				matrix[i][j] = Integer.parseInt(s.substring(j, j + 1));
			}
		}

		int totalFlashes = 0;
		int synFlashes = -1;
		for (int i = 1; i <= 1000; i++) {
			int flashes = 0;
			Queue<int[]> queue = new LinkedList<>();
			int[][] visited = new int[matrix.length][matrix[0].length];
			for (int j = 0; j < matrix.length; j++) {
				for (int k = 0; k < matrix[j].length; k++) {
					matrix[j][k]++;
					if (matrix[j][k] > 9) {
						queue.add(new int[] { j, k });
						visited[j][k] = 1;
					}
				}
			}
			while (!queue.isEmpty()) {
				int size = queue.size();
				for (int j = 0; j < size; j++) {
					int[] temp = queue.remove();
					int r = temp[0];
					int c = temp[1];
					flashes++;
					if (r > 0) {
						matrix[r - 1][c]++;
						if (matrix[r - 1][c] > 9 && visited[r - 1][c] == 0) {
							queue.add(new int[] { r - 1, c });
							visited[r - 1][c] = 1;
						}
					}
					if (r < matrix.length - 1) {
						matrix[r + 1][c]++;
						if (matrix[r + 1][c] > 9 && visited[r + 1][c] == 0) {
							queue.add(new int[] { r + 1, c });
							visited[r + 1][c] = 1;
						}
					}
					if (c > 0) {
						matrix[r][c - 1]++;
						if (matrix[r][c - 1] > 9 && visited[r][c - 1] == 0) {
							queue.add(new int[] { r, c - 1 });
							visited[r][c - 1] = 1;
						}
					}
					if (c < matrix[0].length - 1) {
						matrix[r][c + 1]++;
						if (matrix[r][c + 1] > 9 && visited[r][c + 1] == 0) {
							queue.add(new int[] { r, c + 1 });
							visited[r][c + 1] = 1;
						}
					}
					if (r > 0 && c > 0) {
						matrix[r - 1][c - 1]++;
						if (matrix[r - 1][c - 1] > 9 && visited[r - 1][c - 1] == 0) {
							queue.add(new int[] { r - 1, c - 1 });
							visited[r - 1][c - 1] = 1;
						}
					}
					if (r < matrix.length - 1 && c < matrix[0].length - 1) {
						matrix[r + 1][c + 1]++;
						if (matrix[r + 1][c + 1] > 9 && visited[r + 1][c + 1] == 0) {
							queue.add(new int[] { r + 1, c + 1 });
							visited[r + 1][c + 1] = 1;
						}
					}
					if (r > 0 && c < matrix[0].length - 1) {
						matrix[r - 1][c + 1]++;
						if (matrix[r - 1][c + 1] > 9 && visited[r - 1][c + 1] == 0) {
							queue.add(new int[] { r - 1, c + 1 });
							visited[r - 1][c + 1] = 1;
						}
					}
					if (r < matrix.length - 1 && c > 0) {
						matrix[r + 1][c - 1]++;
						if (matrix[r + 1][c - 1] > 9 && visited[r + 1][c - 1] == 0) {
							queue.add(new int[] { r + 1, c - 1 });
							visited[r + 1][c - 1] = 1;
						}
					}
				}
			}
			if (i <= 100)
				totalFlashes += flashes;
			if (synFlashes == -1 && flashes == matrix.length * matrix[0].length) {
				synFlashes = i;
			}
			for (int j = 0; j < visited.length; j++) {
				for (int k = 0; k < visited[j].length; k++) {
					if (visited[j][k] == 1)
						matrix[j][k] = 0;
				}
			}
		}
		System.out.println("part 1 " + totalFlashes);
		System.out.println("part 2 " + synFlashes);
	}

}
