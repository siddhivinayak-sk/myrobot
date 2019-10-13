/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * This class contains Utility method for creating web driver based upon the
 * configuration set in properties file.
 * @author sandeepkumar
 */
public class LoadDriver {
    enum WebDriverBrowserType {
        CHROME, SAFARI, INTERNET_EXPLORER, FIREFOX
    }
    
    enum WebDriverPlatformType {
        MAC, WINDOWS, LINUX
    }
    
    /**
     * This method read the configuration property file and create web driver as
     * per platform and driver settings.
     * @return Return value
     * @throws IOException Throws runtime IOException
     */
    public static WebDriver createWebDriver() throws IOException {
        String webdriverMode = System.getProperty("driver.mode", "local");
	String driverName = System.getProperty("driver.browser.platform", "CHROME");
	String display = System.getProperty("driver.display.port", ":0");
	WebDriverBrowserType browser = WebDriverBrowserType.valueOf(driverName);
	if(webdriverMode.equals("local")) {
            switch (browser) {
                case CHROME: {
                        String chromeLocation = System.getProperty("driver.chrome.location");
                        Map<String,String> environment = new HashMap<String,String>();
                        environment.put("DISPLAY", display);
                        ChromeDriverService chromeService = new ChromeDriverService.Builder()
                            .usingDriverExecutable(new File(chromeLocation))
                            .usingAnyFreePort().withEnvironment(environment).build();
                        chromeService.start();
                        return new RemoteWebDriver(chromeService.getUrl(),DesiredCapabilities.chrome());
                }
                case SAFARI: {
                        return new SafariDriver();
                }
                case INTERNET_EXPLORER: {
                        String internetExplorerLocation = System.getProperty("driver.ie.location");
                        Map<String,String> environment = new HashMap<String,String>();
                        environment.put("DISPLAY", display);
                        InternetExplorerDriverService ieService = new InternetExplorerDriverService.Builder()
                            .usingDriverExecutable(new File(internetExplorerLocation))
                            .usingAnyFreePort()
                            .withEnvironment(environment)
                            .build();
                        ieService.start();
                        return new RemoteWebDriver(ieService.getUrl(), DesiredCapabilities.internetExplorer()); 
                }
                case FIREFOX:
                default: {
                        String geckoDriverPath = System.getProperty("driver.firefox.location");
                        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
                        FirefoxBinary firefoxBinary = new FirefoxBinary();
                        firefoxBinary.setEnvironmentProperty("DISPLAY", display);
                        ProfilesIni allProfiles = new ProfilesIni();
                        FirefoxProfile profile = allProfiles.getProfile("default");
                        return new FirefoxDriver(firefoxBinary, profile);
                }
            }
	} else {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		switch (browser) {
		case CHROME:
			capabilities = DesiredCapabilities.chrome();
			break;
		case INTERNET_EXPLORER:
			capabilities = DesiredCapabilities.internetExplorer();
			break;
		case SAFARI:
			capabilities = DesiredCapabilities.safari();
			break;
		case FIREFOX:
		default:
			capabilities = DesiredCapabilities.firefox();
		}
		String platformName = System.getProperty("driver.os.platform", "WINDOWS");
		WebDriverPlatformType platform = WebDriverPlatformType.valueOf(platformName);
		switch (platform) {
		case MAC:
			capabilities.setPlatform(Platform.MAC);
			break;
		case WINDOWS:
			capabilities.setPlatform(Platform.WINDOWS);
			break;
		case LINUX:
		default:
			capabilities.setPlatform(Platform.LINUX);
		}
		return new RemoteWebDriver(new URL("http://build.e-monocot.org:4444/wd/hub"), capabilities);
	}
    }
}
