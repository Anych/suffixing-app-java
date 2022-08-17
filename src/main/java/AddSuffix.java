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

    public static void getFilesProperties (Properties prop) throws NullPointerException{
        mode = prop.getProperty("mode");
        recognizeMode(mode);
        suffix = prop.getProperty("suffix");
        try {
            files = prop.getProperty("files").split(":");
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "No files are configured to be copied/moved");
        }
    }

    public static void recognizeMode(String mode) {
        if (!mode.toLowerCase().equals("copy") && !mode.toLowerCase().equals("move")) {
            logger.log(Level.SEVERE, "Mode is not recognized: " + mode);
        }
    }

}
