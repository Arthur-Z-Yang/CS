import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("NaN");
        }

        int index = -1;
        int high = a.length - 1;
        int low = 0;

        // while we haven't found a value, perform modified binary search
        while (low <= high) {
            // update mid and compare with key
            int mid = low + (high - low) / 2;
            int compare = comparator.compare(a[mid], key);

            // case 1: if array is length 1, check if both high and low
            // are the same value, if so and compare is zero return any
            // index, otherwise return -1 if they are equal and compare != 0
            if (high == low && compare == 0) {
                return low;
            }
            else if (high == low) {
                return -1;
            }


            // Once high is one space from low, check if one value is the key
            // starting from the value of low first (because we want first index)
            if (high - low == 1) {
                if (compare == 0) {
                    return mid;
                }
                // if not low, check if high contains the key
                if (index == high) {
                    return index;
                }
                // corner case if first index is the last value, since mid
                // never reaches it we can't use mid to check and so we need
                // an extra compare to check high
                if (high == a.length - 1) {
                    if (comparator.compare(a[high], key) == 0) {
                        return high;
                    }
                }
                // if the key isn't present, return -1
                return -1;
            }

            // update the values of high, low, and index depending on compare
            // if it is a key, move to the lower part of the array
            if (compare == 0) {
                index = mid;
                high = mid;
            }
            // if we are below the key, move to the upper part of the array
            else if (compare < 0) {
                low = mid;
            }
            // if we are above the key, move to the lower part of the array
            else {
                high = mid;
            }
        }
        return index;
    }


    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("NaN");
        }
        int index = -1;
        int high = a.length - 1;
        int low = 0;

        // while we haven't found a value, perform modified binary search
        while (low <= high) {
            // update mid and compare with key
            int mid = low + (high - low) / 2;
            int compare = comparator.compare(a[mid], key);

            // case 1: if array is length 1, check if both high and low
            // are the same value, if so and compare is zero return any
            // index, otherwise return -1 if they are equal and compare != 0
            if (high == low && compare == 0) {
                return low;
            }
            else if (high == low) {
                return -1;
            }

            // Once high is one space from low, check if one value is the key
            // starting from the value of high first (because we want last index)
            if (high - low == 1) {
                if (index == high) {
                    return index;
                }
                // corner case if first index is the last value, since mid
                // never reaches it we can't use mid to check and so we need
                // an extra compare to check high
                if (high == a.length - 1) {
                    if (comparator.compare(a[high], key) == 0) {
                        return high;
                    }
                }
                // check if the lower value contains key
                if (compare == 0) {
                    return mid;
                }
                // if no key is found, return -1
                return -1;
            }

            // update the values of high, low, and index depending on compare
            // if it is a key, move to the upper part of the array
            if (compare == 0) {
                index = mid;
                low = mid;
            }
            // if we are below the key, move to the upper part of the array
            else if (compare < 0) {
                low = mid;
            }
            // if we are above the key, move to the lower part of the array
            else {
                high = mid;
            }
        }

        return index;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // intialize and add values
        int n = Integer.parseInt(args[0]);
        Integer[] testArray = new Integer[5 * n];
        for (int i = 0; i < n; i++) {
            for (int k = i * 5; k < (i + 1) * 5; k++) {
                testArray[k] = i + 1;
            }
        }

        // generate the key and then perform binarySearch for first and last index
        int key = StdRandom.uniformInt(0, n + 1);
        int binaryFirIndex = firstIndexOf(testArray, key, Integer::compare);
        int binaryLastIndex = lastIndexOf(testArray, key, Integer::compare);
        int firstIndex = -1;
        int lastIndex = -1;

        // use brute force method
        for (int i = 0; i < testArray.length; i++) {
            if (testArray[i] == key && firstIndex < 0) {
                firstIndex = i;
            }
        }
        for (int i = testArray.length - 1; i >= 0; i--) {
            if (testArray[i] == key && lastIndex < 0) {
                lastIndex = i;
            }
        }

        // check if binarySearch values equal to brute force values
        if (binaryFirIndex != firstIndex || binaryLastIndex != lastIndex) {
            System.out.println("Error");
        }

    }
}
