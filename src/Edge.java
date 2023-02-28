
public class Edge {
	//datafields
	public int startVertex;
	public int endVertex;
	public int flow = 0;
	public int capacity;
	public Edge residualEdge;
	
	//constructor
	public Edge(int start, int end, int capacity) {
		this.startVertex = start;
		this.endVertex = end;
		this.capacity = capacity;
	}
	
	//helper methods
	
	//returns current capacity of a edge
	public int presentCapacity() {
		return this.capacity - this.flow;
	}
	
	//increments the flow of a edge
	public void incrementFlow(int bottleNeck) {
		this.flow += bottleNeck;
		this.residualEdge.flow -= bottleNeck;
	}
}
