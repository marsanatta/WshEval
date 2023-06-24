package tbl.eval.token;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import tbl.eval.exceptions.BuildTokenException;

/**
 * Token is the basic unit to be processed by the Parser
 * Lexer generates tokens from the raw input
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Token {
    @NonNull
    private final TokenType type;
    private final String value;

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
            if (value != null ^ another.getValue() != null) {
                return false;
            }
            if (value != null && another.getValue() != null && !value.equals(another.getValue())) {
                return false;
            }
            return type.equals(another.getType());
        } else {
            return false;
        }
    }

    public static class Builder {
        private TokenType type;
        private String value;

        public Builder type(TokenType type) {
            this.type = type;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Token build() {
            if (type == TokenType.NUM && value == null) {
                throw new BuildTokenException("value cannot be null for Number token");
            }
            if (type == TokenType.VAR && value == null) {
                throw new BuildTokenException("value cannot be null for Variable token");
            }
            if (type.getValue() != null) {
                value = type.getValue();
            }
            return new Token(type, value);
        }

    }
}
