package vn.edu.vtiacademy.collections.array_list;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashMapDemo {
  private static final Logger LOGGER = LoggerFactory.getLogger(HashMapDemo.class);

  public static void main(String[] args) {
    HashMap<String, String> capitalCities = new HashMap<>();

    // Add keys and values (Country, City)
    capitalCities.put("England", "London");
    capitalCities.put("Germany", "Berlin");
    capitalCities.put("Norway", "Oslo");
    capitalCities.put("USA", "Washington DC");
    addItem(capitalCities, "France", "Paris");
    System.out.println(capitalCities);

    // Access an item
    System.out.println("Capital of England: " + capitalCities.get("England"));
    System.out.println("Capital of Germany: " + getValue(capitalCities, "Germany"));

    // Remove an item
    capitalCities.remove("England");
    System.out.println("After removal: " + capitalCities);
    removeItem(capitalCities, "USA");
    System.out.println("After removal: " + capitalCities);
    // Print keys
    System.out.println("Countries:");
    for (String i : capitalCities.keySet()) {
      System.out.println(i);
    }

    // Print values
    System.out.println("Cities:");
    for (String i : capitalCities.values()) {
      System.out.println(i);
    }

    // Print keys and values
    System.out.println("Details:");
    for (String i : capitalCities.keySet()) {
      System.out.println("key: " + i + " value: " + capitalCities.get(i));
    }

    // Size
    System.out.println("Size: " + capitalCities.size());

    // Clear
//    capitalCities.clear();
    deleteAllItems(capitalCities);
    System.out.println("After clear: " + capitalCities);
  }

  /*
  * description: add item to hash map
  *
  * @params map: HashMap<String, String> - the map to add item to
  * @params key: String - the key of the item
  * @params value: String - the value of the item
  * @return: void - do nothing
  * */
  public static void addItem(HashMap<String, String> map, String key, String value) {
    LOGGER.debug("Adding item with key: {} and value: {}", key, value);
    map.put(key, value);
    LOGGER.info("Added item successfully");
  }

  public static String getValue(HashMap<String, String> map, String key) {
    LOGGER.debug("Getting value of key: {}", key);
    if (!map.containsKey(key)) {
      LOGGER.error("Key {} not found", key);
      return null;
    }
    String value = map.get(key);
    LOGGER.info("Value of key '{}' is '{}'", key, value);
    return value;
  }

  public static String getKey(HashMap<String, String> map, String value) {
    for (String key : map.keySet()) {
      if (map.get(key).equals(value)) {
        return key;
      }
    }
    return null;
  }

  public static void removeItem(HashMap<String, String> map, String key) {
    map.remove(key);
  }

  public static void deleteAllItems(HashMap<String, String> map) {
    map.clear();
  }

  public static void showAllKeys(HashMap<String, String> map) {
    for (String key : map.keySet()) {
      System.out.println(key);
    }
  }

  public static void showAllValues(HashMap<String, String> map) {
    for (String value : map.values()) {
      System.out.println(value);
    }
  }
}
