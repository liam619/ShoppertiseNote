package shoppertise.notes.exception;

public class EmptyInputException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public EmptyInputException() { }

    public EmptyInputException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
