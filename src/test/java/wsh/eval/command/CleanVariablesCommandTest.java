package wsh.eval.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wsh.eval.variable.VariableStore;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CleanVariablesCommandTest {

    @Mock
    private VariableStore varStore;

    @Test
    void testExecute() {
        CleanVariablesCommand command = CleanVariablesCommand.builder()
                .varStore(varStore)
                .build();
        command.execute();
        verify(varStore, times(1)).clean();
    }
}