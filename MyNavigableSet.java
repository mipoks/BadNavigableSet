package LessonSet2;

import java.util.*;

public class MyNavigableSet <T extends Comparable> extends AbstractSet <T> implements SortedSet <T> {

    private Vector<T> mas;
    private Comparator<? super T> comparator = null;
    private MyNavigableSet parent = null;

    private void checkElement(T element) {
        if (element == null)
            throw new NullPointerException();
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * @param element element to be added to this set
     * @return true if this set did not already contain the specified element
     * @throws
     * NullPointerException if the specified element is null
     */
    public boolean add(T element) {
        checkElement(element);
        int tmp = find(element);
        if (tmp < mas.size()) {
            if (mas.get(tmp) == element)
                return false;
            else {
                mas.add(tmp, element);
            }
        }
        else {
            mas.add(tmp, element);
        }
        return true;
    }

    /**
     * Returns a string representation of this same as Vector, containing
     * the String representation of each element.
     */
    public String toString() {
        return mas.toString();
    }

    private int find(T element) {
        checkElement(element);
        int l = 0, r = mas.size();
        while (l < r) {
            int m = (l + r) / 2;
            if (mas.get(m).compareTo(element) < 0) {
                l = m + 1;
            }
            else {
                r = m;
            }
        }
        return r;
    }

    /**
     * Constructs a new, empty Navigable set, sorted according to the natural ordering of its elements.
     */
    public MyNavigableSet() {
        mas = new Vector<>();
    }


    /**
     * Constructs a new, empty Navigable set, sorted according to the specified comparator.
     * @param comparator the comparator that will be used to order this set. If null, the natural ordering of the elements will be used.
     */
    public MyNavigableSet(Comparator<T> comparator) {
        mas = new Vector<>();
        this.comparator = comparator;
    }

    /**
     * Constructs a new Navigable set containing the same elements and using the same ordering as the specified sorted set.
     * @param sortedSet sorted set whose elements will comprise the new set.
     * @throws NullPointerException if the specified sorted set is null
     */
    public MyNavigableSet(SortedSet<T> sortedSet) {
        mas = new Vector<>();
        comparator = sortedSet.comparator();
        for (T e: sortedSet) {
            mas.add(e);
        }
    }

    /**
     * Constructs a new Navigable set containing the elements in the specified collection, sorted according to the natural ordering of its elements.
     * @param collection collection whose elements will comprise the new set.
     * @throws NullPointerException if the specified sorted set is null
     */
    public MyNavigableSet(Collection<T> collection) {
        mas = new Vector<>();
        for (T e: collection) {
            mas.add((T) e);
        }
    }


    /**
     * Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
     * @param element the value to match
     * @return the least element greater than or equal to element
     * @throws
     * NullPointerException if the specified element is null
     */
    public T ceiling(T element) {
        checkElement(element);
        int tmp = find(element);
        if (tmp == mas.size()) {
            return null;
        }
        if (mas.get(tmp).compareTo(element) >= 0)
            return mas.get(tmp);
        return null;
    }

    /**
     * Returns an iterator over the elements in this set, in descending order. Equivalent in effect to descendingSet().iterator().
     * @return an iterator over the elements in this set, in descending order
     */
    public Iterator <T> descendingIterator() {
        Iterator<T> iterator = new Iterator<T>() {
            private int cursor = mas.size() - 1;

            @Override
            public boolean hasNext() {
                return cursor >= 0;
            }

            @Override
            public T next() {
                try {
                    return mas.get(cursor--);
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    throw new NoSuchElementException();
                }
            }
        };
        return iterator;
    }

    /**
     * Returns a view of the portion of this set whose elements range from fromElement to toElement. If fromElement and toElement are equal, the returned set is empty unless fromInclusive and toInclusive are both true. The returned set is backed by this set, so changes in the returned set are reflected in this set, and vice-versa. The returned set supports all optional set operations that this set supports.
     * The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
     * @param fromElement low endpoint of the returned set
     * @param fromInclusive true if the low endpoint is to be included in the returned view
     * @param toElement high endpoint of the returned set
     * @param toInclusive true if the high endpoint is to be included in the returned view
     * @return a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive
     * @throws
     * NullPointerException if fromElement or toElement is null
     * IllegalArgumentException if fromElement is greater than toElement; or if this set itself has a restricted range, and fromElement or toElement lies outside the bounds of the range.
     */
    public MyNavigableSet subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        checkElement(fromElement);
        checkElement(toElement);
        int tmp1 = find(fromElement);

        if (tmp1 == mas.size() || fromElement.compareTo(toElement) >= 0) {
            throw new IllegalArgumentException();
        }
        if (mas.get(tmp1).compareTo(fromElement) == 0 && fromInclusive == false) {
            tmp1++;
        }
        int tmp2 = find(toElement);
        if (tmp2 == mas.size()) {
            throw new IllegalArgumentException();
        }
        if (mas.get(tmp2).compareTo(toElement) > 0)
            tmp2--;
        if (mas.get(tmp2).compareTo(toElement) == 0 && toInclusive == false) {
            tmp2--;
        }
        if (tmp2 < mas.size() && tmp1 <= tmp2) {
            MyNavigableSet<T> tmpSet = new MyNavigableSet<>();
            tmpSet.parent = this;
            for (int i = tmp1; i <= tmp2; i++)
                tmpSet.add(mas.get(i));
            return tmpSet;
        }
        else {
            return new MyNavigableSet<T>();
        }
    }

