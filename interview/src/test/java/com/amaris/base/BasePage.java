package com.amaris.base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Paths;


public abstract class BasePage {
    protected final Page page;
    protected final int DEFAULT_TIMEOUT = 20_000;

    public BasePage(Page page) {
        this.page = page;
    }


    // ---------- Wait Helpers ----------

    protected void waitForVisible(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setTimeout((double) DEFAULT_TIMEOUT));
    }

protected void waitForHidden(Locator locator) {
    locator.waitFor(new Locator.WaitForOptions()
            .setState(WaitForSelectorState.HIDDEN)
            .setTimeout((double) DEFAULT_TIMEOUT));
}

    protected void waitForNetworkIdle() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    protected void waitForSpinnerToDisappear() {
        Locator spinner = page.locator("[class*='spinner'], [role='progressbar']");
        if (spinner.isVisible()) {
            spinner.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.HIDDEN)
                    .setTimeout((double) DEFAULT_TIMEOUT));
        }
    }

    // ---------- Interaction Helpers ----------

    protected void click(Locator locator) {
        waitForVisible(locator);
        locator.click();
    }

    protected void clickIfVisible(Locator locator) {
        if (locator.isVisible()) locator.click();
    }

    protected void fill(Locator locator, String text) {
        waitForVisible(locator);
        locator.fill(text);
    }

    protected void typeAndEnter(Locator locator, String text) {
        waitForVisible(locator);
        locator.fill(text);
        locator.press("Enter");
    }

    public void clickCloseIfVisible() {
    Locator closeButton = page.getByRole(
        AriaRole.BUTTON,
        new Page.GetByRoleOptions().setName("Close"));

    try {
        closeButton.waitFor(new Locator.WaitForOptions().setTimeout(3000));
        if (closeButton.isVisible()) {
            closeButton.click();
            System.out.println("✅ 'Close' button was visible and clicked.");
        }
    } catch (PlaywrightException e) {
        // TimeoutException or not found — ignore and continue
        System.out.println("ℹ️ 'Close' button not visible, continuing...");
    }
}

   
    protected boolean isEnabled(Locator locator) {
        return locator.isEnabled() && !"true".equals(locator.getAttribute("aria-disabled"));
    }

    protected boolean isDisabled(Locator locator) {
        return !isEnabled(locator);
    }

    protected void captureScreenshot(String name) {
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("screenshots/" + name + ".png"))
                .setFullPage(true));
    }


}
