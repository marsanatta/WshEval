package wsh.eval;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import wsh.eval.command.CleanVariablesCommand;
import wsh.eval.command.Command;
import wsh.eval.command.UserCommandHelper;
import wsh.eval.command.ExitCommand;
import wsh.eval.command.InterpretCommand;
import wsh.eval.command.PrintVariablesCommand;
import wsh.eval.module.InterpreterModule;

import java.util.Scanner;

/**
 * Console Prompt for the interpreter
 */
public class ConsolePrompt {
    private static Scanner scanner = null;
    private static final Injector injector = Guice.createInjector(new InterpreterModule());
    private static final Interpreter interpreter = injector.getInstance(
            Key.get(Interpreter.class, Names.named("ConsolePrompt")));

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(ConsolePrompt::cleanup));
        printInstructions();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                handleCommand(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanup() {
        if (scanner != null) {
            scanner.close();
        }
    }

    private static void printInstructions() {
        System.out.println("Please enter the expression here. "
                + "The result of evaluation will be output after press enter.");
        System.out.println(UserCommandHelper.getUserCommandDescription());
    }

    private static void handleCommand(String text) {
        Class<Command> commandClass = UserCommandHelper.getUserCommand(text);
        if (commandClass == null) {
            // Not a user command. Execute as interpret command
            InterpretCommand.builder().interpreter(interpreter).text(text).build()
                    .execute();
        } else if (commandClass.equals(ExitCommand.class)) {
            ExitCommand.builder().exitCode(0).build()
                    .execute();
        } else if (commandClass.equals(PrintVariablesCommand.class)) {
            PrintVariablesCommand.builder().varStore(interpreter.getVarStore()).build()
                    .execute();
        } else if (commandClass.equals(CleanVariablesCommand.class)) {
            CleanVariablesCommand.builder().varStore(interpreter.getVarStore()).build()
                    .execute();
        }
    }
}
