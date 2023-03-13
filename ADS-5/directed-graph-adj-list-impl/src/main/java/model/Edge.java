package model;

/**
 * This class governs the structure of the edges in the graph. Each edge will
 * have a source and destination specifying the connection between the two
 * vertices
 */
public class Edge {

	private String source;
	private String destination;

	public Edge(String source, String destination) {
		this.source = source;
		this.destination = destination;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
