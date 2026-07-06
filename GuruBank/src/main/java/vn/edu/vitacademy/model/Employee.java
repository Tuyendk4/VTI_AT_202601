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

  public static final class Builder {
    private String firstName;
    private String lastName;
    private String email;
    private String age;
    private String salary;
    private String department;

    public Builder() {}

    public static Builder aEmployee() {
      return new Builder();
    }

    public Builder withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder withLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder withAge(String age) {
      this.age = age;
      return this;
    }

    public Builder withSalary(String salary) {
      this.salary = salary;
      return this;
    }

    public Builder withDepartment(String department) {
      this.department = department;
      return this;
    }

    public Employee build() {
      return new Employee(firstName, lastName, email, age, salary, department);
    }
  }
}
