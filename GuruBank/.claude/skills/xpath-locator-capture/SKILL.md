  ---
name: xpath-locator-capture
description: Use when you need to find, verify, or debug the correct XPath/locator and expected validation message for a web element before automating it — a curl+grep workflow to inspect the real DOM and client-side JS instead of guessing based on a spec, screenshot, or "what a similar site usually uses."
---

# Capturing real XPath locators (don't guess)

Guessed locators and paraphrased spec text are a common source of silently-broken automation:
a test can compile and even "look right" while asserting against text or elements that don't
exist on the real page. Verify against the live DOM every time a locator or expected message is
new, not just when something fails.

## Why this matters (real example from this repo)

While automating a "New Customer" form, a first pass over the spreadsheet-based PRD and a quick
`grep -n "<input"` over the fetched HTML concluded a Password field didn't exist on the real page.
It did — the grep just missed it because the real markup used `<INPUT TYPE="password">`
(uppercase) while every other field used lowercase `<input>`. A case-sensitive grep silently
dropped a real form field from the inventory. Two lessons: (1) always grep HTML case-insensitively,
(2) treat "the DOM doesn't have this" as a hypothesis to double check, not a conclusion.

The same investigation also found several PRD-documented error messages didn't match the live
site's actual JS-generated text (e.g. spec said "Address must not be blank", the real
`validateAddress()` function says `"Address Field must not be blank"`). Assertions were written
against the verified real strings, not the spec's paraphrase.

## Workflow: fetch and inventory the real DOM

```bash
curl -s -L "<page-url>" -o page.html
grep -in -E "input|textarea|select|button|<form" page.html
```

- **Always use `-i`.** HTML attribute/tag casing is inconsistent in hand-written markup
  (`<input>` vs `<INPUT>`), and a case-sensitive grep will silently skip real elements.
- Read the `name`/`id` attributes directly from the matched lines — these are what your XPath
  should target (`//input[@name='...']`), not attributes you assume based on the visible label
  text or a similar site you've seen before.
- If nothing useful appears, check for `<script src="...">` includes — some behavior (labels,
  error containers) can be injected by JS rather than present in the static HTML.

## Workflow: authenticated pages (carry a real session with curl)

Many pages require login. curl can drive the same session a browser would:

```bash
# 1. Inspect the login form first to get its real field names
curl -s -L "<login-page-url>" -o login.html
grep -in "form\|name=" login.html

# 2. Log in, keeping a cookie jar
curl -s -L -c cookies.txt -b cookies.txt -X POST "<login-form-action-url>" \
  --data "uid=<value>&password=<value>&btnLogin=Login" -o login_result.html

# 3. Reuse the same cookie jar for any subsequent page/form on that session
curl -s -L -c cookies.txt -b cookies.txt "<protected-page-url>" -o page.html
curl -s -c cookies.txt -b cookies.txt -X POST "<form-action-url>" --data "field=value&..." \
  -o result.html
```

This is how the real post-submit confirmation page (its exact heading text, generated ID field,
and "Continue" link) was discovered for the New Customer flow — it turned out to be a full page
redirect to a dedicated confirmation URL, not the JS `alert()` a spec document described.
Reuse test credentials that already exist in the project (e.g.
`src/main/resources/data/*.json`) rather than fabricating new ones.

## Workflow: read the linked JS for validation ground truth

Client-side validation logic is the authoritative source for both the error-label locator and the
exact expected message text — more reliable than a spec/PRD's paraphrase of it.

```bash
curl -s -L "<js-file-url>" -o app.js
grep -n -B2 -A20 "function validateXyz" app.js
```

Look for two things in each validation function:
- The element ID it writes into: `document.getElementById('messageN')` → your locator is
  `//label[@id='messageN']` (or whatever tag wraps it).
- The exact string assigned: `innerHTML="Some Text"` → this is the string to assert on, verbatim,
  not a rephrased version from a spec.

Also check whether the field's error state only updates `onKeyUp`/`onBlur` (common) — if so, your
automation must actually trigger a blur/keyup event (see `selenium-test-authoring`'s Keys.TAB
trick) or the label will stay in its default hidden state and you'll never observe the message.

## Common XPath patterns seen in this codebase's object_repository JSON

| Pattern | Use for |
|---|---|
| `//input[@name='x']` | Standard text/textarea/password input |
| `//input[@name='x' and @value='y']` | One option of a radio group sharing `name='x'` |
| `//label[@id='messageN']` | Per-field validation error label |
| `//td[.='Label Text']/following-sibling::td` | Read-only value cell next to a label cell |
| `//p[@class='heading3']` | Page/section title or confirmation heading |
| `//a[.='Link Text']` | A link matched by its exact visible text |

## Final check: prove it in a real browser

A locator that matches in curl'd static HTML can still fail in Chrome (JS-rendered content,
timing, iframes). After deriving locators, always confirm with an actual run —
`mvn test -Dtestsuite=<suite>.xml` — before considering the object-repository entry correct.
