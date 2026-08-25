package vn.edu.vtiacademy.pages;

import com.jayway.jsonpath.JsonPath;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vtiacademy.common.keywords.WebUI;

/**
 * Nen chung cho moi page object cua web layer.
 *
 * <p><b>Vai tro:</b> nap locator tu file JSON trong
 * {@code src/main/resources/object_repository/}. Nho vay khi WorkDo doi giao dien, nguoi bao tri
 * sua MOT dong trong JSON thay vi tim trong code Java - va nguoi khong biet Java van sua duoc.
 *
 * <p>Duong dan tinh theo {@code user.dir}. Surefire chay voi thu muc lam viec la thu muc module
 * ({@code web-tests/}) nen duong dan luon dung, khong phu thuoc vao cho ban go lenh {@code mvn}.
 */
public class BasePage {

  protected static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

  private static final String OBJECT_REPOSITORY_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "object_repository";

  protected final WebUI webUI;

  private String repoName;

  public BasePage(WebUI webUI) {
    this.webUI = webUI;
  }

  protected void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  /**
   * Lay locator theo ten trong file JSON cua page nay.
   *
   * @param objectName khoa trong JSON, vi du {@code "BTN_CREATE"}
   * @return chuoi locator, hoac {@code null} neu khong doc duoc (loi da duoc ghi log)
   */
  protected String findTestObject(String objectName) {
    File repoFile = new File(
        OBJECT_REPOSITORY_PATH + File.separator + repoName + ".json");
    try {
      return JsonPath.parse(repoFile).read("$." + objectName);
    } catch (IOException e) {
      LOGGER.error("Khong doc duoc object '{}' trong '{}'. Nguyen nhan: {}",
          objectName, repoFile, e.getMessage());
      return null;
    }
  }
}
