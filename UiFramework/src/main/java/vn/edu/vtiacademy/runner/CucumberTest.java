package vn.edu.vtiacademy.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
    features = "src/main/resources/features",
    glue = "vn.edu.vitacademy.steps",
//    tags = "@smoke",
    plugin = {
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }
)
public class CucumberTest extends AbstractTestNGCucumberTests {

}
