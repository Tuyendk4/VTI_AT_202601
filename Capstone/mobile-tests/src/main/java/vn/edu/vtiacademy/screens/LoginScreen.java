package vn.edu.vtiacademy.screens;

import io.qameta.allure.Step;
import vn.edu.vtiacademy.common.keywords.MobileUI;

/**
 * Man hinh Login cua WDIO Demo App (Android).
 *
 * <p>Locator nam trong {@code src/main/resources/object_repository/mobile/android/LoginScreen.json},
 * KHONG hardcode trong code. Doi locator thi sua JSON, khong phai bien dich lai - day la ly do
 * ton tai cua object repository.
 *
 * <p><b>Ve chup man hinh:</b> ban goc trong {@code UiFramework} chup 3 anh moi thao tac
 * ({@code takeScreenshot()}, {@code takeScreenshot(locator)},
 * {@code takeScreenshotAndMarkElement(locator)}). Voi 6 test thi thanh gan 60 anh, Allure
 * report nang va khong ai doc het. O day moi buoc chi chup MOT anh tai thoi diem co y nghia,
 * va anh danh dau element chi chup khi assert - dung luc can nhin ky nhat.
 */
public class LoginScreen extends BaseScreen {

  /**
   * Timeout (giay) khi kiem tra mot element duoc MONG DOI la KHONG ton tai.
   *
   * <p>Mac dinh cua {@code core} la 30 giay - hop ly khi cho mot element se xuat hien, nhung
   * sai hoan toan khi cau hoi la "element nay co khong": moi cau tra loi "khong" ton dung
   * 30 giay. Bo 6 test tung mat hon 15 phut chi de doi cac element vang mat.
   */
  private static final int ABSENCE_CHECK_TIMEOUT = 3;

  /**
   * Timeout (giay) khi cho mot element duoc MONG DOI la SE XUAT HIEN.
   *
   * <p><b>Phai tach rieng khoi {@link #ABSENCE_CHECK_TIMEOUT}.</b> Hai phep kiem tra nghe giong
   * nhau nhung yeu cau nguoc nhau: kiem tra vang mat can timeout NGAN (de khong doi vo ich),
   * con cho mot thong bao hien ra can timeout DAI (de con kip). Dung chung mot hang so 3 giay
   * cho ca hai da lam ca sau test do tren mot emulator dang cham: React Native chua ve xong
   * thong bao loi thi test da ket luan "khong co thong bao nao". Trieu chung trong het nhu app
   * hong, thuc ra chi la doi chua du.
   */
  private static final int MESSAGE_TIMEOUT = 15;

  /** Hop thoai cua he dieu hanh ve cham hon element trong app - cho lau hon mot chut. */
  private static final int ALERT_TIMEOUT = 8;

  public LoginScreen(MobileUI mobileUI) {
    super(mobileUI);
    setRepoName(LoginScreen.class.getSimpleName());
  }

  @Step("Nhap email: '{0}'")
  public LoginScreen inputEmail(String email) {
    mobileUI.clearText(findTestObject("TXT_EMAIL"));
    if (!email.isEmpty()) {
      mobileUI.inputText(findTestObject("TXT_EMAIL"), email);
    }
    return this;
  }

  @Step("Nhap password: '{0}'")
  public LoginScreen inputPassword(String password) {
    mobileUI.clearText(findTestObject("TXT_PASSWORD"));
    if (!password.isEmpty()) {
      mobileUI.inputText(findTestObject("TXT_PASSWORD"), password);
    }
    return this;
  }

  @Step("Bam nut LOGIN")
  public LoginScreen clickLoginButton() {
    mobileUI.takeScreenshot();
    mobileUI.tapOn(findTestObject("BTN_LOGIN"));
    mobileUI.delayInSeconds(2);
    mobileUI.takeScreenshot();
    return this;
  }

  @Step("Kiem tra thong bao loi cua email = '{0}'")
  public boolean shouldShowEmailErrorMessage(String expectedErrorMessage) {
    return verifyErrorMessage("LBL_EMAIL_ERROR_MESSAGE", expectedErrorMessage);
  }

  @Step("Kiem tra thong bao loi cua password = '{0}'")
  public boolean shouldShowPasswordErrorMessage(String expectedErrorMessage) {
    return verifyErrorMessage("LBL_PASSWORD_ERROR_MESSAGE", expectedErrorMessage);
  }

