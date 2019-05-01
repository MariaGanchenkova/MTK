package stateMachine.nfa;

import org.junit.jupiter.api.Test;
import stateMachine.InvalidTransitionFunctionException;
import stateMachine.TransitionFunctionTest;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NonDeterministicTransitionFunctionTest {

    public static NonDeterministicTransitionFunction buildFunc(String... lines) {
        final NonDeterministicTransitionFunction function = new NonDeterministicTransitionFunction();
        TransitionFunctionTest.loadFunction(function, lines);
        return function;
    }

    void assertInvalid(String... lines) {
        assertThrows(InvalidTransitionFunctionException.class, () -> {
            final NonDeterministicTransitionFunction function = new NonDeterministicTransitionFunction();
            function.loadTransitions(new StringReader(String.join("\n", lines)));
        });
    }

    @Test
    void transitEmpty() {
        TransitionFunctionTest.assertTransits(buildFunc(""));
    }

    @Test
    void transitSingle() {
        TransitionFunctionTest.assertTransits(buildFunc("", "1 a 2"), TransitionFunctionTest.trc(1, 'a', 2));
        TransitionFunctionTest.assertTransits(buildFunc("", "5 c 9"), TransitionFunctionTest.trc(5, 'c', 9));
    }

    @Test
    void transitCheckFinals() {
        TransitionFunctionTest.assertFinals(buildFunc("2"), 2);
        TransitionFunctionTest.assertFinals(buildFunc("5 9"), 5, 9);
        TransitionFunctionTest.assertFinals(buildFunc("1 3", "1 a 2"), 1, 3);
    }

    @Test
    void transitMultiRules() {
        TransitionFunctionTest.assertTransits(buildFunc("", "1 a 2", "2 b 1"), TransitionFunctionTest.trc(1, 'a', 2), TransitionFunctionTest.trc(2, 'b', 1));
        TransitionFunctionTest.assertTransits(buildFunc("", "5 c 9", "8 d 7", "42 z 199"), TransitionFunctionTest.trc(5, 'c', 9), TransitionFunctionTest.trc(8, 'd', 7), TransitionFunctionTest.trc(42, 'z', 199));
    }

    @Test
    void transitHardRule() {
        TransitionFunctionTest.assertTransits(buildFunc("", "1 test 2"), TransitionFunctionTest.trc(1, 't', 2), TransitionFunctionTest.trc(1, 'e', 2), TransitionFunctionTest.trc(1, 's', 2), TransitionFunctionTest.trc(1, 't', 2));
    }

    @Test
    void transitCheckRedundant() {
        TransitionFunctionTest.assertTransits(buildFunc("", "1 a 2"), TransitionFunctionTest.tr(1, 'b'), TransitionFunctionTest.tr(2, 'a'));
    }

    @Test
    void transitInvalid() {
        assertInvalid();
        assertInvalid("test");
        assertInvalid("", "test a 2");
        assertInvalid("", "2 2 2");
        assertInvalid("", "2");
        assertInvalid("", "2 a");
    }

    @Test
    void transitRepeats() {
        TransitionFunctionTest.assertTransits(buildFunc("", "1 a 2", "1 a 2"), TransitionFunctionTest.trc(1, 'a', 2));
        TransitionFunctionTest.assertTransits(buildFunc("", "1 a 2", "1 a 3"), TransitionFunctionTest.trc(1, 'a', 2, 3));
    }

}