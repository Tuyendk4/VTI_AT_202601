package web;

import base.BaseWebTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class NavigationTest extends BaseWebTest {

    @Test
    public void openLoginPage() {

        HomePage homePage = new HomePage(driver);

        homePage.openLoginPage();

        Assert.assertTrue(
                homePage.getCurrentUrl().contains("login")
        );
    }

    @Test
    public void openPricingPage() {

        HomePage homePage = new HomePage(driver);

        homePage.openPricingPage();

        Assert.assertTrue(
                homePage.getCurrentUrl().contains("pricing")
        );
    }
    @Test
    public void verifyHomePageTitle() {

        HomePage homePage = new HomePage(driver);

        homePage.openHomePage();

        Assert.assertFalse(
                homePage.getPageTitle().isEmpty()
        );
    }
}