package term_project_final;

import javax.swing.*;

public class GameStatus {

	private int playSeconds = 0;
	private Timer timer;

	private int stageNum = 0; // 0부터 시작
	private int MaxstageNum = 2;
	private int moveCount = 0;

	private RankingEntry[] rankings = new RankingEntry[10];
	private int rankingCount = 0;

	private String lastPlayerName = "";


	public GameStatus() {
	}

	//시간을 출력하는 함수 - corderanch 사이트 참고
	public void startTimer(JPanel panel) {
		timer = new Timer(1000, e -> {
			playSeconds++;
			panel.repaint();
		});
		timer.start();
	}

	public void stopTimer() {
		timer.stop();
	}

	public void resetTimer() {
		timer.stop();
		playSeconds = 0;
		timer.start();
	}

	public int getPlaySeconds() {
		return playSeconds;
	}

	public String getTimeString() {
		int min = playSeconds / 60;
		int sec = playSeconds % 60;
		return String.format("%02d:%02d", min, sec);
	}

	public int getStageNum() {
		return stageNum;
	}

	public void setStageNum(int n) {
		stageNum = n;
	}

	public boolean nextStage() {
		if (stageNum != MaxstageNum) {
			stageNum++;
			return true;
		}
		else {
			return false;
		}

	}

	public int getMoveCount() {
		return moveCount;
	}

	public void incrementMove() {
		moveCount++;
	}

	public void resetMoveCount() {
		moveCount = 0;
	}

	public void addRanking(String name) {
		if (rankingCount < rankings.length) {
			rankings[rankingCount++] = new RankingEntry(name, moveCount, playSeconds);
		} else {
			for (int i = 1; i < rankings.length; i++)
				rankings[i - 1] = rankings[i];
			rankings[rankings.length - 1] = new RankingEntry(name, moveCount, playSeconds);
		}
	}

	public RankingEntry[] getRankingsByTime() {
		RankingEntry[] copy = new RankingEntry[rankingCount];
		for (int i = 0; i < rankingCount; i++)
			copy[i] = rankings[i];
		for (int i = 0; i < copy.length - 1; i++) {
			int minIdx = i;
			for (int j = i + 1; j < copy.length; j++)
				if (copy[j].playSeconds < copy[minIdx].playSeconds)
					minIdx = j;
			RankingEntry tmp = copy[i];
			copy[i] = copy[minIdx];
			copy[minIdx] = tmp;
		}
		return copy;
	}

	public RankingEntry[] getRankingsByMove() {
		RankingEntry[] copy = new RankingEntry[rankingCount];
		for (int i = 0; i < rankingCount; i++)
			copy[i] = rankings[i];
		for (int i = 0; i < copy.length - 1; i++) {
			int minIdx = i;
			for (int j = i + 1; j < copy.length; j++)
				if (copy[j].moveCount < copy[minIdx].moveCount)
					minIdx = j;
			RankingEntry tmp = copy[i];
			copy[i] = copy[minIdx];
			copy[minIdx] = tmp;
		}
		return copy;
	}

	public void setLastPlayerName(String name) {
		lastPlayerName = name;
	}

	public String getLastPlayerName() {
		return lastPlayerName;
	}
}