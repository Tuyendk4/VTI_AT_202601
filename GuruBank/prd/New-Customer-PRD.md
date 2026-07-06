# PRD: New Customer Registration Screen (Guru99 Bank)

**Source document:** `doc/New Customer.xlsx` (sheet `SRS`)
**Feature area:** Manager Menu → New Customer
**Related code:** `pages/NewCustomerPage.java` (currently a stub), `pages/components/LeftMenu.java#moveToNewCustomer()`, `tests/NewCustomerTest.java`

## 1. Overview

This screen lets a bank manager register a new customer in Guru99 Bank. It is reached from the
left menu ("New Customer") after logging in as a manager. The manager fills in the customer's
personal details, and submitting the form creates the customer record and forwards the manager
to the Customer Info detail page.

## 2. Screen Components

| # | Field | I/O | Control Type | Alignment | Notes |
|---|-------|-----|---------------|-----------|-------|
| 1 | Add new customer | I | Label | Center | Title of the screen |
| 2 | Customer Name | I | Text box | Center | Only alphabet characters are shown/accepted |
| 3 | Error message | - | Label | Center | Hidden by default; shown after invalid input or on blur of a field |
| 4 | Gender | I | Radio button | Center | Two options only: Male, Female |
| 5 | Date of birth | I | Text box | Center | Placeholder `dd/MM/yyyy` |
| 6 | Address | I | Text area | Center | Alphabet, numbers and special characters allowed |
| 7 | City | I | Text box | Center | Alphabet only |
| 8 | State | I | Text box | Center | Alphabet only |
| 9 | Pin | I | Text box | Center | Numbers only |
| 10 | Mobile Number | I | Text box | Center | Numbers only |
| 11 | E-mail | I | Text box | Center | Must be a valid email format |
| 12 | Password | I | Text box | Center | Alphabet, numbers, special characters allowed |
| 13 | Submit | - | Button | Center | See [Button Behavior](#4-button-behavior) |
| 14 | Reset | - | Button | Center | See [Button Behavior](#4-button-behavior) |

## 3. Field Specifications

| Field | Data Type | Required | Default | Min | Max | Format / Placeholder |
|-------|-----------|:--------:|---------|:---:|:---:|-----------------------|
| Customer Name | String | — | Empty | — | — | Alphabet characters only |
| Gender | Boolean | ✓ | Male | — | — | Alphabet and numbers* |
| Date of birth | String | ✓ | Empty | — | — | `dd/MM/YYYY` |
| Address | String | ✓ | Empty | 1 | 50 | Alphabet and numbers |
| City | String | ✓ | Empty | 1 | 25 | Alphabet |
| State | String | ✓ | Empty | 1 | 25 | Alphabet |
| Pin | String | ✓ | Empty | 6 | 6 | Numbers only |
| Mobile Number | String | ✓ | Empty | 1 | 15 | Numbers only |
| E-mail | String | ✓ | Empty | 1 | 30 | Email |
| Password | String | ✓ | Empty | — | — | Alphabet, numbers, special characters |

\* The "Alphabet and numbers" format note on the Gender row is inherited from the source
spreadsheet's column layout and looks like a copy/paste artifact — a radio button has no free-text
format to constrain. Treat it as not applicable; confirm with the source if this needs
clarification.

## 4. Button Behavior

**Submit**
- Sends the request to create a new customer.
- Enabled at all times.
- If any field has a validation error, the request is **not** sent and the corresponding error
  message is shown instead.
- On success, shows a confirmation alert: *"Customer is created successfully."* Clicking **OK**
  on the alert navigates directly to the Customer Info detail page.

**Reset**
- Enabled at all times.
- Clears all fields back to empty.

## 5. Validation Rules & Error Messages

All error messages are displayed to the right of the corresponding text box.

| Field | Trigger condition | Error message |
|-------|--------------------|----------------|
| Customer Name | Empty | Customer name must not be blank |
| Customer Name | Input contains numbers | Numbers are not allowed |
| Customer Name | Input contains special characters | Special characters are not allowed |
| Customer Name | First character is a space | First character can not have space |
| Date of birth | Empty | Date of birth must not be blank |
| Address | Empty | Address must not be blank |
| City | Empty | City must not be blank |
| City | Input contains numbers | Numbers are not allowed |
| City | Input contains special characters | Special characters are not allowed |
| City | First character is a space | First character can not have space |
| State | Empty | State must not be blank |
| State | Input contains numbers | Numbers are not allowed |
| State | Input contains special characters | Special characters are not allowed |
| State | First character is a space | First character can not have space |
| Pin | Empty | Pin must not be blank |
| Pin | Input contains characters (letters) | Characters are not allowed |
| Pin | Input contains special characters | Special characters are not allowed |
| Pin | First character is a space | First character can not have space |
| Pin | Fewer than 6 digits | PIN Code must have 6 Digits |
| Mobile Number | Empty | Mobile number must not be blank |
| Mobile Number | Input contains characters (letters) | Characters are not allowed |
| Mobile Number | Input contains special characters | Special characters are not allowed |
| Mobile Number | First character is a space | First character can not have space |
| E-mail | Empty | Email must not be blank |
| E-mail | First character is a space | First character can not have space |
| E-mail | Invalid email format | Email-ID is not valid |
| Password | Empty | Password must not be blank |
| Password | First character is a space | First character can not have space |

## 6. Open Questions / Gaps in Source Document

These are inconsistencies or missing details found in `doc/New Customer.xlsx` that should be
confirmed with the requester before test cases/automation are finalized:

1. **Customer Name and Password are not marked "Required" (✓) in the field spec table (Section
   3), but both have a "must not be blank" validation rule in Section 5.** Treat both fields as
   required; flag the spec table as inconsistent.
2. **Password has no Min/Max length constraint and no length-related error message** — unclear if
   there's an intended minimum password length. Confirm before assuming "any non-empty value" is
   valid.
3. **Gender has no validation rule listed** (no error message if left unselected) — likely because
   a radio button always has a default (`Male`), so it can never be "empty." Confirmed
   assumption, not a gap requiring follow-up.
4. **"Nơi lấy data" (data source) column is empty for every field** — the source document does not
   specify where field values are persisted to/read from (e.g. no confirmation this maps 1:1 to
   Guru99 Bank's live `Manager Add Customer` form). Assumed to map directly to the existing
   Guru99 Bank demo site fields of the same name.
5. **Mobile Number and Pin "input characters" errors** say "Characters are not allowed" — should
   be confirmed whether this means letters specifically, or any non-digit input (which would
   overlap with the separate "special characters" rule).

## 7. Traceability to Automation

This PRD is the basis for filling in the currently-empty `NewCustomerPage.java` page object and
its `object_repository/NewCustomerPage.json` locator file, and for expanding
`tests/NewCustomerTest.java` beyond its current single "login and navigate" case to cover the
field validations and Submit/Reset behavior described above.