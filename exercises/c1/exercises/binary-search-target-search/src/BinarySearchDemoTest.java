import org.junit.Assert;

public class BinarySearchDemoTest {
    @org.junit.Test
    public void findTargetTest1() throws Exception {
        int[] arr = new int[]{1, 5, 7, 8, 9, 11, 18, 19, 20, 25};
        int target = 11;
        int expect = 5;
        Assert.assertEquals(expect, BinarySearchDemo.findTarget(arr, target));
    }

    @org.junit.Test
    public void findTargetTest2() throws Exception {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        int target = 10;
        int expect = -1;
        Assert.assertEquals(expect, BinarySearchDemo.findTarget(arr, target));
    }

    @org.junit.Test
    public void findTargetTest3() throws Exception {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        int target = 1;
        int expect = 0;
        Assert.assertEquals(expect, BinarySearchDemo.findTarget(arr, target));
    }

    @org.junit.Test
    public void findTargetTest4() throws Exception {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        int target = 5;
        int expect = 4;
        Assert.assertEquals(expect, BinarySearchDemo.findTarget(arr, target));
    }
}