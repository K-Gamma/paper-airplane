import java.awt.image.BufferedImage;

public class Hato extends Enemy {
	Model m;

	Hato(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		pos[0] = Model.WIDTH;
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X + 2;
	}
}