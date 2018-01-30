import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.Scanner;

public class Application {
    private Class clazz;
    private Object instance;
    private Object port;

    public void loadClazzFromJavaArchive() {
        try {
            Configuration conf = new Configuration();
            URL[] urls = {new File(conf.subFolderPathOfJavaArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Application.class.getClassLoader());
            clazz = Class.forName("Component", true, urlClassLoader);
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void provideInstanceOfClass() {
        try {
            instance = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void provideComponentPort() {
        try {
            port = clazz.getDeclaredField("port").get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int executeValueMethod(int[] arr) throws NoSuchMethodException {
        try {
            Method method = port.getClass().getMethod("getValue", int[].class);
            int result = (int) method.invoke(port, arr);
            return result;
        } catch (Exception e) {
            throw new NoSuchMethodException(e.getMessage());
        }
    }

    public String executeVersionMethod() {
        try {
            Method method = port.getClass().getMethod("getVersion");
            String result = (String) method.invoke(port);
            return result;
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }


    public int execute(int[] arr) throws Exception {
        try {
            loadClazzFromJavaArchive();
            provideInstanceOfClass();
            provideComponentPort();
            System.out.println();
            return executeValueMethod(arr);
        } catch (Exception e) {
            throw new Exception(String.valueOf(e.getStackTrace()));
        }
    }

    public static void main(String... args) throws Exception {
        Application application = new Application();
        application.reader();
    }

    private void reader() throws Exception {

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        if (command.equals("exit")) {
            System.out.println("Goodbye");
        } else if (command.equals("show components")) {
            showComponents();
            reader();
        } else if (command.equals("show current component")) {
            showCurrentComponent();
            reader();
        } else if (command.startsWith("set current component ")) {
            String[] parts = command.split(" ");
            if (parts[3].equals("median") || parts[3].equals("mode")) {
                loadComponent(parts[3]);
            }
            else {
                System.out.println("Average is not available. Please choose median or mode!");
            }
            reader();
        } else if (command.startsWith("execute ")) {
            String parts[] = command.split(" ");
            String arr[] = parts[1].split(",");
            int[] numbers = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                numbers[i] = Integer.parseInt(arr[i]);
            }
            System.out.println("result: " + execute(numbers));
            reader();
        } else {
            System.out.println("Unknown command.\nCommands: show components, show current components, set current component <component>, execute <searchVal> <array>");
            reader();
        }
    }

    public void loadComponent(String part) throws IOException {

        Properties prop = new Properties();
        Configuration conf = new Configuration();
        FileOutputStream fileOutputStream = new FileOutputStream(conf.userDirectory + conf.fileSeparator + "average.props");

        prop.setProperty("component", part);
        prop.store(fileOutputStream, null);
        System.out.println("Set current component " + part);
    }

    private void showCurrentComponent() {

        Properties properties = new Properties();
        Configuration conf = new Configuration();

        try {


            FileInputStream fileInputStream = new FileInputStream(conf.userDirectory + conf.fileSeparator + "average.props");
            properties.load(fileInputStream);
            fileInputStream.close();

            loadClazzFromJavaArchive();
            provideInstanceOfClass();
            provideComponentPort();
            System.out.println("Current component: " + executeVersionMethod());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void showComponents() {

        Configuration conf = new Configuration();
        System.out.println("Components: " + conf.nameOfJavaArchive + ", " + conf.nameOfJavaArchive);
    }


}