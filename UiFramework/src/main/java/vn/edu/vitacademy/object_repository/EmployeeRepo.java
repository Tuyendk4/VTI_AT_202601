package vn.edu.vitacademy.object_repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.pages.EmployeesPage;

public class EmployeeRepo {
  public static final String BTN_ADD = "//button[@id='addNewRecordButton']";
  public static final String BTN_EDIT = "//td[normalize-space()='${param}']/following-sibling::td//span[starts-with(@id,'edit-record')]";

  public static final String EMPLOYEE_TABLE_BTN_EDITS = "//span[starts-with(@id,'edit-record')]";
  public static final String EMPLOYEE_TABLE_BTN_ACTIONS = "//div[@class='action-buttons']";

  public static final String EMPLOYEE_TABLE_LBL_FIRST_NAMES = "//td[1]";
  public static final String EMPLOYEE_TABLE_LBL_LAST_NAMES = "//td[2]";
  public static final String EMPLOYEE_TABLE_LBL_AGES = "//td[3]";
  public static final String EMPLOYEE_TABLE_LBL_EMAILS = "//td[4]";
  public static final String EMPLOYEE_TABLE_LBL_SALARIES = "//td[5]";
  public static final String EMPLOYEE_TABLE_LBL_DEPARTMENTS = "//td[6]";
}
