package math_exprs;

import java.io.IOException;
import java.text.ParseException;

public class Parser {

    private final Lexer lexer;
    private Lexeme curr;

    public Parser(Lexer lexer) throws IOException, ParseException {
        this.lexer = lexer;
        curr = lexer.nextLexeme();
    }

    public int executeCalculations() throws IOException, ParseException {
        int result = executeExpr();
        if (curr.type != Lexeme.Type.EOF)
            throw new ParseException("Expected EOF", 0);
        return result;
    }

    private int executeExpr() throws IOException, ParseException {
        int result = executeTerm();
        Lexeme.Type type = curr.type;

        while (type == Lexeme.Type.PLUS || type == Lexeme.Type.MINUS) {
            curr = lexer.nextLexeme();
            if (type == Lexeme.Type.PLUS)
                result += executeTerm();
            else
                result -= executeTerm();
            type = curr.type;
        }

        return result;
    }

    private int executeTerm() throws IOException, ParseException {
        int result = executeFractal();
        Lexeme.Type type = curr.type;

        while (type == Lexeme.Type.MULTIPLICATION || type == Lexeme.Type.DIVISION) {
            curr = lexer.nextLexeme();
            if (type == Lexeme.Type.MULTIPLICATION)
                result *= executeFractal();
            else
                result /= executeFractal();
            type = curr.type;
        }

        return result;
    }

    private int executeFractal() throws IOException, ParseException {
        int result = executePower();
        if (curr.type == Lexeme.Type.POWER) {
            curr = lexer.nextLexeme();
            return (int) Math.pow(result, executeFractal());
        }
        return result;
    }

    private int executePower() throws IOException, ParseException {
        if (curr.type == Lexeme.Type.MINUS) {
            curr = lexer.nextLexeme();
            return -executeAtom();
        }
        return executeAtom();
    }

    private int executeAtom() throws IOException, ParseException {
        if (curr.type == Lexeme.Type.NUMBER) {
            int result = Integer.parseInt(curr.value);
            curr = lexer.nextLexeme();
            return result;
        } else if (curr.type == Lexeme.Type.LEFT_BRACKET) {
            curr = lexer.nextLexeme();
            int result = executeExpr();
            if (curr.type != Lexeme.Type.RIGHT_BRACKET)
                throw new ParseException("Expected close bracket", 0);
            curr = lexer.nextLexeme();
            return result;
        } else
            throw new ParseException("Unexpected lexeme type: " + curr.type.name(), 0);
    }

}
