package tbl.eval.number;

import tbl.eval.exceptions.UnknownNumberTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor
public class Number {
    private NumberType type;
    private Object value;

    public NumberType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public Long longValue() {
        if (type == NumberType.LONG) {
            return (Long)value;
        } else {
            return Long.valueOf(value.toString());
        }
    }

    public Double doubleValue() {
        if (type == NumberType.DOUBLE) {
            return (Double) value;
        } else {
            return Double.valueOf(value.toString());
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
                return new Number(NumberType.BIG_DECIMAL, ((BigDecimal)value).negate());
            case LONG:
                return new Number(type, -(Long)value);
            case DOUBLE:
                return new Number(type, -(Double)value);
            default:
                throw new UnknownNumberTypeException("Unknown type when doing neglect: " + type);
        }
    }

    /**
     * Add another number
     * @param another another number
     * @return result number
     */
    public Number add(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return new Number(NumberType.BIG_DECIMAL, this.bigDecimalValue().add(another.bigDecimalValue()));
        } else if (resultType == NumberType.DOUBLE) {
            return new Number(NumberType.DOUBLE, this.doubleValue() + another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return new Number(NumberType.LONG, this.longValue() + another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    /**
     * Subtract another number
     * @param another another number
     * @return result number
     */
    public Number subtract(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return new Number(NumberType.BIG_DECIMAL, this.bigDecimalValue().subtract(another.bigDecimalValue()));
        } else if (resultType == NumberType.DOUBLE) {
            return new Number(NumberType.DOUBLE, this.doubleValue() - another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return new Number(NumberType.LONG, this.longValue() - another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    /**
     * Multiply another number
     * @param another another number
     * @return result number
     */
    public Number multiply(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return new Number(NumberType.BIG_DECIMAL, this.bigDecimalValue().multiply(another.bigDecimalValue()));
        } else if (resultType == NumberType.DOUBLE) {
            return new Number(NumberType.DOUBLE, this.doubleValue() * another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return new Number(NumberType.LONG, this.longValue() * another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    /**
     * Divide another number
     * @param another another number
     * @return result number
     */
    public Number divide(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return new Number(NumberType.BIG_DECIMAL, this.bigDecimalValue().divide(another.bigDecimalValue(),
                    RoundingMode.HALF_UP));
        } else if (resultType == NumberType.DOUBLE) {
            return new Number(NumberType.DOUBLE, this.doubleValue() / another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return new Number(NumberType.LONG, this.longValue() / another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    /**
     * Remainder another number
     * @param another another number
     * @return result number
     */
    public Number remainder(Number another) {
        NumberType resultType = resolveResultNumberType(this, another);
        if (resultType == NumberType.BIG_DECIMAL) {
            return new Number(NumberType.BIG_DECIMAL, this.bigDecimalValue().remainder(another.bigDecimalValue()));
        } else if (resultType == NumberType.DOUBLE) {
            return new Number(NumberType.DOUBLE, this.doubleValue() % another.doubleValue());
        } else if (resultType == NumberType.LONG) {
            return new Number(NumberType.LONG, this.longValue() % another.longValue());
        } else {
            throw new UnknownNumberTypeException("Unknown result number type: " + resultType);
        }
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", value.toString(), type);
    }
}