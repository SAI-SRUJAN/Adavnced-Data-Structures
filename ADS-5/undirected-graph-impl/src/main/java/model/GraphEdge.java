package model;

/**
 * This class governs the structure of the graph edge.
 * Each edge has the following attributes:
 * 1. The id of the src vertex
 * 2. The id of the destination vertex
 * */
public class GraphEdge {

    private String edgeSrc;
    private String edgeDest;

    public GraphEdge(String edgeSrc, String edgeDest) {
        this.edgeSrc = edgeSrc;
        this.edgeDest = edgeDest;
    }

    public String getEdgeSource(){
        return edgeSrc;
    }
    public String getEdgeDest(){
        return edgeDest;
    }
}
