import java.util.HashSet;
import java.util.Set;

public class TwoSum {

    public static boolean twoSum(int[] nums, int target) {
        Set<Integer> twoSumSet = new HashSet<>();
        for (int num : nums) {
            if (twoSumSet.contains(num)) {
                return true;
            }
            twoSumSet.add(target - num);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
