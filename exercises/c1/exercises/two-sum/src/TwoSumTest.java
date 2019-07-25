import org.junit.Assert;

public class TwoSumTest {
    @org.junit.Test
    public void twoSumTest1() throws Exception {
        Assert.assertTrue(TwoSum.twoSum(new int[]{1, 2, 3, 4}, 5));
    }

    @org.junit.Test
    public void twoSumTest2() throws Exception {
        Assert.assertFalse(TwoSum.twoSum(new int[]{1, 4, 5, 1, 6}, 12));
    }
}