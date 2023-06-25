package wsh.eval.command;

import lombok.Builder;

/**
 * Command to exit the program
 */
@CommandAnnotation(name = "exit", desc = "Exit the program")
@Builder
public class ExitCommand implements Command {

    private static final String NAME = "exit";

    private int exitCode = 0;

    @Override
    public void execute() {
        System.out.println("Thanks for using the service. Good bye!");
        System.exit(exitCode);
    }
}
