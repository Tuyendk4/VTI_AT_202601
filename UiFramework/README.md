# Setup for mobile automation testing

## Install tools

1. Install JDK (11 - 26)
2. Set JAVA_HOME variable in Enviroment variables
3. Add Path variable: %JAVA_HOME%\bin (Windows), $JAVA_HOME\bin (Linux or MacOS)
4. Install Android Studio
5. Install Android SDK coresponding Android OS version on real device (or simulator)
6. Set ANDROID_SDK_HOME variable in Enviroment variables
7. Add Path variable: %ANDROID_SDK_HOME%\platform-tools (Windows), %ANDROID_SDK_HOME\platform-tools
8. Install Xcode (only MacOS)
9. Install Simulator on MacOS
10. Install Nodejs
11. Install appium: npm install -g appium
12. Install drivers:
    appium driver install uiautomator2 (//driver for Android)
    appium driver install xcuitest (//driver for iOS)
    appium driver ls (//list all drivers (installed and uninstalled))
13. Intall Appium Inspector

## Connect Appium Inspector to real device (simulator)

### Android

1. Run Appium Inspector
2. Create Capability:

```json
{
  "appium:platformName": "Android",
  "appium:automationName": "UiAutomator2",
  "appium:deviceName": "Redmi Note 9 Pro",
  "appium:udid": "80eabbae", // enter in cmd: adb devices
  "appium:app": "/VTI_AT_202601/UiFramework/apps/android.wdio.native.app.v2.2.0.apk"
}
```

3. Connect to the android device using cable (real device) or open simulator
4. Press Start Session on Appium Inspector

### iOS

1. Open WebDriverAgent project (/Users/<your_account>/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent)
2. Add icloud account to Xcode
3. Add the icloud account to Integration App, WebDriverAgentLib, WebDriverAgentRunner. Should rename bundleId of each application.
4. Connect to device by cable or open Simulator
5. Build applications to device: Integration App, WebDriverAgentLib, WebDriverAgentRunner
6. Create Capability:

```json

{
  "appium:platformName": "iOS",
  "appium:automationName": "XCUITest",
  "appium:deviceName": "iPhone X",
  "appium:bundleId": "com.apple.weather",
  "appium:platformVersion": "16.7.16",
  "appium:udid": "d7ad0223e16d29a3587a96cf0cc45e4cbd9b9994" // open Music app
}
```

7. Press Start Session on Appium Inspector
8. On real device, move to Settings - General - VPN and Devices Management, trust account
9. Press Start Session on Appium Inspector again
