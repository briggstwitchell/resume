/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.List.List.java
 ******/

package Model.List;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Node<T> {

    /**
     * Counts the current node and all nodes after it, if it's an element node. If empty then returns zero
     * @return the count of current node and all nodes after. Returns 0 if at end of list.
     */
    int count();

    /**
     * Counts the current node and all nodes after it, if it's an element node. If empty then returns zero
     *      based on a predicate condition passed in
     * @return the count of current node and all nodes after. Returns 0 if at end of list.
     */
    int count(Predicate<T> predicate);

    /**
     * Creates a copy of the current node -- if an element node recursively calls on next node
     * @return a new node that is the copy of this node
     */
    Node clone();

    /**
     * Creates a new node for the generic item object passed in if the index equals 0, recursively calls on this.next otherwise
     * @param item a generic item to be stored in an element node
     * @param index the current index to iterate through the linked list
     */
    void insert(T item, int index);

    /**
     * Maps this node's data to new data based on the function passed in
     * @param function a function to transform the node's data from one type to another
     * @return
     * @param <R>
     */
    <R> Node<R> map(Function<T,R> function);

    /**
     * Returns the current node and all subsequent that meet a predicate condition -- returns new empty node for end of list
     * @param predicate a conditional predicate test to determine whether this node will be added to the filterd list
     * @return this node and all subsequent that meet the predicate condition
     */
    Node<T> filter(Predicate<T> predicate);

    /**
     * Summarizes the contents of the node into a single generic value T
     * @param initialValue an intitial value to be passed into the bifuction
     * @param biFunction a function that implements the Bifunction transform method
     * @return a generic value T
     */
    T fold(T initialValue, BiFunction<T,T,T> biFunction);

    /**
     * Returns the generic data that the node holds
     * @return the generic data the node holds
     */
    T getData();

    /**
     * Returns a node's next node
     * @return a node that is the next of this node
     */
    Node getNextNode();

    /**
     * Returns true if there's a next node in the list and false otherwise
     * @return true if there's a next node in the list and false otherwise
     */
    boolean hasNextNode();

    /**
     * Returns the items stored at a node when the index passed in equals 0
     * @return the item stored at this node
     */
    T get(int index);

    /**
     * Removes this node when it matches he item passed in
     * @param item a generic type for the item to be removed
     * @return return a node and the rest of the list with the item removed
     */
    Node<T> remove(T item);

    /**
     * Removes an item if it matches the index passed in
     * @param index the place within the list for an item to be removed
     * @return a node and remainder of the list with the index removed
     */
    Node<T> removeIndex(int index);

    /**
     * Helper method for sort to compare generic items
     * @param item the generic item to be inserted into the list
     * @param comparator a comparator object that compares an object with the next in the list
     * @return a node that has inserted the element in the list
     */
    Node<T> insert(T item, Comparator<T> comparator);

    /**
     * Sorts this node and all remaining according to the comparator functional object passed in
     * @param comparator an instance of a comparator to compare one node's data with the data of the next node
     * @return returns a node and all nodes after sorted according to the comparator
     */
    Node<T> sort(Comparator<T> comparator);

}
