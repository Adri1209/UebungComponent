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
            URL[] urls = {new File(Configuration.instance.subFolderPathOfJavaArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Application.class.getClassLoader());
            clazz = Class.forName("Component", true, urlClassLoader);;
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

    public void executeValueMethod(int[] arr) {
        try {
            Method method = port.getClass().getMethod("getValue", int[].class);
            int result = (int) method.invoke(port, arr);
            System.out.println("result   : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    public void executeVersionMethod(String component){
        try {
            Method method = port.getClass().getMethod("getVersion");
            String result = (String) method.invoke(port);
            System.out.println("show current component, " + result);
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }


    public void execute(int[] arr) {
        loadClazzFromJavaArchive();
        provideInstanceOfClass();
        provideComponentPort();
        System.out.println();
        executeValueMethod(arr);
    }

    public static void main(String... args) throws IOException {
        Application application = new Application();
        application.reader();
    }

    private void reader() throws IOException {

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
            loadComponent(parts[3]);
            reader();
        } else if (command.startsWith("execute ")) {
            String parts[] = command.split(" ");
            String arr[] = parts[1].split(",");
            int[] numbers = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                numbers[i] = Integer.parseInt(arr[i]);
            }
            execute(numbers);
            reader();
        } else {
            System.out.println("Unknown command.\nCommands: show components, show current components, set current component <component>, execute <searchVal> <array>");
            reader();
        }
    }

    private void loadComponent(String part) throws IOException {

        Properties prop = new Properties();

        FileOutputStream fileOutputStream = new FileOutputStream(Configuration.instance.userDirectory + Configuration.instance.fileSeparator + "average.props");

        prop.setProperty("component", part);
        prop.store(fileOutputStream, null);
        System.out.println("Set current component " + part);
    }

    private void showCurrentComponent() {

        Properties properties = new Properties();

        try {

            FileInputStream fileInputStream = new FileInputStream(Configuration.instance.userDirectory + Configuration.instance.fileSeparator + "average.props");
            properties.load(fileInputStream);
            fileInputStream.close();

            loadClazzFromJavaArchive();
            provideInstanceOfClass();
            provideComponentPort();
            executeVersionMethod(properties.getProperty("component"));
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void showComponents() {

        System.out.println("Components: " + Configuration.instance.nameOfJavaArchive + ", " + Configuration.instance.nameOfJavaArchive);
    }
}