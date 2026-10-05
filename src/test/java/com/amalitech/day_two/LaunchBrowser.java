package com.amalitech.day_two;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.*;

public class LaunchBrowser {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
            new LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://ecommerce-playground.lambdatest.io/index.php");
    }
}
