package wsh.eval;

import com.google.inject.Guice;
import com.google.inject.Injector;
import wsh.eval.command.CleanVariablesCommand;
import wsh.eval.command.Command;
import wsh.eval.command.CommandHelper;
import wsh.eval.command.ExitCommand;
import wsh.eval.command.PrintVariablesCommand;
import wsh.eval.module.InterpreterModule;
import wsh.eval.number.Number;

import java.util.Scanner;

/**
 * Console for the interpreter
 * Enter numeric expression
 * Supported Commands
 * - vars: print variables
 * - clean: remove all the stores variables
 * - exit: exit the console
 */
public class Main {
    private static Scanner scanner = null;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(Main::cleanup));
        try (Scanner scanner = new Scanner(System.in)) {
            Injector injector = Guice.createInjector(new InterpreterModule());
            Interpreter interpreter = injector.getInstance(Interpreter.class);
            printInstructions();
            while (true) {
                String text = scanner.nextLine();
                Class<Command> commandClazz = CommandHelper.getCommand(text);
                if (commandClazz == null) {
                    // Not a command. Evaluate the expression
                    try {
                        Number result = interpreter.interpret(text);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println("Fail to interpret!");
                        e.printStackTrace();
                    }
                } else if (commandClazz.equals(ExitCommand.class)) {
                    ExitCommand.builder().exitCode(0).build()
                            .execute();
                } else if (commandClazz.equals(PrintVariablesCommand.class)) {
                    PrintVariablesCommand.builder().varStore(interpreter.getVarStore()).build()
                            .execute();
                } else if (commandClazz.equals(CleanVariablesCommand.class)) {
                    CleanVariablesCommand.builder().varStore(interpreter.getVarStore()).build()
                            .execute();
                }
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
        System.out.println(CommandHelper.getCommandDescription());
    }
}
