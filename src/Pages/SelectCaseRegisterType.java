package Pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectCaseRegisterType extends BasePage {
	
	final WebDriver driver;
	WebElement continueBtn;

	public SelectCaseRegisterType(WebDriver driver){
		this.driver = driver;
		
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		driver.switchTo().defaultContent();
		for (WebElement currentFrame : frames){
			driver.switchTo().frame(currentFrame);
			try {
				continueBtn = driver.findElement(By.xpath("//input[@name=\"save\" and @value=\"¿Desea continuar?\"]"));
				break;
			}
			catch(NoSuchElementException noSuchElemExcept) {
				driver.switchTo().defaultContent();
				continue;
			}
		}
	}

	public void continueToCreate() {
		continueBtn.click();
	}
	
}
