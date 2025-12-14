package insuranceservices.calculatorservice.services.impl;

import insuranceservices.calculatorservice.services.FactorService;
import insuranceservices.calculatorservice.Exceptions.UnknownPostcodeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class FactorServiceImplTest {

    @Autowired
    private FactorService factorService;

    @Test
    void returnsFactorForKnownPostcode() {
        double factor = factorService.getRegionFactorForPostcode("79189");

        // Baden-Württemberg 1.4
        assertThat(factor).isEqualTo(1.4);
    }

    @Test
    void throwsForUnknownPostcode() {
        assertThatThrownBy(() -> factorService.getRegionFactorForPostcode("99999"))
                .isInstanceOf(UnknownPostcodeException.class)
                .hasMessageContaining("Unbekannte Postcode: 99999");
    }
}
