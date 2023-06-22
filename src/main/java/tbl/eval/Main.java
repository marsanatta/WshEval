package tbl.eval;

import tbl.eval.lexer.Lexer;
import tbl.eval.number.Number;
import tbl.eval.parser.Parser;
import tbl.eval.variable.MapVariableStore;
import tbl.eval.variable.VariableStore;
import tbl.eval.interpreter.Interpreter;

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
        Scanner scanner = new Scanner(System.in);
        Lexer lexer = new Lexer();
        Parser parser = new Parser(lexer);
        VariableStore variableStore = new MapVariableStore();
        Interpreter interpreter = new Interpreter(lexer, parser, variableStore);
        try {
            while (true) {
                String text = scanner.nextLine();
                if (text.equals(COMMAND_EXIT)) {
                    System.out.println("Good bye!");
                    break;
                } else if (text.equals(COMMAND_PRINT_VARS)) {
                    System.out.println(variableStore);
                } else if (text.equals(COMMAND_CLEAN_VARS)) {
                    variableStore.clean();
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
        } finally {
            scanner.close();
        }
    }
}
