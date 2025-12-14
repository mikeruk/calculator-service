package insuranceservices.calculatorservice.services.impl;

import insuranceservices.calculatorservice.DTOs.PriceRequestDto;
import insuranceservices.calculatorservice.DTOs.PriceResponseDto;
import insuranceservices.calculatorservice.DTOs.enums.VehicleType;
import insuranceservices.calculatorservice.services.FactorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class PriceCalculationServiceImplTest {

    private FactorService factorService;
    private PriceCalculationServiceImpl priceService;

    @BeforeEach
    void setUp() {
        factorService = spy(new FactorServiceImpl());
        priceService = new PriceCalculationServiceImpl(factorService);
    }

    @Test
    void calculatesPriceCorrectly() {
        PriceRequestDto dto = new PriceRequestDto();
        dto.setKilometers(3000d);
        dto.setVehicleType(VehicleType.PKW);
        dto.setPostcode("79189");

        doReturn(2.0).when(factorService).getRegionFactorForPostcode("79189");

        PriceResponseDto res = priceService.calculatePrice(dto);

        assertThat(res.getKmFactor()).isEqualTo(0.5);
        assertThat(res.getVehicleTypeFactor()).isEqualTo(70.0);
        assertThat(res.getRegionFactor()).isEqualTo(2.0);
        assertThat(res.getPrice()).isEqualTo(0.5 * 70.0 * 2.0);
    }

    @Test
    void vehicleTypeFactorIsCorrect() {
        doReturn(1.0).when(factorService).getRegionFactorForPostcode("12345");

        PriceRequestDto dto = new PriceRequestDto();
        dto.setKilometers(10000d);
        dto.setPostcode("12345");

        dto.setVehicleType(VehicleType.PKW);
        assertThat(priceService.calculatePrice(dto).getVehicleTypeFactor()).isEqualTo(70.0);

        dto.setVehicleType(VehicleType.LKW);
        assertThat(priceService.calculatePrice(dto).getVehicleTypeFactor()).isEqualTo(80.2);

        dto.setVehicleType(VehicleType.WOHNMOBIL);
        assertThat(priceService.calculatePrice(dto).getVehicleTypeFactor()).isEqualTo(50.8);

        dto.setVehicleType(VehicleType.MOTORRAD);
        assertThat(priceService.calculatePrice(dto).getVehicleTypeFactor()).isEqualTo(20.5);
    }
}
