import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Properties;
import java.util.logging.*;

public class AddSuffix {
    private static final Logger logger = Logger.getLogger("AddSuffix");
    static Path configPath;
    static String mode;
    static String suffix;
    static Properties prop = null;
    static String currentFilePath;

    public static void main(String[] args) {
        configPath = Paths.get(args[0]);

        readFile();
        if (prop != null) {
            getFileProperties();
        }

    }

    public static void readFile() {
        try (InputStream input = new FileInputStream(String.valueOf(configPath))) {
            prop = new Properties();
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void getFileProperties() throws NullPointerException{
        getMode();
        getSuffix();
        getFilesNames();
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

    public static void getFilesNames() {
        try {
            String[] filesArray = prop.getProperty("files").split(":");
            for (String filePath: filesArray) {
                currentFilePath = filePath;
                changeFile();
            }
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "No files are configured to be copied/moved");
        }
    }

    public static void changeFile() {
        checkFileExists();
    }

    public static void checkFileExists() {
        File f = new File(currentFilePath);
        if (!f.exists()) {
            String filePath = f.toString();
            String filePathWithForwardSlashes = filePath.replace("\\", "/");
            logger.log(Level.SEVERE, "No such file: " + filePathWithForwardSlashes);
        }
    }
}
