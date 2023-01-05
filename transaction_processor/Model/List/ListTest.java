package Model.List;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {
    List<Integer> myList;

    @BeforeEach
    public void setUp() {
        myList = new List<Integer>();

    }

    @org.junit.jupiter.api.Test
    void count() {
        //System.out.println(myList.count());
        myList.addFront(1);
        myList.addFront(2);
        myList.addFront(3);
        myList.addFront(4);
        assertEquals(4, myList.count());
    }

    @org.junit.jupiter.api.Test
    void testCount() {
        myList.addFront(1);
        myList.addFront(2);
        myList.addFront(3);
        myList.addFront(4);
        assertEquals(2, myList.count(n -> n > 2));
    }


    @org.junit.jupiter.api.Test
    void addFront() {
        myList.addFront(1);
        myList.addFront(2);
        assertEquals(2, myList.get(0));
        assertEquals(1, myList.get(1));
    }

    @org.junit.jupiter.api.Test
    void addBack() {
        myList.addBack(1);
        myList.addBack(2);
        myList.addBack(3);
        assertEquals(1, myList.get(0));
        assertEquals(2, myList.get(1));
        assertEquals(3, myList.get(2));
        System.out.println(myList.toString());
    }

    @org.junit.jupiter.api.Test
    void insert() {
        myList.addFront(3);
        myList.addFront(1);
        myList.insert(2, 1);
        assertEquals(2, myList.get(1));
        assertEquals(3, myList.get(2));
    }

    @org.junit.jupiter.api.Test
    void map() {
        myList.addBack(1);
        myList.addBack(2);
        myList.addBack(3);
        IList<Integer> myNewList = myList.map(n -> 0);
        int sum = 0;
        for (int n : myNewList) {
            sum += n;
        }
        assertEquals(0, sum);
    }

    @org.junit.jupiter.api.Test
    void filter() {
        myList.addBack(1);
        myList.addBack(2);
        myList.addBack(3);
        myList.addBack(4);
        myList.addBack(5);
        myList.addBack(6);

        IList<Integer> myNewList = myList.filter(e -> e > 3);
        int sum = 0;
        for (int n : myNewList) {
            System.out.println(n);
            sum += n;
        }
        assertEquals(15, sum);
    }

    @org.junit.jupiter.api.Test
    void fold() {
        myList.addBack(1);
        myList.addBack(2);
        myList.addBack(3);
        myList.addBack(4);
        myList.addBack(5);
        myList.addBack(6);

            assertEquals(21, myList.fold(0, (n1, n2) -> n1 + n2));
        }

    @org.junit.jupiter.api.Test
    void iterator() {
        myList.addBack(1);
        myList.addBack(2);
        myList.addBack(3);
        myList.addBack(4);
        myList.addBack(5);
        myList.addBack(6);

        Integer sum = myList.filter(n -> n>3).map(e->5).fold(0,(n1,n2)->n1+n2);
        assertEquals(15,sum);
    }

    @org.junit.jupiter.api.Test
    void addListFront() {

        myList.addBack(4);
        myList.addBack(5);
        myList.addBack(6);

        IList<Integer> myList2 = new List<Integer>();
        myList2.addBack(1);
        myList2.addBack(2);
        myList2.addBack(3);

        myList.addFront(myList2);

        for(Integer i: myList){
            System.out.println(i);
        }

    }
    @org.junit.jupiter.api.Test
    void addListBack() {

        myList.addBack(4);
        myList.addBack(5);
        myList.addBack(6);

        IList<Integer> myList2 = new List<Integer>();
        myList2.addBack(1);
        myList2.addBack(2);
        myList2.addBack(3);

        myList2.addBack(myList);

        for(Integer i: myList2){
            System.out.println(i);
        }

    }

    @org.junit.jupiter.api.Test
    void remove() {
        myList.addBack(1);
        myList.addBack(2);
        myList.addBack(3);
        myList.addBack(4);
        myList.addBack(5);
        myList.addBack(6);

        myList.remove(3);

        for(Integer i: myList){
            System.out.println(i);
        }
    }
    @org.junit.jupiter.api.Test
    void removeIndex() {
        myList.addBack(1);
        myList.addBack(2);
        myList.addBack(3);
        myList.addBack(4);
        myList.addBack(5);
        myList.addBack(6);

        myList.removeIndex(5);

        for(Integer i: myList){
            System.out.println(i);
        }
    }

    @org.junit.jupiter.api.Test
    void testInsert(){

        //TODO SORT ISN'T WORKING
        myList.addBack(3);
        myList.addBack(2);
        myList.addBack(1);
        myList.addBack(4);
        myList.addBack(-1);

        //attempting to sort smallest to largest
        myList.sort((e1,e2)-> e1-e2);

        for(Integer i: myList){
            System.out.println(i);
        }

    }


}
