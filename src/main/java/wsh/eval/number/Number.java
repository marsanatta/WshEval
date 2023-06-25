package wsh.eval.number;

import lombok.NonNull;
import wsh.eval.exceptions.UnknownNumberTypeException;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class Number {
    private static final BigDecimal BIG_DECIMAL_MAX_DOUBLE = BigDecimal.valueOf(Double.MAX_VALUE);
    private static final BigDecimal BIG_DECIMAL_MIN_DOUBLE = BigDecimal.valueOf(Double.MIN_VALUE);

    private final NumberType type;
    private final java.lang.Number value;

    private Number(@NonNull NumberType type, @NonNull java.lang.Number value) {
        this.type = type;
        this.value = value;
    }

    public long longValue() {
        if (type == NumberType.LONG) {
            return (Long) value;
        } else {
            return value.longValue();
        }
    }

    public double doubleValue() {
        if (type == NumberType.DOUBLE) {
            return (Double) value;
        } else {
            return value.doubleValue();
        }
    }

    public BigDecimal bigDecimalValue() {
        if (type == NumberType.BIG_DECIMAL) {
            return (BigDecimal) value;
        } else {
            return new BigDecimal(value.toString());
        }
    }

    private NumberType resolveResultNumberType(Number num1, Number num2) {
        if (num1.getType().getRank() > num2.getType().getRank()) {
            return num1.getType();
        } else {
            return num2.getType();
        }
    }

    public Number negate() {
        switch (type) {
            case BIG_DECIMAL:
                return Number.valueOf(((BigDecimal) value).negate());
            case LONG:
                return Number.valueOf(-(Long) value);
            case DOUBLE:
                return Number.valueOf(-(Double) value);
            default:
                throw new UnknownNumberTypeException("Unknown type when doing neglect: " + type);
        }
    }

    /**
     * Add another number
     *
     * @param another another number
     * @return result number
     */
    public Number add(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return Number.valueOf(this.bigDecimalValue().add(another.bigDecimalValue()));
        } else if (resultType == NumberType.DOUBLE) {
            return Number.valueOf(this.doubleValue() + another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return Number.valueOf(this.longValue() + another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    /**
     * Subtract another number
     *
     * @param another another number
     * @return result number
     */
    public Number subtract(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return Number.valueOf(this.bigDecimalValue().subtract(another.bigDecimalValue()));
        } else if (resultType == NumberType.DOUBLE) {
            return Number.valueOf(this.doubleValue() - another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return Number.valueOf(this.longValue() - another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    /**
     * Multiply another number
     *
     * @param another another number
     * @return result number
     */
    public Number multiply(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return Number.valueOf(this.bigDecimalValue().multiply(another.bigDecimalValue()));
        } else if (resultType == NumberType.DOUBLE) {
            return Number.valueOf(this.doubleValue() * another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return Number.valueOf(this.longValue() * another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    /**
     * Divide another number
     *
     * @param another another number
     * @return result number
     */
    public Number divide(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return Number.valueOf(this.bigDecimalValue().divide(another.bigDecimalValue(),
                    RoundingMode.HALF_UP));
        } else if (resultType == NumberType.DOUBLE) {
            return Number.valueOf(this.doubleValue() / another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return Number.valueOf(this.longValue() / another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    /**
     * Remainder another number
     *
     * @param another another number
     * @return result number
     */
    public Number remainder(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return Number.valueOf(this.bigDecimalValue().remainder(another.bigDecimalValue()));
        } else if (resultType == NumberType.DOUBLE) {
            return Number.valueOf(this.doubleValue() % another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return Number.valueOf(this.longValue() % another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    public static Number valueOf(long number) {
        return new Number(NumberType.LONG, number);
    }

    public static Number valueOf(double number) {
        return new Number(NumberType.DOUBLE, number);
    }

    public static Number valueOf(BigDecimal number) {
        return new Number(NumberType.BIG_DECIMAL, number);
    }

    public static Number valueOf(String numStr) {
        BigDecimal bigDecimal = new BigDecimal(numStr);
        if (bigDecimal.scale() > 0 || numStr.toLowerCase().contains("e")) {
            int compareMax = bigDecimal.compareTo(BIG_DECIMAL_MAX_DOUBLE);
            int compareMin = bigDecimal.compareTo(BIG_DECIMAL_MIN_DOUBLE);
            boolean isOverflow = compareMax > 0 || compareMin < 0;
            if (isOverflow) {
                return Number.valueOf(bigDecimal);
            } else {
                return Number.valueOf(bigDecimal.doubleValue());
            }
        } else {
            try {
                return Number.valueOf(bigDecimal.longValueExact());
            } catch (ArithmeticException e) {
                // overflow
                return Number.valueOf(bigDecimal);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", value, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Number) {
            Number another = (Number) obj;
            return type.equals(another.getType()) && value.equals(another.getValue());
        } else {
            return false;
        }
    }

}
