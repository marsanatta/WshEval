package wsh.eval.command;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCommandHelperTest {

    @Test
    void getCommandNames() {
        Set<String> commandNames = UserCommandHelper.getUserCommandNames();
        assertEquals(Set.of("vars", "exit", "clean"), commandNames);
    }

    @Test
    void getCommand_vars() {
        Class<Command> clazz = UserCommandHelper.getUserCommand("vars");
        assertEquals(clazz, PrintVariablesCommand.class);
    }

    @Test
    void getCommand_clean() {
        Class<Command> clazz = UserCommandHelper.getUserCommand("clean");
        assertEquals(clazz, CleanVariablesCommand.class);
    }

    @Test
    void getCommand_exit() {
        Class<Command> clazz = UserCommandHelper.getUserCommand("exit");
        assertEquals(clazz, ExitCommand.class);
    }

    @Test
    void getCommandDescription() {
        assertEquals("Supported Commands:\n"
                + " -exit: Exit the program\n"
                + " -vars: Print variables\n"
                + " -clean: Remove all variables\n", UserCommandHelper.getUserCommandDescription());
    }
}