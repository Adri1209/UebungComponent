import java.util.HashMap;
import java.lang.reflect.Method;

public class ComponentMode {

    private static ComponentMode instance = new ComponentMode();
    public Port port;

    private ComponentMode(){

        port = new Port();
    }

    public static ComponentMode getInstance() {
        return instance;
    }

    public class Port implements IComponentMode {

        @Override
        public String getVersion() {
            return innerMethodVersion();
        }

        @Override
        public int getMode(int[] numbers) {
            return innerMethodMode(numbers);
        }
    }

    private String innerMethodVersion() {

        return "Mode - v1.0";
    }

    private int innerMethodMode(int [] numbers) {

        HashMap<Integer,Integer> hm = new HashMap<>();
        int max  = 1;
        int temp = 0;

        for(int i = 0; i < numbers.length; i++) {

            if (hm.get(numbers[i]) != null) {

                int count = hm.get(numbers[i]);
                count++;
                hm.put(numbers[i], count);

                if(count > max) {
                    max  = count;
                    temp = numbers[i];
                }
            }

            else
                hm.put(numbers[i],1);
        }
        return temp;
    }
}