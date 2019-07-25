import org.junit.Assert;


public class CalculatorTest {
    @org.junit.Test
    public void calculateTest1() throws Exception {
        Assert.assertEquals(Calculator.calculate("1+2"), 3);
    }

    @org.junit.Test
    public void calculateTest2() throws Exception {
        Assert.assertEquals(Calculator.calculate("1+2*5"), 11);
    }
}