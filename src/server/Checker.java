package server;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;

/*Diese Klasse stellt statische Methoden zur Überprüfung von IP-Adresse, Portnummer, Email-Adresse und Passwort zur Verfügung. 
 * Die Methoden können mit Checker.methodenName(String) aufgerufen werden. Den Methoden muss jeweils ein String übergeben werden. 
 * Falls der String den Anforderungen entspricht, gibt die Methode true zurück.
 * */

public class Checker {

	public Checker() {

	}

	/*
	 * Diese Methode überprüft, ob der String "IP" eine gültige IPv4-Adresse in der
	 * Form 255.255.255.255 ist. Ist die IP gültig gibt die Methode true zurück.
	 * Sonst false.
	 */
	public static Boolean checkIP(String IP) {
		boolean valid = false;
		String[] parts = IP.split("\\.", -1);
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

	/*
	 * Diese Methode überprüft, ob der String "Port" eine gültige Portnummer ist.
	 * Ist die Portnummer zwischen 1 und 65535 => gültig, gibt die Methode true
	 * zurück. Sonst false.
	 */

	public static Boolean checkPort(String port) {
		boolean valid = false;
		try {
			int value = Integer.parseInt(port);
			if (value < 1 || value > 65535)
				valid = false;
		} catch (NumberFormatException e) {
			valid = false;
		}

		return valid;
	}

	/*
	 * Diese Methode überprüft, ob der String "email" eine gültige Email-Adresse in
	 * der Form String@String.Top-Level-Domain ist. Ist die Email gültig gibt die
	 * Methode true zurück. Sonst false.
	 */
	public static Boolean checkEmail(String email) {
		Boolean valid = false;
		Boolean partLengthChecker = true;
		String[] parts = email.split("@");
		if (parts.length == 2) {
			String localPart = parts[0];
			String domainPart = parts[1];
			String[] domainParts = parts[1].split("\\.");
			int length = domainParts.length;
			for (int i = 0; i < length; i++) {
				String part = domainParts[i];
				if (part.length() < 2) {
					partLengthChecker = false;
				}
			}
			if (domainParts.length >= 2 && partLengthChecker) {
				valid = true;
			}
		}
		return valid;

	}

	/*
	 * Diese Methode überprüft, ob der String "password" ein gültiges Passwort
	 * (String zwischen 3 und 20 Zeichen) ist. Ist das Passwort gültig gibt die
	 * Methode true zurück. Sonst false.
	 */
	public static Boolean checkPassword(String password) {
		Boolean valid = false;
		int length = password.length();
		if (length > 2 && length < 21) {
			valid = true;
		}
		return valid;
	}

	/*
	 * Diese Methode überprüft, ob ein String eine gültige Länge hat. Die Grenzen
	 * low und high sind inklusive! Ist der String gültig, gibt die Methode true
	 * zurück. Sonst false.
	 */
	public static Boolean checkStringIsBetween(int low, int high, String testString) {
		Boolean valid = false;
		int length = testString.length();
		if (length >= low && length <= high) {
			valid = true;
		}
		return valid;
	}

	/*
	 * Diese Methode erstellt einen zufälligen String und gibt diesen als Token
	 * zurück
	 */
	public static String createToken() {
		SecureRandom random = new SecureRandom();
		Base64.Encoder base64Encoder = Base64.getUrlEncoder();		
		byte[] randomBytes = new byte[24];
	    random.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}

	/*
	 * Diese Methode überprüft ob LocalDateTime dateTime in der Zukunft liegt und
	 * gibt true zurück, wenn das so ist.
	 */
	public static Boolean checkDate(LocalDate date) {
		Boolean valid = false;
		LocalDate now = LocalDate.now();
		if (date.isBefore(now) || date.equals(now)) {
			valid = true;
		}
		return valid;
	}

	/*
	 * Diese Methode überprüft ob der userName noch nicht vergeben ist und gibt true
	 * zurück, wenn das so ist.
	 */
	public static Boolean freeUsername(String userName, Accounts accounts) {
		Boolean valid = true;
		ArrayList<User> users = new ArrayList<User>();
		users = accounts.getUsers();
		int size = users.size();
		for (User u : users) {
			if (u.getUserName().equals(userName)) {
				valid = false;
			}
		}
		return valid;
	}

	public static Boolean checkPriority(String priority) {
		Boolean valid = false;
		valid = Priority.isPriority(priority);
		return valid;
	}

}
