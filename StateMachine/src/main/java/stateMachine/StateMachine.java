package stateMachine;

import java.io.IOException;
import java.io.Reader;

public interface StateMachine {

    boolean validateInput(Reader reader) throws IOException;

}
