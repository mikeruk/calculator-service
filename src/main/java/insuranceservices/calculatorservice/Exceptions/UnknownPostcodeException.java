package insuranceservices.calculatorservice.Exceptions;

public class UnknownPostcodeException extends RuntimeException {

    private final String postcode;

    public UnknownPostcodeException(String postcode) {
        super("Unbekannte Postcode: " + postcode);
        this.postcode = postcode;
    }

    public String getPostcode() {
        return postcode;
    }
}
