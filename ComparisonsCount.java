/**
 * 
 */

package course1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * This class is used to count the number of
 * 
 * @author abinashsinha
 */
public class ComparisonsCount {

	private static PivotTechnique pivotTechnique = PivotTechnique.First; //default value of First

	private static long quickSortAndCompareCount(int[] array, int left, int right) {

		long count = 0;
		if (right > left) {
			int pivotIndex = 0;
			pivotIndex = partitionAndCompareCount(array, left, right, pivotTechnique);
			count += right - left; //number of comparisons when input is array on which partition
			count += quickSortAndCompareCount(array, left, pivotIndex - 1);
			count += quickSortAndCompareCount(array, pivotIndex + 1, right);
		}
		return count;
	}

	private static int partitionAndCompareCount(int[] arr, int left, int right, PivotTechnique pivotTechnique) {

		if (PivotTechnique.Last == pivotTechnique)
			swap(arr, left, right);
		else if (PivotTechnique.MedianOfThree == pivotTechnique) {
			int indexOfMedian = right;
			int mid = left + (right - left) / 2;
			int first = arr[left];
			int middle = arr[mid];
			int last = arr[right];
			if ((middle < first && first < last) || (middle > first && first > last))
				indexOfMedian = left;
			else if ((first < middle && middle < last) || (first > middle && middle > last))
				indexOfMedian = mid;
			swap(arr, left, indexOfMedian);
		}
		int pivotIndex = left;
		int pivot = arr[left];
		int i = left + 1;
		for (int j = left + 1; j <= right; ++j) {
			if (arr[j] < pivot) {
				swap(arr, i, j);
				++i;
			}
		}
		pivotIndex = i - 1;
		swap(arr, left, pivotIndex);
		return pivotIndex;
	}

	/**
	 * Method to swap elements at indexes i and j of array, arr
	 * 
	 * @param arr
	 * @param i
	 * @param j
	 */
	private static void swap(int[] arr, int i, int j) {

		int temp = arr[j];
		arr[j] = arr[i];
		arr[i] = temp;
	}

	public static void main(String[] args) {

		FileReader file = null;
		try {
			file = new FileReader("src/course1/Assignment3.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Error while reading file");
			System.exit(0);
		}
		BufferedReader reader = new BufferedReader(file);
		Scanner s = new Scanner(reader);
		//input array
		int inputLength = 10000;
		int[] array = new int[inputLength];
		int i = 0;
		while (s.hasNextInt()) {
			array[i++] = s.nextInt();
		}
		//counting number of inversions in the array
		System.out.println("Counting the number of inversions using piggybacking on merge sort");
		pivotTechnique = PivotTechnique.MedianOfThree;
		System.out.println(quickSortAndCompareCount(array, 0, inputLength - 1));
	}
}
