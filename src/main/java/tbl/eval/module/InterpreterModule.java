package tbl.eval.module;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import tbl.eval.Interpreter;
import tbl.eval.Lexer;
import tbl.eval.Parser;
import tbl.eval.variable.VariableStore;

/**
 * Interpreter Guice Module
 */
public class InterpreterModule extends AbstractModule {

    @Override
    public void configure() {
        bind(Interpreter.class).toInstance(createInterpreter());
    }

    private Interpreter createInterpreter() {
        Injector injector = Guice.createInjector(new VariableModule());
        Lexer lexer = new Lexer();
        Parser parser = new Parser(lexer);
        return Interpreter.builder()
                .lexer(lexer)
                .parser(parser)
                .varStore(injector.getInstance(VariableStore.class))
                .build();
    }
}
