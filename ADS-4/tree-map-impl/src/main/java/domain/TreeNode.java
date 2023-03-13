package domain;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents the node of the TreeMap which has methods to get and set its left, right and parent pointers.
 * As it is a red-black tree node it also has a color bit which tells if its red node or black node.
 * @param <T>
 */
public class TreeNode<T extends Comparable<T>> implements Comparable<TreeNode<T>>{
    private T _key ;
    private HashSet<Object> _value;
    private Color _color;
    private TreeNode<T> _left;
    private TreeNode<T> _right;
    private TreeNode<T> _parent;


    public TreeNode(T key, Object value){
        var newValue = new HashSet<Object>();
        newValue.add(value);
        _key = key;
        _value = newValue;
    }

    public  T get_key() {
        return _key;
    }
    public Color get_color() {
        return _color;
    }

    public boolean isRoot() {
        return this.get_parent() == null;
    }

    public TreeNode<T> get_right() {
        return _right;
    }

    public TreeNode<T> get_left() {
        return _left;
    }

    public TreeNode<T> get_parent() {
        return _parent;
    }

    public void set_color(Color _color) {
        this._color = _color;
    }

    public void set_right(TreeNode<T> _right) {
        this._right = _right;
    }

    public void set_left(TreeNode<T> _left) {
        this._left = _left;
    }

    public void set_parent(TreeNode<T> _parent) {
        this._parent = _parent;
    }

    @Override
    public int compareTo(TreeNode<T> o) {
        return this.get_key().compareTo(o.get_key());
    }

    public void update_value(Object newValue){
        _value.addAll((Collection<?>) newValue);
    }

    public HashSet<Object> get_value() {
        return _value;
    }

}

