---
name: selenium-test-authoring
description: Use when writing or extending Selenium/TestNG page objects, models, or test classes in this repo (GuruBank) — covers the JSON object-repository POM pattern, the WebUI keyword wrapper, BaseTest lifecycle, fluent page-object methods, and data-driven negative-test style.
---

# Selenium/TestNG Test Authoring (GuruBank conventions)

This project is a Katalon-style Page Object Model on top of raw Selenium + TestNG. It is
deliberately plain and procedural (no lambdas/streams anywhere in the codebase) — match that
style rather than introducing more "modern" Java idioms.

## Page object conventions

- Every page class extends `BasePage` and calls `setRepoName(ThisPage.class.getSimpleName())`
  in its constructor.
- **Never hardcode a locator in Java.** Add the key/locator pair to
  `src/main/resources/object_repository/<PageClassName>.json` and read it with
  `findTestObject("KEY")`. If a widget is reused across pages (e.g. a left-hand menu, a popup),
  give it its own class under `pages/components/` with its own same-named JSON file.
- Page object methods are fluent — return `this` for same-page actions, or construct and return
  the next page object for navigation actions (e.g. `LoginPage.loginWith(...)` returns
  `ManagerPage`). This lets tests read as a chain: `loginPage.loginWith(...).isOnLeftMenu().moveToNewCustomer()`.
- Annotate public actions with `@Step("description with {0} placeholders")` (Allure reporting),
  and call `webUI.takeScreenshotAndMarkElement(locator)` after the action so the step has visual
  evidence in the report.
- Getters for displayed text/values (`getXErrorMessage()`, `getXValue()`) return `String` directly
  via `webUI.getText(...)` / `webUI.getAttributeValue(..., "value")` — don't wrap them in extra
  boolean assertions inside the page object; let the test class decide what to assert.

## The WebUI keyword wrapper — the only way to touch Selenium

`common/keywords/WebUI.java` centralizes every Selenium call. Page objects call
`webUI.click(...)`, `webUI.inputText(...)`, `webUI.clearText(...)`, `webUI.getText(...)`,
`webUI.waitForElementVisible(...)`, etc. — never `WebDriver`/`WebElement` directly, with one
narrow exception below.

Two behaviors to keep in mind when writing assertions:
- Every WebUI method swallows exceptions internally and logs via SLF4J instead of throwing.
  A failed action returns `null`/`false`/empty rather than raising — assert on the return value,
  don't wrap calls in try/catch expecting an exception.
- Locator strings use a `prefix:value` convention (`id:`, `name:`, `css:`, `xpath:`, `class:`,
  `link_text:`, `partial_link_text:`, `tag:`). No prefix (e.g. a bare `//...` string) falls back
  to being treated as XPath.

**Escape hatch:** for native browser widgets that don't respond reliably to `sendKeys` (see the
`<input type="date">` case below), it's acceptable for a page object to get the raw `WebElement`
via `webUI.findWebElement(locator)` and use `(JavascriptExecutor) webUI.getWebDriver()` directly.
Keep this contained inside the one page-object method that needs it — don't let raw driver calls
leak into test classes.

## Two keystroke tricks worth knowing

- **Triggering blur-based validation:** Selenium's `sendKeys` fires real keyboard events, so
  appending `Keys.TAB.toString()` to the text you type (`webUI.inputText(locator, value + Keys.TAB.toString())`)
  shifts focus away and fires the field's `onBlur` handler — no new WebUI keyword needed to
  simulate "the user tabbed to the next field."
- **Native `<input type="date">` fields:** typing digits via `sendKeys` depends on the browser's
  locale-specific keystroke segment order and is unreliable. Instead set the value directly:
  ```java
  WebElement dobField = webUI.findWebElement(findTestObject("TXT_DATE_OF_BIRTH"));
  JavascriptExecutor js = (JavascriptExecutor) webUI.getWebDriver();
  js.executeScript("arguments[0].value=arguments[1];", dobField, isoDate); // yyyy-MM-dd
  webUI.inputText(dobField, Keys.TAB.toString()); // still blur, so onBlur validation still runs
  ```
  Setting `.value` alone does not fire the page's own validation JS — always follow it with the
  same TAB-blur trick so field-level validation behaves identically to a real typed date.

## Test class conventions

- Test classes extend `BaseTest`, which opens the browser in `@BeforeTest` using the suite XML's
  `browser`/`url`/`email` `<parameter>` values, and exposes `protected loginPage` and
  `protected registeredUser` fields already populated — use them, don't re-implement login.
- One TestNG suite XML per test class under `src/main/resources/testsuites/`, referenced via
  `mvn test -Dtestsuite=<file>.xml` (no default value — it must always be passed).
- For a happy-path scenario, write one `@Test` per PRD "positive" case, using page-object method
  chains that end in an assertion on the resulting page object's state (e.g. success message,
  generated ID).
- **For field-level validation coverage, don't write one `@Test` per rule.** Use a single
  `@DataProvider`-fed `@Test(dataProvider = "...")` method with rows of
  `{fieldKey, inputValue, expectedErrorMessage}`, and a small `private` helper in the test class
  that does a `switch` on `fieldKey` to call the right page-object setter/getter pair. This
  mirrors `WebUI.findBy`'s own switch-based dispatch style and scales to dozens of rules without
  dozens of near-identical test methods. Reset the form (`clickReset()`) at the top of each
  iteration so rows don't interfere with each other.

## Before considering a page object "done"

1. Compiles (`mvn compile`) — necessary but **not sufficient**: a compiling test can still be
   silently blocked by client-side JS validation and never reach the page/assertion you think
   it does.
2. Actually run it: `mvn test -Dtestsuite=<YourSuite>.xml` against a real Chrome session, and
   read the failure diff carefully — "expected X but found Y" often means you're still on the
   previous page, not that the assertion text is wrong.
3. If a locator or expected message came from a spec/PRD rather than the live DOM, see the
   companion skill `xpath-locator-capture` before trusting it.