package vn.edu.vtiacademy.spike;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * SPIKE - da tra loi xong. Giu lai lam bang chung khao sat, xoa truoc khi dong goi nop.
 *
 * <p><b>== KET QUA (chay 17/08/2026, Chrome 151) ==</b>
 * <pre>
 * [0] Customer            25 options  size=1x1 clip=rect(0,0,0,0)  -> Select OK: 'Emily Davis - ...'
 * [1] Warehouse           13 options  size=1x1                     -> Select OK: 'East Coast Logistics Hub - ...'
 * [2] Recurring Frequency 12 options  size=1x1                     -> Select OK: 'Every 1 Day'
 * [3][4][5]                0 options  (Commission/Agent/Product - nap dong, rong luc dau)
 * </pre>
 *
 * <p><b>KET LUAN 1:</b> {@link Select} CHAY DUOC tren native select an cua Radix, du chung
 * bi {@code clip: rect(0,0,0,0)} va size 1x1. Selenium 4.43 chon option bang JS nen khong
 * vuong kiem tra interactable. -> Moi dropdown ton 1 dong, tiet kiem ~1 ngay cong.
 *
 * <p><b>KET LUAN 2 (quan trong hon):</b> sau moi lan chon, React render lai form ->
 * moi WebElement lay truoc do deu {@code StaleElementReferenceException}.
 * BAT BUOC: tim lai element ngay truoc moi lan tuong tac, TUYET DOI khong cache WebElement
 * giua cac buoc. Ap dung cho ca page object ngay 4-6.
 *
 * <p><b>Cau hoi:</b> form Create Sales Invoice cua WorkDo dung Radix Select (React) -
 * dropdown nhin thay la cac the {@code <div>}, KHONG phai {@code <select>} chuan.
 * Nhung trong DOM van con 6 the {@code <select>} native bi an (Radix giu lai de form
 * submit duoc), va chung co options that.
 *
 * <p>Selenium co class {@link Select} xu ly dropdown chuan chi bang 1 dong. Neu no dieu
 * khien duoc may the an nay thi moi dropdown ton 1 dong. Neu khong thi phai lam 4 buoc
 * (click trigger -> cho popup -> tim option theo text -> click), nhan voi 4 dropdown
 * tren form, nhan voi 15 test.
 *
 * <p><b>Chenh lech: ~1 ngay cong.</b> Nen tra loi truoc khi bat dau ngay 4.
 *
 * <p>Chay: {@code mvn -pl web-tests test -Dtestsuite=SpikeSuite.xml}
 */
public class RadixSelectSpike {

  private static final String LOGIN_URL = "https://dash-demo.workdo.io/login";
  private static final String CREATE_INVOICE_URL = "https://dash-demo.workdo.io/sales-invoices/create";
  private static final Duration TIMEOUT = Duration.ofSeconds(40);

  @org.testng.annotations.Test
  public void probeRadixSelects() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new", "--window-size=1920,1080");
    WebDriver driver = new ChromeDriver(options);
    WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

    try {
      log("1. Mo trang login");
      driver.get(LOGIN_URL);
      // Trang co overlay "Please wait while we prepare your webapp..." -> cho nut demo hien
      WebElement exploreButton = wait.until(ExpectedConditions.elementToBeClickable(
          By.xpath("//button[contains(normalize-space(.),'Explore All Add-ons')]")));

      log("2. Vao demo bang nut 'Explore All Add-ons'");
      exploreButton.click();
      wait.until(ExpectedConditions.urlContains("/dashboard"));

      log("3. Mo form Create Sales Invoice");
      driver.get(CREATE_INVOICE_URL);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("invoice_date")));
      wait.until(d -> !d.findElements(By.tagName("select")).isEmpty());

      List<WebElement> nativeSelects = driver.findElements(By.tagName("select"));
      log("4. Tim thay " + nativeSelects.size() + " the <select> native trong DOM");

      log("");
      log("=== TRANG THAI TUNG THE <select> ===");
      for (int i = 0; i < nativeSelects.size(); i++) {
        WebElement select = nativeSelects.get(i);
        Dimension size = select.getSize();
        String css = (String) ((JavascriptExecutor) driver).executeScript(
            "const s=getComputedStyle(arguments[0]);"
                + "return `display=${s.display} visibility=${s.visibility} opacity=${s.opacity}"
                + " pointerEvents=${s.pointerEvents} position=${s.position} clip=${s.clip}"
                + " clipPath=${s.clipPath}`;", select);
        int optionCount = select.findElements(By.tagName("option")).size();

        log(String.format("  [%d] options=%-3d size=%dx%d displayed=%-5s enabled=%-5s",
            i, optionCount, size.getWidth(), size.getHeight(),
            safeIsDisplayed(select), select.isEnabled()));
        log("       " + css);
      }

      log("");
      log("=== THU Select CLASS TREN TUNG THE ===");
      boolean anySucceeded = false;
      for (int i = 0; i < nativeSelects.size(); i++) {
        WebElement select = nativeSelects.get(i);
        if (select.findElements(By.tagName("option")).size() < 2) {
          log(String.format("  [%d] BO QUA - it hon 2 option", i));
          continue;
        }
        try {
          Select dropdown = new Select(select);
          dropdown.selectByIndex(1);
          String chosen = dropdown.getFirstSelectedOption().getText();
          log(String.format("  [%d] ✅ THANH CONG -> da chon: '%s'", i, chosen));
          anySucceeded = true;
        } catch (Exception e) {
          log(String.format("  [%d] ❌ THAT BAI -> %s: %s", i,
              e.getClass().getSimpleName(), firstLine(e.getMessage())));
        }
      }

      log("");
      log("=================== KET LUAN ===================");
      if (anySucceeded) {
        log("  Select class CHAY DUOC tren native select an cua Radix.");
        log("  -> Ngay 4: dung new Select(el).selectByVisibleText(...) - 1 dong/dropdown.");
        log("  -> Tiet kiem ~1 ngay cong.");
      } else {
        log("  Select class KHONG chay duoc.");
        log("  -> Ngay 4: viet helper 4 buoc trong WebUI:");
        log("     click(trigger) -> cho [role=option] hien -> click option theo text -> cho popup dong.");
        log("  -> Du tru them ~1 ngay cho ngay 4-6.");
      }
      log("================================================");

    } finally {
      driver.quit();
    }
  }

  private static String safeIsDisplayed(WebElement element) {
    try {
      return String.valueOf(element.isDisplayed());
    } catch (Exception e) {
      return "ERR";
    }
  }

  private static String firstLine(String message) {
    if (message == null) {
      return "(khong co message)";
    }
    int newline = message.indexOf('\n');
    return newline > 0 ? message.substring(0, newline) : message;
  }

  private static void log(String message) {
    System.out.println("[SPIKE] " + message);
  }
}
