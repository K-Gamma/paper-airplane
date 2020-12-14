import java.util.Comparator;

public class EnemyComparator implements Comparator<Enemy> {

	@Override
	public int compare(Enemy e1, Enemy e2) {
		return e1.initpos[0] < e2.initpos[0] ? -1 : 1;
	}
}