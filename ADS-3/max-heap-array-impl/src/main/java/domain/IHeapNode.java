package domain;

import java.util.List;

/**
 * An interface for the generic Heap node using which will be stored in our MaxHeap implementation.
 */
public interface IHeapNode {
    IHeapNode[] CreateArray(Integer Size);
    IHeapNode CreateNewObject(Object Id, Object value);
    void UpdateValue(Object value);
    Object GetId();
    Double GetValue();
}
