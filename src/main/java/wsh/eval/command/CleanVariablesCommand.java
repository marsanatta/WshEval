package wsh.eval.command;

import lombok.Builder;
import lombok.NonNull;
import wsh.eval.variable.VariableStore;

import java.util.Set;

/**
 * Command to clean variables
 */
@CommandAnnotation(name = "clean", desc = "Remove all variables")
@Builder
public class CleanVariablesCommand implements Command {

    @NonNull
    private VariableStore varStore;

    @Override
    public void execute() {
        Set<String> removedVarNames = varStore.clean();
        System.out.println("Removed variables: " + removedVarNames);
    }

}
