import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Viewer extends JPanel {
	private Model m;

	public Viewer(Model m) {
		super();
		setPreferredSize(new Dimension(Model.WIDTH, Model.HEIGHT));
		setBackground(Color.black);
		this.m = m;
		this.m.setBuffer(new BufferedImage(Model.WIDTH, Model.HEIGHT, BufferedImage.TYPE_INT_ARGB));
	}

	public void StartPhase(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// 計算
		m.StartCalclate();

		// アンチエイリアス
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		// 背景
		g2.drawImage(m.getSky(), 0, 0, this);

		// 機体
		g2.drawImage(m.getPlaneImage()[1], m.getPlane()[0] - m.getCloudX() - m.getPlaneImage()[1].getWidth(this) / 2,
				m.getPlane()[1] - m.getPlaneImage()[1].getHeight(this) / 2, this);

		// 雲
		g2.drawImage(m.getCloud(), -m.getCloudX() % m.getSky().getWidth(this), 0, this);
		g2.drawImage(m.getCloud(), -m.getCloudX() % m.getSky().getWidth(this) + m.getSky().getWidth(this) - 1, 0, this);
		// フォントのアンチエイリアス
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// Font font = new Font("Arial", Font.PLAIN, 16);
		// Font font = new Font("ＭＳ 明朝", Font.PLAIN, 32);
		Font font = new Font("ＭＳ 明朝", Font.PLAIN, 32);
		g2.setFont(font);
		g2.drawString("[ENTER]でゲームスタート", 300, 350);

		g2.dispose();
	}

	public void BattlePhase(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// 計算
		m.MoveCalclate();

		// アンチエイリアス
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		// 背景
		g2.drawImage(m.getSky(), 0, 0, this);

		// 敵
		for (int i = 0; i < m.getEnemyAry().size(); i++) {
			// distanceと初期値を比較
			if (m.getDistance() / 10 < m.getEnemyAry().get(i).initpos[0])
				break; // ソートしてるのでbreak

			// 各event関数を呼び出し
			m.getEnemyAry().get(i).event();
			// 範囲外に出ていれば配列から削除
			if (m.isOutOfRange(m.getEnemyAry().get(i))) {
				m.getEnemyAry().remove(i);
				i--;
				continue;
			}
			// 敵描画
			g2.drawImage(m.getEnemyAry().get(i).image, m.getEnemyAry().get(i).pos[0], m.getEnemyAry().get(i).pos[1],
					this);
		}

		// 当たり判定
		if (m.isHit()) {
			// 爆発
			g2.drawImage(m.getDead(), m.getPlane()[0] - m.getCloudX() - m.getDead().getWidth(this) / 2,
					m.getPlane()[1] - m.getDead().getHeight(this) / 2, this);
			m.getTimer().stop();
		} else {
			// 機体
			g2.drawImage(m.getPlaneImage()[m.getPlaneState()],
					m.getPlane()[0] - m.getCloudX() - m.getPlaneImage()[m.getPlaneState()].getWidth(this) / 2,
					m.getPlane()[1] - m.getPlaneImage()[m.getPlaneState()].getHeight(this) / 2, this);
		}

		// 雲
		g2.drawImage(m.getCloud(), -m.getCloudX() % m.getSky().getWidth(this), 0, this);
		g2.drawImage(m.getCloud(), -m.getCloudX() % m.getSky().getWidth(this) + m.getSky().getWidth(this) - 1, 0, this);

		// 目隠しエネミーの描画
		for (int i = 0; i < m.getBlindfoldAry().size(); i++) {
			// 各event関数を呼び出し
			m.getBlindfoldAry().get(i).event();
			// 範囲外に出ていれば配列から削除
			if (m.isOutOfRange(m.getBlindfoldAry().get(i))) {
				m.getBlindfoldAry().remove(i);
				i--;
				continue;
			}
			// 敵描画
			g2.drawImage(m.getBlindfoldAry().get(i).image, m.getBlindfoldAry().get(i).pos[0],
					m.getBlindfoldAry().get(i).pos[1], this);
		}

		// フォントのアンチエイリアス
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Font font = new Font("ＭＳ 明朝", Font.PLAIN, 32);
		g2.setFont(font);
		g2.drawString("距離：" + m.getDistance() / 10 + " m", 620, 390);

		if (!m.getTimer().isRunning()) {
			g2.setColor(Color.black);
			g2.drawString("[ENTER]で再スタート", 400, 350);
			g2.drawImage(m.getGameOver(), 0, 0, this);
		}

		g2.dispose();
	}

	public void ClearPhase(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// アンチエイリアス
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		// 背景
		g2.drawImage(m.getSky(), 0, 0, this);

		// 敵
		for (int i = 0; i < m.getEnemyAry().size(); i++) {
			// 各event関数を呼び出し
			m.getEnemyAry().get(i).event();
			// 範囲外に出ていれば配列から削除
			if (m.isOutOfRange(m.getEnemyAry().get(i))) {
				m.getEnemyAry().remove(i);
				i--;
				continue;
			}
			// 敵描画
			g2.drawImage(m.getEnemyAry().get(i).image,
					m.getEnemyAry().get(i).pos[0] - m.getEnemyAry().get(i).image.getWidth(m.getViewer()) / 2,
					m.getEnemyAry().get(i).pos[1] - m.getEnemyAry().get(i).image.getHeight(m.getViewer()) / 2, this);
		}

		m.ClearCalclate();

		// 当たり判定
		if (m.isHit() || m.getIsEnd()) {
			// ドラゴン
			g2.drawImage(m.getEnemyImage()[12], m.getPlane()[0] - m.getCloudX() - m.getDead().getWidth(this) / 2,
					m.getPlane()[1] - m.getDead().getHeight(this) / 2, this);
			if (m.getEnemyAry().size() != 1)
				m.getEnemyAry().remove(1);

			m.setIsEnd(true);
		} else {
			// 機体
			g2.drawImage(m.getPlaneImage()[m.getPlaneState()],
					m.getPlane()[0] - m.getCloudX() - m.getPlaneImage()[m.getPlaneState()].getWidth(this) / 2,
					m.getPlane()[1] - m.getPlaneImage()[m.getPlaneState()].getHeight(this) / 2, this);
		}

		// 雲
		g2.drawImage(m.getCloud(), -m.getCloudX() % m.getSky().getWidth(this), 0, this);
		g2.drawImage(m.getCloud(), -m.getCloudX() % m.getSky().getWidth(this) + m.getSky().getWidth(this) - 1, 0, this);
		if (m.getIsEnd()) {
			// フォントのアンチエイリアス
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Font font = new Font("ＭＳ 明朝", Font.PLAIN, 32);
			g2.setFont(font);
			g2.setColor(Color.black);
			g2.drawString("距離：" + m.getDistance() / 10 + " m", 620, 390);
			g2.drawString("[ENTER]でスタート画面へ", 400, 350);
			g2.drawImage(m.getGameClear(), 0, 0, this);
		}

		g2.dispose();
	}

	public void paintBuffer() {
		Graphics g = m.getBuffer().getGraphics();

		if (m.getIsStart())
			StartPhase(g);
		else if (!m.getIsClear())
			BattlePhase(g);
		else
			ClearPhase(g);

		g.dispose();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// バッファに描画
		paintBuffer();
		// g2 に描画
		g2.drawImage(m.getBuffer(), 0, 0, this);

		g2.dispose();
		g.dispose();
		requestFocusInWindow();
	}
}
