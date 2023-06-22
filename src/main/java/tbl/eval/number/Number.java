package tbl.eval.number;

import java.math.BigDecimal;
import java.util.Optional;

public class Number {
    private NumberType type;
    private Object value;

    public Number(NumberType type, Object value) {
        this.value = value;
    }

    public NumberType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public Optional<Long> longValue() {
        if (type == NumberType.LONG) {
            return Optional.of((Long)value);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Double> doubleValue() {
        if (type == NumberType.DOUBLE) {
            return Optional.of((Double)value);
        } else {
            return Optional.empty();
        }
    }

    public Optional<BigDecimal> bigDecimalValue() {
        if (type == NumberType.BIG_DECIMAL) {
            return Optional.of((BigDecimal)value);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
