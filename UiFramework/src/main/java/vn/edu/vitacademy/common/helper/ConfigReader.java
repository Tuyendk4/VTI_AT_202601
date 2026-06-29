package vn.edu.vitacademy.common.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads externalised configuration (e.g. login credentials) so secrets never
 * have to live in committed files.
 *
 * <p>Each value is resolved with the following precedence (first non-blank wins):
 * <ol>
 *   <li>Environment variable - intended for CI.</li>
 *   <li>JVM system property ({@code -Dkey=value}) - quick local override.</li>
 *   <li>{@code config.properties} on the classpath - gitignored, per-developer.</li>
 * </ol>
 * Copy {@code config.properties.example} to {@code config.properties} and fill in
 * your values before running locally.
 */
public final class ConfigReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class);
  private static final String CONFIG_FILE = "config.properties";
  private static final Properties PROPERTIES = loadProperties();

  private ConfigReader() {
  }

  private static Properties loadProperties() {
    Properties props = new Properties();
    try (InputStream in =
        ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
      if (in == null) {
        LOGGER.info("'{}' not found on classpath - relying on env vars / system properties",
            CONFIG_FILE);
      } else {
        props.load(in);
        LOGGER.info("Loaded configuration from '{}'", CONFIG_FILE);
      }
    } catch (IOException e) {
      LOGGER.error("Failed to read '{}'. Root cause: {}", CONFIG_FILE, e.getMessage());
    }
    return props;
  }

  /**
   * Resolves a configuration value using the documented precedence.
   *
   * @param propertyKey key looked up in system properties and {@code config.properties}
   * @param envVar      environment variable name checked first
   * @return the resolved value
   * @throws IllegalStateException if the value is found in none of the sources
   */
  public static String get(String propertyKey, String envVar) {
    String value = firstNonBlank(
        System.getenv(envVar),
        System.getProperty(propertyKey),
        PROPERTIES.getProperty(propertyKey));
    if (value == null) {
      throw new IllegalStateException(String.format(
          "Missing configuration for '%s'. Set env var '%s', pass -D%s=..., "
              + "or add it to %s (copy %s.example).",
          propertyKey, envVar, propertyKey, CONFIG_FILE, CONFIG_FILE));
    }
    return value;
  }

  /**
   * Same as {@link #get(String, String)} but uses {@code fallback} as the lowest
   * priority source (e.g. a value passed in from the TestNG suite XML) before
   * giving up.
   *
   * @param propertyKey key looked up in system properties and {@code config.properties}
   * @param envVar      environment variable name checked first
   * @param fallback    last-resort value, used only when no source provides one
   * @return the resolved value
   * @throws IllegalStateException if the value is found in none of the sources
   */
  public static String get(String propertyKey, String envVar, String fallback) {
    String value = firstNonBlank(
        System.getenv(envVar),
        System.getProperty(propertyKey),
        PROPERTIES.getProperty(propertyKey),
        fallback);
    if (value == null) {
      throw new IllegalStateException(String.format(
          "Missing configuration for '%s'. Set env var '%s', pass -D%s=..., "
              + "add it to %s (copy %s.example), or pass it as a suite parameter.",
          propertyKey, envVar, propertyKey, CONFIG_FILE, CONFIG_FILE));
    }
    return value;
  }

  private static String firstNonBlank(String... values) {
    for (String value : values) {
      if (value != null && !value.trim().isEmpty()) {
        return value.trim();
      }
    }
    return null;
  }
}
