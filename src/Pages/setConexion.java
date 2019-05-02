 package Pages;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class setConexion{
		static WebDriver driver;


		//@BeforeTest
		//public static void setUp() throws MalformedURLException{
		//DesiredCapabilities capability = DesiredCapabilities.chrome();
		//capability.setBrowserName("chrome");
		//capability.setPlatform(Platform.WINDOWS);
		//driver = new RemoteWebDriver(new URL("http://10.249.37.243:5557/wd/hub"), capability);
	//	}
//		@BeforeTest
/*		public static void setUp() throws MalformedURLException{
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setBrowserName("chrome");
		capability.setPlatform(Platform.WINDOWS);
		driver = new RemoteWebDriver(new URL("http://10.249.37.243:5557/wd/hub"), capability);
		}


//		@AfterTest
		public void kill(){
			driver.quit();
		}*/

		public void SimpleTest() {
			driver.get("http://www.google.com");	
		}
		
		public static WebDriver init()
		{
			driver.get("https://goo.gl/ULLWHZ");
			return driver;
		}
		public static WebDriver setupPablo() {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		    ChromeOptions options = new ChromeOptions();
		    //options.addArguments("user-data-dir=C:\\Users\\pablo\\AppData\\Local\\Google\\Chrome\\User Data");
		    options.addArguments("start-maximized");
		    driver = new ChromeDriver(options);
		    return driver;
		}
		

		public static WebDriver setupLeo() {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		    ChromeOptions options = new ChromeOptions();
		    options.addArguments("user-data-dir=C:\\Users\\Xappia\\AppData\\Local\\Google\\Chrome\\User Data");
		    options.addArguments("start-maximized");
		    driver = new ChromeDriver(options);
		    return driver;
		}
		public static WebDriver setupEze() {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		    ChromeOptions options = new ChromeOptions();
		    //options.addArguments("user-data-dir=C:\\Users\\Sofia Chardin\\AppData\\Local\\Google\\Chrome\\User Data");
		    options.addArguments("start-maximized");
		   // options.addArguments("--headless"); //visual chrome
		    driver = new ChromeDriver(options);
		    return driver;
		}
}