package Models;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertiesModel {
    private static String filepath;
    private static Properties properties;

    public PropertiesModel() {
        filepath = properties.getProperty("MedizinFile");
    }

    public static void init(String propertiesFile) throws Exception {
        properties = new Properties();
        properties.loadFromXML(new FileInputStream(propertiesFile));
    }

    public String getFilepath() {
        return filepath;
    }
}
