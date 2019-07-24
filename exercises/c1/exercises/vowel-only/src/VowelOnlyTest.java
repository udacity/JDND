import org.junit.Assert;

public class VowelOnlyTest {
    @org.junit.Test
    public void vowelOnlyTest1() throws Exception {
        String input = "Hello World!";
        String output = VowelOnly.vowelOnly(input);
        String expected = "eoo";
        Assert.assertEquals(expected, output);
    }

    @org.junit.Test
    public void vowelOnlyTest2() throws Exception {
        String input = "Udacity Courses";
        String output = VowelOnly.vowelOnly(input);
        String expected = "Uaioue";
        Assert.assertEquals(expected, output);
    }
}