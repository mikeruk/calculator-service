[![CI](https://github.com/mikeruk/calculator-service/actions/workflows/actions.yml/badge.svg?branch=master)](https://github.com/mikeruk/calculator-service/actions/workflows/actions.yml)
# Kalkulator-Service

Ein Spring Boot-Service zur Berechnung von Versicherungsprämien basierend auf Kilometerleistung, Fahrzeugtyp und Region.

## Funktionen
- REST-API für externe Partner
- OpenAPI/Swagger-Dokumentation
- CSV-basierte Postleitzahl-Bundesland-Zuordnung
- Faktorbasierte Preisberechnung:
- Kilometerfaktor
- Fahrzeugtyp-Faktor
- Regionsfaktor (nach Bundesland)

## Tech Stack
- Java 21
- Spring Boot 3.3
- Spring Web & Validation
- springdoc-openapi (Swagger UI)
- Apache Commons CSV

## Ausführen des Dienstes
./gradlew bootRun

Der Dienst startet unter:
http://localhost:8282

API-Dokumentation:
Swagger UI: http://localhost:8282/swagger-ui.html

OpenAPI JSON: http://localhost:8282/v3/api-docs

Beispielanfrage:
POST /api/v1/price/calculate
```json
{
„kilometers“: 12000.1,
‚vehicleType‘: „PKW“,
„postcode“: „80331“
}
```

Beispielantwort:
```json
{
  „price“: 10.35,
  „kmFactor“: 1.5,
  „vehicleTypeFactor“: 1.0,
  „regionFactor“: 6.9
}
```

Übersicht:
• PriceController – Stellt den Berechnungsendpunkt bereit.

• PriceCalculationService – Kombiniert die Faktoren Kilometer, Fahrzeugtyp und Region, um den Endpreis zu berechnen.

• FactorService – Löst den Regionsfaktor durch Zuordnung von Postleitzahl – Bundesland – Faktor.

• CSV Loader (in FactorServiceImpl) – Lädt postcodes.csv beim Start in den Speicher.


## DOKUMENTATION – So generieren Sie die Dokumente automatisch mit dem AsciiDoc-Tool:

1. Die Datei „build.gradle“ muss dieses Plugin enthalten:
```gradle
plugins {
    ...
    id ‚org.asciidoctor.jvm.convert‘ version ‚4.0.2‘
}

...

asciidoctor {
    sources {
        include ‚index.adoc‘
    }
    baseDirFollowsSourceDir()
}
```

2. Die folgende Ordnerstruktur muss manuell innerhalb des Projekts erstellt werden:
   mkdir -p src/docs/asciidoc

3. Erstellen Sie die Indexdatei:
   nano src/docs/asciidoc/index.adoc

4. Die Datei src/docs/asciidoc/index.adoc enthält BEREITS die Dokumente + Markup.

5. Führen Sie dann den Befehl aus:
   ./gradlew asciidoctor
   Dadurch werden die Dokumente im Ordner build/docs/asciidoc/index.html generiert. Öffnen und lesen Sie die Dokumente.


## Testen

Das Projekt umfasst Unit-Tests für die wichtigsten Servicekomponenten:

### Testabdeckung
- **PriceCalculationServiceImplTest**  
  Überprüft den km-Faktor, den Fahrzeugtyp-Faktor, die Verwendung des Regionsfaktors und die endgültige Preisberechnung.

- **FactorServiceImplTest**  
  Stellt die korrekte Zuordnung von Postleitzahl, Bundesland und Regionsfaktor auf der Grundlage der CSV-Daten sicher.

- **PriceControllerImplTest**  
  Testet den REST-Endpunkt mit MockMvc, um Anfrage/Antwort zu validieren.

### Ausführen von Tests

Mit Gradle:

```bash
./gradlew test
```
