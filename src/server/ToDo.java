package server;

import java.util.UUID;


public class ToDo {
	
	private int ID;
	private String title;
	private String description;
	private Priority priority;
	
	
	 public ToDo(int ID, String title, String description, Priority priority) {
		 this.ID = ID;
		 this.title = title;
		 this.description = description;
		 this.priority = priority;
	 }
		 
	
	//create a title
	
	public String createTitle() {
		return title;
		
	}
		
	//create a description
	
	public String createDescription() {
		return description;
		
 }
	// create random ID
	
	public int createID() {
		String randomID = UUID.randomUUID().toString();
		return ID;		
	}
	
	//get priority
	public String getPriority() {
		return this.priority.get();
	}
	
	
	// set priority
	public void setPriority(Priority priority) {
		this.priority.set(priority.toString());
	}
	
	
}



