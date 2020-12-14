import java.awt.image.BufferedImage;

public class StealthFighter extends Enemy {
	Model m;
	BufferedImage stealth;
	BufferedImage original;
	boolean sFlag;

	StealthFighter(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		sFlag = false;
		stealth = new BufferedImage(image.getWidth(m.getViewer()), image.getWidth(m.getViewer()),
				BufferedImage.TYPE_INT_ARGB);
		original = image;
		pos[0] = Model.WIDTH;
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X + 3;
		if (m.getDistance() % 30 == 0)
			sFlag = !sFlag;

		if (sFlag)
			image = original;
		else
			image = stealth;
	}
}