package domain;

import java.util.HashSet;
import java.util.List;

public interface ITreeNode {
    ITreeNode[] CreateArray(Integer Size);
    ITreeNode CreateNewObject(Integer Id);
    void AddValue(Object value);
    Integer GetId();
    HashSet<Object> GetValue();
    List<String> GetHeaders();
}
