# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A Java/Maven UI test automation framework (`vn.edu.vtiacademy`, package `UiFrameork`) built as a learning/teaching codebase. It contains **two parallel automation stacks that share common infrastructure**:

- **Web** — Selenium WebDriver, driven via TestNG (multiple experimental variants: plain POM, PageFactory, Excel-driven, Cucumber/BDD).
- **Mobile** — Appium (Android + iOS), driven via TestNG, supporting parallel execution across multiple devices/simulators.

Java 25, Maven, TestNG 7.10, Appium Java client 10.1, Selenium 4.43, Allure reporting, Cucumber 7.34.

## Build & test commands

```bash
mvn compile                 # compile
mvn test -Dtestsuite=<path> # run a TestNG suite — REQUIRED, see below
```

The Surefire plugin does not run a default suite — `pom.xml` resolves `${testsuite}` against `src/main/resources/testsuites/`, so `-Dtestsuite=...` must always be passed, e.g.:

```bash
mvn test -Dtestsuite=mobile/MobileLoginTestSuite.xml
mvn test -Dtestsuite=EmployeesTestSuite.xml
mvn test -Dtestsuite=EmployeesTestSuite_Cucumber.xml
```

**Running a single test/method**: because the suite is driven by an XML file (not `-Dtest=`), scope a run by editing the `<classes>`/`<methods>` block of the suite XML, e.g.:

```xml
<class name="vn.edu.vtiacademy.tests.web.EmployeesTest_POM">
  <methods>
    <include name="EM002_edit_email_successfully"/>
  </methods>
</class>
```

(`EmployeesTestSuite.xml` already does this as an example.)

Allure results go to `target/allure-results` (wiped at the start of each web suite by `BaseTest.beforeSuite`); app logs go to `testlog/test.log` (log4j, see `log4j.xml`); Appium server logs go to `testlog/appium-<udid>.log` (one file per device — see below).

### Mobile environment prerequisites (from README.md)

Running mobile suites for real requires local setup that Claude cannot verify or perform: JDK, Android Studio/SDK (`ANDROID_SDK_HOME`), Xcode + Simulator (macOS only), Node.js, `npm install -g appium`, and the `uiautomator2` / `xcuitest` Appium drivers. `configuration.properties` (`nodePath`, `appiumPath`) must point at the local Appium install. Don't assume a running emulator/simulator/device is available — flag when a change needs one to actually verify.

## Architecture

### Keyword-driven low-level layer

`common/keywords/WebUI.java` (Selenium) and `common/keywords/MobileUI.java` (Appium) are the only classes that touch the underlying driver. All page/screen objects call through these — never `WebDriver`/`AppiumDriver` directly. Locators are plain strings with a prefix parsed by `findBy`: `id:`, `name:`, `xpath:`, `class:` (defaults to xpath if no known prefix). `MobileUI` additionally owns Appium server lifecycle (`startAppiumServer`/`stopAppiumServer`) and gesture helpers (tap, doubleTap, swipe/scroll, drag-and-drop), and annotates screenshot methods with `@Attachment` so they land in the Allure report automatically.

### Object repository pattern

Locators are not hardcoded in Java — they live in JSON files read via `JsonPath` at runtime:
- Web: `src/main/resources/object_repository/*.json`, read through `BaseTest.findTestData` / `object_repository/EmployeeRepo.java`.
- Mobile: `src/main/resources/object_repository/mobile/{android,ios}/*.json`, read through `screens/BaseScreen.findTestObject`, which auto-selects the android/ios subfolder based on `mobileUI.isAndroid()`. This means **the same `LoginScreen`/`NavigationBar` Java class works for both platforms** — only the JSON differs.

### Property/configuration system

`common/helper/PropertyHelper` is the base property loader; `Configuration` (loads `configuration.properties`) and `Device` (loads `devices/<name>.properties`) extend it. It's implemented with `ThreadLocal<Properties>` specifically so that **parallel TestNG threads each hold their own device/config state** — do not change this back to plain static fields, it will break parallel mobile runs. `Device`'s static getters (`getDeviceName`, `getPlatformName`, `getUdid`, `getAppPath`, `getAppPackage`/`getAppActivity`, `getBundleId`) read whatever was last loaded on the *calling thread*.

### Device profiles & parallel mobile execution

`src/main/resources/devices/*.properties` define one Appium capability set per device (platform, deviceName, udid, and either `appPath` for an app file, or `appPackage`+`appActivity`/`bundleId` for an already-installed app). The TestNG suite's `<parameter name="deviceName">` per `<test>` selects which profile loads (`BaseMobileTest.beforeTest` → `new Device(deviceName)`).

`MobileLoginTestSuite.xml` uses `parallel="tests" thread-count="N"` so each `<test>` (i.e. each device) runs on its own thread with its own `MobileUI`/`AppiumDriverLocalService` instance on a dynamically chosen free port (`usingAnyFreePort()`) and its own Appium log file (`testlog/appium-<udid>.log`) — this per-device log file is required to avoid multiple parallel Appium services corrupting a single shared log. When adding a new device: create `devices/<name>.properties` and add a `<test>` block with `deviceName` in the mobile suite XML.

### Cucumber/BDD stack

`features/` (Gherkin) + `steps/` (`BaseSteps`, `Hooks`, `EmployeeSteps`) + `runner/CucumberTest.java` (`AbstractTestNGCucumberTests`). Note the runner's `glue` package (`vn.edu.vitacademy.steps`) is misspelled relative to the actual step package (`vn.edu.vtiacademy.steps`) — check this if Cucumber suite runs report no steps found.

### Multiple parallel web POM variants

`pages/` vs `pages_factory/` are two competing implementations of the same web page objects (plain POM vs Selenium `PageFactory`), each with matching `tests/web/BaseTest*` and `EmployeesTest*` variants (plain, PageFactory, Excel-driven via `ExcelHelper`/Apache POI, DataFaker-driven). These are intentionally kept side by side as teaching examples — don't merge/delete one in favor of the other unless asked.
