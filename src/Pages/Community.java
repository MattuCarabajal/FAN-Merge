package Pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Tests.TestBase;

public class Community extends TestBase{
	
	public void mobileEmulation(/*String sDeviceName, String sDeviceModel*/) throws AWTException {
		/*Map<String, String> mobileEmulation = new HashMap<>();

		mobileEmulation.put(sDeviceName, sDeviceModel);
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

		WebDriver driver = new ChromeDriver(chromeOptions);
		
		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);*/
		
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_F12);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_SHIFT);
        sleep(1);
        robot.keyPress(KeyEvent.VK_M);
        sleep(1);
        robot.keyRelease(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
}