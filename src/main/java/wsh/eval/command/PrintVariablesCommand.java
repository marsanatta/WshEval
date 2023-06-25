package wsh.eval.command;

import lombok.Builder;
import lombok.NonNull;
import wsh.eval.variable.VariableStore;

/**
 * Command to print variables
 */
@CommandAnnotation(name = "vars", desc = "Print variables")
@Builder
public class PrintVariablesCommand implements Command {

    @NonNull
    private VariableStore varStore;

    @Override
    public void execute() {
        System.out.println(varStore);
    }

}
