# ToDoServer
Miniproject of a Server to work with ToDo`s


Miniprojekt 2, FHNW, WIBB, RE HS20

Projekt von: Berfin Güler, Antoine Schäublin

In diesem Projekt wurde ein Server implementiert auf welchem Accounts und darauf ToDo's erstellt werden können.

Der Server läuft auf dem Port 50002!

Folgendes wurde implementiert:


- Alle Grundanforderungen
- Validierung der Eingaben auf dem Server
- Erstellen eines Token beim Login
- Speichern und Laden der Daten
- Der Client wurde begonnen (MVC-Gerüst und geringe funktionalität) aber nicht fertig gestellt! GUI kann gestartet werden....

Vorgaben Accounts:

Username: E-Mailadresse; Darf nur einmal vergeben werden!

Password: 3-20 Zeichen


Vorgaben ToDo's:

ID: wird für jedes ToDo erstellt und ist nur für diesen User eineindeutig => Andere User können die selbe ToDo-ID haben!

Titel: 3-20 Zeichen

Priority: low, medium, high oder Low, Medium, High

Description: 0-200 Zeichen

Die Vorgaben werden vom Server überprüft! Entsprechen die Eingaben nicht den Vorgaben oder eine Aktion war nicht erfolgreich gibt der Server "Result|false" zurück.

Beim Login wird ein Token erstellt. Dieser Token muss für alle Aktionen (Erstellen von ToDo's, Löschen von ToDo's, Anzeigen von ToDo's, Auflisten von allen ToDo's und Logout) mitgegeben werden.

Jeder User (Account) hat seine eigene ArrayList mit ToDo's.


Der Server erwartet folgendes Protokoll:


Erstellen eines Accounts:

CreateToDo|E-Mail|Password

Falls Erfolgreich gibt der Server folgende Antwort zurück:

Result|true


Login:

Login|E-Mail|Password

Falls Erfolgreich gibt der Server folgende Antwort zurück:

Result|true|token


Logout:

Logout|token

Falls Erfolgreich gibt der Server folgende Antwort zurück:

Result|true


ToDo erstellen:

CreateToDo|token|Titel|Priority|Description

Falls Erfolgreich gibt der Server folgende Antwort zurück:

Result|true|id des ToDo


ToDo löschen:

DeleteToDo|token|id des ToDo

Falls Erfolgreich gibt der Server folgende Antwort zurück:

Result|true


ToDo anzeigen:

GetToDo|token|id des ToDo

Falls Erfolgreich gibt der Server folgende Antwort zurück:

Result|true|id|Titel|Priority|Description


ToDo's auflisten:

ListToDos|token

Falls Erfolgreich gibt der Server folgende Antwort zurück:

Result|true|id|....|id


Ping

Ping oder Ping|token

Falls Erfolgreich gibt der Server folgende Antwort zurück:

Result|true


Accounts und ToDos werden abgespeichert und beim Starten des Servers geladen.

Die Passwörter werden nicht gehashed!
