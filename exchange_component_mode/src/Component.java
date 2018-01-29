import java.lang.reflect.Method;
import java.util.HashMap;

public class Component {

    private static Component instance = new Component();
    public Port port;

    private Component(){

        port = new Port();
    }

    public static Component getInstance() {
        return instance;
    }

    public class Port implements IComponent {

        private Method[] methods = getClass().getMethods();

        @Override
        public String getVersion() {
            return innerMethodVersion();
        }

        @Override
        public int getValue(int[] numbers) {
            return innerMethodMode(numbers);
        }
    }

    private String innerMethodVersion() {

        return "v.1.0";
    }


    // Link to Source Code: https://stackoverflow.com/questions/15725370/write-a-mode-method-in-java-to-find-the-most-frequently-occurring-element-in-an
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