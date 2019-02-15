package Tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestUtils {
	
	public TestUtils() {
		
	}
	
	public Date getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		Date currentDate = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		try {
			currentDate = dateFormat.parse(dtf.format(now));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentDate;
	}
	
	public String getCurrentDateString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public String getCurrentDateWithHoursString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
}