    /**
     * Returns a view of the portion of this set whose elements are less than (or equal to, if inclusive is true) toElement. The returned set is backed by this set, so changes in the returned set are reflected in this set, and vice-versa. The returned set supports all optional set operations that this set supports.
     * The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
     * @param toElement high endpoint of the returned set
     * @param inclusive true if the high endpoint is to be included in the returned view
     * @return a view of the portion of this set whose elements are less than (or equal to, if inclusive is true) toElement
     * @throws
     * NullPointerException if toElement is null
     * IllegalArgumentException if this set itself has a restricted range, and toElement lies outside the bounds of the range
     */
    public MyNavigableSet headSet(T toElement, boolean inclusive) {
        checkElement(toElement);
        int tmp = find(toElement);
        if (tmp == mas.size()) {
            throw new IllegalArgumentException();
        }
        if (mas.get(tmp).compareTo(toElement) > 0)
            tmp--;
        if (mas.get(tmp).compareTo(toElement) == 0 && inclusive == false) {
            tmp--;
        }

        MyNavigableSet<T> navSet = new MyNavigableSet<>();
        navSet.parent = this;
        for (int i = 0; i <= tmp; i++) {
            navSet.add(mas.get(i));
        }
        return navSet;
    }

    /**
     * Returns a view of the portion of this set whose elements are greater than (or equal to, if inclusive is true) fromElement. The returned set is backed by this set, so changes in the returned set are reflected in this set, and vice-versa. The returned set supports all optional set operations that this set supports.
     * The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
     * @param fromElement low endpoint of the returned set
     * @param inclusive true if the low endpoint is to be included in the returned view
     * @return a view of the portion of this set whose elements are greater than or equal to fromElement
     * @throws
     * NullPointerException if fromElement is null
     * IllegalArgumentException if this set itself has a restricted range, and fromElement lies outside the bounds of the range
     */
    public MyNavigableSet tailSet(T fromElement, boolean inclusive) {
        checkElement(fromElement);
        int tmp = find(fromElement);
        if (tmp == mas.size()) {
            throw new IllegalArgumentException();
        }
        if (mas.get(tmp).compareTo(fromElement) == 0 && inclusive == false)
            tmp++;
        MyNavigableSet<T> navSet = new MyNavigableSet<>();
        navSet.parent = this;
        for (int i = tmp;  i < mas.size(); i++) {
            navSet.add(mas.get(i));
        }
        return navSet;
    }

    /**
     * Returns a reverse order view of the elements contained in this set. The descending set is backed by this set, so changes to the set are reflected in the descending set, and vice-versa. If either set is modified while an iteration over either set is in progress (except through the iterator's own remove operation), the results of the iteration are undefined.
     * The returned set has an ordering equivalent to Collections.reverseOrder(comparator()). The expression s.descendingSet().descendingSet() returns a view of s essentially equivalent to s.
     * @return a reverse order view of this set
     */
    public MyNavigableSet<T> descendingSet() {
        List<T> TList = new ArrayList<T>(this);
        Collections.sort(TList, Collections.reverseOrder());
        MyNavigableSet<T>  descMyNavigableSet = new MyNavigableSet<>(TList);
        descMyNavigableSet.parent = this;
        return descMyNavigableSet;
    }



    /**
     * Returns the greatest element in this set strictly less than the given element, or null if there is no such element.
     * @param element the value to match
     * @return the greatest element less than e, or null if there is no such element
     * @throws
     * NullPointerException if the specified element is null
     */
    public T lower(T element) {
        checkElement(element);
        int tmp = find(element);
        if (tmp == 0) {
            if (mas.get(tmp).compareTo(element) < 0) {
                return mas.get(tmp);
            }
            else
                return null;
        }
        return mas.get(tmp - 1);
    }


    /**
     * Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
     * @param element the value to match
     * @return the greatest element less than or equal to e, or null if there is no such element
     * @throws
     * NullPointerException if the specified element is null
     */
    public T floor(T element) {
        checkElement(element);
        int tmp = find(element);
        if (mas.size() == 0)
            return null;
        if (tmp == mas.size())
            return mas.get(tmp - 1);
        else {
            if (mas.get(tmp).compareTo(element) <= 0) {
                return mas.get(tmp);
            }
            if (tmp > 0)
                return mas.get(tmp - 1);
        }
        return null;
    }


