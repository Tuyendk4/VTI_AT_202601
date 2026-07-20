package vn.edu.vtiacademy.common.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vtiacademy.common.exceptions.UnableToLoadPropertiesException;

public class PropertyHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyHelper.class.getSimpleName());
  // Use a static final field initialized at class load time for a thread-safe singleton.
  private static PropertyHelper INSTANCE = null;
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final Properties props = new Properties();
  private static String propertyName;

  private PropertyHelper() {
    this.loadProperties("configuration.properties");
    props.putAll(System.getProperties());
  }

  // This constructor allows creating separate instances, which might be useful for testing.
  protected PropertyHelper(String propertyName) {
    PropertyHelper.propertyName = propertyName;
    this.loadProperties(propertyName + PROPERTIES_EXTENSION);
    props.putAll(System.getProperties());
  }

  private static PropertyHelper getInstance() {
//    if (INSTANCE == null) {
      INSTANCE = new PropertyHelper(propertyName);
//    }
    return PropertyHelper.INSTANCE;
  }
  /**
   * This method can read Property value for any given key
   *
   * @param key
   * @return
   */
  public static String getProperty(final String key) {
    return
        PropertyHelper.getInstance().props.getProperty(key);
  }
  /**
   * This method will read any integer property value
   *
   * @param key
   * @param defaultValue
   * @return
   */
  public static int getIntegerProperty(final String key,
      final int defaultValue) {
    int integerValue = 0;
    final String value = getProperty(key);
    if (value == null || value.isEmpty()) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      LOGGER.warn("Could not parse integer for key '{}', value was '{}'. Returning default value {}.", key, value, defaultValue, e);
      return defaultValue;
    }
  }
  /**
   * If key couldn't be found then it will return default
   value
   *
   * @param key
   * @param defaultValue
   * @return
   */
  public static String getProperty(final String key, final
  String defaultValue) {
    return
        PropertyHelper.getInstance().props.getProperty(key,
            defaultValue);
  }
  /**
   * This method will load properties file in Properties
   object
   *
   * @param path
   */
  public void loadProperties(final String path) {
    LOGGER.info("Attempting to load properties file from classpath: {}", path);
    // Use try-with-resources for automatic stream closing.
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(path)) {
      if (inputStream != null) {
        this.props.load(inputStream);
      } else {
        // Throwing an exception here is better than just printing a stack trace.
        throw new UnableToLoadPropertiesException("Property file '" + path + "' not found in the classpath.");
      }
    } catch (final IOException e) {
      // Wrap the IOException in your custom runtime exception.
      throw new UnableToLoadPropertiesException("Failed to load properties file '" + path + "'.", e);
    }
  }
  /**
   * @return Properties
   */
  public static Properties getProps() {
    return PropertyHelper.getInstance().props;
  }
}
