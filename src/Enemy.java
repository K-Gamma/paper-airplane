import java.awt.image.BufferedImage;

abstract public class Enemy { // 抽象クラス
	protected  BufferedImage image;
	protected int[] initpos;
	protected int[] pos;

	Enemy(BufferedImage image, int[] initpos) {
		this.image = image;
		this.initpos = initpos;
		this.pos = new int[] { 0, initpos[1] };
	};

	abstract void event();
}
