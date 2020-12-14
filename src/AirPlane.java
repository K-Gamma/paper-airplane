import java.awt.image.BufferedImage;

public class AirPlane extends Enemy {
	Model m;

	AirPlane(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		pos[0] = Model.WIDTH;
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X + 3;
	}
}