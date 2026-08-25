package web;

import base.BaseWebTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class ElementValidationTest extends BaseWebTest {

    @Test
    public void verifyEmailFieldIsDisplayed() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();

        Assert.assertTrue(
                loginPage.isEmailDisplayed()
        );
    }

    @Test
    public void verifyLoginButtonIsDisplayed() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();

        Assert.assertTrue(
                loginPage.isLoginButtonDisplayed()
        );
    }
    @Test
    public void verifyPasswordFieldIsDisplayed() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();

        Assert.assertTrue(
                loginPage.isPasswordDisplayed()
        );
    }
}