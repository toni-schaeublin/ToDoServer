package server;

public class ToDo {
	private int ID;
	private String title;
	private String description;
	private Priority priority;
	private String dueDate;

	public ToDo(int id, String title, String priority, String description) {
		this.ID = id;
		this.title = title;
		this.description = description;
		this.priority = Priority.valueOf(priority);
	}

	public ToDo(int id, String title, String priority, String description, String dueDate) {
		this.ID = id;
		this.title = title;
		this.description = description;
		this.priority = Priority.valueOf(priority);
		this.dueDate = dueDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	// get priority
	public String getPriority() {
		return this.priority.toString();
	}

	// set priority
	public void setPriority(String priority) {
		this.priority = Priority.valueOf(priority);
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDueDate() {
		return this.dueDate;
	}

	public int getID() {
		return this.ID;
	}

}
