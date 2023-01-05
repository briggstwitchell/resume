/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.List.IList.java
 ******/
package Model.List;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IList<T> extends Iterable<T>{

    /**
     * Returns the number of items currently in the list
     */
    int count();

    /**
     * Returns the number of items currently in the list that meet the passed in predicate condition
     */
    int count(Predicate<T> predicate);

    /**
     * Inserts an item at specified index
     * @param item the generic object item to be inserted
     * @param index the place in the list where the item will be inserted
     * @throws IndexOutOfBoundsException for index that's greater than the size of the list
     */
    void insert(T item, int index)throws IndexOutOfBoundsException;

    /**
     * Inserts an item at the front of the list
     * @param item the item to be inserted at the front of the list
     */
    void addFront(T item);

    /**
     * Inserts an item at the back of the list
     * @param item the item to be inserted at the back of the list
     */
    void addBack(T item);

    /**
     * Adds another list to the front of this list
     * @param otherList another list of the holding the same generic data type
     */
    void addFront(IList<T> otherList);

    /**
     * Adds another list to the end of this list
     * @param otherList another list of the holding the same generic data type
     */
    void addBack(IList<T> otherList);

    /**
     * Maps the list of objects into a list of different objects, based on the function passed in
     * @param function a function that transforms the objects from one type to another type
     * @return a new list that's been transformed
     * @param <R> the type of object the new list will contain
     */
    <R> IList<R> map(Function<T,R> function);

    /**
     * Filters the list based on the predicate test object passed in
     * @param predicate a Predicate object that implements a .test() method
     * @return a new IList filtered by the predicate test passed in
     */
    public IList<T> filter(Predicate<T> predicate);


    /**
     * Summarizes the list of objects into a single value
     * @param initialT initial generic type value
     * @param biFunction a function to fold the objects into one another
     * @return a single value of type T
     */
    T fold(T initialT, BiFunction<T,T,T> biFunction);

    /**
     * Returns the generic data stored in the list at the index passed in
     * @param index position of item to be retrieved
     * @return the generic data at the indexed place-value
     * @throws IndexOutOfBoundsException for index argument below zero or greater than size of the list
     */
    T get(int index)throws IndexOutOfBoundsException;

    /**
     * Removes generic items from the list that equal the item passed in
     * @param item the items in the list to be removed
     */
    void remove(T item);

    /**
     * Removes the item at the index specified
     * @param index the position in the list to be removed
     */
    void removeIndex(int index) throws IndexOutOfBoundsException;

    /**
     * Sorts a list based on the comparator object passed in
     */
    void sort(Comparator<T> comparator);


    /**
     * Calls the toString() method on each node within list
     * @return a string concatenating the strings returned by the data's toString() method, separated by new line characters
     */
    String toString();

}
