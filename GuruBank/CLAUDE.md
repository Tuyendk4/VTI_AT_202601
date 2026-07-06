# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository layout

This directory (`GuruBank`) is a Maven module inside a larger git repo whose root is one
directory up (`VTI_AT_202601/`). `git` commands run from here operate on that parent repo, and
`git status`/`git diff` will show sibling modules (e.g. `../UiFramework`, `../JavaCore`,
`../MavenDemo`) as part of the same working tree. Only touch files under `GuruBank/` unless a
task explicitly calls for a sibling module.

`GuruBank` is a Selenium WebDriver + TestNG UI test automation project that exercises the
[Guru99 Bank demo site](https://demo.guru99.com/V4/index.php) (manager and new-customer flows).
It's a training/exercise project built incrementally (see recent commit history: page factory ->
data-driven with JSON/Excel -> Selenium Grid -> Cucumber), so patterns are still evolving and not
all pages/tests follow the newest convention yet.

## Common commands

Build/compile:
```
mvn compile
```

Run a test suite (the TestNG suite file is selected via the `-Dtestsuite` property, resolved
relative to `src/main/resources/testsuites/`; see the `maven-surefire-plugin` config in `pom.xml`):
```
mvn test -Dtestsuite=ManagerTestSuite.xml
mvn test -Dtestsuite=NewCustomerTestSuite.xml
```
There is no default value for `testsuite`, so it must always be passed explicitly.

Chrome is currently launched via a plain `new ChromeDriver()` in `WebUI.openBrowser` (WebDriverManager
auto-setup calls are commented out), so a matching chromedriver must already be resolvable on PATH
by Selenium Manager.

## Architecture

**Page Object Model with an external, JSON-based object repository** (Katalon-style, not
`@FindBy`/PageFactory):
- Every page class extends `BasePage` (`src/main/java/vn/edu/vitacademy/pages/BasePage.java`) and
  calls `setRepoName(ThisPage.class.getSimpleName())` in its constructor.
- `findTestObject("SOME_KEY")` looks up the locator string for that key from
  `src/main/resources/object_repository/<PageClassName>.json` at runtime (via `JsonPath`), rather
  than the locator being hardcoded in Java. To add a new element to a page, add the key/locator
  pair to the matching JSON file, not to the Java class.
- Reusable widgets that appear across pages (e.g. `LeftMenu`, `AddEmployeePopup`) live in
  `pages/components/` and have their own object-repository JSON file of the same name.
- Page methods return the next page object, chaining navigation
  (e.g. `LoginPage.loginWith(...)` returns `ManagerPage`; `LeftMenu.moveToNewCustomer()` returns
  `NewCustomerPage`), so tests read as a fluent chain of page transitions.

**All low-level Selenium interaction is centralized in `WebUI`**
(`src/main/java/vn/edu/vitacademy/common/keywords/WebUI.java`). Page objects never touch
`WebDriver` directly — they call `webUI.click(locator)`, `webUI.inputText(locator, text)`, etc.
- Locators are plain strings with a `prefix:value` convention (`id:`, `name:`, `css:`, `xpath:`,
  `class:`, `link_text:`, `partial_link_text:`, `tag:`); no prefix falls back to being treated as
  an XPath expression (`WebUI.findBy`).
- Every keyword method swallows exceptions internally and logs via SLF4J instead of throwing, so
  a failed action returns `null`/`false`/silently no-ops rather than failing fast. When adding
  assertions in tests, check the boolean/`null` return rather than expecting an exception.
- Many actions also call `webUI.takeScreenshotAndMarkElement(...)` for Allure evidence — keep this
  pattern when adding new page actions that should be visible in the Allure report.

**Test lifecycle lives in `BaseTest`** (`src/main/java/vn/edu/vitacademy/tests/BaseTest.java`),
which all TestNG test classes extend:
- `@BeforeTest` opens the browser (`browser`/`url`/`email` TestNG parameters, supplied by the
  suite XML/YAML files under `resources/testsuites/`) and ensures a valid logged-in-capable user
  exists. It reads `resources/data/RegisteredUser.json`; if the stored user is missing or older
  than 20 days it drives the "register new online account" flow on the demo site to mint a fresh
  one and persists it back to that JSON file via `FileHelper`. Don't hardcode credentials in new
  tests — use `this.registeredUser` / `this.loginPage` inherited from `BaseTest`.
- `findTestData("key")` reads ad-hoc JSON test data from `src/main/resources/data/<dataSource>.json`
  (set via `setDataSource(...)`), mirroring `BasePage.findTestObject` for the object repository.
- Allure results are written to `target/allure-results` and the `@BeforeSuite` hook wipes that
  folder at the start of each run.

**Logging**: Log4j 1.x config at `src/main/resources/log4j.xml` writes to `testlog/test.log`
(rolling, overwritten each run) and console; every helper/keyword class logs both success and
failure at INFO/ERROR.

**Data-driven support**: `common/helper/ExcelHelper.java` (Apache POI) and
`common/helper/FileHelper.java`/`DateTimeHelper.java` back the JSON/Excel data-driven test data
files under `src/main/resources/data/`.

**Cucumber**: `cucumber-java`/`cucumber-testng`/`cucumber-picocontainer` and
`allure-cucumber7-jvm` are on the classpath (this module is mid-migration to Cucumber per the
`20260629_introduce_cucumber` branch name), but the actual `.feature` files, step definitions, and
Cucumber runner currently live in the sibling `../UiFramework` module, not in `GuruBank`.