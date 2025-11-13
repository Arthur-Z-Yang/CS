import java.util.Comparator;

public class Term implements Comparable<Term> {
    // the string of the term
    private String query;
    // the value of the weight of the string
    private long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0) {
            throw new IllegalArgumentException("NaN");
        }
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ComparableReverse();
    }

    // helper method which does the comparing of two terms
    private static class ComparableReverse implements Comparator<Term> {
        // compare here checks the weights by descending order
        // first greater than second -> return -1
        public int compare(Term firstTerm, Term secondTerm) {
            return (int) (secondTerm.weight - firstTerm.weight);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("NaN");
        }
        return new ComparablePrefix(r);
    }

    // helper method which compares the characters of the two terms
    private static class ComparablePrefix implements Comparator<Term> {
        // length of string we want to check
        private int r;

        // creates a new ComparablePrefix with check length of r
        public ComparablePrefix(int r) {
            this.r = r;
        }

        // loop through r characters and checks if the lexicographic
        // order of each character
        public int compare(Term firstTerm, Term secondTerm) {
            // getting lengths and strings
            int lengthFirst = firstTerm.query.length();
            int lengthSecond = secondTerm.query.length();
            int count = 0;
            int difference = 0;

            // while we still within the character limit r and there is no
            // difference, keep incrementing through each character
            while (count < r && difference == 0) {
                difference = firstTerm.query.charAt(count)
                        - secondTerm.query.charAt(count);
                count++;

                // after each increment, checking if we've passed the length
                // of one of the two strings
                if (count >= lengthFirst || count >= lengthSecond) {
                    // case 1: both are the same length and there is no difference
                    if (difference == 0 && lengthSecond == lengthFirst) {
                        return difference;
                    }
                    // case 2: we've reached r and it is also the length of
                    // one or both of the arrays
                    else if (count == r) {
                        return difference;
                    }
                    // case 3 & 4: one array is longer than the other but r has
                    // not been reached, so return the value accordingly
                    else if (difference == 0 && lengthSecond <= count) {
                        return firstTerm.query.charAt(count);
                    }
                    else if (difference == 0) {
                        return -secondTerm.query.charAt(count);
                    }
                    return difference;
                }
            }
            return difference;

        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // create some terms
        Term t1 = new Term("prefix", 10);
        Term t2 = new Term("prefact", 10);
        Term t3 = new Term("prefact", 12);
        Term t4 = new Term("prefix", 10);

        // should print out "prefix   10"
        System.out.println(t1);

        // should print out 1 as prefix is higher in order than prefact
        System.out.println(t1.compareTo(t2));
        // should print out 0 as prefix is equal to prefix
        System.out.println(t1.compareTo(t4));
        // should print out -1 as this test is the opposite of t1.compareTo(t2)
        System.out.println(t2.compareTo(t1));

        // testing comparators
        Comparator<Term> weight = Term.byReverseWeightOrder();
        // since the weight of t1 < t3, we expect it to return 1
        System.out.println(weight.compare(t1, t3));
        // since this is the opposite of the test above, we expect -1
        System.out.println(weight.compare(t3, t1));
        // check if weights are the same and returns 0
        System.out.println(weight.compare(t1, t2));

        // using case where the prefix length is longer than the query lengths
        Comparator<Term> prefixOrder = Term.byPrefixOrder(8);
        // since prefix part "i" is larger than prefact "a" we expect 1
        System.out.println(prefixOrder.compare(t1, t2));
        // opposite order so we expect -1
        System.out.println(prefixOrder.compare(t2, t1));
        // check if it works when two strings are equal
        System.out.println(prefixOrder.compare(t2, t3));

        // using case where prefix length is shorter than query length
        Comparator<Term> prefixShort = Term.byPrefixOrder(4);
        // since up to four characters they are the same we expect 0
        System.out.println(prefixShort.compare(t1, t2));
    }

}
