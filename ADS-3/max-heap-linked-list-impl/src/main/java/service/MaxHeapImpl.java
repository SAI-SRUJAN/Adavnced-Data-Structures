package service;

import model.Node;
import org.javatuples.Pair;

import java.util.*;

/**
 * This class provides the implementation for the Max Heap. Each element in the heap is stored in a node which has 3
 * pointers in addition to the data element. One pointer refers to the nodes, parent in the heap and the 2 pointers hold
 * references to the left and right child of the node respectively.
 *
 * The class exposes methods to extract the top element from the heap, and also to extract the top k elements from the heap.
 * */

public class MaxHeapImpl<T extends Comparable<T>> {

    private Node<T> rootNode;
    private int numNodes;


    /**
     * Build a max heap containing the provided data elements. The fact that a heap is always a complete
     * binary tree is exploited during the heap build process.
     * The provided data elements are sorted in descending order and then filled into the tree level by level
     * (left to right in each level). This ensures that the resulting tree is always a complete binary tree.
     * Further, pre-sorting the data in descending order prior to building the heap ensures that the
     * max-heap property is always satisfied.
     *
     * @param data The list of items to be stored within the max-heap
     *
     * */
    public MaxHeapImpl(List<T> data){

        // Sort data in reverse order
        data.sort(Collections.reverseOrder());
        this.numNodes = data.size();

        // Add the data sorted in reverse order to be added to the heap
        Queue<T> dataQueue = new LinkedList<T>(data);

        /* Use this queue to store the sequence in which child nodes can be assigned to already created nodes in the heap
            A node will be assigned two children, before the next candidate parent node is extracted from the queue
        */
        Queue<Node<T>> heapNodes = new LinkedList<>();
        this.rootNode = new Node<T>(dataQueue.poll(), null, null, null);
        heapNodes.offer(this.rootNode);

        Node<T> currParent = null;
        Node<T> currNode = null;

        while(!dataQueue.isEmpty()){
            if(currParent == null){
                currParent = heapNodes.poll();
            }else if(currParent.getLeftChild() != null && currParent.getRightChild()!= null ){
                currParent = heapNodes.poll();
            }
            currNode = new Node<T>(dataQueue.poll(), currParent, null, null);
            heapNodes.add(currNode);
            if(currParent.getLeftChild() == null){
                currParent.setLeftChild(currNode);
            }else{
                currParent.setRightChild(currNode);
            }
        }
    }

