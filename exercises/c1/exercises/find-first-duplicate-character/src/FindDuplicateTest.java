import org.junit.Assert;

public class FindDuplicateTest {
    @org.junit.Test
    public void findDuplicateTest1() throws Exception {
        Assert.assertEquals(FindDuplicate.findDuplicate("abcabc"), 3);
    }

    @org.junit.Test
    public void findDuplicateTest2() throws Exception {
        Assert.assertEquals(FindDuplicate.findDuplicate("abcde"), -1);
    }
}