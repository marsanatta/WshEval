package wsh.eval.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wsh.eval.command.PrintVariablesCommand;
import wsh.eval.variable.VariableStore;

@ExtendWith(MockitoExtension.class)
class PrintVariablesCommandTest {

    @Mock
    private VariableStore varStore;

    @Test
    void execute() {
        PrintVariablesCommand command = PrintVariablesCommand.builder()
                .varStore(varStore)
                .build();
        command.execute();
        // Mockito cannot verify toString got called
    }
}