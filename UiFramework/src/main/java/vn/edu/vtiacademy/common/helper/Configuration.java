package vn.edu.vtiacademy.common.helper;

public class Configuration extends PropertyHelper {

  public Configuration(String propertyName) {
    super(propertyName);
  }

  public int getTimeout() {
    return PropertyHelper.getIntegerProperty("timeout", 30);
  }

  public String getNodePath() {
    return PropertyHelper.getProperty("nodePath");
  }

  public String getAppiumPath() {
    return PropertyHelper.getProperty("appiumPath");
  }
}
