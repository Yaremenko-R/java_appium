import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "8.0");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "C:\\Dev\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testSearch() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            10);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            "Java",
            "Cannot find search input",
            10);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
            15);
  }

  @Test
  public void testCancelSearch() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' Input",
            10);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            "Java",
            "Cannot find search input",
            15);

    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search field",
            15);

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            10);

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            10);
  }

  @Test
  public void testCompareArticleTitle() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            "Java",
            "Cannot find search input",
            15);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            30);

    String article_title = title_element.getAttribute("text");

    Assert.assertEquals(
            "Unexpected title",
            "Java (programming language)",
            article_title);
  }

  @Test
  public void testTextCheck() {
    waitForElementPresent(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            10);

    assertElementHasText(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Search Wikipedia",
            "Actual text of the element is not equal to expected text");
  }

  @Test
  public void testCancelSearch2() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' Input",
            10);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            "Java",
            "Cannot find search input",
            15);

    waitForElementPresent(
            By.id("org.wikipedia:id/search_results_list"),
            "Search results list is empty",
            15);

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            10);

    waitForElementPresent(
            By.id("org.wikipedia:id/search_empty_message"),
            "Search results is still on the page",
            15);
  }

  @Test
  public void testSearchResultsCheck() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' Input",
            10);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            "JAVA",
            "Cannot find search input",
            15);

    assertSearchResultsCorrectness(
            By.id("org.wikipedia:id/page_list_item_title"),
            "JAVA",
            "Search results do not contain expected values");
  }

  @Test
  public void testSwipeArticle() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            "Java",
            "Cannot find search input",
            15);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            30);

    swipeUp(2000);
    swipeUp(2000);
    swipeUp(2000);
    swipeUp(2000);
    swipeUp(2000);
  }

  private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by));
  }

  private WebElement waitForElementPresent(By by, String error_message) {
    return waitForElementPresent(by, error_message, 10);
  }

  private WebElement waitForElementPresent(By by) {
    return waitForElementPresent(by, "Cannot find element", 10);
  }

  private List<WebElement> waitForElementsPresent(By by) {
    WebDriverWait wait = new WebDriverWait(driver, 15);
    wait.withMessage("Cannot find elements" + "\n");
    return wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(by));
  }

  private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, 10);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, 10);
    element.sendKeys(value);
    return element;
  }

  private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by));
  }

  private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, 10);
    element.clear();
    return element;
  }

  private void assertElementHasText(By by, String expected_text, String error_message) {
    WebElement element = waitForElementPresent(by);
    String act_text = element.getAttribute("text");
    String exp_text = expected_text;
    Assert.assertEquals(
            error_message,
            exp_text,
            act_text);
  }

  private void assertSearchResultsCorrectness(By by, String expected_text, String error_message) {
    List<WebElement> elements = waitForElementsPresent(by);
    for (WebElement element : elements) {
      String act_text = element.getAttribute("text");
      String exp_text = expected_text;
      Assert.assertTrue(error_message, act_text.toLowerCase().contains(exp_text.toLowerCase()));
    }
  }

  private void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);
    action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
  }
}