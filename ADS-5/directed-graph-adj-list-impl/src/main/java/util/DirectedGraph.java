package util;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.Edge;
import model.Vertex;

/**
 * This class contains the actual implementation of the directed graph using the
 * adjacency list approach. The created graph will have two types of vertices
 * and directed links between vertices of different types. Note that the
 * supported graph will effectively be a bipartite graph (effectively meaning
 * that there will be no edges between vertices of the same type)
 */

public class DirectedGraph<T1 extends Vertex, T2 extends Vertex> implements Graph<T1, T2> {

	/**
	 * Two hashmaps store the two types of vertices stored within the graph. A
	 * hashmap has been used here in place of a list to enable quick retrieval of
	 * the vertex data given a vertex id.
	 */
	private HashMap<String, T1> sourceVertices = new HashMap<>();
	private HashMap<String, T2> destVertices = new HashMap<>();

	/**
	 * This hashmap stores the various vertices. For each vertex, a linked list is
	 * created. So, in the hashmap the key will be the vertex id, the value will be
	 * the list. The list will have all the edges with the destination information
	 * from the source which is the current vertex.
	 */
	private HashMap<String, List<String>> graph = new HashMap<>();

	/**
	 * This method is used to add the edges and the corresponding vertices to the
	 * graph<br>
	 * Workflow of this method:<br>
	 * 1. This method first checks if the source vertex id is present in the
	 * graph.<br>
	 * 2. If it is present it will get the source vertex and add the directed edge
	 * to the destination vertex id.<br>
	 * 3. If the source is not present, the source vertex will first be added to the
	 * graph, the linked list will be created for the edges and then the directed
	 * edge will be added to the destination vertex <br>
	 * 4. The source and the destination vertices will be added to the corresponding
	 * hashmaps with the id and details for quick retrieval.
	 * 
	 * @param sourceVertex      The source vertex to be added into graph or the
	 *                          source to which the destination should be joined
	 * @param destinationVertex The destination vertex to be added in the graph with
	 *                          the directed edge from the source
	 * @param edge              The directed edge from the source to destination
	 *                          vertex
	 */
	@Override
	public void addEdge(T1 sourceVertex, T2 destinationVertex, Edge edge) {
		// If source vertex is not added in the graph
		if (!this.sourceVertices.containsKey(edge.getSource())) {
			this.graph.put(edge.getSource(), new LinkedList<>());
			// Storing the source vertices in the relevant hashmap for quick retrieval
			this.sourceVertices.put(sourceVertex.getVertexId(), sourceVertex);
		}
		// Add the destination vertex to the graph and associating the directed edge
		// from the source
		this.graph.get(edge.getSource()).add(edge.getDestination());
		// Storing the destination vertices in the relevant hashmap for quick retrieval
		if (!this.destVertices.containsKey(edge.getDestination()))
			this.destVertices.put(destinationVertex.getVertexId(), destinationVertex);
	}

	/**
	 * @param sourceId This refers to an identifier for a source vertex
	 * @return The source vertex corresponding to the provided vertex id. null if a
	 *         vertex with the said id is not present
	 */
	@Override
	public T1 getSourceVertex(String sourceId) {
		return this.sourceVertices.get(sourceId);
	}

	/**
	 * @param destId This refers to an identifier for a destination vertex
	 * @return The destination vertex corresponding to the provided vertex id. null
	 *         if a vertex with the said id is not present
	 */
	@Override
	public T2 getDestinationVertex(String destId) {
		return this.destVertices.get(destId);
	}

	/**
	 * @param sourceId This refers to an identifier for a source vertex
	 * @return The list of destination vertices that the provided source vertex is
	 *         connected to
	 */
	@Override
	public List<T2> getSourceVertexEdges(String sourceId) {
		List<T2> edges = new ArrayList<>();
		// get the list of all the destination vertices connected to from the source
		List<String> sourceEdges = this.graph.get(sourceId);
		if (sourceEdges == null)
			return null;
		// Iterate over each of the destination vertex id in the list, get the
		// corresponding vertex details and return the entire list
		Iterator<String> iterator = sourceEdges.iterator();
		while (iterator.hasNext())
			edges.add(this.destVertices.get(iterator.next()));
		return edges;
	}

	/**
	 * Tests whether the graph is empty.
	 * 
	 * @return true if the graph is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.graph.isEmpty();
	}

	/**
	 * Returns the number of vertices in the graph.
	 * 
	 * @return number of vertices in the graph
	 */
	public int size() {
		return this.sourceVertices.size() + this.destVertices.size();
	}
}
