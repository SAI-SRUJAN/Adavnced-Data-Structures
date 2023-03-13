package util;

import java.util.List;

import model.Edge;

public interface Graph<T1, T2> {

	/**
	 * Returns the number of vertices in the graph.
	 * 
	 * @return number of vertices in the graph
	 */
	public int size();

	/**
	 * Tests whether the graph is empty.
	 * 
	 * @return true if the graph is empty, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * This method is used to add the edges and the corresponding vertices to the
	 * graph<br>
	 * 
	 * @param sourceVertex      The source vertex to be added into graph or the
	 *                          source to which the destination should be joined
	 * @param destinationVertex The destination vertex to be added in the graph with
	 *                          the directed edge from the source
	 * @param edge              The directed edge from the source to destination
	 *                          vertex
	 */
	public void addEdge(T1 sourceVertex, T2 destinationVertex, Edge edge);

	/**
	 * @param sourceId This refers to an identifier for a source vertex
	 * @return The source vertex corresponding to the provided vertex id. null if a
	 *         vertex with the said id is not present
	 */
	public T1 getSourceVertex(String sourceId);

	/**
	 * @param destId This refers to an identifier for a destination vertex
	 * @return The destination vertex corresponding to the provided vertex id. null
	 *         if a vertex with the said id is not present
	 */
	public T2 getDestinationVertex(String destId);

	/**
	 * @param sourceId This refers to an identifier for a source vertex
	 * @return The list of destination vertices that the provided source vertex is
	 *         connected to
	 */
	public List<T2> getSourceVertexEdges(String sourceId);

}
