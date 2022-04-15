import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OddComparer implements Comparator<Integer>{
    List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 29, 20, 14, 7));

    @Override
    public int compare(Integer o1, Integer o2) {
        if ((o1 % 2 == 0) == (o2 % 2 == 0)) {
            if (o1 > o2) {
                return 1;
            } else if (o1 < o2) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return (o1 % 2 == 0) ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        OddComparer oc = new OddComparer();
        oc.numbers.sort(oc);
        System.out.println(oc.numbers);
    }
}
