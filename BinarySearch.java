import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class BinarySearch {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        int index = -1;
        int high = a.length - 1;
        int low = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int compare = comparator.compare(a[mid], key);
            if (high == low && compare == 0) {
                return low;
            }
            if (high - low == 1) {
                if (comparator.compare(a[high], key) != 0 && compare != 0) {
                    return -1;
                }
                else if (compare == 0 && comparator.compare(a[low], key) == 0) {
                    return low;
                }
                else if (compare == 0) {
                    return mid;
                }
                else if (comparator.compare(a[high], key) == 0) {
                    return high;
                }
            }
            if (compare == 0) {
                index = mid;
                high = mid;
            }
            else if (compare < 0) {
                low = mid;
            }
            else {
                high = mid;
            }
        }
        return index;
    }


    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        int index = -1;
        int high = a.length - 1;
        int low = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int compare = comparator.compare(a[mid], key);

            if (high == low && compare == 0) {
                return low;
            }
            if (high - low == 1) {
                if (comparator.compare(a[high], key) == 0 && high == a.length - 1) {
                    return high;
                }
                if (comparator.compare(a[high], key) != 0 && compare != 0) {
                    return -1;
                }
                else if (comparator.compare(a[high], key) == 0) {
                    return high;
                }
                else if (compare == 0) {
                    return low;
                }
            }
            if (compare == 0) {
                index = mid;
                low = mid;
            }
            else if (compare < 0) {
                low = mid;
            }
            else {
                high = mid;
            }
        }
        return index;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Integer[] testArray = new Integer[5 * n];
        for (int i = 0; i < n; i++) {
            for (int k = i * 5; k < (i + 1) * 5; k++) {
                testArray[k] = i + 1;
            }
        }
        int key = StdRandom.uniformInt(0, n + 1);
        int binaryFirIndex = firstIndexOf(testArray, key, Integer::compare);
        int binaryLastIndex = lastIndexOf(testArray, key, Integer::compare);
        int firstIndex = -1;
        int lastIndex = -1;
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
        if (binaryFirIndex != firstIndex || binaryLastIndex != lastIndex) {
            System.out.println("Error");
        }
        String query = "GXNMSQP";
        String[] a = { "CALHXZS", "CFQGVTC", "DSINQQH", "JTIIJTG", "VUHQQDT", "WIVDOWQ" };
        System.out.println(lastIndexOf(a, query, String::compareTo));
        System.out.println(firstIndexOf(a, query, String::compareTo));
    }
}
