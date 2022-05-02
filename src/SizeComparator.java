import java.util.Comparator;

/**
 * the abstract class of the comparator
 */
public class SizeComparator implements Comparator<Ball> {

    @Override
    public int compare(Ball o1, Ball o2) {
        double r1 = o1.getRadius();
        double r2 = o2.getRadius();
        if (r1 > r2) {
            return -1;
        } else if (r1 < r2) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "SizeComparator []";
    }
}