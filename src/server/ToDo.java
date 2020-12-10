package server;

public class ToDo {
	
	private int ID;
	private String title;
	private String description;
	private Priority priority;
	
	
	 public ToDo(String title, String description, int ID, Priority priority) {
		 this.ID = ID;
		 this.title = title;
		 this.description = description;
		 this.priority = priority;
	 }
	public ToDo() {
	
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
		ID ++;
		return ID;

		//String randomID = UUID.randomUUID().toString();
		//this.randomID = ID.toString();
		//return ID;
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



