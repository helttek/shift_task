package shift.exceptions;

public class ArgsParsingErrorException extends RuntimeException {
    public ArgsParsingErrorException(String message) {
        super(message);
    }
}
