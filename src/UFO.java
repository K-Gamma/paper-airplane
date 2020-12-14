import java.awt.image.BufferedImage;

public class UFO extends Enemy {
	Model m;

	UFO(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		pos[0] = Model.WIDTH;
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X + 2;
		pos[1] += 5 * Math.sin((Model.HEIGHT - pos[0]) * Math.PI / 150);
	}
}