import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
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
            "Appium",
            "Cannot find search input",
            15);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find 'Appium' in search",
            15);

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            30);

    swipeUpToFindElement(
            By.xpath("//*[@text='View page in browser']"),
            "Cannot find the end of the article",
            20
    );
  }

  @Test
  public void testSaveFirstArticleToMyList() {
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
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='More options']"),
            "Cannot find button to open article options",
            15);

    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            15);

    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got it' tip overlay",
            15);

    waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of article's folder",
            15);

    String name_of_folder = "Learning programming";

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot put text into article's folder input",
            15);

    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content_desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content_desc='My lists']"),
            "Cannot find navigation button to My list",
            15);

    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            15);

    swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article");

    waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            15);
  }

  @Test
  public void testAmountOfNotEmptySearch() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    String search_line = "Linkin Park Discography";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            search_line,
            "Cannot find search input",
            15);

    String search_results_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

    waitForElementPresent(
            By.xpath(search_results_locator),
            "Cannot find anything by request" + search_line,
            15);

    int amount_of_search_results = getAmountOfElements(By.xpath(search_results_locator));
    Assert.assertTrue(
            "We found too few results",
            amount_of_search_results > 0
    );
  }

  @Test
  public void testAmountOfEmptySearch() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    String search_line = "fasdfassdfggdgdg";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            search_line,
            "Cannot find search input",
            15);

    String search_results_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String empty_results_label = "//*[@text='No results found']";

    waitForElementPresent(
            By.xpath(empty_results_label),
            "Cannot find empty results label by the request" + search_line,
            15);

    assertElementNotPresent(
            By.xpath(search_results_locator),
            "We have found some results by request " + search_line);
  }

  @Test
  public void testChangingScreenOrientationOnSearchResults() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            search_line,
            "Cannot find search input",
            15);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
            15);

    String title_before_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find the title of the article",
            15);

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find the title of the article",
            15);

    Assert.assertEquals(
            "Article title has been changed after rotation",
            title_before_rotation,
            title_after_rotation);

    driver.rotate(ScreenOrientation.PORTRAIT);

    String title_after_second_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find the title of the article",
            15);

    Assert.assertEquals(
            "Article title has been changed after rotation",
            title_before_rotation,
            title_after_second_rotation);
  }

  @Test
  public void testCheckSearchArticleInBackground() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            search_line,
            "Cannot find search input",
            15);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
            15);

    driver.runAppInBackground(2);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find article after returning from background",
            15);
  }

  @Test
  public void testSaveTwoArticles() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            search_line,
            "Cannot find search input",
            15);

    String article1_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']";

    waitForElementAndClick(
            By.xpath(article1_locator),
            "Cannot find expected article",
            15);

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='More options']"),
            "Cannot find button to open article options",
            15);

    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            15);

    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got it' tip overlay",
            15);

    waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of article's folder",
            15);

    String name_of_folder = "Learning programming";

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot put text into article's folder input",
            15);

    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content_desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            15);

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            search_line,
            "Cannot find search input",
            15);

    String article2_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='JavaScript']";

    waitForElementAndClick(
            By.xpath(article2_locator),
            "Cannot find expected article",
            15);

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15);

    String article_title_before = waitForElementAndGetAttribute(
            By.xpath(article2_locator),
            "text",
            "Cannot find the title of the article",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='More options']"),
            "Cannot find button to open article options",
            15);

    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            15);

    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find list " + name_of_folder,
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content_desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content_desc='My lists']"),
            "Cannot find navigation button to My list",
            15);

    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find list " + name_of_folder,
            15);

    swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article");

    waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            15);

    waitForElementPresent(
            By.xpath("//*[@text='JavaScript']"),
            "Cannot find the second saved article in the list " + name_of_folder,
            15);

    waitForElementAndClick(
            By.xpath(article2_locator),
            "Cannot find expected article",
            15);

    String article_title_after = waitForElementAndGetAttribute(
            By.xpath("//*[@text='JavaScript']"),
            "text",
            "Cannot find the title of the article",
            15);

    Assert.assertEquals(
            "Title of the article in the list '" + name_of_folder + "' is not matching",
            article_title_before,
            article_title_after);
  }

  @Test
  public void testAssertTitleNoWait() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' Input",
            15);

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search')]"),
            search_line,
            "Cannot find search input",
            15);

    String article_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']";

    waitForElementAndClick(
            By.xpath(article_locator),
            "Cannot find expected article",
            15);

    noWaitForElementAndGetAttribute(
            By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
            "text",
            "Cannot find the title of the article");
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

  protected void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);
    action
            .press(x, start_y)
            .waitAction(timeOfSwipe)
            .moveTo(x, end_y)
            .release()
            .perform();
  }

  protected void swipeUpQuick() {
    swipeUp(200);
  }

  protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
    int already_swiped = 0;
    while (driver.findElements(by).size() == 0) {

      if (already_swiped > max_swipes) {
        waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message);
        return;
      }

      swipeUpQuick();
      ++already_swiped;
    }
  }

  protected void swipeElementToLeft(By by, String error_message) {
    WebElement element = waitForElementPresent(by, error_message, 15);
    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();
    int upper_y = element.getLocation().getY();
    int lower_y = upper_y + element.getSize().getHeight();
    int middle_y = (upper_y + lower_y) / 2;

    TouchAction action = new TouchAction(driver);
    action
            .press(right_x, middle_y)
            .waitAction(300)
            .moveTo(left_x, middle_y)
            .release()
            .perform();
  }

  private int getAmountOfElements(By by) {
    List elements = driver.findElements(by);
    return elements.size();
  }

  private void assertElementNotPresent(By by, String error_message) {
    int amount_of_elements = getAmountOfElements(by);
    if (amount_of_elements > 0) {
      String default_message = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  private String waitForElementAndGetAttribute(By by, String attribute, String error_message, int timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    return element.getAttribute(attribute);
  }

  private String noWaitForElementAndGetAttribute(By by, String attribute, String error_message) {
    WebElement element = driver.findElementByXPath(String.valueOf(by));
    if (element.getAttribute(attribute).isEmpty()) {
      String default_message = "An attribute '" + attribute + "' supposed to be present";
      throw new AssertionError(default_message + " " + error_message);
    }
    return element.getAttribute(attribute);
  }
}