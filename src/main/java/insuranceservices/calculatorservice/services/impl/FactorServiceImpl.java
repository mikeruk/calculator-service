package insuranceservices.calculatorservice.services.impl;

import insuranceservices.calculatorservice.DTOs.enums.VehicleType;
import insuranceservices.calculatorservice.Exceptions.UnknownPostcodeException;
import insuranceservices.calculatorservice.services.FactorService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class FactorServiceImpl implements FactorService {

    private final Map<String, Double> bundeslandFactors = new HashMap<>();
    private final Map<String, String> postcodeToBundesland = new HashMap<>();

    public FactorServiceImpl() {
        bundeslandFactors.put("Bayern", 1.0);
        bundeslandFactors.put("Niedersachsen", 1.2);
        bundeslandFactors.put("Baden-Württemberg", 1.4);
        bundeslandFactors.put("Nordrhein-Westfalen", 1.6);
        bundeslandFactors.put("Brandenburg", 1.8);
        bundeslandFactors.put("Mecklenburg-Vorpommern", 2.1);
        bundeslandFactors.put("Hessen", 2.3);
        bundeslandFactors.put("Sachsen-Anhalt", 2.5);
        bundeslandFactors.put("Rheinland-Pfalz", 2.7);
        bundeslandFactors.put("Sachsen", 2.9);
        bundeslandFactors.put("Thüringen", 3.1);
        bundeslandFactors.put("Schleswig-Holstein", 3.3);
        bundeslandFactors.put("Saarland", 3.5);
        bundeslandFactors.put("Berlin", 3.7);
        bundeslandFactors.put("Hamburg", 3.9);
        bundeslandFactors.put("Bremen", 4.1);
    }

    @PostConstruct
    private void init() {
        loadPostcodesFromCsv();
    }

    private void loadPostcodesFromCsv() {
        Path path = Paths.get("src/main/resources/postcodes.csv");

        if (!Files.exists(path)) {
            throw new IllegalStateException("postcodes.csv not found at " + path.toAbsolutePath());
        }

        try (var reader = Files.newBufferedReader(path)) {

            CSVParser parser = CSVParser.parse(reader,
                    CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .withIgnoreSurroundingSpaces()
                            .withTrim()
            );

            for (CSVRecord record : parser) {

                if (record.size() <= 6) {
                    System.err.println("Skipping malformed row: " + record);
                    continue;
                }

                String bundesland = sanitize(record.get(2)); // REGION1
                String postcode   = sanitize(record.get(6)); // POSTLEITZAHL

                if (!postcode.isEmpty() && !bundesland.isEmpty()) {
                    postcodeToBundesland.putIfAbsent(postcode, bundesland);
                }
            }

        } catch (IOException e) {
            throw new IllegalStateException("Failed to load postcodes.csv", e);
        }
    }

    private String sanitize(String raw) {
        return raw == null ? "" : raw.trim();
    }

    @Override
    public double getRegionFactorForPostcode(String postcode)
    {
        String bundesland = postcodeToBundesland.get(postcode);

        if (bundesland == null) {
            throw new UnknownPostcodeException(postcode);
        }

        return bundeslandFactors.get(bundesland);
    }

    @Override
    public double getKmFactor(double km)
    {
        if (km <= 5000) {
            return 0.5;
        } else if (km <= 10000) {
            return 1.0;
        } else if (km <= 20000) {
            return 1.5;
        } else {
            return 2.0;
        }
    }

    @Override
    public double getVehicleTypeFactor(VehicleType vehicleType) {
        return switch (vehicleType) {
            case PKW -> 70.0;
            case LKW -> 80.2;
            case WOHNMOBIL -> 50.8;
            case MOTORRAD -> 20.5;
        };
    }
}
