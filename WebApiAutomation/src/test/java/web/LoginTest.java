package web;

import base.BaseWebTest;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseWebTest {

    @Test
    public void loginSuccessfully() throws InterruptedException {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();

        loginPage.enterEmail("company@example.com");

        loginPage.enterPassword("1234");

        loginPage.clickLogin();

        Thread.sleep(5000);
    }
}