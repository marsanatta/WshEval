package wsh.eval.tree;

import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.number.Number;

/**
 * Abstract syntax tree node
 */
public interface TreeNode {
    Number accept(TreeVisitor visitor) throws VariableNotFoundException;

}
