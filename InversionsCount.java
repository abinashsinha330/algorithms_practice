
package course1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class InversionsCount {

	private static long mergeSortAndInvCount(int[] array, int[] res, int left, int right) {

		long count = 0;
		if (right > left) {
			int mid = (left + right) / 2;
			count = mergeSortAndInvCount(array, res, left, mid);
			count += mergeSortAndInvCount(array, res, mid + 1, right);
			count += mergeAndSplitInvCount(array, res, left, mid + 1, right);
		}
		return count;
	}

	/**
	 * Counts number of split inversions between sub-arrays being merged
	 * 
	 * @param array
	 * @param res
	 * @param left
	 * @param mid
	 * @param right
	 * @return
	 */
	private static long mergeAndSplitInvCount(int[] array, int[] res, int left, int mid, int right) {

		long splitCount = 0;
		int i, j, k;
		i = left; // The left-most point in the first array
		j = mid;
		k = left; // Left-most point in the second array
		// Sorts the array while counting the inversions
		while ((i <= mid - 1) && (j <= right)) // While the left number is less than the midpoint AND when the midpoint is less than the right point
		{
			if (array[i] <= array[j]) // If the left number is smaller than the right number
			{
				res[k++] = array[i++]; // Add the smaller number to the 2nd array
			} else // If the right number is greater than the left number
			{
				res[k++] = array[j++]; // Add the smaller to the 2nd array...
				splitCount = splitCount + (mid - i); //... AND increase the inversion count by 1.
			}
		}
		// Copies the remaining elements in the left array to the temporary array
		while (i <= mid - 1)
			res[k++] = array[i++];
		// Copies the remaining elements in the right array to the temp array
		while (j <= right)
			res[k++] = array[j++];
		// Copies the merged elements to the original array
		for (i = left; i <= right; i++)
			array[i] = res[i];
		return splitCount;
	}

	public static void main(String[] args) {

		FileReader file = null;
		try {
			file = new FileReader("src/course1/Assignment2.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Error while reading file");
			System.exit(0);
		}
		BufferedReader reader = new BufferedReader(file);
		Scanner s = new Scanner(reader);
		//input array
		int inputLength = 100000;
		int[] array = new int[inputLength];
		int i = 0;
		while (s.hasNextInt()) {
			array[i++] = s.nextInt();
		}
		int[] res = new int[inputLength];
		//counting number of inversions in the array
		System.out.println("Counting the number of inversions using piggybacking on merge sort");
		System.out.println(mergeSortAndInvCount(array, res, 0, inputLength - 1));
	}
}
