/**
 * 
 */

package course2.medianmaintenance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This class is used to maintain the median of the collection of numbers whose size is increasing with number coming
 * from input stream
 * 
 * @author abinashsinha
 */
public class MedianMaintainer {

	public static void main(String[] args) throws IOException {

		FileReader fileReader = new FileReader("src/course2/medianmaintenance/Assignment3.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = bufferedReader.readLine();
		List<Integer> mediansList = new ArrayList<>();
		int mediansSum = 0;
		int currMedian = 0;
		Comparator<Integer> minimumComparator = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {

				return o1.compareTo(o2);
			}
		};
		Comparator<Integer> maximumComparator = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {

				return o2.compareTo(o1);
			}
		};
		PriorityQueue<Integer> smallerHalfHeap = new PriorityQueue<>(maximumComparator);
		PriorityQueue<Integer> largerHalfHeap = new PriorityQueue<>(minimumComparator);
		int smallerHeapSize = 0;
		int largerHeapSize = 0;
		//		int counter = 0;
		while (line != null && line != "") {
			Integer element = Integer.parseInt(line);
			if (smallerHalfHeap.peek() == null ? true : element < smallerHalfHeap.peek())
				smallerHalfHeap.offer(element);
			else
				largerHalfHeap.offer(element);
			smallerHeapSize = smallerHalfHeap.size();
			largerHeapSize = largerHalfHeap.size();
			//re-balance heaps so that difference in number of elements in both heaps is never greater than one
			if (smallerHeapSize - largerHeapSize >= 2)
				largerHalfHeap.offer(smallerHalfHeap.poll());
			else if (smallerHeapSize - largerHeapSize <= -2)
				smallerHalfHeap.offer(largerHalfHeap.poll());
			//find the median of all numbers retrieved till now from the file
			if ((smallerHeapSize + largerHeapSize) % 2 == 0 || smallerHeapSize > largerHeapSize) {
				currMedian = smallerHalfHeap.peek();
				mediansList.add(currMedian);
			} else {
				currMedian = largerHalfHeap.peek();
				mediansList.add(currMedian);
			}
			mediansSum = mediansSum + currMedian;
			//			++counter;
			line = bufferedReader.readLine(); //moving to next line
		}
		System.out.println(mediansSum % 10000);
		//		System.out.println(mediansList);
		bufferedReader.close();
	}
}
