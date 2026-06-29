package vn.edu.vitacademy.pages.guru;

import io.qameta.allure.Step;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.BasePage;

/**
 * Guru99 Banking manager home page shown after a successful login.
 * Exposes the left-menu navigation to each scenario form page.
 */
public class ManagerHomePage extends BasePage {

  public ManagerHomePage(WebUI webUI) {
    super(webUI);
    setRepoName(ManagerHomePage.class.getSimpleName());
  }

  @Step("Verify manager home page is loaded")
  public boolean isLoaded() {
    return webUI.verifyElementVisible(findTestObject("LBL_WELCOME"));
  }

  @Step("Open New Customer page")
  public NewCustomerPage openNewCustomer() {
    webUI.clickOn(findTestObject("LNK_NEW_CUSTOMER"), 10);
    return new NewCustomerPage(webUI);
  }

  @Step("Open Edit Customer page")
  public EditCustomerPage openEditCustomer() {
    webUI.clickOn(findTestObject("LNK_EDIT_CUSTOMER"), 10);
    return new EditCustomerPage(webUI);
  }

  @Step("Open Delete Customer page")
  public DeleteCustomerPage openDeleteCustomer() {
    webUI.clickOn(findTestObject("LNK_DELETE_CUSTOMER"), 10);
    return new DeleteCustomerPage(webUI);
  }

  @Step("Open New Account page")
  public NewAccountPage openNewAccount() {
    webUI.clickOn(findTestObject("LNK_NEW_ACCOUNT"), 10);
    return new NewAccountPage(webUI);
  }

  @Step("Open Edit Account page")
  public EditAccountPage openEditAccount() {
    webUI.clickOn(findTestObject("LNK_EDIT_ACCOUNT"), 10);
    return new EditAccountPage(webUI);
  }

  @Step("Open Delete Account page")
  public DeleteAccountPage openDeleteAccount() {
    webUI.clickOn(findTestObject("LNK_DELETE_ACCOUNT"), 10);
    return new DeleteAccountPage(webUI);
  }

  @Step("Open Deposit page")
  public DepositPage openDeposit() {
    webUI.clickOn(findTestObject("LNK_DEPOSIT"), 10);
    return new DepositPage(webUI);
  }

  @Step("Open Fund Transfer page")
  public FundTransferPage openFundTransfer() {
    webUI.clickOn(findTestObject("LNK_FUND_TRANSFER"), 10);
    return new FundTransferPage(webUI);
  }

  @Step("Open Balance Enquiry page")
  public BalanceEnquiryPage openBalanceEnquiry() {
    webUI.clickOn(findTestObject("LNK_BALANCE_ENQUIRY"), 10);
    return new BalanceEnquiryPage(webUI);
  }

  @Step("Open Mini Statement page")
  public MiniStatementPage openMiniStatement() {
    webUI.clickOn(findTestObject("LNK_MINI_STATEMENT"), 10);
    return new MiniStatementPage(webUI);
  }

  @Step("Open Customized Statement page")
  public CustomizedStatementPage openCustomizedStatement() {
    webUI.clickOn(findTestObject("LNK_CUSTOMIZED_STATEMENT"), 10);
    return new CustomizedStatementPage(webUI);
  }

  @Step("Open Change Password page")
  public ChangePasswordPage openChangePassword() {
    webUI.clickOn(findTestObject("LNK_CHANGE_PASSWORD"), 10);
    return new ChangePasswordPage(webUI);
  }
}
