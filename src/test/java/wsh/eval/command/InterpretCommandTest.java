package wsh.eval.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wsh.eval.Interpreter;
import wsh.eval.exceptions.InvalidSyntaxException;
import wsh.eval.number.Number;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterpretCommandTest {

    @Mock
    private Interpreter interpreter;

    @BeforeEach
    void setUp() throws Exception {
        when(interpreter.interpret(anyString())).thenReturn(Number.valueOf(1L));
    }

    @Test
    void testExecute() throws Exception {
        String text = "mock";
        InterpretCommand.builder().interpreter(interpreter).text(text).build()
                .execute();
        verify(interpreter, times(1)).interpret(eq(text));
    }

    @Test
    void testExecute_InterpreterException() throws Exception {
        when(interpreter.interpret(anyString())).thenThrow(new InvalidSyntaxException("error"));
        InterpretCommand.builder().interpreter(interpreter).text("mock").build()
                .execute();
    }
}