  /**
   * Kiem tra hop thoai bao dang nhap thanh cong.
   *
   * <p>App hien mot <b>alert cua he dieu hanh Android</b> chu khong phai element cua app.
   * {@code MobileUI} khong co API alert, nhung alert nay van nam trong cay UI voi
   * {@code resource-id="android:id/alertTitle"} nen tim bang XPath binh thuong duoc.
   * Cach nay re hon nhieu so voi viec them API alert vao {@code core} chi de phuc vu mot case.
   */
  @Step("Kiem tra hop thoai thanh cong: tieu de '{0}', noi dung '{1}'")
  public boolean shouldShowSuccessAlert(String expectedTitle, String expectedMessage) {
    String titleLocator = findTestObject("LBL_ALERT_TITLE");
    if (!mobileUI.waitForElementPresent(titleLocator, ALERT_TIMEOUT)) {
      LOGGER.warn("Khong thay hop thoai nao sau khi bam LOGIN");
      mobileUI.takeScreenshot();
      return false;
    }
    String actualTitle = mobileUI.getText(titleLocator, ALERT_TIMEOUT);
    String actualMessage = getErrorMessage("LBL_ALERT_MESSAGE", ALERT_TIMEOUT);
    mobileUI.takeScreenshot();
    // Kiem ca noi dung chu khong chi tieu de: mot hop thoai loi cung co the co tieu de
    // chung chung, chinh cau "You are logged in!" moi chung minh dang nhap da thanh cong.
    boolean matched = expectedTitle.equals(actualTitle) && expectedMessage.equals(actualMessage);
    if (!matched) {
      LOGGER.warn("Hop thoai khong khop. Mong doi ['{}' / '{}'], thuc te ['{}' / '{}']",
          expectedTitle, expectedMessage, actualTitle, actualMessage);
    }
    return matched;
  }

  /** Dong hop thoai de test sau bat dau tu trang thai sach. */
  @Step("Dong hop thoai")
  public LoginScreen closeAlert() {
    if (mobileUI.waitForElementPresent(findTestObject("BTN_ALERT_OK"), ABSENCE_CHECK_TIMEOUT)) {
      mobileUI.tapOn(findTestObject("BTN_ALERT_OK"));
      mobileUI.delayInSeconds(1);
    }
    return this;
  }

  /** Xoa trang hai o nhap de test truoc khong anh huong test sau. */
  @Step("Xoa trang form dang nhap")
  public LoginScreen clearForm() {
    mobileUI.clearText(findTestObject("TXT_EMAIL"));
    mobileUI.clearText(findTestObject("TXT_PASSWORD"));
    return this;
  }

  /**
   * Doc thong bao loi that su hien tren man hinh.
   *
   * <p>Tra ve chuoi rong khi khong co thong bao nao - de test bao "mong doi X, thuc te ''"
   * thay vi nem exception khong ro nguyen nhan.
   */
  @Step("Doc thong bao loi hien tai cua '{0}'")
  public String getErrorMessage(String objectName) {
    return getErrorMessage(objectName, MESSAGE_TIMEOUT);
  }

  /**
   * Doc thong bao loi, tu chon thoi gian cho.
   *
   * @param timeoutSeconds dung {@link #MESSAGE_TIMEOUT} khi mong doi thong bao SE hien ra,
   *     dung {@link #ABSENCE_CHECK_TIMEOUT} khi chi muon biet no co ton tai khong
   */
  private String getErrorMessage(String objectName, int timeoutSeconds) {
    String locator = findTestObject(objectName);
    if (!mobileUI.waitForElementPresent(locator, timeoutSeconds)) {
      return "";
    }
    return mobileUI.getText(locator, timeoutSeconds);
  }

  /**
   * So khop thong bao loi thuc te voi ky vong.
   *
   * <p>Dung lai {@link #getErrorMessage(String)} thay vi goi
   * {@code mobileUI.verifyElementText(...)}: ham do dung timeout mac dinh 30 giay, nen moi
   * lan assert THAT BAI se treo them nua phut - bo test do se cham gap nhieu lan bo test xanh,
   * dung luc dang can vong lap sua-chay nhanh nhat.
   */
  private boolean verifyErrorMessage(String objectName, String expectedErrorMessage) {
    // Mong doi thong bao SE hien ra -> cho dai.
    String actual = getErrorMessage(objectName, MESSAGE_TIMEOUT);
    boolean matched = expectedErrorMessage.equals(actual);
    if (matched) {
      mobileUI.takeScreenshotAndMarkElement(findTestObject(objectName));
    } else {
      LOGGER.warn("Thong bao loi cua '{}' khong khop. Mong doi '{}', thuc te '{}'",
          objectName, expectedErrorMessage, actual);
      mobileUI.takeScreenshot();
    }
    return matched;
  }
}
