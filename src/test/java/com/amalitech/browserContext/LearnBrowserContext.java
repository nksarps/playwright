package com.amalitech.browserContext;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LearnBrowserContext {
    public static void main(String[] args) {
        // Starts the Playwright driver; everything else is created from this object
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));

        // A context is an isolated browser session (own cookies, storage, cache),
        // like a fresh incognito profile. Pages in different contexts don't share logins
        BrowserContext context = browser.newContext();

        // Pages (tabs) are opened from the context, not the browser, so they belong to this session
        Page page = context.newPage();
        page.navigate("https://ecommerce-playground.lambdatest.io/index.php");

        // XPath is case-sensitive: the site's text is "My account", not "My Account"
        Locator myAccount = page.locator("//a[contains(.,'My account')][@role='button']");
        // Login link only appears in the dropdown, which opens on hover
        myAccount.hover();

        Locator login = page.locator("//a[contains(.,'Login')]");
        login.click();

        // getByLabel finds an input by its associated <label> text
        page.getByLabel("E-Mail Address").fill("koushik350@gmail.com");
        page.getByLabel("Password").fill("Pass123$");
        // getByRole matches by accessibility role + name, so it ignores text case
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Login")).click();

        // Text that only shows on the account page confirms the login worked
        Locator editAccount = page.getByText("Edit your account information");
        assertThat(editAccount).isVisible();

        // Open a new tab in the same context
        Page newTab = page.context().newPage();
        newTab.navigate("https://ecommerce-playground.lambdatest.io/index.php?route=account/account");
        // Same context shares cookies, so the new tab is already logged in
        assertThat(editAccount).isVisible();

        // Open a new context, which doesn't share the login from the first context
        BrowserContext newContext = browser.newContext();
        Page newContextPage = newContext.newPage();
        // The login from the first context doesn't carry over, so we see the login page
        newContextPage.navigate("https://ecommerce-playground.lambdatest.io/index.php?route=account/account");

        // Playwright can also drive other browser engines; this launches a separate Firefox browser
        BrowserType firefox =playwright.firefox();
        Page firefoxPage = firefox.launch(new LaunchOptions().setHeadless(false)).newPage();

        // Close in reverse order of creation to release everything
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}
