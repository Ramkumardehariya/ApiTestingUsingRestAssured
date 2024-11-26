package TestNG;
import org.testng.annotations.*;

public class TestNG {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite: Runs once before the entire suite.");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite: Runs once after all tests in the suite.");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class: Runs once before any tests in this class.");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method: Runs before each test method.");
    }

    @Test
    public void testAddition() {

        System.out.println("Test Addition: Passed");
    }

    @Test
    public void testSubtraction() {

        System.out.println("Test Subtraction: Passed");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method: Runs after each test method.");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class: Runs once after all tests in this class.");
    }

}