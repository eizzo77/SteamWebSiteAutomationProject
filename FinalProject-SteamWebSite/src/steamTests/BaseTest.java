package steamTests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

	WebDriver driver;

	@BeforeClass
	public void setup() throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream("./src/resources/data.properties"));
		switch (props.getProperty("browser")) {
		case ("firefox"):
			System.setProperty("webdriver.gecko.driver", "C:\\FireFoxWebDriver\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		default: // Chrome
			System.setProperty("webdriver.chrome.driver", "C:\\ChromeWebDriver85\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(props.getProperty("url"));

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
