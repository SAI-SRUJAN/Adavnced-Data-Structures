package services;

import domain.Color;
import domain.TreeNode;

public class TreeMap<T extends Comparable<T>> {
    private TreeNode<T> root;

    /**
     * Inserts or updates the entry in the tree map based on the key.
     * </br>
     * For insertion, it searches the position of the node as per the binary tree search algorithm and then
     * calls balance tree method which checks for any violations and perform correction operations on the tree.
     * @param key
     * @param value
     */
    public void Put(T key, Object value) {
        var node = new TreeNode<>(key,value);
        if (root != null) {
            var nearestNode = searchNearest(node);
            if (node.compareTo(nearestNode) > 0) {
                nearestNode.set_right(node);
                node.set_parent(nearestNode);
                node.set_color(Color.red);
                balanceTree(node);
            } else if (node.compareTo(nearestNode) < 0) {
                nearestNode.set_left(node);
                node.set_parent(nearestNode);
                node.set_color(Color.red);
                balanceTree(node);
            } else {
                nearestNode.update_value(node.get_value());
                return;
            }
        } else {
            root = node;
            node.set_color(Color.black);
        }
    }

    public TreeNode<T> Get(T key) {
        return search(key);
    }

    /**
     * This method is responsible for maintaining the balance of the tree.
     * <br>We maintain the loop till the current node is not red.
     * <br>If the parent is red then we perform appropriate rotations based on the
     * color of the current node's grand-parent and uncle.
     * @param node
     */
    private void balanceTree(TreeNode<T> node) {
        while (node.get_parent() != null && node.get_parent().get_color() == Color.red) {
            var parent = node.get_parent();
            var gParent = parent.get_parent();
            if (parent.equals(gParent.get_left())) {
                var uncle = gParent.get_right();
                if (uncle == null || uncle.get_color() == Color.black) {
                    if (node.equals(parent.get_right())) {
                        node = parent;
                        left_Rotate(node);
                    }
                    parent = node.get_parent();
                    gParent = parent.get_parent();
                    parent.set_color(Color.black);
                    gParent.set_color(Color.red);
                    right_Rotate(gParent);
                } else {
                    uncle.set_color(Color.black);
                    parent.set_color(Color.black);
                    gParent.set_color(Color.red);
                    node = gParent;
                }
            } else {
                var uncle = gParent.get_left();
                if (uncle == null || uncle.get_color() == Color.black) {
                    if (node.equals(parent.get_left())) {
                        node = parent;
                        right_Rotate(node);
                    }
                    parent = node.get_parent();
                    gParent = parent.get_parent();
                    parent.set_color(Color.black);
                    gParent.set_color(Color.red);
                    left_Rotate(gParent);
                } else {
                    uncle.set_color(Color.black);
                    parent.set_color(Color.black);
                    gParent.set_color(Color.red);
                    node = gParent;
                }
            }
        }
        this.root.set_color(Color.black);
    }

    private void left_Rotate(TreeNode<T> node) {
        var parent = node.get_parent();
        var right_Child = node.get_right();
        node.set_right(right_Child.get_left());
        if(right_Child.get_left() != null) {
            right_Child.get_left().set_parent(node);
        }
        right_Child.set_parent(parent);
        if (parent == null) {
            this.root = right_Child;
        } else if (node.equals(parent.get_left())) {
            parent.set_left(right_Child);
        } else {
            parent.set_right(right_Child);
        }
        right_Child.set_left(node);
        node.set_parent(right_Child);
    }

    private void right_Rotate(TreeNode<T> node) {
        var left_child = node.get_left();
        var parent = node.get_parent();
        node.set_left(left_child.get_right());
        if(left_child.get_right() != null) {
            left_child.get_right().set_parent(node);
        }
        left_child.set_parent(parent);
        if (parent == null) {
            this.root = left_child;
        } else if (node.equals(parent.get_left())) {
            parent.set_left(left_child);
        } else {
            parent.set_right(left_child);
        }
        left_child.set_right(node);
        node.set_parent(left_child);
    }

    /**
     * returns the node for the key specified and if key is not present returns null.
     * @param key
     * @return
     */
    private TreeNode<T> search(T key) {
        var currentNode = root;

        while (currentNode != null) {
            if (currentNode.get_key().compareTo(key) > 0) {
                currentNode = currentNode.get_left();
            } else if (currentNode.get_key().compareTo(key) < 0) {
                currentNode = currentNode.get_right();
            } else {
                return currentNode;
            }
        }
        return null;
    }

    /**
     * same as the search method but instead of returning null when key was not found it returns the nearest
     * node to the specified key.
     * @param node
     * @return
     */
    private TreeNode<T> searchNearest(TreeNode<T> node) {
        var currentNode = root;
        var prevNode = root;
        while (currentNode != null) {
            prevNode = currentNode;
            if (currentNode.compareTo(node) > 0) {
                currentNode = currentNode.get_left();
            } else if (currentNode.compareTo(node) < 0) {
                currentNode = currentNode.get_right();
            } else {
                return currentNode;
            }
        }
        return prevNode;
    }
}
