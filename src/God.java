import java.awt.image.BufferedImage;

public class God extends Enemy {
	Model m;
	boolean cflag;

	God(Model m, BufferedImage image, int[] initpos) {
		super(image, initpos);
		this.m = m;
		cflag = false;
		pos[0] = Model.WIDTH;
	}

	@Override
	void event() {
		if (!cflag) {
			pos[0] -= Model.SPEED_X + 2;
			if (Model.WIDTH * 3 / 4 > pos[0]) {
				cflag = true;
			}
		} else if (m.getEnemyAry().size() == 1) {
			m.setIsClear(true);
			if ((Model.WIDTH / 4 < m.getPlane()[0] - m.getCloudX()) && (Model.HEIGHT / 2 == m.getPlane()[1])
					&& !m.getIsEnd()) {
				m.getEnemyAry().add(new Light(m, m.getLight(),
						new int[] { pos[0] - m.getLight().getWidth() - image.getWidth() / 2, pos[1] }));
			}
		}
	}
}
