package wsh.eval.variable;

import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.number.Number;

import java.util.Set;

/**
 * Interface for variable store
 */
public interface VariableStore {
    /**
     * Get the variable with name
     * @param varName variable name
     * @return number
     */
    Number get(String varName) throws VariableNotFoundException;

    /**
     * Set the variable with name and value
     * @param varName variable name
     * @param value variable value
     */
    void set(String varName, Number value);

    /**
     * Remove all stored variables
     * @return the removed variable names
     */
    Set<String> clean();

    /**
     * Convert variables to string
     * @return string
     */
    String variablesToString();
}