    /**
     *
     * This method returns the top k elements from the max-heap. If the heap is empty, an empty list is returned.
     * The function internally uses the extractMax() method iteratively to retrieve the top k elements.
     * If the heap has fewer thank k elements, all elements from the heap are returned in descending order
     *
     * @param k Number of elements to be returned from the heap
     * @return The top k elements from the heap in descending order.
     * */
    public List<T> extractMax(int k){

        double startTime = System.nanoTime();
        List<T> res = new ArrayList<>();

        /* Invoke the extract max method k times and add the values to the list containing the results. If
        we run out of elements in the heap before being able to extract k elements, stop the loop
         */
        while(this.numNodes != 0 && k > 0){
            res.add(this.extractMax());
            k--;
        }
        double endTime = System.nanoTime();
        System.out.printf("Query time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
        return res;
    }

    /**
     * This method returns the max element from the heap. The max element of the heap is always at the root of
     * the heap.
     *
     * Workflow within this method:
     * 1. Store the data element from the top node into a local variable
     * 2. Swap the top node of the heap with the last node of the heap
     * 2. Delete the last node from the heap
     * 3. Heapify to restore the heap property
     * 4. Return the data element stored in the local variable
     *
     * @return The max element from the heap
     * */
    public T extractMax(){
        if(this.rootNode == null)
            return null;

        // Copy over the value at the root node of the heap to be returned at the end of the method
        T val = this.rootNode.getVal();

        if(this.numNodes == 1){
            this.rootNode = null;
            this.numNodes--;
            return val;
        }

        Pair<Node<T>, Boolean> lastNode = getLastNode();

        // Swap the root node with the last node of the heap
        swapNodeValues(lastNode.getValue0(), this.rootNode);

        // Delete the last node from the heap
        if(lastNode.getValue1())
            lastNode.getValue0().getParent().setLeftChild(null);
        else
            lastNode.getValue0().getParent().setRightChild(null);

        this.numNodes--;

        /* Restore the heap property. This is needed as the last node of the heap was swapped with the root
        to facilitate removal of the root node. This swap might have violated the heap property, which is why we run the
        requisite operations to restore the heap property.
         */
        pushDownRoot();
        return val;
    }

    /**
     * This method pushes the root node down the binary tree to ensure that the heap property is satisfied.
     * This method is used to restore the max heap property after running the extract max operation.
     *
     * Workflow within this method
     * 1. If the root node is null, return
     * 2. If the node being processed is the leaf node, return
     * 3. If the node does not have a right child and the data element in the node is less than the data element at its left child,
     * swap the data elements between the node and its left child and return.
     * 4. If current node has 2 children, and the value of the data element at the current node is greater than the value of elements at both its children, return
     * 5. Finally, if the value of the node is greater than the value at either/both of its children, swap the element at the node with the
     * element at its child with holding the higher value and continue the process in the relevant sub-tree
     * */
    private void pushDownRoot(){

        Node<T> currNode = this.rootNode;
        Node<T> greaterChild = null;

        while(true){
            if(currNode == null)
                return;

            if(currNode.getLeftChild() == null && currNode.getRightChild()==null)
                return;

            // If the node only has a left child and if it violates the heap property, swap the node with its left child
            if(currNode.getRightChild() == null){
                if(currNode.getVal().compareTo(currNode.getLeftChild().getVal()) < 0)
                    swapNodeValues(currNode, currNode.getLeftChild());
                return;
            }

            // Return if the heap property is already satisfied. No swaps needed in this case.
            if(currNode.getVal().compareTo(currNode.getLeftChild().getVal()) >= 0 &&
                    currNode.getVal().compareTo(currNode.getRightChild().getVal()) >= 0)
                return;

            /* If the heap property is not satisfied, swap the node with the child that has the greater value.
                Then recursively heapify the impacted sub-tree
             */
            if(currNode.getLeftChild().getVal().compareTo(currNode.getRightChild().getVal()) > 0){
                greaterChild = currNode.getLeftChild();
            }else{
                greaterChild = currNode.getRightChild();
            }
            swapNodeValues(currNode, greaterChild);
            currNode = greaterChild;
        }
    }

    /**
     * This method swaps the elements of the two passed nodes in the heap
     *
     * @param n1 Node 1
     * @param n2 Node 2
     * */
    private void swapNodeValues(Node<T> n1, Node<T> n2){
        T tmp = n1.getVal();
        n1.setVal(n2.getVal());
        n2.setVal(tmp);
    }

    /**
     *This method returns a pari with the path to the last node in the heap and a boolean value indicating whether the
     * last node is the left child  of its parent.
     *
     * To identify the path to the last node the following algorithm is used.
     * 1. Convert the number of nodes in the heap to its binary representation and ignore the most significant bit (MSB)
     * 2. For the remaining bits, traverse from MSB to LSB and navigate into the child nodes. Navigate into the left child
     * if the bit is 0 and into the right child if the bit is 1. The node that we end up in after traversing through the
     * entire binary notation of the number of nodes is the last node in the heap.
     * */
    private Pair<Node<T>, Boolean> getLastNode(){
        Node<T> currNode = this.rootNode;

        // Determine traversal path to last node based on approach specified in javadoc of this method
        char[] path = Integer.toString(this.numNodes, 2).substring(1).toCharArray();
        boolean isLeftChild = false;
        for(char p: path){
            // Navigate into the left sub-tree if the next path element is 0, else navigate into the right sub-tree
            if(p == '0'){
                currNode = currNode.getLeftChild();
                isLeftChild = true;
            }else{
                currNode = currNode.getRightChild();
                isLeftChild = false;
            }
        }
        return new Pair<>(currNode, isLeftChild);
    }
}
