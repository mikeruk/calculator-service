package insuranceservices.calculatorservice.controllers.tariffs.impl;

import insuranceservices.calculatorservice.DTOs.PriceRequestDto;
import insuranceservices.calculatorservice.DTOs.PriceResponseDto;
import insuranceservices.calculatorservice.DTOs.enums.VehicleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerImplTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void calculatePriceReturns200AndCorrectBody() {
        PriceRequestDto dto = new PriceRequestDto();
        dto.setKilometers(3000d);
        dto.setVehicleType(VehicleType.PKW);
        dto.setPostcode("79189");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PriceRequestDto> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<PriceResponseDto> response =
                restTemplate.postForEntity(url("/api/v1/price/calculate"), entity, PriceResponseDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PriceResponseDto body = response.getBody();

        assertThat(body).isNotNull();
        assertThat(body.getKmFactor()).isEqualTo(0.5);
        assertThat(body.getVehicleTypeFactor()).isEqualTo(70.0);
        assertThat(body.getRegionFactor()).isEqualTo(1.4);
        assertThat(body.getPrice()).isEqualTo(0.5 * 70.0 * 1.4);
    }

    @Test
    void returnsBadRequestForUnknownPostcode() {
        PriceRequestDto dto = new PriceRequestDto();
        dto.setKilometers(3000d);
        dto.setVehicleType(VehicleType.PKW);
        dto.setPostcode("99999");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PriceRequestDto> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url("/api/v1/price/calculate"), entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Unbekannte Postcode: 99999");
    }
}
