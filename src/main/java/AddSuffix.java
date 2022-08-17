import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.Properties;
import java.util.logging.*;

public class AddSuffix {
    private static final Logger logger = Logger.getLogger("AddSuffix");
    static Path configPath;
    static String mode;
    static String suffix;
    static Properties prop = null;
    static String currentFilePath;
    static String endFilePath;
    static String[] filesArray;

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
        getFilesPath();
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

    public static void getFilesPath() {
        try {
            filesArray = prop.getProperty("files").split(":");
            for (String filePath: filesArray) {
                currentFilePath = filePath;
                changeFile();
            }
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "No files are configured to be copied/moved");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeFile() throws IOException {
        boolean exists = checkFileExists();
        if (exists) {
            getEndFilePath();
            if (mode.toLowerCase().equals("copy")) {
                copyFile();
            } else {
                moveFile();
            }
        }
    }

    public static boolean checkFileExists() {
        File f = new File(currentFilePath);
        if (!f.exists()) {
            String filePath = f.toString();
            String filePathWithForwardSlashes = filePath.replace("\\", "/");
            logger.log(Level.SEVERE, "No such file: " + filePathWithForwardSlashes);
            return false;
        } else {
            return true;
        }
    }

    public static void getEndFilePath() {
        String[] pathArray = currentFilePath.split("/");
        String currentFileName = pathArray[pathArray.length - 1];

        String[] fileNameArray = currentFileName.split("\\.");
        String fileExtension = fileNameArray[fileNameArray.length - 1];

        String newFileName = fileNameArray[0] + suffix + "." + fileExtension;
        pathArray[pathArray.length - 1] = newFileName;

        endFilePath = String.join("/", pathArray);
    }
    public static void copyFile() throws IOException {
        File src = new File(currentFilePath);
        File dest = new File(endFilePath);
        Files.copy(src.toPath(), dest.toPath());
        logger.log(Level.INFO, currentFilePath + " -> " + endFilePath);
    }

    public static void moveFile() {

    }
}
