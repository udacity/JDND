import java.util.HashSet;
import java.util.Set;

public class FindDuplicate {

    public static int findDuplicate(String input) {
        Set<Character> seen = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            if (seen.contains(input.charAt(i))) {
                return i;
            } else {
                seen.add(input.charAt(i));
            }
        }
        return -1;
    }
}
