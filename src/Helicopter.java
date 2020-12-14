import java.awt.image.BufferedImage;

public class Helicopter extends Enemy {
	Model m;

	Helicopter(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		pos[0] = Model.WIDTH;
	}

	@Override
	void event() {
		pos[0] -= Model.SPEED_X + 3;
		if ((pos[0] + image.getWidth(m.getViewer()) < 0)) {
			m.getBlindfoldAry().add(new SkyDiving(m, m.getEnemyImage()[9],
					new int[] { Model.WIDTH * 3 / 4, -(m.getEnemyImage()[9].getHeight(m.getViewer()) + 50) }));
		}
	}
}