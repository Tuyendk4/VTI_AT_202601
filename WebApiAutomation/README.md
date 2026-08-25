# Web & API Automation Testing Project

## 1. Introduction

This project is an automation testing project developed for Web UI and REST API testing.

The project applies Selenium WebDriver, TestNG, Maven and REST Assured to automate functional test scenarios.

The Web Automation section focuses on the WorkDo Dash demo website, including login page validation, navigation and a Purchase Transfer business workflow.

The API Automation section tests the Restful Booker API with positive and negative scenarios.

---

## 2. Technologies

- Java
- Maven
- Selenium WebDriver
- TestNG
- REST Assured
- IntelliJ IDEA
- Google Chrome

---

## 3. Project Structure

```text
WebApiAutomation
│
├── pom.xml
├── testng.xml
├── README.md
├── .gitignore
│
├── docs
│   ├── TEST_CASES.md
│   └── TEST_REPORT.md
│
├── screenshots
│   └── full-suite-15-tests-passed.png
│
└── src
    ├── main
    │   └── java
    │       ├── base
    │       │   └── BaseWebTest.java
    │       │
    │       └── pages
    │           ├── LoginPage.java
    │           ├── HomePage.java
    │           ├── DemoLoginPage.java
    │           └── TransferPage.java
    │
    └── test
        └── java
            ├── base
            │   └── BaseApiTest.java
            │
            ├── web
            │   ├── LoginTest.java
            │   ├── NavigationTest.java
            │   ├── ElementValidationTest.java
            │   └── TransferWorkflowTest.java
            │
            └── api
                ├── BookingApiTest.java
                └── BookingNegativeApiTest.java
4. Web Automation

Website:

https://dash-demo.workdo.io

Main automated scenarios:

Demo login
Login page navigation
Pricing page navigation
Home page title validation
Email field validation
Password field validation
Login button validation
Purchase → Transfers → Create Transfer workflow

The Transfer workflow performs the following steps:

Login to WorkDo demo
Open Purchase menu
Open Transfers
Open Create Transfer form
Select From Warehouse
Select To Warehouse
Select Product
Enter Quantity
Create Transfer
5. API Automation

API:

https://restful-booker.herokuapp.com

Automated API scenarios:

GET all bookings
POST create booking
GET booking by ID
PUT update booking
DELETE booking
GET non-existing booking
DELETE non-existing booking
6. Test Execution

Run all tests using TestNG:

testng.xml

Or execute with Maven:

mvn test
7. Test Result

Total automated test cases:

Web Automation: 8
API Automation: 7
Total: 15

Final execution result:

Tests: 15
Passed: 15
Failed: 0
Skipped: 0

Evidence:

8. Design Pattern

The project applies the Page Object Model pattern.

Page classes contain locators and page actions, while test classes contain test scenarios and assertions.

Common WebDriver setup and teardown operations are handled by BaseWebTest.

Common API configuration is handled by BaseApiTest.
