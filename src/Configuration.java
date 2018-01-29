import java.io.FileInputStream;
import java.util.Properties;

public enum Configuration {

    instance;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String nameOfSubFolder = userDirectory + fileSeparator + "exchange_component_" + getAverageType() + fileSeparator + "jar";
    public String nameOfJavaArchive = "component.jar";
    public String subFolderPathOfJavaArchive = nameOfSubFolder + fileSeparator + nameOfJavaArchive;
    public String fullPathToJavaArchive = userDirectory + subFolderPathOfJavaArchive;
    public String nameOfClass = "Component";

    public Averages getAverageType() {
        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(userDirectory + fileSeparator + "average.props");
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("component").equals("mean"))
                return Averages.mean;
            else if (properties.getProperty("component").equals("mode"))
                return Averages.mode;
            else
                return  Averages.mean;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void print() {
        System.out.println("--- Configuration");
        System.out.println("fileSeparator              : " + fileSeparator);
        System.out.println("userDirectory              : " + userDirectory);
        System.out.println("nameOfSubFolder            : " + nameOfSubFolder);
        System.out.println("nameOfJavaArchive          : " + nameOfJavaArchive);
        System.out.println("subFolderPathOfJavaArchive : " + subFolderPathOfJavaArchive);
        System.out.println("fullPathToJavaArchive      : " + fullPathToJavaArchive);
        System.out.println("nameOfClass                : " + nameOfClass);
        System.out.println();
    }

}
