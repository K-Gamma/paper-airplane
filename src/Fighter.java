import java.awt.image.BufferedImage;

public class Fighter extends Enemy {
	Model m;

	Fighter(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		pos[0] = Model.WIDTH;
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X + 3;
		pos[1] += 1;
	}
}