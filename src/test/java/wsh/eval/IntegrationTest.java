package wsh.eval;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wsh.eval.exceptions.InvalidSyntaxException;
import wsh.eval.module.InterpreterModule;
import wsh.eval.number.Number;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SuppressWarnings({"checkstyle:WhitespaceAround"})
public class IntegrationTest {
    private final Injector injector = Guice.createInjector(new InterpreterModule());
    private Interpreter interpreter;

    @BeforeEach
    public void setup() {
        interpreter = injector.getInstance(Key.get(Interpreter.class, Names.named("ConsolePrompt")));
    }

    @Test
    public void test1() throws Exception {
        interpreter.interpret("i=1");
        interpreter.interpret("j= ++i + i-- + i++");
        long i=1;
        long j=++i + i-- + i++;
        assertEquals(Number.valueOf(j), interpreter.getVarStore().get("j"));
        assertEquals(Number.valueOf(i), interpreter.getVarStore().get("i"));
    }

    @Test
    public void test2() throws Exception {
        interpreter.interpret("a=1");
        interpreter.interpret("a += ++a");
        long a = 1;
        a += ++a;

        assertEquals(Number.valueOf(a), interpreter.getVarStore().get("a"));
    }

    @Test
    public void test3() throws Exception {
        interpreter.interpret("a=432.1E9");
        interpreter.interpret("b=123.4E8");
        interpreter.interpret("b += ((a+b) * 30 % 2 + -(+-+-+(b-a)*9e-2 + 1.1e+2 - --a)) / (b++ * 9)");
        double a = 432.1E9;
        double b = 123.4E8;
        b += ((a+b) * 30 % 2 + -(+-+-+(b-a)*9e-2 + 1.1e+2 - --a)) / (b++ * 9);

        assertEquals(Number.valueOf(a), interpreter.getVarStore().get("a"));
        assertEquals(Number.valueOf(b), interpreter.getVarStore().get("b"));
    }

    @Test
    public void test4() throws Exception {
        interpreter.interpret("a = 1+2*3.0-4.0/3%2");
        double a = 1+2*3.0-4.0/3%2;
        assertEquals(Number.valueOf(a), interpreter.getVarStore().get("a"));

        interpreter.interpret("b = - - -(+(-a))");
        double b = - - -(+(-a));
        assertEquals(Number.valueOf(b), interpreter.getVarStore().get("b"));

        interpreter.interpret("c =(1+2)*(((3)-4)*6)");
        long c = (1+2)*(((3)-4)*6);
        assertEquals(Number.valueOf(c), interpreter.getVarStore().get("c"));

        interpreter.interpret("d = ++a - c--");
        double d = ++a - c--;
        assertEquals(Number.valueOf(d), interpreter.getVarStore().get("d"));
        assertEquals(Number.valueOf(a), interpreter.getVarStore().get("a"));
        assertEquals(Number.valueOf(c), interpreter.getVarStore().get("c"));

        interpreter.interpret("d += a");
        interpreter.interpret("d *= b");
        interpreter.interpret("d -= c");
        interpreter.interpret("d /= 2.1");
        interpreter.interpret("d %= 3.4");
        d += a;
        d *= b;
        d -= c;
        d /= 2.1;
        d %= 3.4;
        assertEquals(Number.valueOf(d), interpreter.getVarStore().get("d"));

        interpreter.interpret("f = 1.2e+333 * 1.4e+555 / d % -(5.6e-222 + 7.8e333)");
        BigDecimal f = new BigDecimal("1.2e+333")
                .multiply(new BigDecimal("1.4e+555"))
                .divide(BigDecimal.valueOf(d), RoundingMode.HALF_UP)
                .remainder(
                        new BigDecimal("5.6e-222")
                                .add(new BigDecimal("7.8e333")).negate()
                );
        assertEquals(Number.valueOf(f), interpreter.getVarStore().get("f"));
    }

    /**
     * Paul's case
     */
    @Test
    public void test5() {
        assertThrows(InvalidSyntaxException.class, () -> interpreter.interpret("++a=0"));
        assertThrows(InvalidSyntaxException.class, () -> interpreter.interpret("--a=0"));
        assertThrows(InvalidSyntaxException.class, () -> interpreter.interpret("a++=0"));
        assertThrows(InvalidSyntaxException.class, () -> interpreter.interpret("a--=0"));
    }

}
