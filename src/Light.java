import java.awt.image.BufferedImage;

public class Light extends Enemy {
	Model m;

	Light(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		pos[0] = initpos[0];
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X + 1;
	}
}