package wsh.eval;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wsh.eval.module.InterpreterModule;
import wsh.eval.number.Number;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SuppressWarnings({"checkstyle:WhitespaceAround"})
public class IntegrationTest {
    private final Injector injector = Guice.createInjector(new InterpreterModule());
    private Interpreter interpreter;

    @BeforeEach
    public void setup() {
        interpreter = injector.getInstance(Interpreter.class);
    }

    @Test
    public void test1() throws Exception {
        interpreter.interpret("a=1");
        interpreter.interpret("b=a + 2 * 3.0");
        long a = 1L;
        double b = a + 2 * 3.0;
        assertEquals(Number.valueOf(a), interpreter.getVarStore().get("a"));
        assertEquals(Number.valueOf(b), interpreter.getVarStore().get("b"));
    }

    @Test
    public void test2() throws Exception {
        interpreter.interpret("a=1");
        interpreter.interpret("b=-(a+1)*3.0");
        long a = 1L;
        double b = -(a+1)*3.0;
        assertEquals(Number.valueOf(a), interpreter.getVarStore().get("a"));
        assertEquals(Number.valueOf(b), interpreter.getVarStore().get("b"));
    }

    @Test
    public void test3() throws Exception {
        interpreter.interpret("i=1");
        interpreter.interpret("j= ++i + i-- + i++");
        long i=1;
        long j=++i + i-- + i++;
        assertEquals(Number.valueOf(j), interpreter.getVarStore().get("j"));
        assertEquals(Number.valueOf(i), interpreter.getVarStore().get("i"));
    }

    @Test
    public void test4() throws Exception {
        interpreter.interpret("a=1");
        interpreter.interpret("a += ++a");
        long a = 1;
        a += ++a;

        assertEquals(Number.valueOf(a), interpreter.getVarStore().get("a"));
    }

    @Test
    public void test5() throws Exception {
        interpreter.interpret("a=432.1E9");
        interpreter.interpret("b=123.4E8");
        interpreter.interpret("b += ((a+b) * 30 % 2 + -(+-+-+(b-a)*9e-2 - --a)) / (b++ * 9)");
        double a = 432.1E9;
        double b = 123.4E8;
        b += ((a+b) * 30 % 2 + -(+-+-+(b-a)*9e-2 - --a)) / (b++ * 9);

        assertEquals(Number.valueOf(a), interpreter.getVarStore().get("a"));
        assertEquals(Number.valueOf(b), interpreter.getVarStore().get("b"));
    }

}
