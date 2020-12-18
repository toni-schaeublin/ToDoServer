package server;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
	 * Diese Methode überprüft, ob ein String eine gültige Länge hat. Die Grenzen low und high sind inklusive!
	 * Ist der String gültig, gibt die Methode true zurück. Sonst false.
	 */
	public static Boolean checkStringIsBetween(int low, int high, String testString) {
		Boolean valid = false;
		int length = testString.length();
		if (length>= low && length <=high) {
			valid = true;
		}
		return valid;
	}
	/*
	 * Diese Methode erstellt einen zufälligen String aus 20 Zeichen und gibt diesen als Token zurück
	 */
	public static String createToken() {
		String token;
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		token = bytes.toString();
		return token;
		
	}

	/*
	 * Diese Methode überprüft ob LocalDateTime dateTime in der Zukunft liegt und gibt true zurück, wenn das so ist.
	 */
	public static Boolean checkDate(LocalDateTime dateTime) {
		Boolean valid = false;
		LocalDateTime now = LocalDateTime.now();
		if (dateTime.isBefore(now)) {
			valid = true;
		}
		return valid;
	}
	
}
