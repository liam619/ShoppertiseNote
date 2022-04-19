package shoppertise.notes.exception;

public class NoElementFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public NoElementFoundException() { }

    public NoElementFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
