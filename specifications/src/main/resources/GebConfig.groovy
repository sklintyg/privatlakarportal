import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.safari.SafariDriver
import org.openqa.selenium.safari.SafariOptions
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.Platform

waiting {
    timeout = 4 // default wait is two seconds
}
environments {
    chrome {
        driver = { new ChromeDriver() }
    }
    safari {
        driver = {
            SafariOptions options = new SafariOptions(); 
            options.setUseCleanSession(true); 
            new SafariDriver(options)
        }
    }
    firefox {
        driver = { new FirefoxDriver() }
    }
    firefoxRemote {
                driver = {
            new RemoteWebDriver(new URL("http://selenium1.nordicmedtest.se:4444/wd/hub"), DesiredCapabilities.firefox())
        }
    }
    headless {
        driver = { new HtmlUnitDriver() }
    }
    'win-ie' {
        driver = {
            new RemoteWebDriver(new URL("http://windows.ci-server.local"), DesiredCapabilities.internetExplorer())
        }
    }
}
