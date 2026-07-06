package vn.edu.vitacademy.model;

public class Customer {

  private String name;
  private String gender;
  private String dateOfBirth;
  private String address;
  private String city;
  private String state;
  private String pin;
  private String mobileNumber;
  private String email;
  private String password;

  public Customer(String name, String gender, String dateOfBirth, String address, String city,
      String state, String pin, String mobileNumber, String email, String password) {
    this.name = name;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.city = city;
    this.state = state;
    this.pin = pin;
    this.mobileNumber = mobileNumber;
    this.email = email;
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public String getGender() {
    return gender;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPin() {
    return pin;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public static final class Builder {
    private String name;
    private String gender;
    private String dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String mobileNumber;
    private String email;
    private String password;

    public Builder() {}

    public static Builder aCustomer() {
      return new Builder();
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withGender(String gender) {
      this.gender = gender;
      return this;
    }

    public Builder withDateOfBirth(String dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    public Builder withAddress(String address) {
      this.address = address;
      return this;
    }

    public Builder withCity(String city) {
      this.city = city;
      return this;
    }

    public Builder withState(String state) {
      this.state = state;
      return this;
    }

    public Builder withPin(String pin) {
      this.pin = pin;
      return this;
    }

    public Builder withMobileNumber(String mobileNumber) {
      this.mobileNumber = mobileNumber;
      return this;
    }

    public Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Customer build() {
      return new Customer(name, gender, dateOfBirth, address, city, state, pin, mobileNumber,
          email, password);
    }
  }
}