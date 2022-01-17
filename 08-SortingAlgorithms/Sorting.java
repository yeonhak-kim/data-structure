import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
/**
 * Your implementation of various sorting algorithms.
 *
 * @author Yeonhak Kim
 * @version 1.0
 * @userid ykim713
 * @GTID 903170274
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place -> No elements are copied over
     * unstable -> The order of duplicate data might possibly change.
     * not adaptive
     *
     * -Process-
     * Read arr from the back (arr.length - 1) and
     * save max data at this location.
     *
     * Iterate from 0 to last data and compare them with last data.
     *
     * Update index max data.
     *
     * Swap
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr,
                                         Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr to be sorted is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator to be used"
                    + " for comparing is null.");
        }

        if (arr.length <= 1) {
            return;
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int maxInd = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[maxInd]) > 0) {
                    maxInd = j;
                }
            }
            if (maxInd != i) {
                T temp = arr[maxInd];
                arr[maxInd] = arr[i];
                arr[i] = temp;
            }
        }

    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr,
                                         Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr to be sorted is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator to be used"
                    + " for comparing is null.");
        }

        if (arr.length <= 1) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int ind = i;
            while (ind != 0 && comparator.compare(arr[ind], arr[ind - 1]) < 0) {
                T temp = arr[ind];
                arr[ind] = arr[ind - 1];
                arr[ind - 1] = temp;
                ind--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr,
                                        Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr to be sorted is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator to be used"
                    + " for comparing is null.");
        }

        if (arr.length <= 1) {
            return;
        }

        boolean needSwap = true;
        int startInd = 0;
        int endInd = arr.length - 1;
        int newEndInd = 0;
        int newStartInd = 0;
        while (needSwap) {
            needSwap = false;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    needSwap = true;
                    newEndInd = i;
                }
            }

            if (needSwap) {
                endInd = newEndInd;
                needSwap = false;
                for (int j = endInd; j > startInd; j--) {
                    if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                        T temp = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp;
                        needSwap = true;
                        newStartInd = j;
                    }
                }
                startInd = newStartInd;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr,
                                     Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr to be sorted is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator to be used"
                    + " for comparing is null.");
        }

        if (arr.length <= 1) {
            return;
        }

        int length = arr.length;
        int midInd = length / 2;
        T[] left = (T[]) new Object[midInd];
        T[] right = (T[]) new Object[length - midInd];

        for (int i = 0; i < midInd; i++) {
            left[i] = arr[i];
        }
        for (int j = 0; j < length - midInd; j++) {
            right[j] = arr[j + midInd];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int leftInd = 0;
        int rightInd = 0;
        int arrInd = 0;

        while (leftInd < midInd && rightInd < length - midInd) {
            if (comparator.compare(left[leftInd], right[rightInd]) <= 0) {
                arr[arrInd] = left[leftInd];
                leftInd++;
            } else {
                arr[arrInd] = right[rightInd];
                rightInd++;
            }
            arrInd++;
        }

        while (leftInd < midInd) {
            arr[arrInd] = left[leftInd];
            leftInd++;
            arrInd++;
        }
        while (rightInd < length - midInd) {
            arr[arrInd] = right[rightInd];
            rightInd++;
            arrInd++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr to be sorted is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator to be used"
                    + " for comparing is null.");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Rand is null.");
        }

        if (arr.length <= 1) {
            return;
        }

        quickSortHelper(arr, comparator, rand, 0, arr.length);
    }

    /**
     * Helper method for quickSort().
     * Recursively sorts out the given data set.
     * @param <T> data type to sort
     * @param arr the array that must be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivot
     * @param start start index
     * @param end end index
     */
    private static <T> void quickSortHelper(T[] arr,
                                            Comparator<T> comparator,
                                            Random rand, int start, int end) {
        if (end - start <= 1) {
            return;
        }

        int pivotIndex = rand.nextInt(end - start) + start;
        T pivotValue = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = pivotValue;

        int left = start + 1;
        int right = end - 1;
        while (left <= right) {
            while (left <= right && comparator.compare(arr[left],
                    pivotValue) <= 0) {
                left++;
            }
            while (left <= right && comparator.compare(arr[right],
                    pivotValue) >= 0) {
                right--;
            }

            if (left <= right) {
                T temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
        T temp = arr[start];
        arr[start] = arr[right];
        arr[right] = temp;

        quickSortHelper(arr, comparator, rand, start, right);
        quickSortHelper(arr, comparator, rand, left, end);

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr is to be sorted is null.");
        }

        if (arr.length <= 1) {
            return;
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];
        int divideBase = 1;
        int iterations = 0;
        int max = 0;
        int min = 0;
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        while (max != 0 || min != 0) {
            max = max / 10;
            min = min / 10;
            iterations++;
        }

        for (int j = 0; j < iterations; j++) {
            for (int k = 0; k < arr.length; k++) {
                int bucket = arr[k] / divideBase % 10;
                bucket += 9;
                if (buckets[bucket] == null) {
                    buckets[bucket] = new LinkedList<>();
                }
                buckets[bucket].add(arr[k]);
            }
            int index = 0;
            for (int k = 0; k < 19; k++) {
                while (buckets[k] != null && !buckets[k].isEmpty()) {
                    arr[index] = buckets[k].remove();
                    index++;
                }
            }
            divideBase *= 10;
        }
    }
}
