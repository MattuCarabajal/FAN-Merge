package Pages;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Tests.TestBase;
import javafx.scene.control.Tab;

public class BasePage {
	
	protected WebDriver driver;
	
	@FindBy (how = How.ID, using = "tsidButton")
	private WebElement mainMenuButton;
	
	@FindBy (how = How.ID, using = "tsid-menuItems")
	private WebElement menuOptionsWrapper;
	
	@FindBy (how = How.ID, using = "ext-gen213")
	private WebElement tabsWrapperBar;
		
	public BasePage() {
		this.driver = null;
		//older constructor maintained.
	}
	
	public BasePage(WebDriver newDriver) {
		//System.out.println(newDriver.getCurrentUrl());
		this.driver = newDriver;
		//System.out.println(this.driver.getCurrentUrl());
	}
	
	public WebElement obtenerCampo(String nombre) {
		return driver.findElement(By.xpath("//input[@id='" + nombre + "']|//select[@id='" + nombre + "']|//textarea[@id='" + nombre + "']"));
	}
	
	public void setSimpleDropdown(WebElement element, String value) {
		Select field = new Select(element);
		element.click();
		field.selectByVisibleText(value);
	}
	
	public void setElementFromList(WebElement element, String value, WebElement arrow) {
		Select field = new Select(element);
		field.selectByVisibleText(value);
		arrow.click();
	}

	public WebElement getElementFromList(List<WebElement> elements, String field) {
		List<Integer> a = new ArrayList<>();
		Integer i = 0;
		for(WebElement e : elements) {
			if(e.getText().equals(field)) {
				a.add(i);
			}
			i++;
		}
		return elements.get(a.get(0));
	}
	
