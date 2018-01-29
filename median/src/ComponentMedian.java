import java.lang.reflect.Method;
import java.util.Arrays;

public class ComponentMedian {

    private static ComponentMedian instance = new ComponentMedian();
    public Port port;

    private ComponentMedian() {
        port = new Port();
    }

    public static ComponentMedian getInstance(){
        return instance;
    }

    public class Port implements IComponentMedian {

        private Method[] methods = getClass().getMethods();

        public String getVersion(){
            return innerMethodGetVersion();
        }
        public int getMean(int[] list){
            return innerMethodGetMean(list);
        }

    }

    public String innerMethodGetVersion(){
        return "mean - v.1.0";
    }

    public int innerMethodGetMean(int[] list){
        int median;
        Arrays.sort(list);
        if (list.length % 2 == 0)
            median = (list[list.length/2] + list[list.length/2 - 1])/2;
        else
            median = list[list.length/2];
        return median;
    }
}