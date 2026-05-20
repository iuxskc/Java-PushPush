package term_project_final;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//mp3 파일 실행 라이브러리 javazoom.net JLayer
public class MainScreen extends JFrame {
	private CardLayout card;
	private JPanel ChangePanel;
	private GameMap map;

	private String lastPlayerName = "";
	private Image ScreenImage;
	private Graphics ScreenGraphic;

	private Image MainImage = new ImageIcon(getClass().getResource("../image/MainImage.png")).getImage(); //경로 찾아서 image얻는 방법 - joyful velog 참고
	private Music MainMusic = new Music("MainMusic.mp3", true);

	private Image GameImage = new ImageIcon(getClass().getResource("../image/GameImage.png")).getImage();
	private Music GameMusic = new Music("GameMusic.mp3", true);

	private ImageIcon MainNewBtn0 = new ImageIcon(getClass().getResource("../image/MainNewBtn0.png"));
	private ImageIcon MainNewBtn1 = new ImageIcon(getClass().getResource("../image/MainNewBtn1.png"));
	private ImageIcon MainContinueBtn0 = new ImageIcon(getClass().getResource("../image/MainContinueBtn0.png"));
	private ImageIcon MainContinueBtn1 = new ImageIcon(getClass().getResource("../image/MainContinueBtn1.png"));
	private ImageIcon MainRankBtn0 = new ImageIcon(getClass().getResource("../image/MainRankBtn0.png"));
	private ImageIcon MainRankBtn1 = new ImageIcon(getClass().getResource("../image/MainRankBtn1.png"));

	private ImageIcon GameMainBtn0 = new ImageIcon(getClass().getResource("../image/GameMainBtn0.png"));
	private ImageIcon GameMainBtn1 = new ImageIcon(getClass().getResource("../image/GameMainBtn1.png"));
	private ImageIcon GameResetBtn0 = new ImageIcon(getClass().getResource("../image/GameResetBtn0.png"));
	private ImageIcon GameResetBtn1 = new ImageIcon(getClass().getResource("../image/GameResetBtn1.png"));
	private ImageIcon GameRankBtn0 = new ImageIcon(getClass().getResource("../image/GameRankBtn0.png"));
	private ImageIcon GameRankBtn1 = new ImageIcon(getClass().getResource("../image/GameRankBtn1.png"));

	private JButton MainNewBtn = new JButton(MainNewBtn0);
	private JButton MainContinueBtn = new JButton(MainContinueBtn0);
	private JButton MainRankBtn = new JButton(MainRankBtn0);

	private JButton GameMainBtn = new JButton(GameMainBtn0);
	private JButton GameResetBtn = new JButton(GameResetBtn0);
	private JButton GameRankBtn = new JButton(GameRankBtn0);

