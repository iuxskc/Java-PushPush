package term_project_final;

import java.awt.*;
import javax.swing.*;

public class GameMap extends JPanel {
	private MapData mapData;
	private PlayerController playerCtrl;
	private GameStatus status;
	private int[][] map;

	public GameMap() {
		setFocusable(true);
		mapData = new MapData();

		status = new GameStatus();
		status.startTimer(this);
		loadStage(status.getStageNum());
	}

	public GameStatus getStatus() {
		return status;
	}

	public void continuePlayTime() {
		status.startTimer(this);
	}

	public void stopPlayTime() {
		status.stopTimer();
	}

	public void startFromFirstStage() {
		status.setStageNum(0); // 스테이지 번호 0으로
		status.resetMoveCount(); // 이동 횟수 0으로
		status.resetTimer();
		loadStage(status.getStageNum()); // 맵 로딩
	}

	public void loadStage(int num) {
		map = mapData.getStage(num);
		playerCtrl = new PlayerController(map);
		repaint();
	}

	public void resetMap() {
		loadStage(status.getStageNum());
	}

	public void movePlayer(int dr, int dc) {
		if (playerCtrl.movePlayer(dr, dc, map, mapData.getStage(status.getStageNum()))) {
			status.incrementMove();
			repaint();
			if (isWin()) {
				if (status.nextStage()) {
					Music StageClear = new Music("StageClear.mp3", false);
					StageClear.start();
					loadStage(status.getStageNum());
				} else {
					Music GameClear = new Music("GameClear.mp3", false);
					GameClear.start();
					JOptionPane.showMessageDialog(this, "모든 스테이지를 클리어했습니다!"); //팝업 메시지
					status.addRanking(status.getLastPlayerName());
					showRankingDialog();
					status.stopTimer();

					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this); //stack overflow 질문 게시판 참고
						((MainScreen) topFrame).showMainPanel();
						((MainScreen) topFrame).resetPlayerName();
				}
			}
		}
	}
	

	private boolean isWin() {
		int[][] stageMap = mapData.getStage(status.getStageNum());
		for (int r = 0; r < MapData.ROW_COUNT; r++) {
			for (int c = 0; c < MapData.COL_COUNT; c++) {
				if (stageMap[r][c] == 3 && map[r][c] != 6) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image[] imgs = mapData.getImages();
		for (int r = 0; r < MapData.ROW_COUNT; r++)
			for (int c = 0; c < MapData.COL_COUNT; c++)
				drawCell(g, r, c, imgs);
		
		g.setColor(new Color(255, 255, 255, 180));
		g.fillRoundRect(10, 10, 150, 85, 20, 20);
		g.setColor(Color.BLACK);
		g.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		g.drawString("스테이지: " + (status.getStageNum() + 1), 20, 35);
		g.drawString("이동 횟수: " + status.getMoveCount(), 20, 60);
		g.drawString("플레이 시간: " + status.getTimeString(), 20, 85);

	}

	private void drawCell(Graphics g, int row, int col, Image[] imgs) {
		int cellW = 500 / MapData.COL_COUNT;
		int cellH = 500 / MapData.ROW_COUNT;
		int idx = map[row][col];
		int x = cellW * col, y = cellH * row;

		// 배경 먼저 그림 (stageMap 기준)
		int[][] stageMap = mapData.getStage(status.getStageNum());
		int bg = stageMap[row][col];

		if (bg == 3) { // 목표지점
			g.drawImage(imgs[3], x, y, cellW, cellH, this);
		} else if (bg == 2 || bg == 4 || bg == 5) { // 바닥
			g.drawImage(imgs[2], x, y, cellW, cellH, this);
		} else if (bg == 1) { // 벽
			g.drawImage(imgs[1], x, y, cellW, cellH, this);
		} else { // 빈 칸
			g.drawImage(imgs[0], x, y, cellW, cellH, this);
		}

		// 상자, 플레이어, 상자+목표는 위에 덮어 그림 (map 기준)
		if (idx == 4) {
			g.drawImage(imgs[4], x, y, cellW, cellH, this); // 상자
		} else if (idx == 5) {
			g.drawImage(imgs[5], x, y, cellW, cellH, this); // 플레이어
		} else if (idx == 6) {
			g.drawImage(imgs[6], x, y, cellW, cellH, this); // 상자+목표
		}
	}

	public void showRankingDialog() {
		String[] options = { "플레이 시간 랭킹", "이동 횟수 랭킹" };
		int sel = JOptionPane.showOptionDialog(this, "확인할 랭킹 종류를 선택하세요.", "랭킹 보기", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); //버튼 두 개 팝업은 JOptionPane의 기능 - 요이미야 티스토리 참고
		if (sel == 0)
			showRankingByTime();
		else if (sel == 1)
			showRankingByMove();
	}

	public void showRankingByTime() {
		RankingEntry[] arr = status.getRankingsByTime();
		String msg = "플레이 시간 랭킹\n";
		for (int i = 0; i < arr.length; i++) {
			int min = arr[i].playSeconds / 60;
			int sec = arr[i].playSeconds % 60;
			msg += (i + 1) + ". " + arr[i].name + " - " + String.format("%02d:%02d", min, sec) + "\n";
		}
		JOptionPane.showMessageDialog(this, msg);
	}

	public void showRankingByMove() {
		RankingEntry[] arr = status.getRankingsByMove();
		String msg = "이동 횟수 랭킹\n";
		for (int i = 0; i < arr.length; i++) {
			int min = arr[i].playSeconds / 60;
			int sec = arr[i].playSeconds % 60;
			msg += (i + 1) + ". " + arr[i].name + " - " + arr[i].moveCount + "회\n";
		}
		JOptionPane.showMessageDialog(this, msg);
	}

}