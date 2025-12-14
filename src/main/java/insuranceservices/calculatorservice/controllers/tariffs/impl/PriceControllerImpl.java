package insuranceservices.calculatorservice.controllers.tariffs.impl;

import insuranceservices.calculatorservice.DTOs.PriceRequestDto;
import insuranceservices.calculatorservice.DTOs.PriceResponseDto;
import insuranceservices.calculatorservice.controllers.PriceController;
import insuranceservices.calculatorservice.services.PriceCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/price")
public class PriceControllerImpl implements PriceController {

    private final PriceCalculationService priceCalculationService;

    public PriceControllerImpl(PriceCalculationService priceCalculationService)
    {
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    @Operation(
            summary = "Calculate insurance price",
            description = "Calculates an insurance price based on kilometers, vehicle type and region (postcode)."
    )
    @ApiResponse(responseCode = "200", description = "Price calculated successfully")
    @PostMapping("/calculate")
    public ResponseEntity<PriceResponseDto> calculatePrice(@Valid @RequestBody PriceRequestDto request)
    {
        PriceResponseDto response = priceCalculationService.calculatePrice(request);
        return ResponseEntity.ok(response);
    }
}
