package server;

public enum Priority {

	Low, low, Medium, medium, High, high;

	public static Priority getDefault() {
		return Priority.Low;
	}

	public static Boolean isPriority(String priority) {
		Boolean valid = false;
		try {
			Priority.valueOf(priority);
			valid = true;
		} catch (Exception e) {
			System.out.println("Priority entspricht nicht den Vorgaben! " + e);
		}
		return valid;

	}
}
