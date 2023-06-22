package tbl.eval.token;

public enum TokenType {
    NUM, // number
    PLUS, // +
    PRE_INCREMENT, // ++ before variable
    POST_INCREMENT, // ++ after variable
    MINUS, // -
    PRE_DECREMENT, // -- before variable
    POST_DECREMENT, // -- after variable
    MUL, // *
    DIV, // /
    REM, // %
    LPAREN, // (
    RPAREN, // )
    VAR, // variable
    ASSIGN, // =
    ADD_ASSIGN, // +=
    SUB_ASSIGN, // -=
    MUL_ASSIGN, // *=
    DIV_ASSIGN, // /=
    REM_ASSIGN, // %=
    EOF // end of input

}
