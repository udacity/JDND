import org.junit.Assert;

import java.util.Arrays;

public class TopKLargestTest {
    @org.junit.Test
    public void topKLargerTest1() {
        Assert.assertEquals(TopKLargest.
                topKLarger(new int[] {-1, 15, 59, 22, 6, 42, 45, 0}, 4),
                Arrays.asList(22, 42, 45, 59));
    }

    @org.junit.Test
    public void topKLargerTest2() {
        Assert.assertEquals(TopKLargest.
                        topKLarger(new int[] {5, 10, 15, 100, 8}, 2),
                Arrays.asList(15, 100));
    }
}