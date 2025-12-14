package insuranceservices.calculatorservice.controllers;

import insuranceservices.calculatorservice.DTOs.PriceRequestDto;
import insuranceservices.calculatorservice.DTOs.PriceResponseDto;
import org.springframework.http.ResponseEntity;

public interface PriceController {

    ResponseEntity<PriceResponseDto> calculatePrice(PriceRequestDto request);
}
