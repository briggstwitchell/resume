/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.List.EmptyNode.java
 ******/
package Model.List;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class EmptyNode<T> implements Node<T>{

    /**
     * Returns a 0 count for this empty node
     * @return a 0 count for this empty node
     */
    public int count(){
        return 0;
    }

    /**
     * Returns a 0 count for this empty node
     * @param predicate a predicate condition
     * @return a 0 count for this empty node
     */
    public int count(Predicate<T> predicate){
        return 0;
    }

    public Node clone(){
        return new EmptyNode();
    }

    /**
     * Creates a new node for the generic item object passed in if the index equals 0, recursively calls on this.next otherwise
     * @param item a generic item to be stored in an element node
     * @param index the current index to iterate through the linked list
     */
    public void insert(T item, int index){
        if(index != 0){
            return;
        }
        Node newTail = new ElementNode(item,this);
        return;
    }

    /**
     * Maps this node's data to new data based on the function passed in
     * @param function a function to transform the node's data from one type to another
     * @return
     * @param <R>
     */
    public <R> Node<R> map(Function<T,R> function){
        return new EmptyNode<R>();
    }

    /**
     * Returns new empty node for end of list
     * @param predicate a conditional predicate test
     * @return new empty node for the end of the filtered list
     */
   public Node<T> filter(Predicate<T> predicate){
        return new EmptyNode();
    }

    /**
     * Summarizes the contents of the node into a single generic value T
     * @param biFunction a function that implements the Bifunction transform method
     * @return a generic value T
     */
    public T fold(T initialValue, BiFunction<T,T,T> biFunction){
        return initialValue;
    }

    /**
     * Returns null because the node holds no data
     * @return null
     */
    public T getData(){
        return null;
    }

    /**
     * Returns null for the next node
     * @return null for the next node
     */
    public Node getNextNode(){
        return null;
    }

    /**
     * Returns false because an empty node has no next node
     * @return false
     */
    public boolean hasNextNode(){
        return false;
    }

    /**
     * Returns the items stored at a node when the index passed in equals 0
     * @return the item stored at this node
     */
    public T get(int index){
        if(index > 0){
            throw new IndexOutOfBoundsException();
        }
        return null;
    }

    /**
     * Base case for removing of a node when an generic item is passed in
     * @param item a generic type for the item to be removed
     * @return this EmptyNode
     */
    public Node<T> remove(T item){
        return this;
    }

    /**
     * Base case for removing a node via index -- returns this empty node
     * @param index the place within the list for an item to be removed
     * @return this empty node (shouldn't be reached)
     */
    public Node<T> removeIndex(int index) {
        return this;
    }

    /**
     * Inserts an element node before this node
     * @param item the generic item to be inserted into the list
     * @param comparator a comparator object that compares an object with the next in the list
     * @return a new Element node containing the item and this emptyNode as its next node
     */
    public Node<T> insert(T item, Comparator<T> comparator){
        return new ElementNode<T>(item,this);
    }

    /**
     * Returns a new EmptyNode represented a sorted empty list
     * @param comparator an instance of a comparator to compare one node's data with the data of the next node
     * @return a new EmptyNode represented a sorted empty list
     */
    public Node<T> sort(Comparator<T> comparator){
        return new EmptyNode();
    }

    /**
     * Returns an empty string because an empty node has no data
     * @return an empty string
     */
    public String toString(){
        return "";
    }

}
