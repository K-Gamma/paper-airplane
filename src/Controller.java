import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.Timer;

public class Controller extends JApplet implements ActionListener, KeyListener {
	private Viewer v;
	private Model m;

	public void init() {
		/*--画像ファイルを読み込む--*/
		MediaTracker tracker = new MediaTracker(this);

		BufferedImage sky = null;
		BufferedImage cloud = null;
		BufferedImage dead = null;
		BufferedImage light = null;
		BufferedImage gameClear = null;
		BufferedImage gameOver = null;
		BufferedImage[] planeImage = new BufferedImage[3];
		BufferedImage[] enemyImage = new BufferedImage[14];
		try {
			URL url = Controller.class.getResource("sky.png");
			sky = ImageIO.read(url);
			tracker.addImage(sky, 0);

			url = Controller.class.getResource("cloud.png");
			cloud = ImageIO.read(url);
			tracker.addImage(cloud, 0);

			url = Controller.class.getResource("dead.png");
			dead = ImageIO.read(url);
			tracker.addImage(dead, 0);

			url = Controller.class.getResource("light.png");
			light = ImageIO.read(url);
			tracker.addImage(light, 0);

			url = Controller.class.getResource("gameclear.png");
			gameClear = ImageIO.read(url);
			tracker.addImage(gameClear, 0);

			url = Controller.class.getResource("gameover.png");
			gameOver = ImageIO.read(url);
			tracker.addImage(gameOver, 0);

			for (int i = 0; i < planeImage.length; i++) {
				url = Controller.class.getResource("plane (" + (i + 1) + ").png");
				planeImage[i] = ImageIO.read(url);
				tracker.addImage(planeImage[i], 0);
			}

			for (int i = 0; i < enemyImage.length; i++) {
				url = Controller.class.getResource("enemy (" + (i + 1) + ").png");
				enemyImage[i] = ImageIO.read(url);
				tracker.addImage(enemyImage[i], 0);
			}

			tracker.waitForID(0); // 読み込み終了まで待つ
		} catch (Exception e) {
			planeImage = enemyImage = null;
			System.out.println("画像読み込み失敗");
			e.printStackTrace();
			System.exit(1);
		}

		/*--コンポーネントの生成--*/
		m = new Model(this);
		v = m.getViewer();
		m.setSky(sky);
		m.setCloud(cloud);
		m.setDead(dead);
		m.setLight(light);
		m.setGameClear(gameClear);
		m.setGameOver(gameOver);
		m.setPlaneImage(planeImage);
		m.setEnemyImage(enemyImage);
		m.reset();

		/*--イベントリスナの登録--*/
		v.addKeyListener(this);

		/*--コンポーネントの配置--*/
		getContentPane().add(v);

		/*--タイマー開始--*/
		m.setTimer(new Timer(17, this));
		m.getTimer().start();
	}

	// キーが押されたとき（文字キーの検出）
	@Override
	public void keyTyped(KeyEvent e) {
	}

	// キーが押されたとき
	@Override
	public void keyPressed(KeyEvent e) {
		m.pressedKeyJudge(e.getKeyCode());
	}

	// キーが離されたとき
	@Override
	public void keyReleased(KeyEvent e) {
		m.releasedKeyJudge(e.getKeyCode());
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		v.repaint();
	}
}
