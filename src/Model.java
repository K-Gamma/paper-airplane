import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.Timer;

public class Model {

	private Controller c;
	private Viewer v;

	public Model(Controller c) {
		this.c = c;
		v = new Viewer(this);
		isStart = true;
	}

	public Controller getController() {
		return c;
	}

	public Viewer getViewer() {
		return v;
	}

	/*--Controller.java--*/
	private Timer timer;

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	/*--Viewer.java--*/
	public static final int WIDTH = 800;
	public static final int HEIGHT = 400;
	public static final int SPEED_X = 4; // 横向き最大速度
	public static final int SPEED_Y = 4; // 上下向き最大速度
	private BufferedImage buffer;
	private BufferedImage sky;
	private BufferedImage cloud;
	private BufferedImage dead;
	private BufferedImage light;
	private BufferedImage gameClear;
	private BufferedImage gameOver;
	private BufferedImage[] planeImage;
	private BufferedImage[] enemyImage;
	private ArrayList<Enemy> enemyAry;
	private ArrayList<Enemy> blindfoldAry;
	private int[] plane; // 画像の中心座標
	private int planeState; // 0:上向き 1:横向き 2:下向き
	private int[] planeSpeed; // 横向きの速さ
	private int cloudX; // 雲の横座標
	private int distance; // 飛んだ距離
	private boolean isStart; // スタートクリアフラグ
	private boolean isClear; // ゲームクリアフラグ
	private boolean isEnd; // ゲームエンドフラグ

