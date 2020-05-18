package cn.mamp.ds.tree;

/**
 * 树: 除了根节点,每个结点有且只能有一个父结点的数据结构
 * 二叉树的定义: 每个结点**最多有两个子树**的 树结构
 * 结点的高度: 从结点x向下到某个叶结点最长简单路径中边的条数
 * 树的深度: 根节点的高度就是树的深度
 * 结点的度: 结点的子树的个数
 * 树的度: 树中最大的结点度
 * <p>
 * 分类:
 * 1. 满二叉树: 一颗深度为k且有2^k-1个结点的二叉树称为满二叉树[从外观上看是左右对称的]
 * 2. 完全二叉树:  若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边[外观上看 h-1层之前是满二叉树,H层的节点都在左侧]
 * 3. 平衡二叉树(AVL 树)
 * |- a. 二叉排序树
 * |- b. 空树或者左右两个子树的高度差的绝对值不超过1
 * |- c. 左右两个子树都是 平衡二叉树
 *
 * @author mamp
 * @data 2020/5/15
 */
public class BinaryTree {
    private Node root;

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.createTree();
        System.out.println("前: ");
        bt.pre(bt.root);
        System.out.println();
        System.out.println("中: ");
        bt.mid(bt.root);
        System.out.println();
        System.out.println("后: ");
        bt.after(bt.root);
    }

    /**
     * 构造树,从下往上构造
     */
    public void createTree() {
        Node d = new Node("D", null, null);
        Node e = new Node("E", null, null);
        Node f = new Node("F", null, null);
        Node g = new Node("G", null, null);
        Node b = new Node("B", d, e);
        Node c = new Node("C", f, g);
        Node a = new Node("A", b, c);
        this.root = a;

    }

    /**
     * 先序遍历
     * 根-> 左 ->右
     * 遇到根据节点 打印
     *
     * @param root 树的根节点
     */
    public void pre(Node root) {
        if (root == null) {
            return;
        }
        // 1. 打印根节点
        System.out.print(root.getData());
        // 2. 遍历左子树
        pre(root.getLeft());
        // 3. 遍历右子树
        pre(root.getRight());

    }

    /**
     * 中序遍历
     * 左 -> 根-> 右
     * 遇到根据节点 打印
     *
     * @param root 树的根节点
     */
    public void mid(Node root) {
        if (root == null) {
            return;
        }
        // 1. 遍历左子树
        mid(root.getLeft());
        // 2. 打印根节点
        System.out.print(root.getData());
        // 3. 遍历右子树
        mid(root.getRight());
    }

    /**
     * 后序遍历
     * 左 -> 右-> 根
     * 遇到根据节点 打印
     *
     * @param root 树的根节点
     */
    public void after(Node root) {
        if (root == null) {
            return;
        }
        // 1. 遍历左子树
        after(root.getLeft());
        // 2. 遍历右子树
        after(root.getRight());
        // 3. 打印根节点
        System.out.print(root.getData());
    }


}

/**
 * 二叉树节点定义类
 */
class Node {
    /**
     * 节点数据
     */
    private String data;
    /**
     * 左子树的根节点
     */
    private Node left;
    /**
     * 右子树的根节点
     */
    private Node right;

    public Node(String data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}