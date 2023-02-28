import java.util.ArrayList;
import java.util.List;

public class NetworkFlowGraph {
	//datafields
	public List<Edge>[] adjancencyList;
	public int[] visited;
	public int visitedCounter;
	public ArrayList<String> minCut;
	
	//constructor
	public NetworkFlowGraph(int numberOfVertices, int numberOfCities) {
		this.visitedCounter = 1;
		this.minCut = new ArrayList<>();
	}
	
	//helper methods	
	@SuppressWarnings("unchecked")
	public void createList(int numberOfVertices) {
		this.adjancencyList = new List[numberOfVertices];
		this.visited = new int[numberOfVertices];
	}
	
}
