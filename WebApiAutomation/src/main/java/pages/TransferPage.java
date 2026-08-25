package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransferPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public TransferPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // =========================
    // PURCHASE -> TRANSFERS
    // =========================
    public void openTransferFromMenu() {

        WebElement purchaseMenu = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//button[.//span[normalize-space()='Purchase']]"
                        )
                )
        );

        purchaseMenu.click();

        WebElement transferMenu = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//a[contains(@href,'/transfers')]"
                        )
                )
        );

        transferMenu.click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//h1[normalize-space()='Manage Transfers']"
                        )
                )
        );

        System.out.println(
                "Đã vào Transfers: "
                        + driver.getCurrentUrl()
        );
    }


    // =========================
    // CLICK NÚT +
    // =========================
    public void clickCreateTransferButton() {

        WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//h1[normalize-space()='Manage Transfers']" +
                                        "/following::button[1]"
                        )
                )
        );

        addButton.click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//*[normalize-space()='Create Transfer']"
                        )
                )
        );
    }


    // =========================
    // FROM WAREHOUSE
    // =========================
    public void selectFromWarehouse(
            String warehouseName) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//label[@for='from_warehouse']" +
                                        "/following::button[@role='combobox'][1]"
                        )
                )
        );

        dropdown.click();

        selectDropdownOption(warehouseName);
    }


    // =========================
    // TO WAREHOUSE
    // =========================
    public void selectToWarehouse(
            String warehouseName) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//label[@for='to_warehouse']" +
                                        "/following::button[@role='combobox'][1]"
                        )
                )
        );

        dropdown.click();

        selectDropdownOption(warehouseName);
    }


    // =========================
    // PRODUCT
    // =========================
    public void selectProduct(
            String productName) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//label[@for='product_id']" +
                                        "/following::button[@role='combobox'][1]"
                        )
                )
        );

        dropdown.click();

        selectDropdownOption(productName);
    }


    // =========================
    // HÀM CHỌN OPTION CHUNG
    // =========================
    private void selectDropdownOption(
            String optionText) {

        By optionLocator = By.xpath(
                "//*[@role='option']" +
                        "[contains(normalize-space(.), '" +
                        optionText +
                        "')]"
        );

        WebElement option = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        optionLocator
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        option
                );

        try {

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            option
                    )
            ).click();

        } catch (Exception e) {

            // Nếu dropdown Radix chặn click thông thường
            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            option
                    );
        }
    }


    // =========================
    // QUANTITY
    // =========================
    public void enterQuantity(
            String quantity) {

        WebElement quantityField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("quantity")
                )
        );

        quantityField.clear();
        quantityField.sendKeys(quantity);
    }


    // =========================
    // CREATE
    // =========================
    public void clickCreate() {

        WebElement createButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//button[@type='submit'" +
                                        " and normalize-space()='Create']"
                        )
                )
        );

        createButton.click();
    }
}