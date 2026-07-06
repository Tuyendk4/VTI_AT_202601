@regression
Feature: CRUD Employee

  @smoke
  Scenario: Create a new employee 01
    Given I access to Employee page
    When I click Add button
    And I input "John" into First name text box
    And I input "Hope" into Last name text box
    And I input "johnhope@mailinator.com" into email text box
    And I input "38" into age text box
    And I input "2000"$ into salary text box
    And I input "IT" into department text box
    And I click Submit button
    Then I should see "John" at First name column in Employee table
    And I should see "Hope" at Last name column in Employee table

  @function
  Scenario Outline: Create a new employee 02
    Given I access to Employee page
    When I click Add button
    And I input "<first_name>" into First name text box
    And I input "<last_name>" into Last name text box
    And I input "<email>" into email text box
    And I input "<age>" into age text box
    And I input "<salary>"$ into salary text box
    And I input "<department>" into department text box
    And I click Submit button
    Then I should see "<first_name>" at First name column in Employee table
    And I should see "<last_name>" at Last name column in Employee table

    Examples:
      | first_name | last_name | email                   | age | salary | department |
      | John       | Hope      | johnhope@mailinator.com | 38  | 2500   | IT         |
      | Beni       | Tan       | benitan@mailinator.com  | 48  | 4000   | Sales      |

  @smoke
  Scenario Outline: Create a new employee 03
    Given I access to Employee page
    When I create a new employee with first name "<first_name>", last name "<last_name>", email "<email>", age "<age>", salary "<salary>", department "<department>"
    Then I should see "<first_name>" at First name column in Employee table
    And I should see "<last_name>" at Last name column in Employee table

    Examples:
      | first_name | last_name | email                   | age | salary | department |
      | John       | Hope      | johnhope@mailinator.com | 38  | 2500   | IT         |

  Scenario: Create a new employee 04
    Given I access to Employee page
    When I create a new employee with information as below
      | John | Hope | johnhope@mailinator.com | 38 | 2500 | IT |
    Then I should see "John" at First name column in Employee table
    And I should see "Hope" at Last name column in Employee table

  Scenario: Create a new employee 05
    Given I access to Employee page
    When I create a new employee with full information as below
      | first_name | last_name | email                   | age | salary | department |
      | John       | Hope      | johnhope@mailinator.com | 38  | 2500   | IT         |
    Then I should see "John" at First name column in Employee table
    And I should see "Hope" at Last name column in Employee table

  Scenario: Create a new employee 06
    Given Admin access to Employee page
    When He click Add button
    And He input "John" into First name text box
    And I input "Hope" into Last name text box
    And I input "johnhope@mailinator.com" into email text box
    And I input "38" into age text box
    And I input "2000"$ into salary text box
    And I input "IT" into department text box
    And I click Submit button
    Then I should see "John" at First name column in Employee table
    And I should see "Hope" at Last name column in Employee table