package server;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DueDate extends ToDo{
	private DateTimeFormatter formatter;
	
	public DueDate(String title, String description, int ID, Priority priority) {
		super (title, description, ID, priority);
	}
	
	public LocalDate setDate() {
	LocalDate date = LocalDate.now();
	String text = date.format(formatter);
	LocalDate parsedDate = LocalDate.parse(text, formatter);
	
	return parsedDate;
	 
	

}
}
