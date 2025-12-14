package insuranceservices.calculatorservice.services;

import insuranceservices.calculatorservice.DTOs.PriceRequestDto;
import insuranceservices.calculatorservice.DTOs.PriceResponseDto;

public interface PriceCalculationService {

    PriceResponseDto calculatePrice(PriceRequestDto request);
}
