# Delish-Dish Anwendung - Android Studio Abschlussprojekt

Delish-Dish ist eine mobile Anwendung, entwickelt als Android Modul im Rahmen eines Abschlussprojekts in Android Studio mit der Programmiersprache Kotlin. 
Diese Anwendung ermöglicht es Benutzern, verschiedene Rezepte für Speisen und Cocktails zu sehen und in ihren Küchen zu verwenden. 
Die Anwendung arbeitet integriert mit Rezepten, die von einer RESTful-API geliefert werden, und falls das gewünschte Rezept nicht 
verfügbar ist, greift der von GPT-3.5 Turbo 
unterstützte Künstliche Intelligenz Rezeptgenerator 
(Recipes Generator) ein. Dieser KI ist ausschließlich auf die Erstellung von Speisen- und Cocktailrezepten beschränkt.

## Funktionen
- **Rezeptansicht**: Sehen Sie Speisen- und Cocktailrezepte einfach an.
- **Rezeptspeicherung**: Fügen Sie Rezepte zu Ihren Favoriten hinzu und speichern Sie sie lokal in der Room-Datenbank.
- **Notizen machen**: Sie können Notizen zu Rezepten machen und diese in der Anwendung speichern.
- **Kontoverwaltung**: Benutzerregistrierung und Anmeldung über Google Firebase.
- **Themenoptionen**: Dunkles Thema, helles Thema oder verwenden Sie das Thema Ihres Geräts.
- **MVVM Architektur**: Modernes und nachhaltiges Software-Entwicklungsdesign gemäß der MVVM-Architektur.

## Architekturkomponenten
- **Modelle**: Strukturen, die zur Verwaltung der Anwendungsdaten verwendet werden.
- **API-Dienste**: Dienste, die für den Datenaustausch der Anwendung verwendet werden.
- **Repository**: Verwaltung von Datenquellen.
- **MainViewModel**: Verwaltet die Interaktionen zwischen UI und Daten.
- **Room Datenbank**: Lokale Datenbankoperationen.
- **Utils**: Hilfsfunktionen und Werkzeuge.
- **UI Fragmente**: 12 Benutzeroberflächenkomponenten.

##

###
###

### Light Theme
*Light Theme bietet ein helles und klares Benutzererlebnis, perfekt für die Nutzung bei Tageslicht.*
###
![Light-Theme](https://github.com/SI-Classroom-Batch-013/android-abschluss-VolkanSyntax/assets/148045998/0a089784-84b7-4adb-9616-0c54d6ed151a)

##
###
###

### Dark Theme
*Dark Theme bietet ein dunkles und augenschonendes Benutzererlebnis, ideal für die Nutzung bei geringer Beleuchtung oder nachts.*
###
![Dark-Theme](https://github.com/SI-Classroom-Batch-013/android-abschluss-VolkanSyntax/assets/148045998/f4903da1-62e3-4fd0-87b8-02861d4ecd20)


