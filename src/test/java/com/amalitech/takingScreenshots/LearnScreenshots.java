package com.amalitech.takingScreenshots;

import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Page.ScreenshotOptions;
import com.microsoft.playwright.options.ScreenshotCaret;

public class LearnScreenshots {
    public static void main (String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://www.testmuai.com/selenium-playground/simple-form-demo/");

        // Take a screenshot of the visible viewport only (the default); setPath saves it to disk
        ScreenshotOptions options = new ScreenshotOptions();
        page.screenshot(options.setPath(Paths.get("./snaps/scr.png")));

        // setFullPage(true) captures the whole scrollable page, not just the viewport.
        // Note: this mutates the shared options object, so fullPage stays on for later reuse
        page.screenshot(options.setFullPage(true).setPath(Paths.get("./snaps/scr-fs.png")));

        // Taking a screenshot of a specific element.
        // Use the header button's class: getByText("Book a Demo") matches 3 elements and breaks strict mode.
        // Locator.screenshot() needs Locator.ScreenshotOptions, not Page.ScreenshotOptions
        Locator bookBtn = page.locator("button.chfw-header_demo_btn");
        bookBtn.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("./snaps/button-locator.jpg")));

        // Take a screenshot of a region of the page. E.g., header section:
        // screenshotting a container element captures it and everything inside it
        Locator header = page.locator("#chfw-header");
        header.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("./snaps/header-section.png")));

        // How to mask a sensitive element in a screenshot. E.g., the email input field
        // Fill the field and scroll it into view so it is visible in the viewport
        Locator userInput = page.locator("input#user-message");
        userInput.fill("Something");
        userInput.scrollIntoViewIfNeeded();
        // setMask() covers the given locators with a solid box so their content is hidden.
        // fullPage is reset to false because the shared options had it turned on earlier
        page.screenshot(options.setPath(Paths.get("./snaps/input-mask.jpg"))
            .setFullPage(false)
            .setMask(Arrays.asList(userInput))
        );

        // Show/hide the caret pointer in a screenshot
        // Click the input so it is focused and the blinking caret is present
        userInput.click();
        // HIDE removes the caret from the screenshot (this is the default)
        page.screenshot(new ScreenshotOptions().setCaret(ScreenshotCaret.HIDE)
            .setPath(Paths.get("./snaps/caret-hide.png"))
        );

        // INITIAL leaves the caret as it is on the page, so it may show in the image
        page.screenshot(new ScreenshotOptions().setCaret(ScreenshotCaret.INITIAL)
            .setPath(Paths.get("./snaps/caret-show.png"))
        );

        // Close in reverse order of creation to release everything
        page.close();
        browser.close();
        playwright.close();
    }
}
