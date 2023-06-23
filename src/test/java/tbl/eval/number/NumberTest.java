package tbl.eval.number;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {

    @Test
    void testLongValue() {
        Number num = new Number(NumberType.LONG, 1L);
        Long l = num.longValue();
        assertEquals(1L, l);
    }

    @Test
    void testLongValue_NotLong() {
        Number num = new Number(NumberType.DOUBLE, 1.2);
        Long l = num.longValue();
        assertEquals(1L, l);
    }

    @Test
    void testDoubleValue() {
        Number num  = new Number(NumberType.DOUBLE, 1.0);
        Double d = num.doubleValue();
        assertEquals(1.0, d);
    }

    @Test
    void testDoubleValue_NotDouble() {
        Number num = new Number(NumberType.LONG, 1L);
        Double d = num.doubleValue();
        assertEquals(1.0, d);


    }

    @Test
    void testBigDecimalValue() {
        Number num = new Number(NumberType.BIG_DECIMAL, new BigDecimal("1"));
        BigDecimal big = num.bigDecimalValue();
        assertEquals(big, new BigDecimal("1"));
    }

    @Test
    void testBigDecimalValue_NotBigDecimal() {
        Number num = new Number(NumberType.LONG, 1L);
        BigDecimal big = num.bigDecimalValue();
        assertEquals(big, new BigDecimal("1"));
    }


    @Test
    void testNegate() {
        Number num = new Number(NumberType.LONG, 1L);
        num = num.negate();
        assertEquals(-1L, num.getValue());
    }

    @Test
    void testNegate_BigDecimal() {
        Number num = new Number(NumberType.BIG_DECIMAL, new BigDecimal("1"));
        num = num.negate();
        assertEquals(new BigDecimal("-1"), num.getValue());
    }

    @Test
    void testAdd_TargetBigDecimal() {
        Number num1 = new Number(NumberType.LONG, 1L);
        Number num2 = new Number(NumberType.BIG_DECIMAL, new BigDecimal("1"));
        Number num3 = num1.add(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("2"), num3.getValue());
    }

    @Test
    void testAdd_TargetDouble() {
        Number num1 = new Number(NumberType.LONG, 1L);
        Number num2 = new Number(NumberType.DOUBLE, 1.0);
        Number num3 = num1.add(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(2.0, num3.getValue());
    }

    @Test
    void testAdd_TargetLong() {
        Number num1 = new Number(NumberType.LONG, 1L);
        Number num2 = new Number(NumberType.LONG, 1L);
        Number num3 = num1.add(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(2L, num3.getValue());
    }

    @Test
    void testSubtract_TargetBigDecimal() {
        Number num1 = new Number(NumberType.LONG, 1L);
        Number num2 = new Number(NumberType.BIG_DECIMAL, new BigDecimal("1"));
        Number num3 = num1.subtract(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("0"), num3.getValue());
    }

    @Test
    void testSubtract_TargetDouble() {
        Number num1 = new Number(NumberType.LONG, 1L);
        Number num2 = new Number(NumberType.DOUBLE, 1.0);
        Number num3 = num1.subtract(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(0.0, num3.getValue());
    }

    @Test
    void testSubtract_TargetLong() {
        Number num1 = new Number(NumberType.LONG, 1L);
        Number num2 = new Number(NumberType.LONG, 1L);
        Number num3 = num1.subtract(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(0L, num3.getValue());
    }

    @Test
    void testMultiply_TargetBigDecimal() {
        Number num1 = new Number(NumberType.LONG, 2L);
        Number num2 = new Number(NumberType.BIG_DECIMAL, new BigDecimal("4.1"));
        Number num3 = num1.multiply(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("8.2"), num3.getValue());
    }

    @Test
    void testMultiply_TargetDouble() {
        Number num1 = new Number(NumberType.LONG, 2L);
        Number num2 = new Number(NumberType.DOUBLE, 4.1);
        Number num3 = num1.multiply(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(8.2, num3.getValue());
    }

    @Test
    void testMultiply_TargetLong() {
        Number num1 = new Number(NumberType.LONG, 2L);
        Number num2 = new Number(NumberType.LONG, 4L);
        Number num3 = num1.multiply(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(8L, num3.getValue());
    }

    @Test
    void testDivide_TargetBigDecimal() {
        Number num1 = new Number(NumberType.LONG, 10L);
        Number num2 = new Number(NumberType.BIG_DECIMAL, new BigDecimal("2.5"));
        Number num3 = num1.divide(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("4"), num3.getValue());
    }

    @Test
    void testDivide_TargetDouble() {
        Number num1 = new Number(NumberType.LONG, 10L);
        Number num2 = new Number(NumberType.DOUBLE, 2.5);
        Number num3 = num1.divide(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(4.0, num3.getValue());
    }

    @Test
    void testDivide_TargetLong() {
        Number num1 = new Number(NumberType.LONG, 8L);
        Number num2 = new Number(NumberType.LONG, 2L);
        Number num3 = num1.divide(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(4L, num3.getValue());
    }

    @Test
    void testRemainder_TargetBigDecimal() {
        Number num1 = new Number(NumberType.LONG, 8L);
        Number num2 = new Number(NumberType.BIG_DECIMAL, new BigDecimal("3.1"));
        Number num3 = num1.remainder(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("1.8"), num3.getValue());
    }

    @Test
    void testRemainder_TargetDouble() {
        Number num1 = new Number(NumberType.LONG, 8L);
        Number num2 = new Number(NumberType.DOUBLE, 3.1);
        Number num3 = num1.remainder(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(1.8D, (Double)num3.getValue(), 0.01D);
    }

    @Test
    void testRemainder_TargetLong() {
        Number num1 = new Number(NumberType.LONG, 8L);
        Number num2 = new Number(NumberType.LONG, 3L);
        Number num3 = num1.remainder(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(2L, num3.getValue());
    }

    @Test
    void testConstructor_InconsistentTypeValue_Long() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Number(NumberType.LONG, 1.0);
        });
    }

    @Test
    void testConstructor_InconsistentTypeValue_Double() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Number(NumberType.DOUBLE, 1L);
        });
    }

    @Test
    void testConstructor_InconsistentTypeValue_BigDecimal() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Number(NumberType.BIG_DECIMAL, 1L);
        });
    }
}