package tbl.eval.variable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import tbl.eval.exceptions.VariableNotFoundException;
import tbl.eval.number.Number;

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
        varStore.set(varName1, Number.valueOf(1L));
        varStore.set(varName2, Number.valueOf(3.0));
        assertEquals("(a=1(LONG),b=3.0(DOUBLE))", varStore.toString());
    }
}