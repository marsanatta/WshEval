package wsh.eval.variable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.number.Number;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MapVariableStoreTest {
    MapVariableStore varStore;

    @BeforeEach
    void setup() {
        varStore = new MapVariableStore();
    }

    @Test
    void testSetAndGet() throws Exception {
        Number num = Number.valueOf(1L);
        String varName = "a";
        varStore.set(varName, num);
        assertEquals(num, varStore.get(varName));
    }

    @Test
    void testGet_NotFound() {
        assertThrows(VariableNotFoundException.class, () ->varStore.get("a"));
    }

    @Test
    void clean() {
        String varName1 = "a";
        String varName2 = "b";
        varStore.set(varName1, Number.valueOf(1L));
        varStore.set(varName2, Number.valueOf(3.0));
        varStore.clean();
        assertThrows(VariableNotFoundException.class, () ->varStore.get(varName1));
        assertThrows(VariableNotFoundException.class, () ->varStore.get(varName2));
    }

    @Test
    void testToString() {
        String varName1 = "a";
        String varName2 = "b";
        String varName3 = "c";
        varStore.set(varName1, Number.valueOf(1L));
        varStore.set(varName2, Number.valueOf(3.0));
        varStore.set(varName3, Number.valueOf(new BigDecimal("2.1e+9")));
        assertEquals("(a=1(LONG),b=3.0(DOUBLE),c=2.1E+9(BIG_DECIMAL))", varStore.toString());
    }
}