    /**
     * Returns the least element in this set strictly greater than the given element, or null if there is no such element.
     * @param element the value to match
     * @return the least element greater than e, or null if there is no such element
     * @throws
     * NullPointerException if the specified element is null
     */
    public T higher(T element) {
        checkElement(element);
        int tmp = find(element);
        if (tmp == mas.size())
            return null;
        if (mas.get(tmp).compareTo(element) > 0) {
            return mas.get(tmp);
        }
        if (tmp + 1 < mas.size())
            return mas.get(tmp + 1);
        return null;
    }


    /**
     * Retrieves and removes the first (lowest) element, or returns null if this set is empty.
     * @return the first element, or null if this set is empty
     */
    public T pollFirst() {
        T tmp = null;
        if (mas.size() > 0) {
            tmp = mas.get(0);
            mas.remove(0);
        }
        if (parent != null) {
            parent.remove(tmp);
        }
        return tmp;
    }

    private void remove(T element) {
        if (element == null)
            return;
        int tmp = find(element);
        mas.remove(tmp);
        if (parent != null)
            parent.remove(tmp);
    }


    /**
     * Retrieves and removes the last (highest) element, or returns null if this set is empty.
     * @return the last element, or null if this set is empty
     */
    public T pollLast() {
        T tmp = null;
        if (mas.size() > 0) {
            tmp = mas.get(mas.size() - 1);
            mas.remove(mas.size() - 1);
        }
        if (parent != null) {
            parent.remove(tmp);
        }
        return tmp;
    }

    /**
     * Returns an iterator over the elements in this set, in ascending order.
     * @return an iterator over the elements in this set, in ascending order
     */
    public Iterator <T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < mas.size();
            }

            @Override
            public T next() {
                try {
                    return mas.get(cursor++);
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    throw new NoSuchElementException();
                }
            }
        };
        return iterator;
    }


    /**
     * Returns the number of elements in this set.
     * @return the number of elements in this set
     */
    public int size() {
        return mas.size();
    }


    /**
     * Returns the comparator used to order the elements in this set, or null if this set uses the natural ordering of its elements.
     * @return the comparator used to order the elements in this set, or null if this set uses the natural ordering of its elements
     */
    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    /**
     * Returns a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive. (If fromElement and toElement are equal, the returned set is empty.) The returned set is backed by this set, so changes in the returned set are reflected in this set, and vice-versa. The returned set supports all optional set operations that this set supports.
     * The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
     * Equivalent to subSet(fromElement, true, toElement, false).
     * @param fromElement low endpoint (inclusive) of the returned set
     * @param toElement high endpoint (exclusive) of the returned set
     * @return a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive
     * @throws NullPointerException if fromElement or toElement is null
     * IllegalArgumentException if fromElement is greater than toElement; or if this set itself has a restricted range, and fromElement or toElement lies outside the bounds of the range
     */
    @Override
    public SortedSet <T> subSet(T fromElement, T toElement) {
        SortedSet <T> sortedSet = this.subSet(fromElement, true, toElement, false);
        return sortedSet;
    }

    /**
     * Returns a view of the portion of this set whose elements are strictly less than toElement. The returned set is backed by this set, so changes in the returned set are reflected in this set, and vice-versa. The returned set supports all optional set operations that this set supports.
     * The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
     *
     * Equivalent to headSet(toElement, false).
     * @param toElement high endpoint (exclusive) of the returned set
     * @return a view of the portion of this set whose elements are strictly less than toElement
     * @throws
     * NullPointerException if toElement is null
     * IllegalArgumentException if this set itself has a restricted range, and toElement lies outside the bounds of the range
     */
    public SortedSet <T> headSet(T toElement) {
        SortedSet<T> sortedSet = this.headSet(toElement, false);
        return sortedSet;
    }

    /**
     * Returns a view of the portion of this set whose elements are greater than or equal to fromElement. The returned set is backed by this set, so changes in the returned set are reflected in this set, and vice-versa. The returned set supports all optional set operations that this set supports.
     * The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
     *
     * Equivalent to tailSet(fromElement, true).
     * @param fromElement low endpoint (inclusive) of the returned set
     * @return a view of the portion of this set whose elements are greater than or equal to fromElement
     * NullPointerException if fromElement is null
     * IllegalArgumentException if this set itself has a restricted range, and fromElement lies outside the bounds of the range
     */
    public SortedSet <T> tailSet(T fromElement) {
        SortedSet<T> sortedSet = this.tailSet(fromElement, true);
        return sortedSet;
    }

    /**
     * Returns the first (lowest) element currently in this set.
     * @return the first (lowest) element currently in this set
     * @throws NoSuchElementException if this set is empty
     */
    @Override
    public T first() {
        if (mas.size() > 0)
            return mas.get(0);
        else
            throw new NoSuchElementException();
    }

    /**
     * Returns the last (highest) element currently in this set.
     * @return the last (highest) element currently in this set
     * @throws NoSuchElementException if this set is empty
     */
    @Override
    public T last() {
        if (mas.size() > 0)
            return mas.get(mas.size() - 1);
        else
            throw new NoSuchElementException();
    }


}
