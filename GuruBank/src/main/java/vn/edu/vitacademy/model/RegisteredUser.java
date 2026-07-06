package vn.edu.vitacademy.model;

import vn.edu.vitacademy.model.Employee.Builder;

public class RegisteredUser {
  private String userId;
  private String password;
  private String createdDate;

  public RegisteredUser(String userId, String password, String createdDate) {
    this.userId = userId;
    this.password = password;
    this.createdDate = createdDate;
  }

  public String getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public static final class Builder {
    private String userId;
    private String password;
    private String createdDate;
    public Builder() {}

    public static Builder aUser() {
      return new Builder();
    }

    public Builder withUserId(String userId) {
      this.userId = userId;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder withCreatedDate(String createdDate) {
      this.createdDate = createdDate;
      return this;
    }

    public RegisteredUser build() {
      return new RegisteredUser(userId, password, createdDate);
    }
  }
}
