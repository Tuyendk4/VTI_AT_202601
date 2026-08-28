package web;

import base.BaseWebTest;
import org.testng.annotations.Test;
import pages.DemoLoginPage;
import pages.TransferPage;

public class TransferWorkflowTest extends BaseWebTest {

    @Test
    public void createTransferSuccessfully()
            throws InterruptedException {

        // ===== 1. LOGIN =====
        DemoLoginPage loginPage =
                new DemoLoginPage(driver);

        loginPage.openLoginPage();

        loginPage.loginAsCompanyDemo();

        Thread.sleep(2000);


        // ===== 2. PURCHASE → TRANSFERS =====
        TransferPage transferPage =
                new TransferPage(driver);

        transferPage.openTransferFromMenu();

        Thread.sleep(2000);


        // ===== 3. CLICK + CREATE TRANSFER =====
        transferPage.clickCreateTransferButton();

        Thread.sleep(1000);


        // ===== 4. FROM WAREHOUSE =====
        transferPage.selectFromWarehouse(
                "Central Distribution Center"
        );

        Thread.sleep(1000);


        // ===== 5. TO WAREHOUSE =====
        transferPage.selectToWarehouse(
                "East Coast Logistics Hub"
        );

        Thread.sleep(1000);


        // ===== 6. PRODUCT =====
        transferPage.selectProduct(
                "Shampoo"
        );

        Thread.sleep(1000);


        // ===== 7. QUANTITY =====
        transferPage.enterQuantity("1");

        Thread.sleep(1000);


        // ===== 8. CREATE =====
        transferPage.clickCreate();

        Thread.sleep(5000);
    }
}