	public int getIndexFrame(WebDriver driver, By byForElement) { //working correctly
		//TODO: Do the same for a WebElement instead of a By.
		int index = 0;
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		for(WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);

				driver.findElement(byForElement).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.findElement(byForElement).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.

				driver.switchTo().defaultContent();
				return index;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().defaultContent();
			}
		}
		return -1; //if this is called, the element wasnt found.
	}
	
	public WebElement getFrameForElement(WebDriver driver, By byForElement) {
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		try {return frames.get(getIndexFrame(driver, byForElement));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {System.out.println("Elemento no encontrado en ningun frame.");
			return null;
		}

	}

	public int getIndexFrame(WebDriver driver, WebElement webElementToFind) {
		//TODO: Do the same for a WebElement instead of a By.
		int index = 0;
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		for(WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				webElementToFind.getText();
				//System.out.println(index); //prints the used index.
				driver.switchTo().defaultContent();
				return index;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().defaultContent();

			}catch(NullPointerException noSuchElemExcept) {
				index++;
				driver.switchTo().defaultContent();

			}
		}
		return -1;//if this is called, the element wasnt found.
	}
	
	public WebElement getFrameForElement(WebDriver driver, WebElement webElementToFind) {
		driver.switchTo().defaultContent();
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		return frames.get(getIndexFrame(driver, webElementToFind));
	}
	
	public void switchAppsMenu() {

		driver.switchTo().defaultContent();
		mainMenuButton.click();
	}
	
	
	
	public void switchAppsMenu(WebDriver driver) {
		driver.switchTo().defaultContent();
		driver.findElement(By.className("menuButtonButton")).click();
		//mainMenuButton.click();
	}
	
	public void selectAppFromMenuByName(String optionName) {
		//each option is a menuButtonMenuLink class
//		List<WebElement> options = menuOptionsWrapper.findElements(By.className("menuButtonMenuLink"));
		driver.switchTo().defaultContent();
		List<WebElement> options = driver.findElement(By.id("tsid-menuItems")).findElements(By.tagName("a"));

		for (WebElement option : options) {
			if(option.getText().toLowerCase().equals(optionName.toLowerCase())){
				option.click();
				return;
			}
		}
		System.out.println("Opcion del menu principal NO encontrada.");
		switchAppsMenu();
	}

	public void selectMainTabByName(String tabName) {
//		An Alternative.
//		List<WebElement> tabs = tabsWrapperBar.findElements(By.tagName("li"));
		driver.switchTo().defaultContent();
		List<WebElement> tabs = tabsWrapperBar.findElements(By.tagName("a"));

		for(WebElement tab : tabs) {//tabs LINKS
			if(tab.getText().toLowerCase().equals(tabName.toLowerCase())){
				tab.click();
				return;
			}
		}
		System.out.println("Tab NO encontrado.");
	}
	
	//this is for Consola FAN, closes all tabs, takes in account Alert messages
	public void closeAllTabs(WebDriver driver) {
		driver.switchTo().defaultContent();
		List<WebElement> tabs = driver.findElement(By.id("ext-gen27")).findElements(By.className("x-tab-strip-closable"));
		for (WebElement tab : tabs) {
			//se itera al reves porque el primer elemento puede no estar visible.
			//closeTab(tab);
			closeTab(driver, tab);
		}
	}
	
	public void closeTab(WebDriver driver, WebElement tab) {
		//Warning: doesn't save when closing.
		Actions action = new Actions(driver);
		action.moveToElement(tab);
		action.moveToElement(tab.findElement(By.className("x-tab-strip-close"))).click().build().perform();
		try {
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.switchTo().alert();
			List<WebElement> buttons = driver.findElements(By.tagName("button"));
			for(WebElement button : buttons) {
				if (button.getText().toLowerCase().contains("no guardar")) {
					button.click();
					break;
				}
			}
		}catch(NoAlertPresentException noAlertExcept) {
			//does Nothing.
		}
	}
	
	public List<String> getSelectOptions (Select select){
		  List<String> optionsNames = new ArrayList<String>();
		  for (WebElement option:select.getOptions()) {
		   optionsNames.add(option.getText());
		  }
		  
		  return optionsNames;
		 }
	
	public void selectByText(WebElement element, String data){
		Select select = new Select(element);
		select.selectByVisibleText(data);
		}
	
	final String SITpage = "https://tuidsrvtest.telecom.com.ar:8443/nidp/app/login?id=IDM_NamePassword&sid=0&option=credential&sid=0&target=https%3A%2F%2Ftuidsrvtest.telecom.com.ar%3A8443%2Fnidp%2Fsaml2%2Fidpsend%3FSAMLRequest%3DjZNtb6owFMe%252FiuE9WB58gAwNiOyyzcEERfbGYCmKSqttgc1PP6Z3y%252B6b5Z6kSU%252FOw%252F%252B0%252FfVu%252FFYeOzWirCDYFGQJCB2EIckKvDWFReSKQ2E8umNpeTwZVsV3eI7OFWK809ZhZlwDplBRbJCUFczAaYmYwaERWrMnQ5GAcaKEE0iOQsdiDFHeCk0IZlWJaIhoXUC0mD%252BZwo7zEzO6XUhLUQy9SIJM1qTyXWLpEbGcUIgkSMoxIyYADgRXUxtZj4SO0w5U4JRfz%252FDViFdFxmjN25jE0RG1xZ8NpJQaQ01Tu7jITt3P%252BZVuu2MIZ%252BMiM8NvtXYGoeM5prBWJlZrsVU4iq%252FOpsMmuVjNzPle4DMMaDZ93CJO5Nw%252FkOfDKZPzHF0WsI%252FtvQfDnftq7zeibfui1uPzZaasEnXpDvk9kQdbSvnpwUly61zF%252BkUnMFELmvW2jyKAzT7FAad59rLcupGLDvpa4d59NfCUd%252F5Sb%252BLFTEtk1d0Dp9gAFTEvOITuLHn0c9H2YxXG6C1YDhprFl1ca1NPFScIavGgNvNYfz739b0zqUMShCt%252FveaYNBSfs2Ja9adsAYKnSmm0OEwUZ%252BW86dZ2kheBdlg95E0SuPtd%252FG7PVziDbvrS3hZjFfIw4ynmpqAAeSDKsgj6kdwzQM9QNElR%252B69CJ%252FiLhF3gG2i%252F8bO5JTHjTxQFYuCH7bMsv4BtE4QbnsZVnP7g8ve26ReMwug%252F0bvr%252FtAZ3bx%252FP8XoAw%253D%253D%26RelayState%3D%252F%26SigAlg%3Dhttp%253A%252F%252Fwww.w3.org%252F2000%252F09%252Fxmldsig%2523rsa-sha1%26Signature%3DTt2v5J4iLGiCWAZy1OvWbgjc93hORZDfCHxkk2WSnVWIXVucRmjzDalrxjE6ND96lDjYxV8nzcuDMAReZKFOf%252B9uS9EOUhC%252FpMRgrdrdz4dMQ5x4Y5XC%252F9W1VWMGDtL%252FwxIXNbSj5UH8T4zGLtCEB6IvDBi6w5dfNxJgtae71L8cJ2wf1b%252BOWJ7yAUZVCN3ZUUkrrYg6UJPCt8CqNo%252FGfwyeN8wtb4T25%252FDhJzhIpmWmjLJFgZTZqcvYpE7NKkwWv6FZ8oO4iRti94O2829xVNV5oXDJ03E9MdoL9JnltlmHSV97WUYb8OKz9mqqnucfZMloUlGmIhp2wGLCEU8%252BZM4lLjt%252F5Z0FD0icF02v9eFvi3gg9lg4%252F7Xu7%252BhnHrw2bH83mCsqNQlJW3eguz8BRYgRFEG8naVRX7MrmiPejZ2146l9GssJxh0naB4YgQDjhb01gYBqiLBliFIWDvrgGF%252BY2heuYVG3dE80MTYsc14zD9G49S5ib0g679P4m5HEx2D2eZEkxRVgGDb58ej5igd%252FWpQ3JAkyq6%252FqU595jyhWa8HlCH9UeyMXSwtgakHABbewF%252Bug%252F6jN%252F61XYhpmb9Wa5fugZNKC5E9jSo2FZUrJnTx0ShCnRwITUcwKM75wLI6ra33eLuMmwv3t6H6%252BFhKiDiGmnMyqBPzhFrwgzVg%253D%26id%3DSalesforceSIT";
	
	public void setupNuevaPage(WebDriver driv) {
		driver = driv;
	}
	
	public void login(String ambiente) {
		switch(ambiente) {
		case "SIT":
			driver.get(SITpage);
			//Usuario de Daniel
			//driver.findElement(By.xpath("//input[@name='Ecom_User_ID']")).sendKeys("u589831");
			//Usuario UAT OOCC
			driver.findElement(By.xpath("//input[@name='Ecom_User_ID']")).sendKeys("UAT195528");
			driver.findElement(By.xpath("//input[@name='Ecom_Password']")).sendKeys("Testa10k");
			break;
		}

		driver.findElement(By.xpath("//input[@name='Ecom_Password']")).submit();
	}
	
	public void cajonDeAplicaciones(String nombre) {
		driver.switchTo().defaultContent();
		sleep(5000);
		WebElement cajon = driver.findElement(By.xpath("//span[@id='tsidLabel']"));
		
		if (!nombre.equalsIgnoreCase("Consola FAN") && cajon.getText().equalsIgnoreCase(nombre))
			return;
			
		cajon.click();
		driver.findElement(By.xpath("//a[contains(.,'" +  nombre + "')]")).click();
		
		if (nombre.equalsIgnoreCase("Consola FAN")) {
			try {
				(new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".x-window.x-window-plain.x-window-dlg")));
				driver.findElement(By.tagName("button")).click();
				driver.findElement(By.xpath("//span[@id='tsidLabel']")).click();
				driver.findElement(By.xpath("//a[contains(.,'Consola FAN')]")).click();
			} catch (TimeoutException e) {}
		}
	}
	
	protected void waitForVisibilityOfAllElements(List<WebElement> elems) {
		(new WebDriverWait(driver, 8)).until(ExpectedConditions.visibilityOfAllElements(elems));
	}
	
	protected void waitForVisibilityOf(WebElement elem) {
		(new WebDriverWait(driver, 15)).until(ExpectedConditions.visibilityOf(elem));
	}
	
	protected void waitForVisibilityOfElementLocated(By locator) {
		(new WebDriverWait(driver, 8)).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	protected void waitForElementToBeSelected(WebElement elem) {
		(new WebDriverWait(driver, 8)).until(ExpectedConditions.elementToBeSelected(elem));
	}
	
	protected void waitForAttributeContains(WebElement elem, String atrib, String valor) {
		(new WebDriverWait(driver, 8)).until(ExpectedConditions.attributeContains(elem, atrib, valor));
	}
	
	protected void waitForElementToBeClickable(WebElement elem) {
		(new WebDriverWait(driver, 8)).until(ExpectedConditions.elementToBeClickable(elem));
	}
	
	protected void waitForNumberOfElementsToBe(By locator, Integer number) {
		(new WebDriverWait(driver, 8)).until(ExpectedConditions.numberOfElementsToBe(locator, number));
	}
	
	protected void waitForStalenessOf(WebElement elem) {
		(new WebDriverWait(driver, 8)).until(ExpectedConditions.stalenessOf(elem));
	}
	
	protected static void sleep(int miliseconds) {
		try {Thread.sleep(miliseconds);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	public String obtenerTrackingNumber(WebDriver driver) {
		String tn = null;
		
		return tn;
	}
	
	public void closeTabByName(WebDriver driver, String sTabName) {
		driver.switchTo().defaultContent();
		WebElement wMenu = driver.findElement(By.id("ext-gen65"));
		List <WebElement> wTabs = wMenu.findElements(By.tagName("li"));
		for (WebElement wAux : wTabs) {
			if (wAux.findElement(By.className("tabText")).getText().equalsIgnoreCase(sTabName)) {
				Actions builder = new Actions(driver);
				builder.moveToElement(wMenu).perform();
				wAux.click();
				wAux.findElement(By.className("x-tab-strip-close")).click();
				break;
			}
		}
	}
	
}
