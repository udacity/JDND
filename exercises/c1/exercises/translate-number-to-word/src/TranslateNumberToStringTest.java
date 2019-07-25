import org.junit.Assert;

public class TranslateNumberToStringTest {
    @org.junit.Test
    public void translateNumberToWordTest1() throws Exception {
        Assert.assertEquals(TranslateNumberToString.translateNumberToWord(10245), "ten thousand two hundred forty five");
    }

    @org.junit.Test
    public void translateNumberToWordTest2() throws Exception {
        Assert.assertEquals(TranslateNumberToString.translateNumberToWord(125), "one hundred twenty five");
    }
}