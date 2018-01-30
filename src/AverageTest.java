import org.junit.Assert;
import org.junit.Test;

public class AverageTest {

    @Test
    public void CheckMedianMethod() throws Exception {

        Application app = new Application();
        app.loadComponent("median");
        int [] numbers = new int[] {1,2,3,2,8,6,45};
        Assert.assertEquals(3,app.execute(numbers));
    }

    @Test
    public void CheckModeMethod() throws Exception {

        Application app = new Application();
        app.loadComponent("mode");
        int [] numbers = new int[] {1,2,3,2,8,6,45};
        Assert.assertEquals(2,app.execute(numbers));
    }
}
