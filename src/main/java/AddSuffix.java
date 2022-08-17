import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.*;

public class AddSuffix {
    private static final Logger logger = Logger.getLogger("AddSuffix");
    static String configPath;
    static String mode;
    static String suffix;
    static String[] files;

    public static void main(String[] args) {
        configPath = args[0];

        Properties properties = readFile();
        if (properties != null) {
            getFilesProperties(properties);
        }
    }

    public static Properties readFile() {
        try (InputStream input = new FileInputStream(configPath)) {

            Properties prop = new Properties();
            prop.load(input);
            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void getFilesProperties(Properties prop) {
        mode = prop.getProperty("mode");
        recognizeMode(mode);
        suffix = prop.getProperty("suffix");
        files = prop.getProperty("files").split(":");
    }

    public static void recognizeMode(String mode) {
        if (!mode.toLowerCase().equals("copy") && !mode.toLowerCase().equals("move")) {
            logger.log(Level.SEVERE, "Mode is not recognized: " + mode);
            System.exit(0);
        }
    }

}
