package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * Singleton class. There should always be only one Property instance available because it
 * does not make sense to have multiple readers on the same file.
 * 
 * @author michel
 *
 */
public class Property {

  private static Property instance = null;
  private Properties prop = null;

  /**
   * Private constructor Loads the properties from the config.properties file.
   */
  private Property() {
    InputStream input = null;
    try {
      prop = new Properties();
      input = new FileInputStream("src/config/config.properties");

      prop.load(input);
    } catch (IOException e) {
      System.err.println("Could not open the config file");
    } finally {
      closeInput(input);
    }
  }

  /**
   * Create a new Property or retrieve the existing one.
   * 
   * @return an instance of the LearnerProperty class
   */
  public static synchronized Property getInstance() {
    if (instance == null) {
      instance = new Property();
    }
    return instance;
  }

  /**
   * Retrieve the corresponding value from the properties file
   * 
   * @param key
   *          retrieve the data that is saved under the given key.
   * @return the value of the given key, null if not found
   */
  public String get(String key) {
    return prop.getProperty(key);
  }

  /**
   * Retrieve the corresponding value from the properties file and tries to parse the result as an
   * int.
   * 
   * @param key
   *          retrieve the data that is saved under the given key.
   * @return the value of the given key, -1 if not found or if not an integer.
   */
  public int getInt(String key) {
    try {
      return Integer.parseInt(prop.getProperty(key));
    } catch (NumberFormatException nfe) {
      return -1;
    }
  }

  /**
   * Closes the FileInputStream reader.
   * 
   * @param input
   *          A file-reader.
   */
  private void closeInput(InputStream input) {
    if (input != null) {
      try {
        input.close();
      } catch (IOException e) {
        System.err.println("Could not close the config file");
      }
    }
  }
}
