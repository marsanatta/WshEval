package wsh.eval.command;

import lombok.Builder;
import lombok.NonNull;
import wsh.eval.Interpreter;
import wsh.eval.number.Number;

/**
 * Command to interpret an expression
 */
@CommandAnnotation(name = "interpret", desc = "interpret an expression", isUserCommand = false)
@Builder
public class InterpretCommand implements Command {

    @NonNull
    private Interpreter interpreter;

    @NonNull
    private String text;

    @Override
    public void execute() {
        try {
            Number result = interpreter.interpret(text);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Fail to interpret!");
            e.printStackTrace();
        }
    }
}
