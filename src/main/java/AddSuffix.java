import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class AddSuffix {
    static String configPath = "D:\\java\\suffixing-app\\src\\test\\resources\\sandbox\\config\\three-files-config.properties";
    static String mode;
    static String suffix;
    static String[] files;

    public static void main(String[] args) {
        try (InputStream input = new FileInputStream(configPath)) {

            Properties prop = new Properties();
            prop.load(input);

            getFilesProperties(prop);
            files = prop.getProperty("files").split(":");
            System.out.println(mode);
            System.out.println(suffix);
            System.out.println(Arrays.toString(files));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void getFilesProperties(Properties prop) {
        mode = prop.getProperty("mode");
        suffix = prop.getProperty("suffix");
        files = prop.getProperty("files").split(":");
    }

}
