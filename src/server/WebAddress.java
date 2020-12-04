package server;

public class WebAddress {
	private String ipAddress;
	private int port;

	public WebAddress(String ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	
	public WebAddress() {
		
	}
	
	
	
	//Method to validate the IP
	protected boolean isValidIP(String ipToTest) {
		boolean valid = false;
		String[] parts = ipToTest.split("\\.", -1);
		if (parts.length == 4) {
			valid = true;
			for (String part : parts) {
				try {
					int value = Integer.parseInt(part);
					if (value < 0 || value > 255)
						valid = false;
				} catch (NumberFormatException e) {
					valid = false;
				}
			}
		}
		return valid;
	}
	
	
	
//Method to validate the Port-Number
	protected boolean isValidPort(String portToTest) {
		boolean valid = true;

		try {
			int value = Integer.parseInt(portToTest);
			if (value < 1 || value > 65535)
				valid = false;
		} catch (NumberFormatException e) {
			valid = false;
		}
		return valid;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}
	
	

}
