# Automation Test Cases

| ID | Type | Test Case | Expected Result |
|---|---|---|---|
| WEB-01 | Web | Login with WorkDo company demo | User successfully leaves the login page |
| WEB-02 | Web | Open Login page | Login page URL is displayed |
| WEB-03 | Web | Open Pricing page | Pricing page URL is displayed |
| WEB-04 | Web | Verify Home page title | Page title is not empty |
| WEB-05 | Web | Verify Email field | Email field is displayed |
| WEB-06 | Web | Verify Password field | Password field is displayed |
| WEB-07 | Web | Verify Login button | Login button is displayed |
| WEB-08 | Web | Create Purchase Transfer | Transfer form is completed and submitted successfully |
| API-01 | API | Get all bookings | HTTP status code is 200 |
| API-02 | API | Create booking | Booking is created successfully |
| API-03 | API | Get booking by ID | Created booking can be retrieved |
| API-04 | API | Update booking | Booking information is updated successfully |
| API-05 | API | Delete booking | Booking is deleted and GET returns 404 |
| API-06 | API | Get non-existing booking | API returns HTTP 404 |
| API-07 | API | Delete non-existing booking | API returns expected error status |

## Total

- Web Test Cases: 8
- API Test Cases: 7
- Total Test Cases: 15