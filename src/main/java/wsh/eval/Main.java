package wsh.eval;

import com.google.inject.Guice;
import com.google.inject.Injector;
import wsh.eval.module.InterpreterModule;
import wsh.eval.number.Number;
import wsh.eval.variable.VariableStore;

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
    public static final String COMMAND_PRINT_VARS = "vars";
    public static final String COMMAND_CLEAN_VARS = "clean";
    public static final String COMMAND_EXIT = "exit";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Injector injector = Guice.createInjector(new InterpreterModule());
            Interpreter interpreter = injector.getInstance(Interpreter.class);
            VariableStore varStore = interpreter.getVarStore();
            System.out.println("Please enter the expression here. "
                    + "The result of evaluation will be output after press enter");
            System.out.println("Commands:\n"
                    + " - vars: print variables\n"
                    + " - clean: remove all stored variables\n"
                    + " - exit: exit the program");
            while (true) {
                String text = scanner.nextLine();
                if (text.equals(COMMAND_EXIT)) {
                    System.out.println("Good bye!");
                    break;
                } else if (text.equals(COMMAND_PRINT_VARS)) {
                    System.out.println(varStore);
                } else if (text.equals(COMMAND_CLEAN_VARS)) {
                    varStore.clean();
                    System.out.println("Variables are clean up");
                } else {
                    try {
                        Number result = interpreter.interpret(text);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println("Invalid expression!");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
