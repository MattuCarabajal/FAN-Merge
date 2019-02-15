package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class CustomerCasesManager {

final WebDriver driver;

private List<WebElement> cases;

//Case information
	public CustomerCasesManager(WebDriver driver) {
		this.driver = driver;
		
		List<WebElement> iFrames = driver.findElements(By.tagName("iframe"));
		for (WebElement frame : iFrames) {
			driver.switchTo().frame(frame);
			if(driver.findElement(By.id("ext-gen12")) != null) {
				break;
			}
		}
		cases = driver.findElements(By.className("x-grid3-row"));
	}
	
	@FindBy (how = How.TAG_NAME, using = "iframe")
	private List<WebElement> iFrames;
	
	@FindBy (how = How.ID, using = "phSearchInput")
	private WebElement searcher;
	
	public List<WebElement> getCases(){
		return cases;
	}
	
	private WebElement getCaseLink(WebElement caseElement) {
		return caseElement.findElement(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-CASES_CASE_NUMBER")).findElement(By.tagName("a"));
	}
	
	public WebElement getCase(String caseNumber) {
		for (WebElement currectCase : cases) {
			//System.out.println(currectCase.findElement(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-CASES_CASE_NUMBER")).findElement(By.tagName("a")).getText());
			if (getCaseLink(currectCase).getText().equals(caseNumber)) {
				return currectCase;
			}
		}
		System.out.println("Numero de Caso no encontrado, se devuelve el 7mo.");
		return cases.get(6);
	}
	
	public void clickCase(String caseNumber) {
		getCaseLink(getCase(caseNumber)).click();
	}
	
}
