package math_exprs;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    void assertCalcs(String input, int result) throws IOException, ParseException {
        try (Lexer lexer = new Lexer(new StringReader(input))) {
            assertEquals(result, new Parser(lexer).executeCalculations());
        }
    }

    @Test
    void executeCalculationsSingleNumber() throws IOException, ParseException {
        assertCalcs("12300", 12300);
    }

    @Test
    void executeCalculationsSingleOperation() throws IOException, ParseException {
        assertCalcs("2 + 2", 4);
        assertCalcs("10 - 28", -18);
        assertCalcs("25 * 4", 100);
        assertCalcs("500 / 25", 20);
        assertCalcs("2 ^ 12", 4096);
    }

    @Test
    void executeCalculationsBracketsSingleNumber() throws IOException, ParseException {
        assertCalcs("(15)", 15);
        assertCalcs("((128))", 128);
        assertCalcs("((((2048))))", 2048);
    }

    @Test
    void executeCalculationsBracketsAndOperations() throws IOException, ParseException {
        assertCalcs("(- 9)", -9);
        assertCalcs("100 + (-100)", 0);
        assertCalcs("(2 + 2)", 4);
        assertCalcs("((10 - 28))", -18);
        assertCalcs("(25) * (4)", 100);
        assertCalcs("((500)) / ((25))", 20);
        assertCalcs("((((2)) ^ ((12))))", 4096);
    }

    @Test
    void executeCalculationsRightComplex() throws IOException, ParseException {
        assertCalcs("3 ^ 6 ^ 1 / 2", 364);
        assertCalcs("2 * 4 * 8 * 16 * 32 * 64 * 128", 268435456);
        assertCalcs("(2 + 8) * 4 - 5 / (-8)", 40);
    }

}
