/**
 * 
 */

package course2.dijkstra;

import java.util.HashMap;
import java.util.Map;

public class Vertex {

	// A list of all vertices adjacent to this one along with length of edge to that vertex
	private Map<Vertex, Integer> adjacentVertices;
	// Flag denoting whether a vertex has already been visited
	private boolean visited;
	// Number value associated with each vertex
	private int value;

	/**
	 * 
	 */
	public Vertex(int value) {

		this.adjacentVertices = new HashMap<>();
		this.visited = false;
		this.value = value;
	}

	public int getValue() {

		return this.value;
	}

	public void setValue(int value) {

		this.value = value;
	}

	/**
	 * @return
	 */
	public boolean isVisited() {

		return visited;
	}

	/**
	 * @param visited
	 */
	public void setVisited(boolean visited) {

		this.visited = visited;
	}

	/**
	 * @return
	 */
	public Map<Vertex, Integer> getAdjacentVertices() {

		return adjacentVertices;
	}

	/**
	 * @param newVertex
	 */
	public void addAdjacentNode(Vertex newVertex, int edgeLength) {

		this.adjacentVertices.put(newVertex, edgeLength);
	}

	/**
	 * @param v
	 */
	public void removeAdjacentNode(Vertex v) {

		this.adjacentVertices.remove(v);
	}
}
