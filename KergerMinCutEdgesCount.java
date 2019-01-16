
package course1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This class uses Karger's randomized minimum cut algorithm to find out the best minimum cut and return its number of
 * crossing edges
 * 
 * @author abinashsinha
 */
public class KergerMinCutEdgesCount {

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		//Read the graph from the text file
		Map<Integer, ArrayList<Integer>> originalGraph = readGraphFromFile();
		int minimumCut = 0;
		for (int i = 0; i < 1000; i++) {
			//Copy the original graph in each iteration
			Map<Integer, ArrayList<Integer>> copyGraph = copyGraph(originalGraph);
			int result = processKargerMinimumCutAlgorithm(copyGraph);
			if (minimumCut == 0) {
				minimumCut = result;
			} else {
				if (result < minimumCut) {
					minimumCut = result;
				}
			}
			System.out.println("Partial Result => " + result);
		}
		System.out.println("*** Minimum Cut => " + minimumCut + " ***");
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken: " + (endTime - startTime) / 1000);
	}

	private static int processKargerMinimumCutAlgorithm(Map<Integer, ArrayList<Integer>> graph) {

		//Iterate until there are only two nodes
		while (graph.size() > 2) {
			processKargerAlgorithmStep(graph);
		}
		//Return the Minimum Cut (the number of edges of both nodes is the same)
		return graph.get((Integer) graph.keySet().toArray()[0]).size();
	}

	private static void processKargerAlgorithmStep(Map<Integer, ArrayList<Integer>> graph) {

		//Choose random items
		List<Integer> randomItems = chooseRandomItems(graph);
		Integer firstItem = randomItems.get(0);
		Integer secondItem = randomItems.get(1);
		ArrayList<Integer> firstItemList = graph.get(firstItem);
		ArrayList<Integer> secondItemList = graph.get(secondItem);
		//Add second list items to first list
		firstItemList.addAll(secondItemList);
		//Remove second list items
		graph.remove(randomItems.get(1));
		//Replace second item appearances by first item
		Iterator<Integer> it = graph.keySet().iterator();
		while (it.hasNext()) {
			Integer currentKey = it.next();
			ArrayList<Integer> currentItemList = graph.get(currentKey);
			for (Integer i : currentItemList) {
				if (i.intValue() == secondItem.intValue()) {
					currentItemList.set(currentItemList.indexOf(i), firstItem);
					//there can be mulitple edges between two vertices that is why we do not break
				}
			}
		}
		//Remove loops if any
		ArrayList<Integer> itemsToRemove = new ArrayList<Integer>();
		for (Integer i : firstItemList) {
			if (i.intValue() == firstItem.intValue()) {
				itemsToRemove.add(i);
			}
		}
		firstItemList.removeAll(itemsToRemove);
	}

	/**
	 * Select a random node and a random edge of the list of this node
	 * 
	 * @param graph
	 * @return
	 */
	private static List<Integer> chooseRandomItems(Map<Integer, ArrayList<Integer>> graph) {

		ArrayList<Integer> randomItems = new ArrayList<Integer>();
		int nodeIndex = (int) (Math.random() * graph.keySet().size());
		Integer randomNode = (Integer) (graph.keySet().toArray()[nodeIndex]);
		int edgeIndex = (int) (Math.random() * graph.get(randomNode).size());
		Integer randomEdge = graph.get(randomNode).get(edgeIndex);
		randomItems.add(randomNode);
		randomItems.add(randomEdge);
		return randomItems;
	}

	private static Map<Integer, ArrayList<Integer>> copyGraph(Map<Integer, ArrayList<Integer>> graph) {

		Map<Integer, ArrayList<Integer>> graphCopy = new HashMap<Integer, ArrayList<Integer>>();
		Iterator<Integer> it = graph.keySet().iterator();
		while (it.hasNext()) {
			Integer currentKey = it.next();
			ArrayList<Integer> currentItemList = graph.get(currentKey);
			graphCopy.put(currentKey, new ArrayList<Integer>(currentItemList));
		}
		return graphCopy;
	}

	private static Map<Integer, ArrayList<Integer>> readGraphFromFile() {

		Map<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("src/course1/Assignment4.txt");
			BufferedReader br = new BufferedReader(fileReader);
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(line);
				ArrayList<Integer> edges = new ArrayList<Integer>();
				Integer vertex = Integer.parseInt(tokens.nextToken());
				while (tokens.hasMoreTokens()) {
					edges.add(Integer.parseInt(tokens.nextToken()));
				}
				graph.put(vertex, edges);
			}
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Error in finding file");
			System.exit(0);
		} catch (IOException ex) {
			System.out.println("Error in buffering contents of file");
			System.exit(0);
		} finally {
			try {
				fileReader.close();
			} catch (IOException ex) {
				System.out.println("Error in closing file");
				System.exit(0);
			}
		}
		return graph;
	}
}
