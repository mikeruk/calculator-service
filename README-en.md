[![CI](https://github.com/mikeruk/calculator-service/actions/workflows/actions.yml/badge.svg?branch=master)](https://github.com/mikeruk/calculator-service/actions/workflows/actions.yml)
# Calculator Service

A Spring Boot service for calculating insurance prices based on kilometers, vehicle type, and region.

## Features
- REST API for external partners
- OpenAPI/Swagger documentation
- CSV-based postcode - Bundesland mapping
- factor-based price calculation:
    - kilometer factor
    - vehicle type factor
    - region factor (by Bundesland)

## Tech Stack
- Java 21
- Spring Boot 3.3
- Spring Web & Validation
- springdoc-openapi (Swagger UI)
- Apache Commons CSV

## Running the Service
./gradlew bootRun

Service starts at:
http://localhost:8282

API Documentation:
Swagger UI: http://localhost:8282/swagger-ui.html

OpenAPI JSON: http://localhost:8282/v3/api-docs

Example Request:
POST /api/v1/price/calculate
```json
{
"kilometers": 12000.1,
"vehicleType": "PKW",
"postcode": "80331"
}
```

Example response:
```json
{
  "price": 10.35,
  "kmFactor": 1.5,
  "vehicleTypeFactor": 1.0,
  "regionFactor": 6.9
}
```

Overview:
• PriceController - Exposes the calculation endpoint.

• PriceCalculationService - Combines km, vehicle type, and region factors to compute the final price.

• FactorService - Resolves region factor by mapping postcode - Bundesland - factor.

• CSV Loader (in FactorServiceImpl) - Loads postcodes.csv at startup into memory.



## DOCUMENTATION - how to auto-generate the docs via AsciiDoc tool:

1. The build.gradle file must have this plugin:
```gradle
plugins {
    ...
    id 'org.asciidoctor.jvm.convert' version '4.0.2'
}

...

asciidoctor {
    sources {
        include 'index.adoc'
    }
    baseDirFollowsSourceDir()
}
```

2. Have to manually create the following folder structure inside the project:
mkdir -p src/docs/asciidoc

3. and create the index file:
nano src/docs/asciidoc/index.adoc

4. The file src/docs/asciidoc/index.adoc ALREADY contains the docs + markup. 

5. Then run the command:
   ./gradlew asciidoctor
, this will generate the docs in the folder: build/docs/asciidoc/index.html - open and read the docs


## Testing

The project includes unit tests for the main service components:

### Test Coverage
- **PriceCalculationServiceImplTest**  
  Verifies km factor, vehicle type factor, region factor usage, and final price calculation.

- **FactorServiceImplTest**  
  Ensures correct postcode, Bundesland, and region factor mapping based on the CSV data.

- **PriceControllerImplTest**  
  Tests the REST endpoint using MockMvc to validate request/response.

### Running Tests

Using Gradle:

```bash
./gradlew test
```
