package domain;
import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Graph {

    /**
     * Vertices: hashmap of vertices labels which again internally contains all the vertex objects that come under that label
     * <br/>
     * edges: hashmap of edge labels and edge list that belong to that edge type.
     * <br/>
     * edgeInfo: hashmap same as edges but this keeps the information of inVertex and outVertex label.
     */
    private final HashMap<String,HashMap<Integer,Object>> vertices;
    private final HashMap<String, HashSet<Pair<Integer,Integer>>> edges;
    private final HashMap<String, Pair<String,String>> edgeInfo;

    public Graph() {
        vertices = new HashMap<>();
        edges = new HashMap<>();
        edgeInfo = new HashMap<>();
    }

    /**
     * Adds a vertex under a vertex name label. If new vertex label creates a new entry in vertices and then add the
     * vertex object else updates the appends the vertex object in its appropriate vertex label.
     * @param vertexName
     * @param id
     * @param value
     */
    public void addV(String vertexName,Integer id, Object value) {
        if(vertices.containsKey(vertexName)){
            vertices.get(vertexName).put(id,value);
        } else {
            var newHashMap = new HashMap<Integer,Object>();
            newHashMap.put(id,value);
            vertices.put(vertexName,newHashMap);
        }
    }

    /**
     * Adds an edge of a edge type between 2 vertices and at the same time if the edge type is new
     * adds to and from vertex names for that edge type.
     * @param edgeName
     * @param toId
     * @param toVertexName
     * @param fromId
     * @param fromVertexName
     */
    public void addE(String edgeName,int toId,String toVertexName,
                     int fromId, String fromVertexName) {
        if(edges.containsKey(edgeName)){
            edges.get(edgeName).add(new Pair<>(toId,fromId));
        } else {
            var edgeList = new HashSet<Pair<Integer,Integer>>();
            edgeList.add(new Pair<>(toId,fromId));
            edges.put(edgeName,edgeList);
            edgeInfo.put(edgeName,new Pair<>(toVertexName,fromVertexName));
        }
    }

    public Object getV(String vertexName, int id){
        return vertices.get(vertexName).get(id);
    }

    /**
     * Traverses to the out degree of the vertex through a specified edge.
     * @param edgeName
     * @param fromId
     * @return
     */
    public Object out(String edgeName, int fromId){
        var links = edges.get(edgeName).stream()
                .filter(x->x.getValue0().equals(fromId)).map(Pair::getValue1).collect(Collectors.toList());
        var vertexNames = edgeInfo.get(edgeName);
        var result = new ArrayList<>();
        for(var link: links){
            result.add(getV(vertexNames.getValue1(),link));
        }
        return result;
    }

}
