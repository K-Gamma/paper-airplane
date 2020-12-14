import java.awt.image.BufferedImage;

public class SkyDiving extends Enemy {
	Model m;

	SkyDiving(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
	}

	@Override
	void event() {
		pos[0] ++;
		pos[1] += 3;
	}
}