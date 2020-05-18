package cn.mamp.ds.tree;

/**
 * 二叉搜索树=二叉排序树
 * 1. 二叉搜索树的 中序遍历是一个有序数列
 * 2. 二分搜索树 中没有重复元素
 *
 * @author mamp
 * @data 2020/5/15
 */
public class BinarySearchTree {
    private int data;
    private BinarySearchTree left;
    private BinarySearchTree right;

    public BinarySearchTree(int data, BinarySearchTree left, BinarySearchTree right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    /**
     * 二叉树 插入
     *
     * @param root 树根节
     * @param data 要插入的数据
     */
    public void insert(BinarySearchTree root, int data) {
        if (root == null) {
            root = new BinarySearchTree(data, null, null);
            return;
        }
        if (root.data > data) {
            if (root.left != null) {
                insert(root.left, data);
            } else {
                root.left = new BinarySearchTree(data, null, null);
            }

        } else {
            if (root.right != null) {
                insert(root.right, data);
            } else {
                root.right = new BinarySearchTree(data, null, null);
            }
        }
    }

    /**
     * 二叉树 查找
     *
     * @param root 二叉树根节点
     * @param data 需要查找的数据
     */
    public BinarySearchTree find(BinarySearchTree root, int data) {
        if (null == root) {
            return null;
        }
        BinarySearchTree tree = null;
        if (root.data > data) {
            tree = find(root.left, data);
        } else if (root.data < data) {
            tree = find(root.right, data);
        } else {
            System.out.println("找到了");
            System.out.println(data);
            return root;
        }
        return tree;
    }

    /**
     * 删除分三种情况:
     * 1. 叶子节点的删除
     * 2. 删除只有左子树的结点
     * 3. 删除只胡右子树的结点
     * 4. 删除有两个子树的结点, 找到后继结点(右子树)代替当前节点
     */

    public void del(BinarySearchTree root, int data) {
        if (root == null) {
            return;
        }
        BinarySearchTree tree = find(root, data);
        if (tree.right == null && tree.left == null) {
            tree = null;
        }
    }
}
