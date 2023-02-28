import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class project5 {
	public static void main(String[] args) throws IOException {
		//for reading inputs
		File myFile = new File(args[0]);
		FileReader myFileReader = new FileReader(myFile);		
		BufferedReader myReader = new BufferedReader(myFileReader);
		
		//for output file
		File outputFile = new File(args[1]);
		FileWriter myWriter = new FileWriter(outputFile);
		
		int numberOfCities = 0;
		int numberOfVertices = numberOfCities + 8;
		NetworkFlowGraph myGraph = new NetworkFlowGraph(numberOfVertices, numberOfCities);
		Vertex sourceVertex = new Vertex("s", numberOfVertices - 2);
		Vertex sinkVertex = new Vertex("KL", numberOfVertices - 1);
		
		//reading inputs and adding storing them
		String inputData = null;
		int counter = 0;
		
		while((inputData = myReader.readLine()) != null) {
			if(counter == 0) {
				numberOfCities = Integer.valueOf(inputData);
				numberOfVertices = numberOfCities + 8;
				sourceVertex.index = numberOfVertices - 2;
				sinkVertex.index = numberOfVertices - 1;
				myGraph.createList(numberOfVertices);
				initiliazeAdjencyLsit(numberOfVertices, myGraph);
				counter++;
				continue;
			}
			else if(counter == 1) {
				String[] split = inputData.split(" ");
				for(int i = 0; i < split.length; i++) {
					addEdge(sourceVertex.index, i, Integer.valueOf(split[i]), myGraph);
				}
				counter++;
				continue;
			}
			else if(counter > 1 && counter < 8) {
				String[] split = inputData.split(" ");
				if(split.length == 1) {
					counter++;
					continue;
				}
				for(int j = 1; j < split.length; j += 2) {
					if(split[j].equals("KL")) {
						addEdge(counter - 2, sinkVertex.index, Integer.valueOf(split[j+1]), myGraph);
					}
					else {
						int index = Integer.valueOf( split[j].substring(1)) + 6;
						addEdge(counter - 2, index, Integer.valueOf(split[j+1]), myGraph);
					}	
				}
				counter++;
				continue;
			}
			else if(counter > 7) {
				String[] split = inputData.split(" ");
				if(split.length == 1) {
					counter++;
					continue;
				}
				int start = Integer.valueOf(split[0].substring(1)) + 6;
				for(int j = 1; j < split.length; j += 2) {
					if(split[j].equals("KL")) {
						addEdge(start, sinkVertex.index, Integer.valueOf(split[j+1]), myGraph);
					}
					else {
						int index = Integer.valueOf( split[j].substring(1)) + 6;
						addEdge(start, index, Integer.valueOf(split[j+1]), myGraph);
					}
				}
				counter++;
				continue;
			}
			
		}
		myReader.close();
		
		int maxFlow = 0;
		int INF = Integer.MAX_VALUE;
		for(int flow = dfs(sourceVertex.index, INF, sinkVertex.index, sourceVertex.index, myGraph); flow != 0; flow = dfs(sourceVertex.index, INF, sinkVertex.index, sourceVertex.index, myGraph)) {
			myGraph.visitedCounter++;
			maxFlow += flow;
		}
	
		myWriter.write(String.valueOf(maxFlow) + "\n");
			
		List<Edge> edgesOfSource = myGraph.adjancencyList[numberOfVertices - 2];
		for(int a = 0; a < edgesOfSource.size(); a++) {
			if(edgesOfSource.get(a).flow == edgesOfSource.get(a).capacity) {
				myWriter.write("r" + String.valueOf(edgesOfSource.get(a).endVertex) +"\n");
			}
		}
		myWriter.close();
	}
	
	//initiliazes the adjacency List
	private static void initiliazeAdjencyLsit(int numberOfVertices, NetworkFlowGraph myGraph) {
		for(int i = 0; i < numberOfVertices; i++) {
			myGraph.adjancencyList[i] = new ArrayList<Edge>();
		}
		
	}
	//implies depth first search
	private static int dfs(int index, int flow, int sinkVertex, int sourceVertex, NetworkFlowGraph myGraph) {
		if(index == sinkVertex) {
			return flow;
		}
		myGraph.visited[index] = myGraph.visitedCounter;
		List<Edge> edges = myGraph.adjancencyList[index];
		for(Edge eachEdge: edges) {
			if(eachEdge.presentCapacity() > 0 && myGraph.visited[eachEdge.endVertex] != myGraph.visitedCounter) {
				int bottleNeck = dfs(eachEdge.endVertex, Math.min(flow, eachEdge.presentCapacity()), sinkVertex, sourceVertex, myGraph);
				if(bottleNeck > 0) {
					eachEdge.incrementFlow(bottleNeck);
					return bottleNeck;
				}
			}
		}
		return 0;
	}
	//adding edges to adjacency list
	public static void addEdge(int start, int end, Integer capacity, NetworkFlowGraph myGraph) {
		Edge firstEdge = new Edge(start, end, capacity);
		Edge residualEdge = new Edge(end, start, 0);
		firstEdge.residualEdge = residualEdge;
		residualEdge.residualEdge = firstEdge;
		myGraph.adjancencyList[start].add(firstEdge);
		myGraph.adjancencyList[end].add(residualEdge);
		
	}
}
