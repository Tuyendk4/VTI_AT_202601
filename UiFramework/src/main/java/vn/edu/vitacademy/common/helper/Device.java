package vn.edu.vitacademy.common.helper;

import java.io.File;

public class Device extends PropertyHelper {

  private static final String DEVICE_FOLDER_NAME = "devices";

  public Device(String propertyName) {
    super(DEVICE_FOLDER_NAME + File.separator + propertyName);
  }

  public static String getDeviceName() {
    return PropertyHelper.getProperty("deviceName");
  }

  public static String getPlatformName() {
    return PropertyHelper.getProperty("platform");
  }

  public static String getPlatformVersion() {
    return PropertyHelper.getProperty("platformVersion");
  }

  public static String getBundleId() {
    return PropertyHelper.getProperty("bundleId");
  }

  public static String getUdid() {
    return PropertyHelper.getProperty("udid");
  }

  public static String getAppPath() {
    return PropertyHelper.getProperty("appPath");
  }

  public static String getAppActivity() {
    return PropertyHelper.getProperty("appActivity");
  }

  public static String getAppPackage() {
    return PropertyHelper.getProperty("appPackage");
  }
}
