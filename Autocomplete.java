import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {
    // collection of items;
    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("NaN");
        }

        // intialize the array of terms
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException("NaN");
            }
            this.terms[i] = terms[i];
        }

        // sort the terms using the basic compareTo method we built
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("NaN");
        }

        // intialize the prefix term
        int r = prefix.length();
        Term pre = new Term(prefix, 0);

        // find indexes of first and last occurance of the terms
        int first = BinarySearchDeluxe.firstIndexOf(terms, pre, Term.byPrefixOrder(r));
        int last = BinarySearchDeluxe.lastIndexOf(terms, pre, Term.byPrefixOrder(r));

        // if we didn't find a value, return an empty array
        if (first == -1 || last == -1) {
            return new Term[0];
        }

        // if we found a value, create a new array and populate with the data
        Term[] matches = new Term[last - first + 1];
        for (int i = 0; i < matches.length; i++) {
            matches[i] = terms[i + first];
        }

        // sort the array using the weight comparator
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("NaN");
        }

        // intialize the prefix term
        int r = prefix.length();
        Term pre = new Term(prefix, 0);

        // find the indexes of the first and last occurance of the prefix
        int first = BinarySearchDeluxe.firstIndexOf(terms, pre, Term.byPrefixOrder(r));
        int last = BinarySearchDeluxe.lastIndexOf(terms, pre, Term.byPrefixOrder(r));

        // if no terms were found, return 0
        if (first == -1 || last == -1) {
            return 0;
        }

        // return the difference between the first and last indexes
        return last - first + 1;
    }

    // unit testing
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
