import java.awt.image.BufferedImage;

public class Balloon extends Enemy {
	Model m;
	boolean upFlag;

	Balloon(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		upFlag = false;
		pos[0] = Model.WIDTH;
		pos[1] = Model.HEIGHT - image.getHeight(m.getViewer());
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X;
		if ((!upFlag) && (Math.random() < 0.01))
			upFlag = true;

		if (upFlag)
			pos[1] -= Model.SPEED_X + 2;

	}
}