package insuranceservices.calculatorservice.services;

import insuranceservices.calculatorservice.DTOs.enums.VehicleType;

public interface FactorService {

    double getRegionFactorForPostcode(String postcode);

    double getKmFactor(double km);

    double getVehicleTypeFactor(VehicleType vehicleType);
}
