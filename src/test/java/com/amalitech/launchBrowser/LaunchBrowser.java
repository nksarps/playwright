package com.amalitech.launchBrowser;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LaunchBrowser {
    public static void main(String[] args) {
        // Starts the Playwright driver; everything else is created from this object
        Playwright playwright = Playwright.create();

        // Playwright is headless by default, so headless=false makes the browser window visible
        Browser browser = playwright.chromium().launch(
            new LaunchOptions().setHeadless(false)
        );

        // A "page" is a browser tab
        Page page = browser.newPage();
        page.navigate("https://ecommerce-playground.lambdatest.io/index.php");

        // XPath is case-sensitive: the site's text is "My account", not "My Account"
        Locator myAccount = page.locator("//a[contains(.,'My account')][@role='button']");
        // Login link only appears in the dropdown, which opens on hover
        myAccount.hover();

        Locator login = page.locator("//a[contains(.,'Login')]");
        login.click();

        // Assertions auto-retry until the title matches or the timeout is hit
        assertThat(page).hasTitle("Account Login");

        // Find the inputs by their placeholder text
        page.getByPlaceholder("E-Mail Address").fill("koushik350@gmail.com");
        page.getByPlaceholder("Password").fill("Pass123$");
        page.locator("//input[@value='Login']").click();

        // Confirms the login worked by checking the page we landed on
        assertThat(page).hasTitle("My Account");

        // Close in reverse order of creation to release the browser and driver
        page.close();
        browser.close();
        playwright.close();
    }
}
