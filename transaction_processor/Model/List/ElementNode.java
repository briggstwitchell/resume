/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.List.ElementNode.java
 ******/
package Model.List;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ElementNode<T> implements Node<T>{
    private T data;
    private Node next;

    /**
     * Single argument constructor to set the nodes data value to generic type passed in
     * @param data generic data the the node holds
     * @param next the next node in the list
     */
    public ElementNode(T data, Node next){
        this.data = data;
        this.next = next;
    }

    /**
     * Counts the current node and all nodes after it
     * @return the count of this node and all nodes after this
     */
    public int count(){
        return 1+this.next.count();
    }

    /**
     * Counts the current node and all nodes after it, if it's an element node. If empty then returns zero
     *      based on a predicate condition passed in
     * @return the count of current node and all nodes after if predicate condition is met
     */
    public int count(Predicate<T> predicate){
        if(predicate.test(this.data)){
            return 1 + this.next.count(predicate);
        }
        return this.next.count(predicate);
    }

    /**
     * Returns the generic data that the node holds
     * @return the generic data the node holds
     */
    public T getData(){
        return this.data;
    }

    /**
     * Returns a node's next node
     * @return a node that is the next of this node
     */
    public Node getNextNode(){
        return this.next;
    }

    /**
     * Returns true if there's a next node in the list and false otherwise
     * @return true if there's a next node in the list and false otherwise
     */
    public boolean hasNextNode(){
        if(this.next != null) {
            return true;
        }
        return false;
    }

    public Node<T> clone(){
        return new ElementNode<T>(this.data,this.next.clone());
    }

    /**
     * Creates a new node for the generic item object passed in if the index equals 0, recursively calls on this.next otherwise
     * @param item a generic item to be stored in an element node
     * @param index the current index to iterate through the linked list
     */
    public void insert(T item, int index){
        if(index - 1 == 0){
            Node newElementNode = new ElementNode(item,this.next);
            this.next = newElementNode;
            return;
        }
        this.next.insert(item,index-1);
    }

    /**
     * Helper method for sort to compare generic items
     * @param item the generic item to be inserted into the list
     * @param comparator a comparator object that compares an object with the next in the list
     * @return a node that has inserted the element in the list
     */
    public Node<T> insert(T item, Comparator<T> comparator){
        //if item is less than node data, put before
        if(comparator.compare(this.data,item) < 0){

            return new ElementNode(this.data, this.next.insert(item,comparator));
        }
            return new ElementNode<T>(item,this);
    }

    /**
     * Helper method for sort to compare generic items
     * @param comparator an instance of a comparator to compare one node's data with the data of the next node
     * @return the head of a sorted list based on the defined comparator passed in
     */
    public Node<T> sort(Comparator<T> comparator){
        return this.next.sort(comparator).insert(this.data,comparator);
    }

    /**
     * Maps this node's data to new data based on the function passed in
     * @param function a function to transform the node's data from one type to another
     * @return
     * @param <R>
     */
    public <R> Node<R> map(Function<T,R> function){
        return new ElementNode<R>(function.apply(this.data),this.next.map(function));
    }

    /**
     * Returns the current node and all subsequent that meet a predicate condition -- returns new empty node for end of list
     * @param predicate a conditional predicate test to determine whether this node will be added to the filterd list
     * @return this node and all subsequent that meet the predicate condition
     */
    public Node<T> filter(Predicate<T> predicate){
        if(predicate.test(this.data)){
            return new ElementNode(this.data,this.next.filter(predicate));
        }
        return this.next.filter(predicate);
    }

    /**
     * Summarizes the contents of the node into a single generic value T
     * @param initialValue an intitial value to be passed into the bifuction
     * @param biFunction a function that implements the Bifunction transform method
     * @return a generic value T
     */
    public T fold(T initialValue, BiFunction<T,T,T> biFunction){
        T reducedT = biFunction.apply(initialValue,this.data);
        return (T) this.next.fold(reducedT,biFunction);
    }

    /**
     * Returns the items stored at a node when the index passed in equals 0
     * @return the item stored at this node
     */
    public T get(int index){
        if(index == 0){
            return this.getData();
        }
        return (T) this.next.get(index - 1);
    }

    public Node<T> remove(T item){
        if(item.equals(this.data)){
            return this.next;
        }
        this.next = this.next.remove(item);
        return this;
    }

    /**
     * Removes an item if it matches the index passed in
     * @param index the place within the list for an item to be removed
     * @return a node and remainder of the list with the index removed
     */
    public Node<T> removeIndex(int index){
        if(index == 0){
            return this.next;
        }
        this.next = this.next.removeIndex(index-1);
        return this;
    }

    /**
     * Returns the string that results from the node's toString() method
     * @return a string for the node's data
     */
    public String toString(){
        if(this.next instanceof EmptyNode){
            return this.getData().toString();
        }
        return this.getData().toString()+", "+this.next.toString();
    }

}
