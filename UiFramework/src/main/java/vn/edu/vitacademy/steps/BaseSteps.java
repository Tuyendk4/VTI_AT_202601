package vn.edu.vitacademy.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.EmployeesPage;

public class BaseSteps {

  protected final String EMPLOYEE_URL = "https://demoqa.com/webtables";
  protected static WebUI webUI;
  protected EmployeesPage employeesPage;
  protected static final Logger LOGGER = LoggerFactory.getLogger(BaseSteps.class);

}
