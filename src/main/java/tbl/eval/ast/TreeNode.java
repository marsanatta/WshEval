package tbl.eval.ast;

import tbl.eval.number.Number;

/**
 * Abstract syntax tree node
 */
public interface TreeNode {
    Number accept(TreeVisitor visitor);

}