	public BufferedImage getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}

	public BufferedImage getSky() {
		return sky;
	}

	public void setSky(BufferedImage sky) {
		this.sky = sky;
	}

	public BufferedImage getCloud() {
		return cloud;
	}

	public void setCloud(BufferedImage cloud) {
		this.cloud = cloud;
	}

	public BufferedImage getDead() {
		return dead;
	}

	public void setDead(BufferedImage dead) {
		this.dead = dead;
	}

	public BufferedImage getLight() {
		return light;
	}

	public void setLight(BufferedImage light) {
		this.light = light;
	}

	public BufferedImage getGameClear() {
		return gameClear;
	}

	public void setGameClear(BufferedImage gameClear) {
		this.gameClear = gameClear;
	}

	public BufferedImage getGameOver() {
		return gameOver;
	}

	public void setGameOver(BufferedImage gameOver) {
		this.gameOver = gameOver;
	}

	public BufferedImage[] getPlaneImage() {
		return planeImage;
	}

	public void setPlaneImage(BufferedImage[] planeImage) {
		this.planeImage = planeImage;
	}

	public BufferedImage[] getEnemyImage() {
		return enemyImage;
	}

	public void setEnemyImage(BufferedImage[] enemyImage) {
		this.enemyImage = enemyImage;
	}

	public ArrayList<Enemy> getEnemyAry() {
		return enemyAry;
	}

	public void setEnemyAry(ArrayList<Enemy> enemyAry) {
		this.enemyAry = enemyAry;
	}

	public ArrayList<Enemy> getBlindfoldAry() {
		return blindfoldAry;
	}

	public void setBlindfoldAry(ArrayList<Enemy> blindfoldAry) {
		this.blindfoldAry = blindfoldAry;
	}

	public int[] getPlane() {
		return plane;
	}

	public void setPlane(int[] plane) {
		this.plane = plane;
	}

	public int getPlaneState() {
		return planeState;
	}

	public void setPlaneState(int planeState) {
		this.planeState = planeState;
	}

	public int[] getPlaneSpeed() {
		return planeSpeed;
	}

	public void setPlaneSpeed(int[] planeSpeed) {
		this.planeSpeed = planeSpeed;
	}

	public int getCloudX() {
		return cloudX;
	}

	public void setCloudX(int cloudX) {
		this.cloudX = cloudX;
	}

	public int getDistance() {
		return distance;
	}
	// セットはできない

	public boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean getIsClear() {
		return isClear;
	}

	public void setIsClear(boolean isClear) {
		this.isClear = isClear;
	}

	public boolean getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	/*
	 * 初期値を設定 plane : {w/2, h/2} planestate : 1 planeSpeed : {SPEED_X, 0}
	 */
	public void reset() {
		plane = new int[] { WIDTH / 4, HEIGHT * 2 / 5 };
		planeState = 1; // 横向き
		planeSpeed = new int[] { SPEED_X, 0 };
		cloudX = 0;
		distance = 0;
		isEnd = isClear = false;
		enemyAry = new ArrayList<Enemy>();
		blindfoldAry = new ArrayList<Enemy>();
		EnemyCreate();
	}

	/* キーの処理を行う */
	// キーを押したときの操作
	public void pressedKeyJudge(int key) {
		switch (key) {
			case KeyEvent.VK_UP: // 上
				planeState = 0; // 上向き
				planeSpeed[1] = SPEED_Y;
				break;
			case KeyEvent.VK_DOWN: // 下
				planeState = 2; // 下向き
				planeSpeed[1] = -SPEED_Y;
				break;
			case KeyEvent.VK_RIGHT: // 右
				planeState = 1; // 横向き
				planeSpeed[0] = 2 * SPEED_X;
				break;
			case KeyEvent.VK_LEFT: // 左
				planeState = 1; // 横向き
				planeSpeed[0] = 0;
				break;
			case KeyEvent.VK_ENTER:
				// スタート, リスタート
				if (isStart)
					isStart = false;

				if (!timer.isRunning()) {
					reset();
					timer.restart();
				}
				if (isEnd)
					isStart = true;

				break;
			default:
				return;
		}
	}

	// キーを離したときの操作
	public void releasedKeyJudge(int key) {
		switch (key) {
			case KeyEvent.VK_UP: // 上
			case KeyEvent.VK_DOWN: // 下
				planeState = 1;
				planeSpeed[1] = 0;
				break;
			case KeyEvent.VK_RIGHT: // 右
			case KeyEvent.VK_LEFT: // 左
				planeSpeed[0] = SPEED_X;
				break;
			default:
				return;
		}
	}

	/* 敵が範囲外に出ているかどうか */
	public void OutOfRange(int[] plane) {
		int x = planeImage[planeState].getWidth(v) / 2 - 10; // 中心から10ピクセルはみ出し許容
		if (plane[0] - cloudX < x)
			plane[0] = x + cloudX;

		x = sky.getWidth(v) - planeImage[planeState].getWidth(v) / 2 + 5; // 5ピクセルはみ出し許容
		if (x < plane[0] - cloudX)
			plane[0] = x + cloudX;

		int y = planeImage[planeState].getHeight(v) / 2 - 10; // 10ピクセルはみ出し許容
		if (plane[1] < y)
			plane[1] = y;

		y = sky.getHeight(v) - planeImage[planeState].getHeight(v) / 2 + 5; // 5ピクセルはみ出し許容
		if (y < plane[1])
			plane[1] = y;
	}

	/* スタート画面での計算を行う */
	public void StartCalclate() {
		plane[0] += SPEED_X;
		cloudX += SPEED_X;
		if (sky.getWidth(v) < cloudX) {
			plane[0] -= cloudX;
			cloudX = 0; // 横座標をリセット
		}
	}

	/* バトル画面の計算を行う */
	public void MoveCalclate() {
		distance++;
		plane[0] += planeSpeed[0];
		cloudX += SPEED_X;
		if (sky.getWidth(v) < cloudX) {
			plane[0] -= cloudX;
			cloudX = 0; // 横座標をリセット
		}
		plane[1] -= planeSpeed[1];

		// 範囲外処理
		OutOfRange(plane);
	}

	/* ゲームクリア時の計算を行う */
	public void ClearCalclate() {
		plane[0] += SPEED_X;
		cloudX += SPEED_X;
		if (WIDTH / 4 > plane[0] - cloudX)
			plane[0] += 2;

		if (sky.getWidth(v) < cloudX) {
			plane[0] -= cloudX;
			cloudX = 0; // 横座標をリセット
		}
		if (HEIGHT / 2 < plane[1]) {
			plane[1]--;
			planeState = 0;
		} else if (HEIGHT / 2 > plane[1]) {
			plane[1]++;
			planeState = 2;
		} else
			planeState = 1;
	}

	/* 敵配列を作成 */
	public void EnemyCreate() {
		// ファイルを読み込む(距離, 初期座標)
		// 番号によってswitch->エネミー生成、配列へ加える
		try (InputStream stream = this.getClass().getResourceAsStream("enemyinfo.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() == 0)
					continue; // 空行は無視

				String[] tv = line.split(",", 0);
				int i = Integer.parseInt(tv[0]) - 1;
				switch (i) {
					case 0:
						enemyAry.add(new Building(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 1:
						enemyAry.add(new SkyBiru(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 2:
						enemyAry.add(new TokyoTower(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 3:
						enemyAry.add(new Hato(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 4:
						enemyAry.add(new Kamome(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 5:
						enemyAry.add(new AirPlane(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 6:
						enemyAry.add(new Balloon(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 7:
						enemyAry.add(new Fighter(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 8:
						enemyAry.add(new Helicopter(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 9:
						// 画像のみ
						break;
					case 10:
						enemyAry.add(new StealthFighter(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 11:
						enemyAry.add(new UFO(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					case 12:
						// 画像のみ
						break;
					case 13:
						enemyAry.add(new God(this, enemyImage[i],
								new int[] { Integer.parseInt(tv[1]), Integer.parseInt(tv[2]) }));
						break;
					default:
						break;
				}
			}
			Collections.sort(enemyAry, new EnemyComparator()); // ソート

		} catch (FileNotFoundException e) {
			System.err.println("ファイルが見つかりませんでした.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("ファイルを開けませんでした.");
			e.printStackTrace();
		}
	}

	/* 当たり判定 */
	public boolean isHit() {
		/* 1ピクセルの値でのみ判定(敏感) */
		/*
		 * Color rgb = new Color(buffer.getRGB(plane[0] - cloudX, plane[1])); int r =
		 * rgb.getRed(); int g = rgb.getGreen(); int b = rgb.getBlue();
		 *
		 * if (((125 < r || r < 104)) && ((205 < g || g < 192)) && ((245 < b || b <
		 * 240))) { return true; } return false;
		 */

		/* 5×5の矩形の中の中央値を求める(高精度) */
		int[] pixel = buffer.getRGB(plane[0] - cloudX - 2, plane[1] - 2, 5, 5, null, 0, 5);
		int[] R = new int[25];
		int[] G = new int[25];
		int[] B = new int[25];
		for (int i = 0; i < pixel.length; i++) {
			Color pixelColor = new Color(pixel[i]);
			R[i] = pixelColor.getRed();
			G[i] = pixelColor.getGreen();
			B[i] = pixelColor.getBlue();
		}
		Arrays.sort(R);
		Arrays.sort(G);
		Arrays.sort(B);
		int r, g, b;
		if (pixel.length % 2 == 1) {
			r = R[pixel.length / 2];
			g = G[pixel.length / 2];
			b = B[pixel.length / 2];
		} else {
			r = R[(pixel.length / 2) + 1] + R[(pixel.length / 2) - 1];
			g = G[(pixel.length / 2) + 1] + G[(pixel.length / 2) - 1];
			b = B[(pixel.length / 2) + 1] + B[(pixel.length / 2) - 1];
		}
		if ((125 < r || r < 104) && (205 < g || g < 192) && (245 < b || b < 240))
			return true;

		return false;
	}

	/* 敵が範囲外に出ているかどうか */
	public boolean isOutOfRange(Enemy enemy) {
		if ((enemy.pos[0] + enemy.image.getWidth(v) < 0) || (Model.WIDTH < enemy.pos[0])) // 上下は都合によりなし
			return true;

		return false;
	}
}
