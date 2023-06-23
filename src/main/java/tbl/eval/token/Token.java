package tbl.eval.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import tbl.eval.exceptions.BuildTokenException;
import tbl.eval.number.Number;

/**
 * Token is the basic unit to be processed by the Parser
 * Lexer generates tokens from the raw input
 */
@Getter
@AllArgsConstructor
public class Token {
    @NonNull
    private final TokenType type;
    private final Object value;

    @Override
    public String toString() {
        return String.format("[%s:%s]", type.name(), value);
    }

    public static Token.Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token) {
            Token another = (Token)obj;
            return type.equals(another.getType()) && value.equals(another.getValue());
        } else {
            return false;
        }
    }

    public static class Builder {
        private TokenType type;
        private Object value;

        public Builder type(TokenType type) {
            this.type = type;
            return this;
        }

        public Builder value(Object value) {
            this.value = value;
            return this;
        }

        public Token build() {
            if (type == TokenType.NUM && value == null) {
                throw new BuildTokenException("value cannot be null for Number token");
            }
            if (type == TokenType.NUM && !(value instanceof Number)) {
                throw new BuildTokenException("value must be Number for Number token");
            }
            if (type == TokenType.VAR && value == null) {
                throw new BuildTokenException("value cannot be null for Variable token");
            }
            if (type == TokenType.VAR && !(value instanceof String)) {
                throw new BuildTokenException("value must be String for Variable token to indicate the variable name");
            }
            return new Token(type, value);
        }

    }
}
