package insuranceservices.calculatorservice.services.impl;

import insuranceservices.calculatorservice.DTOs.PriceRequestDto;
import insuranceservices.calculatorservice.DTOs.PriceResponseDto;
import insuranceservices.calculatorservice.services.FactorService;
import insuranceservices.calculatorservice.services.PriceCalculationService;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculationServiceImpl implements PriceCalculationService {

    private final FactorService factorService;

    public PriceCalculationServiceImpl(FactorService factorService) {
        this.factorService = factorService;
    }

    @Override
    public PriceResponseDto calculatePrice(PriceRequestDto request) {

        double km = request.getKilometers();
        double kmFactor = factorService.getKmFactor(km);
        double vehicleTypeFactor = factorService.getVehicleTypeFactor(request.getVehicleType());
        double regionFactor = factorService.getRegionFactorForPostcode(request.getPostcode());

        double price = kmFactor * vehicleTypeFactor * regionFactor;

        PriceResponseDto response = new PriceResponseDto();
        response.setKmFactor(kmFactor);
        response.setVehicleTypeFactor(vehicleTypeFactor);
        response.setRegionFactor(regionFactor);
        response.setPrice(price);

        return response;
    }

}
