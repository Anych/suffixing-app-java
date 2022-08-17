import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.*;

public class AddSuffix {
    private static final Logger logger = Logger.getLogger("AddSuffix");
    static String configPath;
    static String mode;
    static String suffix;
    static String[] files;
    static Properties prop = null;

    public static void main(String[] args) {
        configPath = args[0];

        readFile();
        if (prop != null) {
            getFileProperties();
        }
    }

    public static void readFile() {
        try (InputStream input = new FileInputStream(configPath)) {
            prop = new Properties();
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void getFileProperties() throws NullPointerException{
        getMode();
        getSuffix();
        getFilesPaths();
    }

    public static void getMode() {
        mode = prop.getProperty("mode");
        if (!mode.toLowerCase().equals("copy") && !mode.toLowerCase().equals("move")) {
            logger.log(Level.SEVERE, "Mode is not recognized: " + mode);
        }
    }

    public static void getSuffix() {
        suffix = prop.getProperty("suffix");
        if (suffix == null) {
            logger.log(Level.SEVERE, "No suffix is configured");
        }
    }

    public static void getFilesPaths() {
        try {
            files = prop.getProperty("files").split(":");
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "No files are configured to be copied/moved");
        }
    }
}