	public MainScreen() {
		setTitle("PushPush 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		card = new CardLayout();
		ChangePanel = new JPanel(card);

		map = new GameMap();

		setSize(500, 500);
		setResizable(false);

		setLocationRelativeTo(null);
		setLayout(null);

		JPanel Mainpanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainImage, 0, 0, null);
			}
		};
		Mainpanel.setLayout(null);

		JPanel MainNamePanel = new JPanel();

		JTextField NameField = new JTextField(10);
		NameField.setText("닉네임");
		MainNamePanel.setBounds(120, 390, 110, 30);
		MainNamePanel.add(NameField);

		Mainpanel.add(MainNamePanel);

		MainNewBtn.setBounds(150, 230, 400, 100);
		MainNewBtn.setBorderPainted(false); // 버튼 모양 고정 해제 - 안경잡이개발자 블로그 참고
		MainNewBtn.setContentAreaFilled(false);
		MainNewBtn.setFocusPainted(false);
		MainNewBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				MainNewBtn.setIcon(MainNewBtn1);
				Music BtnEnterMusic = new Music("BtnEnterMusic.mp3", false);
				BtnEnterMusic.start();
			}

			public void mouseExited(MouseEvent e) {
				MainNewBtn.setIcon(MainNewBtn0);
			}

			public void mousePressed(MouseEvent e) {
				Music BtnPressMusic = new Music("BtnPressMusic.mp3", false);
				BtnPressMusic.start();
				String inputName = NameField.getText().trim();
				if (inputName.equals("")) {
					JOptionPane.showMessageDialog(null, "이름을 입력하세요."); //팝업창 -  참고
					return;
				}
				lastPlayerName = inputName;

				map.getStatus().setLastPlayerName(lastPlayerName);
				map.startFromFirstStage();
				showGamePanel();
			}
		});
		Mainpanel.add(MainNewBtn);

		MainContinueBtn.setBounds(150, 290, 400, 100);
		MainContinueBtn.setBorderPainted(false);
		MainContinueBtn.setContentAreaFilled(false);
		MainContinueBtn.setFocusPainted(false);
		MainContinueBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				MainContinueBtn.setIcon(MainContinueBtn1);
				Music BtnEnterMusic = new Music("BtnEnterMusic.mp3", false);
				BtnEnterMusic.start();
			}

			public void mouseExited(MouseEvent e) {
				MainContinueBtn.setIcon(MainContinueBtn0);
			}

			public void mousePressed(MouseEvent e) {
				Music BtnPressMusic = new Music("BtnPressMusic.mp3", false);
				BtnPressMusic.start();
				if (lastPlayerName.equals("")) {
					JOptionPane.showMessageDialog(null, "플레이 도중 저장된 기록이 없습니다.");
					return;
				}
				map.getStatus().setLastPlayerName(lastPlayerName);
				map.continuePlayTime();
				showGamePanel();
			}
		});
		Mainpanel.add(MainContinueBtn);

		MainRankBtn.setBounds(150, 350, 400, 100);
		MainRankBtn.setBorderPainted(false);
		MainRankBtn.setContentAreaFilled(false);
		MainRankBtn.setFocusPainted(false);
		MainRankBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				MainRankBtn.setIcon(MainRankBtn1);
				Music BtnEnterMusic = new Music("BtnEnterMusic.mp3", false);
				BtnEnterMusic.start();
			}

			public void mouseExited(MouseEvent e) {
				MainRankBtn.setIcon(MainRankBtn0);
			}

			public void mousePressed(MouseEvent e) {
				Music BtnPressMusic = new Music("BtnPressMusic.mp3", false);
				BtnPressMusic.start();
				map.showRankingDialog();
			}
		});
		Mainpanel.add(MainRankBtn);

		ChangePanel.add(Mainpanel, "main");

		JPanel Gamepanel = new JPanel(new BorderLayout());
		JPanel GameBtnpanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(GameImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		Gamepanel.setSize(new Dimension(500, 50));

		map.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keycode = e.getKeyCode();

				switch (keycode) {
				case KeyEvent.VK_UP:
					map.movePlayer(-1, 0);
					break;
				case KeyEvent.VK_DOWN:
					map.movePlayer(1, 0);
					break;
				case KeyEvent.VK_LEFT:
					map.movePlayer(0, -1);
					break;
				case KeyEvent.VK_RIGHT:
					map.movePlayer(0, 1);
					break;
				}
			}
		});
		Gamepanel.add(map, BorderLayout.CENTER);

		GameMainBtn.setBorderPainted(false); // 버튼 모양 고정 해제
		GameMainBtn.setContentAreaFilled(false);
		GameMainBtn.setFocusPainted(false);
		GameMainBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				GameMainBtn.setIcon(GameMainBtn1);
				Music BtnEnterMusic = new Music("BtnEnterMusic.mp3", false);
				BtnEnterMusic.start();
			}

			public void mouseExited(MouseEvent e) {
				GameMainBtn.setIcon(GameMainBtn0);
			}

			public void mousePressed(MouseEvent e) {
				Music BtnPressMusic = new Music("BtnPressMusic.mp3", false);
				BtnPressMusic.start();
				map.stopPlayTime();
				showMainPanel();
			}
		});
		GameBtnpanel.add(GameMainBtn);

		GameResetBtn.setBorderPainted(false); // 버튼 모양 고정 해제
		GameResetBtn.setContentAreaFilled(false);
		GameResetBtn.setFocusPainted(false);
		GameResetBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				GameResetBtn.setIcon(GameResetBtn1);
				Music BtnEnterMusic = new Music("BtnEnterMusic.mp3", false);
				BtnEnterMusic.start();
			}

			public void mouseExited(MouseEvent e) {
				GameResetBtn.setIcon(GameResetBtn0);
			}

			public void mousePressed(MouseEvent e) {
				Music BtnPressMusic = new Music("BtnPressMusic.mp3", false);
				BtnPressMusic.start();
				map.resetMap();
				map.requestFocusInWindow();
			}
		});
		GameBtnpanel.add(GameResetBtn);

		GameRankBtn.setBorderPainted(false); // 버튼 모양 고정 해제
		GameRankBtn.setContentAreaFilled(false);
		GameRankBtn.setFocusPainted(false);
		GameRankBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				GameRankBtn.setIcon(GameRankBtn1);
				Music BtnEnterMusic = new Music("BtnEnterMusic.mp3", false);
				BtnEnterMusic.start();
			}

			public void mouseExited(MouseEvent e) {
				GameRankBtn.setIcon(GameRankBtn0);
			}

			public void mousePressed(MouseEvent e) {
				Music BtnPressMusic = new Music("BtnPressMusic.mp3", false);
				BtnPressMusic.start();
				map.showRankingDialog();
				map.requestFocusInWindow(); //랭킹 버튼을 클릭한 후 게임 화면에 focus되지 않는 문제 해결
			}
		});
		GameBtnpanel.add(GameRankBtn);

		Gamepanel.add(GameBtnpanel, BorderLayout.SOUTH);

		ChangePanel.add(Gamepanel, "game");
		setContentPane(ChangePanel);
		setVisible(true);
		MainMusic.start();
	}

	public void resetPlayerName() {
		lastPlayerName = "";
	}
	public void showMainPanel() {
		card.show(ChangePanel, "main");
		
		GameMusic.close();
		MainMusic = new Music("MainMusic.mp3", true);
		MainMusic.start();
	}

	public void showGamePanel() {
		card.show(ChangePanel, "game");
		map.requestFocusInWindow();

		MainMusic.close();
		GameMusic = new Music("GameMusic.mp3", true);
		GameMusic.start();
	}

	public static void main(String[] args) {
		new MainScreen();
	}
}
