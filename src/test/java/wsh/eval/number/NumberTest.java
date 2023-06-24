package wsh.eval.number;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {

    @Test
    void testLongValue() {
        Number num = Number.valueOf(1L);
        Long l = num.longValue();
        assertEquals(1L, l);
    }

    @Test
    void testLongValue_NotLong() {
        Number num = Number.valueOf(1.2);
        Long l = num.longValue();
        assertEquals(1L, l);
    }

    @Test
    void testDoubleValue() {
        Number num  = Number.valueOf(1.0);
        Double d = num.doubleValue();
        assertEquals(1.0, d);
    }

    @Test
    void testDoubleValue_NotDouble() {
        Number num = Number.valueOf(1L);
        Double d = num.doubleValue();
        assertEquals(1.0, d);
    }

    @Test
    void testBigDecimalValue() {
        Number num = Number.valueOf(new BigDecimal("1"));
        BigDecimal big = num.bigDecimalValue();
        assertEquals(big, new BigDecimal("1"));
    }

    @Test
    void testBigDecimalValue_NotBigDecimal() {
        Number num = Number.valueOf(1L);
        BigDecimal big = num.bigDecimalValue();
        assertEquals(big, new BigDecimal("1"));
    }


    @Test
    void testNegate() {
        Number num = Number.valueOf(1L);
        num = num.negate();
        assertEquals(-1L, num.getValue());
    }

    @Test
    void testNegate_BigDecimal() {
        Number num = Number.valueOf(new BigDecimal("1"));
        num = num.negate();
        assertEquals(new BigDecimal("-1"), num.getValue());
    }

    @Test
    void testAdd_TargetBigDecimal() {
        Number num1 = Number.valueOf(1L);
        Number num2 = Number.valueOf(new BigDecimal("1"));
        Number num3 = num1.add(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("2"), num3.getValue());
    }

    @Test
    void testAdd_TargetDouble() {
        Number num1 = Number.valueOf(1L);
        Number num2 = Number.valueOf(1.0);
        Number num3 = num1.add(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(2.0, num3.getValue());
    }

    @Test
    void testAdd_TargetLong() {
        Number num1 = Number.valueOf(1L);
        Number num2 = Number.valueOf(1L);
        Number num3 = num1.add(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(2L, num3.getValue());
    }

    @Test
    void testSubtract_TargetBigDecimal() {
        Number num1 = Number.valueOf(1L);
        Number num2 = Number.valueOf(new BigDecimal("1"));
        Number num3 = num1.subtract(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("0"), num3.getValue());
    }

    @Test
    void testSubtract_TargetDouble() {
        Number num1 = Number.valueOf(1L);
        Number num2 = Number.valueOf(1.0);
        Number num3 = num1.subtract(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(0.0, num3.getValue());
    }

    @Test
    void testSubtract_TargetLong() {
        Number num1 = Number.valueOf(1L);
        Number num2 = Number.valueOf(1L);
        Number num3 = num1.subtract(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(0L, num3.getValue());
    }

    @Test
    void testMultiply_TargetBigDecimal() {
        Number num1 = Number.valueOf(2L);
        Number num2 = Number.valueOf(new BigDecimal("4.1"));
        Number num3 = num1.multiply(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("8.2"), num3.getValue());
    }

    @Test
    void testMultiply_TargetDouble() {
        Number num1 = Number.valueOf(2L);
        Number num2 = Number.valueOf(4.1);
        Number num3 = num1.multiply(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(8.2, num3.getValue());
    }

    @Test
    void testMultiply_TargetLong() {
        Number num1 = Number.valueOf(2L);
        Number num2 = Number.valueOf(4L);
        Number num3 = num1.multiply(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(8L, num3.getValue());
    }

    @Test
    void testDivide_TargetBigDecimal() {
        Number num1 = Number.valueOf(10L);
        Number num2 = Number.valueOf(new BigDecimal("2.5"));
        Number num3 = num1.divide(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("4"), num3.getValue());
    }

    @Test
    void testDivide_TargetDouble() {
        Number num1 = Number.valueOf(10L);
        Number num2 = Number.valueOf(2.5);
        Number num3 = num1.divide(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(4.0, num3.getValue());
    }

    @Test
    void testDivide_TargetLong() {
        Number num1 = Number.valueOf(8L);
        Number num2 = Number.valueOf(2L);
        Number num3 = num1.divide(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(4L, num3.getValue());
    }

    @Test
    void testRemainder_TargetBigDecimal() {
        Number num1 = Number.valueOf(8L);
        Number num2 = Number.valueOf(new BigDecimal("3.1"));
        Number num3 = num1.remainder(num2);
        assertEquals(NumberType.BIG_DECIMAL, num3.getType());
        assertEquals(new BigDecimal("1.8"), num3.getValue());
    }

    @Test
    void testRemainder_TargetDouble() {
        Number num1 = Number.valueOf(8L);
        Number num2 = Number.valueOf(3.1);
        Number num3 = num1.remainder(num2);
        assertEquals(NumberType.DOUBLE, num3.getType());
        assertEquals(1.8D, (Double)num3.getValue(), 0.01D);
    }

    @Test
    void testRemainder_TargetLong() {
        Number num1 = Number.valueOf(8L);
        Number num2 = Number.valueOf(3L);
        Number num3 = num1.remainder(num2);
        assertEquals(NumberType.LONG, num3.getType());
        assertEquals(2L, num3.getValue());
    }

    @Test
    void testValueOfString_NumberFloatOverflow() {
        String num = BigDecimal.valueOf(Double.MAX_VALUE).add(new BigDecimal("1.1")).toString();
        assertEquals(Number.valueOf(new BigDecimal(num)), Number.valueOf(num));
    }

    @Test
    void testValueOfString_NumberFloatUnderflow() throws Exception {
        String num = (BigDecimal.valueOf(Double.MIN_VALUE).divide(BigDecimal.TEN, RoundingMode.HALF_UP)).toString();
        assertEquals(Number.valueOf(new BigDecimal(num)), Number.valueOf(num));
    }

    @Test
    void testValueOfString_NumberFloat() {
        String num = "1.0";
        assertEquals(Number.valueOf(1.0), Number.valueOf(num));
    }

    @Test
    void testValueOfString_NumberScientificNotion() {
        String num = "1.3e10";
        assertEquals(Number.valueOf(1.3e10), Number.valueOf(num));
    }

    @Test
    void testValueOfString_NumberScientificNotionPlus() {
        String num = "1.3e+10";
        assertEquals(Number.valueOf(1.3e+10), Number.valueOf(num));
    }

    @Test
    void testValueOfString_NumberNegScientificNotionNeg() {
        String num = "1.3e-10";
        assertEquals(Number.valueOf(1.3e-10), Number.valueOf(num));
    }

    @Test
    void testValueOfString_NumberIntegerOverflow() {
        String num = (BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE)).toString();
        assertEquals(Number.valueOf(new BigDecimal(num)), Number.valueOf(num));
    }

    @Test
    void testValueOfString_NumberIntegerUnderflow() {
        String num = (BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE)).toString();
        assertEquals(Number.valueOf(new BigDecimal(num)), Number.valueOf(num));
    }

    @Test
    void testValueOfString_NumberInteger() {
        String num = "123";
        assertEquals(Number.valueOf(123L), Number.valueOf(num));
    }

    @Test
    void testValueOfBigDecimal() {
        BigDecimal num = new BigDecimal("1");
        assertEquals(num, Number.valueOf(num).bigDecimalValue());
    }

    @Test
    void testValueOfLong() {
        long l = 1L;
        assertEquals(l, Number.valueOf(l).longValue());
    }

    @Test
    void testValueOfDouble() {
        double d = 1.0;
        assertEquals(d, Number.valueOf(d).doubleValue());
    }
}