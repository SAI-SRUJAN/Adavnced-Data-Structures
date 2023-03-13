package service;

import domain.IHeapNode;

/**
 * Implementation of MaxHeap
 */

public class MaxHeap implements Cloneable {
    private IHeapNode[] nodes;
    private Integer lastIndex;

    /**
     * takes size of the heap and type of the heap node as parameter.
     *
     * @param n
     * @param heapNodeType
     */
    public MaxHeap(int n, IHeapNode heapNodeType) {
        nodes = heapNodeType.CreateArray(n);
        lastIndex = -1;
    }

    /**
     * gets the index of the left child from the current index.
     * complexity : O(1)
     *
     * @param index
     * @return
     */
    private Integer leftChild(int index) {
        return index * 2 + 1;
    }

    /**
     * gets the index of the right child from the current index.
     * complexity : O(1)
     *
     * @param index
     * @return
     */
    private Integer rightChild(int index) {
        return index * 2 + 2;
    }

    /**
     * gets the index of the parent from the current index.
     * complexity : O(1)
     *
     * @param index
     * @return
     */
    private Integer parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * It is a node position correction operation and this method helps in shifting
     * the node up in the tree if the node's priority is high.
     * <p>
     * complexity: log n
     *
     * @param index
     * @return
     */
    private Integer ShiftUp(Integer index) {
        while (index > -1 && nodes[index].GetValue() > nodes[parent(index)].GetValue()) {
            var temp = nodes[parent(index)];
            nodes[parent(index)] = nodes[index];
            nodes[index] = temp;
            index = parent(index);
        }
        return index;
    }

    /**
     * It is a node position correction operation and this method helps in shifting
     * the node down in the tree if the node's priority is low.
     * <p>
     * complexity: log n
     *
     * @param index
     * @return
     */
    private Integer ShiftDown(Integer index) {
        while (index <= (lastIndex - 1) / 2 && lastIndex > 0) {
            var rightChildIndex = rightChild(index);
            var leftChildIndex = leftChild(index);
            Integer childToReplace;

            if (nodes[rightChildIndex] != null && nodes[leftChildIndex] != null) {
                childToReplace = nodes[rightChildIndex].GetValue() > nodes[leftChildIndex].GetValue()
                        ? rightChildIndex
                        : leftChildIndex;
            } else if (nodes[rightChildIndex] != null)
                childToReplace = rightChildIndex;
            else
                childToReplace = leftChildIndex;

            if (nodes[index].GetValue() < nodes[childToReplace].GetValue()) {
                var temp = nodes[childToReplace];
                nodes[childToReplace] = nodes[index];
                nodes[index] = temp;
                index = childToReplace;
            } else {
                break;
            }
        }
        return index;
    }

    /**
     * this method updates the priority of the node in the heap and
     * by calling shift up or shift down operations according to the change in the priority.
     * <p>
     * Complexity: log n
     *
     * @param index
     * @param newValue
     * @return
     */
    public Integer UpdateHeap(int index, Double newValue) {
        var oldValue = nodes[index].GetValue();
        nodes[index].UpdateValue(newValue);
        newValue = nodes[index].GetValue();
        if (oldValue > newValue) {
            return ShiftDown(index);
        } else if (oldValue < newValue) {
            return ShiftUp(index);
        }
        return index;
    }

    /**
     * Inserts a new node.
     * Initially the node is inserted at the end of the tree as a leaf node and then
     * shift up us called in order to determine the correct position of the node.
     * <p>
     * Complexity: log n
     *
     * @param node
     * @return
     */
    public Integer InsertNode(IHeapNode node) {
        var index = 0;
        lastIndex++;
        if (lastIndex == 0) {
            nodes[lastIndex] = node;
            index = lastIndex;
        } else {
            nodes[lastIndex] = node;
            index = ShiftUp(lastIndex);
        }
        return index;
    }

    /**
     * Get the root of the tree i.e. the node with the highest priority.
     * It does it by assigning root node to the result.
     * Then swapping the leaf node with the root node and deleting the leaf node.
     * Then calling shift down operation on the root node to correct the organization of the heap.
     * <p>
     * complexity: log n.
     *
     * @return
     */
    public IHeapNode ExtractMax() {
        IHeapNode result;
        if (lastIndex == 0) {
            lastIndex = -1;
            result = nodes[0];
        } else {
            result = nodes[0];
            nodes[0] = nodes[lastIndex];
            nodes[lastIndex] = null;
            lastIndex--;
            ShiftDown(0);
        }
        return result;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}