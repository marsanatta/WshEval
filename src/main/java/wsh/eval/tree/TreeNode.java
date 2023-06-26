package wsh.eval.tree;

import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.number.Number;

/**
 * Abstract syntax tree node
 */
public interface TreeNode {

    /**
     * Accept a tree visitor to handle the node
     * @param visitor vistor
     * @return number is the evaluation result of the node
     * @throws VariableNotFoundException variable not found exception
     */
    Number accept(TreeVisitor visitor) throws VariableNotFoundException;

}
