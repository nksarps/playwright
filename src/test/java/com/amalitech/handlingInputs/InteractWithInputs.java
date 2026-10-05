package com.amalitech.handlingInputs;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class InteractWithInputs {
    public static void main(String[] args) {
        // Starts the Playwright driver; everything else is created from this object
        Playwright playwright = Playwright.create();
        // Playwright is headless by default, so headless=false makes the browser window visible
        Browser browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false)
        );

        // A "page" is a browser tab
        Page page = browser.newPage();
        page.navigate("https://www.testmuai.com/selenium-playground/simple-form-demo/");

        // The site is a Next.js app: its click handlers attach after the load event.
        // Clicking too early does nothing, so wait for the page to finish loading scripts
        page.waitForLoadState(LoadState.NETWORKIDLE);
        // "input#user-message" targets the text box only; the same id is reused on other elements
        page.locator("input#user-message").fill("AmaliTech rocks!");
        page.locator("button#showInput").click();

        // textContent() reads the text right now without waiting, so it can be empty if the page is slow
        String message = page.locator("id=message").textContent();
        System.out.println("The message is: " + message);
        // hasText() retries until the text appears, so it is the reliable check
        assertThat(page.locator("id=message")).hasText("AmaliTech rocks!");

        // Close in reverse order of creation to release everything
        page.close();
        browser.close();
        playwright.close();
    }
    
}
