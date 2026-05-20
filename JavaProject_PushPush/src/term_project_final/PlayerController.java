package term_project_final;

public class PlayerController {
	private int playerRow, playerCol;

	public PlayerController(int[][] map) {
		findPlayer(map);
	}

	private void findPlayer(int[][] map) {
		for (int r = 0; r < map.length; r++)
			for (int c = 0; c < map[0].length; c++)
				if (map[r][c] == 5) {
					playerRow = r;
					playerCol = c;
					return;
				}
	}

	public int getPlayerRow() {
		return playerRow;
	}

	public int getPlayerCol() {
		return playerCol;
	}

	public boolean movePlayer(int dr, int dc, int[][] map, int[][] stageMap) {
		int nr = playerRow + dr, nc = playerCol + dc;
		if (!inRange(nr, nc, map.length, map[0].length))
			return false;
		if (map[nr][nc] == 1)
			return false;

		if (map[nr][nc] == 4 || map[nr][nc] == 6) {
			int br = nr + dr, bc = nc + dc;
			if (!inRange(br, bc, map.length, map[0].length))
				return false;
			if (map[br][bc] != 2 && map[br][bc] != 3)
				return false;

			map[br][bc] = (map[br][bc] == 3) ? 6 : 4;
			map[nr][nc] = 2;
		}

		map[playerRow][playerCol] = (stageMap[playerRow][playerCol] == 3) ? 3 : 2;
		playerRow = nr;
		playerCol = nc;
		map[playerRow][playerCol] = 5;
		Music PlayerMove = new Music("PlayerMove.mp3", false);
		PlayerMove.start();
		return true;
	}

	private boolean inRange(int r, int c, int rowCount, int colCount) {
		return r >= 0 && r < rowCount && c >= 0 && c < colCount;
	}
}