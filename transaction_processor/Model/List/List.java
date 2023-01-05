/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.List.List.java
 ******/

package Model.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a singly-linked list that holds generic data within each node
 * @param <T> the generic datatype held by each node in the list
 */

public class List<T> implements IList<T>{
    private Node<T> head;

    /**
     * no argument constructor that creates an empty list
     */
    public List(){
        Node<T> emptyNode = new EmptyNode<T>();
        this.head = emptyNode;
    }

    /**
     * One argument constructor that accepts a generic object as the list's head
     */
    public List(T head){
        Node<T> newNode = new ElementNode<T>(head,null);
        this.head = newNode;
    }

    /**
     * One argument constructor that accepts a generic Node as the list's head
     */
    private List(Node<T> head){
        this.head = head;
    }

    /**
     * Returns the number of items currently in the list
     */
    @Override
    public int count(){
        return this.head.count();
    }

    /**
     * Returns the number of items currently in the list that meet the passed in predicate condition
     */
    @Override
    public int count(Predicate<T> predicate){
        return this.head.count(predicate);
    }

    /**
     * Inserts an item at specified index
     * @param item the generic object item to be inserted
     * @param index the place in the list where the item will be inserted
     * @throws IndexOutOfBoundsException for index that's greater than the size of the list
     */
    @Override
    public void insert(T item, int index)throws IndexOutOfBoundsException{
        if(index > this.count() || index < 0){
            throw  new IndexOutOfBoundsException();
        }
        if(index == 0){
            this.addFront(item);
        }

        this.head.insert(item, index);
        return;
    }

    /**
     * Returns the generic data stored in the list at the index passed in
     * @param index position of item to be retrieved
     * @return the generic data at the indexed place-value
     * @throws IndexOutOfBoundsException for index argument below zero or greater than size of the list
     */
    @Override
    public T get(int index)throws IndexOutOfBoundsException{
        if(index > this.count()-1 || index < 0){
            throw  new IndexOutOfBoundsException();
        }
        return this.head.get(index);
    }

    /**
     * Inserts an item at the front of the list
     * @param item the item to be inserted at the front of the list
     */
    @Override
    public void addFront(T item){
        Node newHead = new ElementNode(item,this.head);
        this.head = newHead;
        return;
    }

    /**
     * Inserts an item at the back of the list
     * @param item the item to be inserted at the back of the list
     */
    @Override
    public void addBack(T item){
        if(this.head instanceof EmptyNode<T>){
            Node newHead = new ElementNode(item,this.head);
            this.head = newHead;
            return;
        }
        this.head.insert(item,this.count());
    }

    /**
     * Returns a concatenated string of all the elements in the list
     * @return a concatenated string of all the elements in the list
     */
    @Override
    public String toString(){
        return this.head.toString();
    }

    /**
     * Maps the list of objects into a list of different objects, based on the function passed in
     * @param function a function that transforms the objects from one type to another type
     * @return a new list that's been transformed
     * @param <R> the type of object the new list will contain
     */
    @Override
    public <R> IList<R> map(Function<T,R> function){
        return new List<R>(this.head.map(function));
    }

    /**
     * Filters the list based on the predicate test object passed in
     * @param predicate a Predicate object that implements a .test() method
     * @return a new IList filtered by the predicate test passed in
     */
    @Override
    public IList<T> filter(Predicate<T> predicate){
        return new List<T>(this.head.filter(predicate));
    }

    /**
     * Summarizes the list of objects into a single value
     * @param biFunction a function to fold the objects into one another
     * @return a single value of type T
     */
    @Override
    public T fold(T initialT, BiFunction<T,T,T> biFunction){
        return this.head.fold(initialT, biFunction);
    }

    /**
     * Adds another list to the front of this list
     * @param otherList another list of the holding the same generic data type
     */
    @Override
    public void addFront(IList<T> otherList){
        for(int i = otherList.count()-1; i>=0;i--){
            this.addFront(otherList.get(i));
        }
    }

    /**
     * Adds another list to the end of this list
     * @param otherList another list of the holding the same generic data type
     */
    @Override
    public void addBack(IList<T> otherList){
        for(int i = 0;i<otherList.count(); i++){
            this.addBack(otherList.get(i));
        }
    }

    /**
     * Removes generic items from the list that equal the item passed in
     * @param item the items in the list to be removed
     */
    public void remove(T item){
        head = head.remove(item);
    }

    /**
     * Removes the item at the index specified
     * @param index the position in the list to be removed
     */
    @Override
    public void removeIndex(int index) throws IndexOutOfBoundsException{
        if(index > this.count()-1 || index < 0){
            throw new IndexOutOfBoundsException("index invalid");
        }
        head = head.removeIndex(index);
    }

    /**
     * Sorts the list according to the comparator
     * @param comparator an instance of a comparator to determine how the nodes will be sorted
     */
    @Override
    public void sort(Comparator<T> comparator){
        this.head = head.sort(comparator);
    }


    /**
     *
     * @return an iterator of a generic type
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator(this.head);
    }

    /* **************************************
        LIST ITERATOR
     ***************************************/
    /**
     * Class that allows the list to be iterated through
     * @param <T> generic data type that the nodes hold
     */
    private class ListIterator<T> implements Iterator<T>{
        private Node<T> current;

        /**
         * Single argument constructor to set the start of the iterator
         * @param head
         */
        public ListIterator(Node<T> head){
            current = head;
        }

        /**
         * Calls the has nextNode on each node in the list
         * @return true if a node has another node and false otherwise
         */
        @Override
        public boolean hasNext(){return current.hasNextNode();}

        /**
         * Returns the data of this node and iterates through the list
         * @return the data at each node
         */
        @Override
        public T next(){
            T data = current.getData();
            current = current.getNextNode();
            return data;
        }
    }
}
