import org.junit.Assert;

public class ReverseStringTest {
    @org.junit.Test
    public void reverseStringTest1() {
        Assert.assertEquals(ReverseString.reverseString("Hello World!"), "!dlroW olleH");
    }

}