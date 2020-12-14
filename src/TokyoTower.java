import java.awt.image.BufferedImage;

public class TokyoTower extends Enemy {
	Model m;

	TokyoTower(Model m, BufferedImage image, int[] initpos) {
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