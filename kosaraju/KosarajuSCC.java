/**
 * 
 */

package course2.kosaraju;

import java.io.IOException;
import java.util.List;

/**
 * @author abinashsinha
 */
public class KosarajuSCC {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// Construct the digraph from data file
		String filePath = "src/course2/Assignment1.txt";
		Graph g = new Graph(filePath);
		// Compute the strongly connected components
		List<Integer> components = g.stronglyConnectedComponents();
		// Print out sizes of (up to five) of the largest SCCs in comma separated list
		int size = components.size();
		for (int i = size - 1; size - i <= 5 && i >= 0; i--) {
			System.out.print(components.get(i) + ",");
		}
	}
}
