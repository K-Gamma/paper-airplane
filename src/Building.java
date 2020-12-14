import java.awt.image.BufferedImage;

public class Building extends Enemy {
	Model m;

	Building(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		pos[0] = Model.WIDTH;
		pos[1] = Model.HEIGHT - image.getHeight(m.getViewer());
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X;
	}
}