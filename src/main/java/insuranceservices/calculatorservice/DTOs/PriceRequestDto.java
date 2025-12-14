package insuranceservices.calculatorservice.DTOs;

import insuranceservices.calculatorservice.DTOs.enums.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class PriceRequestDto {


    @Schema(description = "A 'double' number, example 12000 or 12000.5)")
    @NotNull(message = "kilometers is required")
    @Positive(message = "kilometers must be positive")
    @DecimalMax(value = "999999999", message = "kilometers must be less than 1,000,000,000")
    private Double kilometers;

    @NotNull(message = "vehicleType is required")
    private VehicleType vehicleType;

    @NotBlank(message = "postcode is required")
    private String postcode;

    public Double getKilometers() {
        return kilometers;
    }

    public void setKilometers(Double kilometers) {
        this.kilometers = kilometers;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
