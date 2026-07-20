package vn.edu.vtiacademy.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vtiacademy.common.keywords.WebUI;
import vn.edu.vtiacademy.pages.EmployeesPage;

public class BaseSteps {

  protected final String EMPLOYEE_URL = "https://demoqa.com/webtables";
  protected static WebUI webUI;
  protected EmployeesPage employeesPage;
  protected static final Logger LOGGER = LoggerFactory.getLogger(BaseSteps.class);

}
