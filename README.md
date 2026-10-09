# Playwright Tutorial

A learning project for browser automation with [Playwright for Java](https://playwright.dev/java/). It is a set of small, commented examples, each focused on one concept, built with Maven.

## Topics covered

- Launching a browser in headed mode and opening pages
- Locating elements with XPath, CSS, labels, placeholders and roles
- Hovering, clicking and filling in forms
- Isolated browser contexts and multiple tabs
- Launching different browser engines
- Assertions on page titles and element visibility or text
- Reading text from the page
- Working with inputs: filling, clearing, reading values and attributes, and ticking checkboxes
- Taking screenshots: viewport, full page, a single element, masking and caret options

## Prerequisites

- [JDK](https://adoptium.net/) 8 or newer (developed with JDK 17)
- [Maven](https://maven.apache.org/download.cgi) 3.6 or newer, or an IDE with Maven support
- [Git](https://git-scm.com/downloads)

Check your setup:

```bash
java -version
mvn -version
```

## Getting started

1. Clone the repo:
   ```bash
   git clone https://github.com/nksarps/playwright.git
   cd playwright
   ```
2. Download the dependencies and compile:
   ```bash
   mvn compile test-compile
   ```
3. Run an example. Each example is a class with a `main` method, so the easiest way is to open the project in your IDE (VS Code with the Java extensions, IntelliJ or Eclipse) and run the class directly.

   To run one from the command line:
   ```bash
   mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="com.amalitech.<package>.<ClassName>"
   ```
   Replace `<package>` and `<ClassName>` with the example you want to run.

On the first run, Playwright downloads the browsers it needs automatically. This can take a few minutes and needs an internet connection.

## Running the unit tests

```bash
mvn test
```

## Project layout

- The main source tree holds the application entry point.
- The test source tree holds the Playwright examples, grouped into one package per topic.

Current example packages:

| Package | What it shows |
| --- | --- |
| `launchBrowser` | Launching a browser and a basic login flow |
| `browserContext` | Contexts, new tabs and launching Firefox |
| `handlingInputs` | Text inputs, attributes and checkboxes |
| `takingScreenshots` | Page, full-page and element screenshots, masking and caret |

New examples go in their own package under the test source tree.

Screenshots from `takingScreenshots` are saved to the `snaps/` folder.

## Notes

- Examples run in headed mode so you can watch the browser. To run without a window, change the headless option to `true` when launching the browser.
- Locators must match exactly one element when you act on them (strict mode). If one matches several, make it more specific instead of using `.first()`.
- The examples run against public practice sites, so they can break if those sites change.
- Don't commit real credentials. Use environment variables or a gitignored config file for anything sensitive.

## Resources

- [Playwright for Java docs](https://playwright.dev/java/docs/intro)
- [Playwright Java API reference](https://playwright.dev/java/docs/api/class-playwright)
