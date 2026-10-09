package com.amalitech.handlingInputs;

import com.microsoft.playwright.*;
// import com.microsoft.playwright.options.LoadState;
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
        // page.navigate("https://www.testmuai.com/selenium-playground/simple-form-demo/");

        // // The site is a Next.js app: its click handlers attach after the load event.
        // // Clicking too early does nothing, so wait for the page to finish loading scripts
        // page.waitForLoadState(LoadState.NETWORKIDLE);
        // // "input#user-message" targets the text box only; the same id is reused on other elements
        // page.locator("input#user-message").fill("AmaliTech rocks!");
        // page.locator("button#showInput").click();

        // // textContent() reads the text right now without waiting, so it can be empty if the page is slow
        // String message = page.locator("id=message").textContent();
        // System.out.println("The message is: " + message);
        // // hasText() retries until the text appears, so it is the reliable check
        // assertThat(page.locator("id=message")).hasText("AmaliTech rocks!");

        // Reading values from an edit page
        // page.navigate("https://letcode.in/edit");
        // inputValue() returns what is currently typed in the field (not the element's text)
        // String inputValue = page.locator("#getMe").inputValue();
        // System.out.println(inputValue);

        // getAttribute() reads an HTML attribute, here the placeholder hint of the empty field
        // String placeholderValue = page.locator("#fullName").getAttribute("placeholder");
        // System.out.println(placeholderValue);

        // A Locator can be stored and reused; hasAttribute() asserts the attribute value and retries
        // Locator fullNameLocator = page.locator("#fullName");
        // assertThat(fullNameLocator).hasAttribute("placeholder", "Enter first & last name");

        // clear() empties the input field
        // page.locator("#clearMe").clear();

        // Checkbox example
        page.navigate("https://www.testmuai.com/selenium-playground/checkbox-demo/");
        // The input is nested in a <label>, so getByLabel() finds the checkbox by the label text
        Locator checkbox = page.getByLabel("Click on check box");
        // Confirm the checkbox starts unticked
        assertThat(checkbox).not().isChecked();

        // check() ticks the box (and does nothing if it is already ticked)
        checkbox.check();
        // Confirm the box is now ticked
        assertThat(checkbox).isChecked();

        // Close in reverse order of creation to release everything
        page.close();
        browser.close();
        playwright.close();
    }
    
}
