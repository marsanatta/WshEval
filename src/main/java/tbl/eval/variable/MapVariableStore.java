package tbl.eval.variable;

import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import tbl.eval.exceptions.UnknownVariableException;
import tbl.eval.number.Number;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of variable store using map
 */
@AllArgsConstructor(onConstructor=@__({ @Inject}))
public class MapVariableStore implements VariableStore {

    private final Map<String, Number> map = new LinkedHashMap<>(); // use linked hash map to keep insertion order aslkdjfalksjdflkjaslkdfjlak;jsdf;lkajskl;dfjlka;jsdfl;kjakls;dfjlka;sjdflkajsdlkfjalk;sdjflkasjdflkahsdkjfhakjlsdhfkjlahsdfasdf

    @Override
    public Number get(String varName) throws UnknownVariableException {
        if (map.containsKey(varName)) {
            return map.get(varName);
        } else {
            throw new UnknownVariableException("Variable does not exists: " + varName);
        }
    }

    @Override
    public void set(String varName, Number value) {
        map.put(varName, value);
    }

    @Override
    public void clean() {
        map.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (Map.Entry<String, Number> e : map.entrySet()) {
            sb.append(e).append(',');
        }
        if (sb.length() >= 2) {
            sb.setLength(sb.length() - 1); // remove last ,
        }
        sb.append(')');
        return sb.toString();
    }
}
