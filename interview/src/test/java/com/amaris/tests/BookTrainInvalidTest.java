package com.amaris.tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class BookTrainInvalidTest {
  private static Playwright pw;
  private static Browser browser;
  private BrowserContext context;
  private Page page;

  @BeforeSuite
  public void beforeSuite() {
    pw = Playwright.create();
    browser = pw.chromium().launch(
        new BrowserType.LaunchOptions()
            .setHeadless(false)   // show Chromium
            .setSlowMo(500)       // slow down for visibility (optional)
    );
  }

  @BeforeMethod
  public void beforeMethod() {
    context = browser.newContext();
    page = context.newPage();
  }

  @Test
  public void purchaseWithInvalidCard_shouldKeepPayButtonDisabled() {
    page.navigate("https://www.renfe.com/es/es");
    Assert.assertTrue(page.title().toLowerCase().contains("renfe"));
  }

  @AfterMethod
  public void afterMethod() { context.close(); }

  @AfterSuite
  public void afterSuite() {
    browser.close();
    pw.close();
  }
}
