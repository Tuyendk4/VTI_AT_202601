package vn.edu.vitacademy.model;

public class Employee {

  private String firstName;
  private String lastName;
  private String email;
  private String age;
  private String salary;
  private String department;

  public Employee(String firstName, String lastName, String email, String age, String salary, String department) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
    this.salary = salary;
    this.department = department;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getAge() {
    return age;
  }

  public String getSalary() {
    return salary;
  }

  public String getDepartment() {
    return department;
  }
}
