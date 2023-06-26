package wsh.eval.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import wsh.eval.Interpreter;
import wsh.eval.Lexer;
import wsh.eval.Parser;
import wsh.eval.command.UserCommandHelper;
import wsh.eval.variable.MapVariableStore;
import wsh.eval.variable.VariableStore;

/**
 * Interpreter Guice Module
 */
public class InterpreterModule extends AbstractModule {

    @Override
    public void configure() {
    }

    @Provides
    public VariableStore getVariableStore() {
        return new MapVariableStore();
    }

    @Named("ConsolePrompt")
    @Provides
    public Lexer getLexer() {
        return new Lexer(UserCommandHelper.getUserCommandNames());
    }

    @Named("ConsolePrompt")
    @Provides
    public Parser getParser(@Named("ConsolePrompt") Lexer lexer) {
        return new Parser(lexer);
    }

    @Named("ConsolePrompt")
    @Provides
    public Interpreter getInterpreter(@Named("ConsolePrompt") Parser parser, VariableStore varStore) {
        return Interpreter.builder()
                .parser(parser)
                .varStore(varStore)
                .build();
    }
}
