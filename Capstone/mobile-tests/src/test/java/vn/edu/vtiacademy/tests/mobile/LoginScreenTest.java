package vn.edu.vtiacademy.tests.mobile;

import static org.testng.Assert.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vtiacademy.screens.LoginScreen;

/**
 * Kiem thu man hinh Login cua WDIO Demo App tren Android.
 *
 * <p><b>Thiet ke bo case:</b> ap dung Equivalence Partitioning + Boundary Value Analysis cho
 * hai o nhap:
 * <ul>
 *   <li>Email: lop hop le / lop khong dung dinh dang / lop rong</li>
 *   <li>Password: lop du dai (>= 8) / lop thieu ky tu (< 8, bien la 7) / lop rong</li>
 * </ul>
 *
 * <p><b>Vi sao dung {@code @DataProvider} cho nhom case loi:</b> nam case loi khac nhau
 * DUY NHAT o bo du lieu vao va thong bao mong doi - phan thao tac hoan toan giong nhau.
 * Viet nam ham gan nhu y het la nhan ban loi va nhan ba chi phi bao tri. Mot ham + mot bang
 * du lieu vua ngan hon, vua cho thay ro bo case dang phu nhung lop tuong duong nao.
 *
 * <p><b>Vi sao case dang nhap thanh cong tach rieng:</b> no khong kiem tra thong bao loi ma
 * kiem tra hop thoai he thong - khac han ve co che, nhet chung vao data provider se phai
 * cai them nhanh {@code if} trong test, lam test kho doc.
 *
 * <p>Chay: {@code mvn -pl mobile-tests test} (can emulator da boot va Appium cai san)
 */
@Epic("Capstone - Mobile Layer")
@Feature("WDIO Demo App - Man hinh Login")
public class LoginScreenTest extends BaseMobileTest {

  /** Thong bao app hien khi email rong hoac sai dinh dang. */
  private static final String EMAIL_ERROR = "Please enter a valid email address";

  /** Thong bao app hien khi password rong hoac ngan hon 8 ky tu. */
  private static final String PASSWORD_ERROR = "Please enter at least 8 characters";

  private static final String VALID_EMAIL = "capstone.tester@vti.edu.vn";
  private static final String VALID_PASSWORD = "Password123";

  private LoginScreen loginScreen;

  @BeforeClass(alwaysRun = true)
  public void openLoginTab() {
    loginScreen = homeScreen.navigationBar().clickLoginTab();
  }

  /**
   * Bo du lieu cho cac case validate.
   *
   * <p>Moi dong: {@code {ma case, email, password, loi email mong doi, loi password mong doi}}.
   * Chuoi rong o hai cot cuoi nghia la "khong duoc phep hien thong bao loi cho o nay".
   */
  @DataProvider(name = "invalidLoginData")
  public Object[][] invalidLoginData() {
    return new Object[][] {
        {"MOB_LOGIN_02", "khong-phai-email", VALID_PASSWORD, EMAIL_ERROR, ""},
        {"MOB_LOGIN_03", "", VALID_PASSWORD, EMAIL_ERROR, ""},
        {"MOB_LOGIN_04", VALID_EMAIL, "1234567", "", PASSWORD_ERROR},
        {"MOB_LOGIN_05", VALID_EMAIL, "", "", PASSWORD_ERROR},
        {"MOB_LOGIN_06", "", "", EMAIL_ERROR, PASSWORD_ERROR},
    };
  }

  @Test(dataProvider = "invalidLoginData",
      description = "Man hinh Login chan du lieu khong hop le va bao dung thong bao")
  @Story("Validate du lieu dang nhap")
  @Severity(SeverityLevel.CRITICAL)
  @Description("Kiem tra tung lop tuong duong cua email va password. Case MOB_LOGIN_04 dung "
      + "password 7 ky tu - dung bien duoi cua rang buoc 'toi thieu 8 ky tu'.")
  public void loginWithInvalidData_showsExpectedValidationMessages(
      String caseId, String email, String password,
      String expectedEmailError, String expectedPasswordError) {

    loginScreen.clearForm()
        .inputEmail(email)
        .inputPassword(password)
        .clickLoginButton();

    if (!expectedEmailError.isEmpty()) {
      assertTrue(loginScreen.shouldShowEmailErrorMessage(expectedEmailError),
          caseId + " - phai hien thong bao loi email '" + expectedEmailError + "'");
    }
    if (!expectedPasswordError.isEmpty()) {
      assertTrue(loginScreen.shouldShowPasswordErrorMessage(expectedPasswordError),
          caseId + " - phai hien thong bao loi password '" + expectedPasswordError + "'");
    }
  }

  @Test(description = "MOB_LOGIN_01 - Dang nhap voi du lieu hop le hien hop thoai thanh cong")
  @Story("Dang nhap thanh cong")
  @Severity(SeverityLevel.BLOCKER)
  @Description("Duong di hanh phuc. App bao thanh cong bang mot alert cua Android chu khong "
      + "phai element trong app - xem ghi chu trong LoginScreen#shouldShowSuccessAlert.")
  public void loginWithValidCredentials_showsSuccessDialog() {
    loginScreen.clearForm()
        .inputEmail(VALID_EMAIL)
        .inputPassword(VALID_PASSWORD)
        .clickLoginButton();

    assertTrue(loginScreen.shouldShowSuccessAlert("Success", "You are logged in!"),
        "MOB_LOGIN_01 - phai hien hop thoai 'Success' kem noi dung 'You are logged in!'");

    // Dong hop thoai de khong anh huong cac test chay sau trong cung phien app.
    loginScreen.closeAlert();
  }
}
