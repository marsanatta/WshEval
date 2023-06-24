package tbl.eval.token;

public enum TokenType {
    PLUS("+"),
    DOUBLE_PLUS("++"),
    MINUS("-"),
    DOUBLE_MINUS("--"),
    MUL("*"),
    DIV("/"),
    REM("%"),
    LPAREN("("),
    RPAREN(")"),
    ASSIGN("="),
    ADD_ASSIGN("+="),
    SUB_ASSIGN("-="),
    MUL_ASSIGN("*="),
    DIV_ASSIGN("/="),
    REM_ASSIGN("%="),
    NUM(null), // number
    VAR(null), // variable
    EOF(null); // end of input

    private String value;

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
