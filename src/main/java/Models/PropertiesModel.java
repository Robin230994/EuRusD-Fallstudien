package Models;


import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesModel {
    private static String filepath;
    private static Properties properties;

    /**
     * constructor
     */
    public PropertiesModel() {
        filepath = properties.getProperty("MedizinFile");
    }

    /**
     * method that initializes our properties file
     * @param propertiesFile
     * @throws Exception
     */
    public static void init(String propertiesFile) throws Exception {
        properties = new Properties();
        properties.loadFromXML(new FileInputStream(propertiesFile));
    }

    /**
     * get method to get the file path
     * @return the filepath 
     */
    public String getFilepath() {
        return filepath;
    }
}
