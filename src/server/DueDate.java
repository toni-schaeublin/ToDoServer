package server;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DueDate extends ToDo{
	private DateTimeFormatter formatter;
	private LocalDate date;
	
	public DueDate(String title, String description, int ID, Priority priority, LocalDate date) {
		super (title, description, ID, priority);
		this.date=date;
	}
	
	public LocalDate getDate() {
	//LocalDate date = LocalDate.now();
	String text = this.date.format(formatter);
	LocalDate parsedDate = LocalDate.parse(text, formatter);
	return parsedDate;
	 
	

}
}
