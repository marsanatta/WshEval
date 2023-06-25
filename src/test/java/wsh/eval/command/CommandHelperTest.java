package wsh.eval.command;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandHelperTest {

    @Test
    void getCommandNames() {
        Set<String> commandNames = CommandHelper.getCommandNames();
        assertEquals(Set.of("vars", "exit", "clean"), commandNames);
    }

    @Test
    void getCommand_vars() {
        Class<Command> clazz = CommandHelper.getCommand("vars");
        assertEquals(clazz, PrintVariablesCommand.class);
    }

    @Test
    void getCommand_clean() {
        Class<Command> clazz = CommandHelper.getCommand("clean");
        assertEquals(clazz, CleanVariablesCommand.class);
    }

    @Test
    void getCommand_exit() {
        Class<Command> clazz = CommandHelper.getCommand("exit");
        assertEquals(clazz, ExitCommand.class);
    }

    @Test
    void getCommandDescription() {
        assertEquals("Supported Commands:\n"
                + " -exit: Exit the program\n"
                + " -vars: Print variables\n"
                + " -clean: Remove all variables\n", CommandHelper.getCommandDescription());
    }
}