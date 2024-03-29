package math_exprs;

public class Lexeme {

    public final Type type;
    public final String value;

    public Lexeme(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Lexeme(Type type) {
        this(type, type.defaultValue);
    }

    public enum Type {
        NUMBER,
        PLUS("+"),
        MINUS("-"),
        MULTIPLICATION("*"),
        DIVISION("/"),
        POWER("^"),
        LEFT_BRACKET("("),
        RIGHT_BRACKET(")"),
        EOF(null);

        private final String defaultValue;

        Type(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        Type() {
            this(null);
        }
    }
}
