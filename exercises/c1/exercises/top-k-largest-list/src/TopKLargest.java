import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TopKLargest {

    public static List<Integer> topKLarger(int[] arr, int k) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int num :  arr) {
            treeSet.add(num);
            if (treeSet.size() > k) {
                treeSet.pollFirst();
            }
        }
        return treeSet.stream().collect(Collectors.toList());
    }
}
