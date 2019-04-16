package stateMachine;

public class InvalidTransitionFunctionException extends RuntimeException {

    public InvalidTransitionFunctionException(Throwable cause) {
        super(cause);
    }

    public InvalidTransitionFunctionException(String message) {
        super(message);
    }

}
