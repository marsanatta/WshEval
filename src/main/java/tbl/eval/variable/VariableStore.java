package tbl.eval.variable;

import tbl.eval.exceptions.UnknownVariableException;
import tbl.eval.number.Number;

/**
 * Interface for variable store
 */
public interface VariableStore {
    /**
     * Get the variable with name
     * @param varName variable name
     * @return number
     */
    Number get(String varName) throws UnknownVariableException;

    /**
     * Set the variable with name and value
     * @param varName variable name
     * @param value variable value
     */
    void set(String varName, Number value);

    /**
     * Remove all stored variables
     */
    void clean();
}
