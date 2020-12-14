import java.awt.image.BufferedImage;

public class Kamome extends Enemy {
	Model m;

	Kamome(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		pos[0] = Model.WIDTH;
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X + 2;
		pos[1] -= 2;
	}
}