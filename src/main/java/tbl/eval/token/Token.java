package tbl.eval.token;

/**
 * Token is the basic unit to be processed by the Parser
 * Lexer generates tokens from the raw input
 */
public class Token {
    private final TokenType type;
    private final Object value;
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("[%s:%s]", type.name(), value);
    }
}
