package wsh.eval.variable;

import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.number.Number;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of variable store using map
 */
public class MapVariableStore implements VariableStore {

    private final Map<String, Number> map = new LinkedHashMap<>(); // use linked hash map to preserve insertion order

    @Override
    public Number get(String varName) throws VariableNotFoundException {
        if (map.containsKey(varName)) {
            return map.get(varName);
        } else {
            throw new VariableNotFoundException("Variable does not exists: " + varName);
        }
    }

    @Override
    public void set(String varName, Number value) {
        map.put(varName, value);
    }

    @Override
    public Set<String> clean() {
        Set<String> varNames = new HashSet<>(map.keySet());
        map.clear();
        return varNames;
    }

    @Override
    public String variablesToString() {
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
