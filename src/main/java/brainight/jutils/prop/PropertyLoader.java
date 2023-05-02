package brainight.jutils.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class PropertyLoader {

    protected Properties properties;
    protected String propertyPath;

    public PropertyLoader(String propertyFile, boolean relativeToUserDir) {
        this.propertyPath = relativeToUserDir ? System.getProperty("user.dir") + File.separator + propertyFile : propertyFile;
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream in = new FileInputStream(this.propertyPath)) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Properties getProperties() {
        return this.properties;
    }

    public String getProperty(String property) {
        return this.properties.getProperty(property);
    }

    public int getIntProperty(String property, int defaultValue) {
        try {
            return Math.abs(Integer.parseInt(this.getProperty(property)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String property) {
        return this.getProperty(property).equals("true");
    }
}
