// Briggs Twitchell
// 10/6/2022
// Homework Assignment 3
//
// Compile this assignment with: gcc -g -Wall main.c -o main
// Use this file to implement testing for your
// doubly linked list implementation
//
// Include parts of the C Standard Library
// These have been written by some other really
// smart engineers.
#include <stdio.h>  // For IO operations
#include <stdlib.h> // for malloc/free

// Our library that we have written.
// Also, by a really smart engineer!
#include "my_dll.h"
// Note that we are locating this file
// within the same directory, so we use quotations
// and provide the path to this file which is within
// our current directory.


void test_nodes(){
	node_t* myNode = new_node(1,NULL,NULL);
	
	printf("Node data = %d\n",myNode->data);	
	
	free_node(myNode);

	
	return;
}


void test_create_dll(){
	
	int expected = 0;
	
	dll_t* aDLL = create_dll();
	
	if(aDLL->count != expected){
		printf("TEST FAIL: aDLL->count not 0\n");
		return;
	}

	expected = 1;	
	if(dll_empty(aDLL) != expected){	
		printf("TEST FAIL: dll_empty(aDLL) not 0\n");
		return;
	}
	
	else{printf("Create DLL tests passed\n");}
	free_dll(aDLL);
	return;
	
}

void test_push_front(){
	dll_t* aDLL = create_dll();

	//push to empty dll
	dll_push_front(aDLL, 1);
	int expected = 1;

	if(aDLL->head->data != expected){
		printf("Pushing to list size 0 failed\n");
		printf("aDLL->head->data = %d\n",aDLL->head->data);
	}

	else{printf("push empty passed\n");}

	//push to list size 1
	dll_push_front(aDLL, 2);
	expected = 2;

	if(aDLL->head->data != expected){
		printf("Pushing to list size 1 failed\n");
		printf("aDLL->head->data = %d\n",aDLL->head->data);
	}

	else{printf("push to size 1 passed\n");
		printf("aDLL->count = %d\n",aDLL->count);}
	
	//push to list size 2
	dll_push_front(aDLL, 3);
	expected = 3;
	
	if(aDLL->head->data != expected){
		printf("Pushing to list size 2 failed\n");
		printf("aDLL->head->data = %d\n",aDLL->head->data);
	}

	else{printf("push to size 2 passed\n");
		printf("aDLL->count = %d\n",aDLL->count);}

	free_dll(aDLL);
	return;
}

void test_push_back(){
	dll_t* aDLL = create_dll();

	//push to empty dll
	dll_push_back(aDLL, 1);
	int expected = 1;

	if(aDLL->tail->data != expected){
		printf("Pushing to list size 0 failed\n");
		printf("aDLL->tail->data = %d\n",aDLL->tail->data);
	}

	else{printf("push empty passed\n");}

	//push to list size 1
	dll_push_back(aDLL, 2);
	expected = 2;

	if(aDLL->tail->data != expected){
		printf("Pushing to list size 1 failed\n");
		printf("aDLL->tail->data = %d\n",aDLL->tail->data);
	}

	else{printf("push to size 1 passed\n");
		printf("aDLL->count = %d\n",aDLL->count);}
	
	//push to list size 2
	dll_push_back(aDLL, 3);
	expected = 3;
	
	if(aDLL->tail->data != expected){
		printf("Pushing to list size 2 failed\n");
		printf("aDLL->tail->data = %d\n",aDLL->tail->data);
	}

	else{printf("push to size 2 passed\n");
		printf("aDLL->count = %d\n",aDLL->count);}
	free_dll(aDLL);

	return;
}

void test_pop_front(){
	dll_t* myDLL = create_dll();
	dll_push_back(myDLL, 2);
	dll_push_back(myDLL,3);
	dll_push_front(myDLL, 1);

	int actual;
	actual = dll_pop_front(myDLL);
	
	int expected = 1;

	if(expected != actual){
		printf("pop front test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop front test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}

	//TODO GUESSING IT CAN'T LOCATE myDLL->head; (line 201 in header file)
	actual = dll_pop_front(myDLL);
	
	
	expected = 2;

	if(expected != actual){
		printf("pop front test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop front test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}

	actual = dll_pop_front(myDLL);
	
	expected = 3;

	if(expected != actual){
		printf("pop front test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop front test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	dll_pop_front(myDLL);

	free_dll(myDLL);
	return;
}


void test_pop_back(){

	dll_t* myDLL = create_dll();
	dll_push_back(myDLL, 2);
	dll_push_back(myDLL,3);
	dll_push_front(myDLL, 1);

	int actual;
	actual = dll_pop_back(myDLL);
	
	int expected = 3;

	if(expected != actual){
		printf("pop back test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop back test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}

	actual = dll_pop_back(myDLL);
	
	
	expected = 2;

	if(expected != actual){
		printf("pop back test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop back test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	
	printf("getting to line 222\n");

	actual = dll_pop_back(myDLL);
	printf("getting to line 225\n");
	
	expected = 1;

	if(expected != actual){
		printf("pop back test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop back test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	dll_pop_back(myDLL);

	free_dll(myDLL);
	return;
}


void test_pop_back_and_front(){

	dll_t* myDLL = create_dll();
	dll_push_front(myDLL, 2);
	dll_push_back(myDLL,3);
	dll_push_front(myDLL, 1);
	dll_push_back(myDLL, 4);
	
	int actual;
	actual = dll_pop_back(myDLL);
	
	int expected = 4;

	if(expected != actual){
		printf("pop back test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop back test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}

	actual = dll_pop_front(myDLL);
	
	
	expected = 1;

	if(expected != actual){
		printf("pop front test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop front test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	
	actual = dll_pop_back(myDLL);
	
	expected = 3;

	if(expected != actual){
		printf("pop back test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop back test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	
	actual = dll_pop_front(myDLL);
	
	expected = 2;

	if(expected != actual){
		printf("pop front test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}	
	
	else{
		printf("pop front test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}

	dll_pop_front(myDLL);

	free_dll(myDLL);
	return;
}


void test_insert(){
	
	dll_t* myDLL = create_dll();
	dll_push_front(myDLL, 2);
	dll_push_back(myDLL,3);
	dll_push_front(myDLL, 1);
	dll_push_back(myDLL, 5);

	dll_insert(myDLL,3,4);

	int i;
	int data;
	int length = myDLL->count;
	for(i=0; i<length;i++){
		data = dll_pop_front(myDLL);
		printf("Popped %d from front of list\n",data);
	}	
	
	dll_pop_front(myDLL);

	return;
}

void test_insert_pop_back(){
	
	dll_t* myDLL = create_dll();
	dll_push_front(myDLL, 2);
	dll_push_back(myDLL,3);
	dll_push_front(myDLL, 1);
	dll_push_back(myDLL, 5);

	dll_insert(myDLL,3,4);

	int i;
	int data;
	int length = myDLL->count;
	for(i=0; i<length;i++){
		data = dll_pop_back(myDLL);
		printf("Popped %d from back of list\n",data);
	}	
	
	dll_pop_front(myDLL);
	free_dll(myDLL);

	return;
}


void test_insert_1(){
	
	dll_t* myDLL = create_dll();
	dll_push_front(myDLL, 1);
	dll_insert(myDLL,1,2);

	int i;
	int data;
	int length = myDLL->count;
	for(i=0; i<length;i++){
		data = dll_pop_back(myDLL);
		printf("Popped %d from back of list\n",data);
	}	
	
	dll_pop_front(myDLL);

	return;
}

void test_insert_2(){
	
	dll_t* myDLL = create_dll();
	dll_push_front(myDLL, 2);
	dll_insert(myDLL,0,1);

	int i;
	int data;
	int length = myDLL->count;
	for(i=0; i<length;i++){
		data = dll_pop_front(myDLL);
		printf("Popped %d from front of list\n",data);
	}	
	
	dll_pop_front(myDLL);
	free_dll(myDLL);

	return;
}


void test_get(){

	dll_t* myDLL = create_dll();
	dll_push_front(myDLL,3);
	dll_push_front(myDLL,2);
	dll_push_front(myDLL,1);
	
	int expected = 1;
	int actual = dll_get(myDLL,0);
	
	if(expected == actual){
		printf("dll_get test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	else{
		printf("dll_get test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}
	
	expected = 2;
	actual = dll_get(myDLL,1);
	
	if(expected == actual){
		printf("dll_get test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	else{
		printf("dll_get test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}
	
	expected = 3;
	actual = dll_get(myDLL,2);
	
	if(expected == actual){
		printf("dll_get test PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	else{
		printf("dll_get test FAILED -- expected = %d, actual = %d\n",expected,actual);
	}

	free_dll(myDLL);
}

void test_remove(){

	dll_t* myDLL = create_dll();
	dll_push_front(myDLL,3);
	dll_push_front(myDLL,2);
	dll_push_front(myDLL,1);
	dll_push_back(myDLL,4);

	int expected = 3;
	int actual = dll_remove(myDLL,2);			

	int expected_count = 3;
	int actual_count = myDLL->count;
	
	if(expected == actual && expected_count == actual_count){
		printf("dll_remove test PASSED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	else{
		printf("dll_remove test FAILED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	

	expected = 4;
	actual = dll_remove(myDLL,2);			

	expected_count = 2;
	actual_count = myDLL->count;
	
	if(expected == actual && expected_count == actual_count){
		printf("dll_remove test PASSED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	else{
		printf("dll_remove test FAILED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	
	expected = 2;
	actual = dll_remove(myDLL,1);			

	expected_count = 1;
	actual_count = myDLL->count;
	
	if(expected == actual && expected_count == actual_count){
		printf("dll_remove test PASSED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	else{
		printf("dll_remove test FAILED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	
	expected = 1;
	actual = dll_remove(myDLL,0);			

	expected_count = 0;
	actual_count = myDLL->count;
	
	if(expected == actual && expected_count == actual_count){
		printf("dll_remove test PASSED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	else{
		printf("dll_remove test FAILED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	
	dll_push_front(myDLL,3);
	dll_push_front(myDLL,2);
	dll_push_front(myDLL,1);
	dll_push_back(myDLL,4);
	
	expected = -1;
	actual = dll_remove(myDLL,5);
	
	expected_count = 4;
	actual_count = myDLL->count;
	
	if(expected == actual && expected_count == actual_count){
		printf("dll_remove test PASSED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}
	else{
		printf("dll_remove test FAILED -- expected = %d, actual = %d, expected_count = %d, actual_count = %d\n",expected,actual,expected_count,actual_count);
	}

	free_dll(myDLL);
	return;
}

void test_size(){
	
	dll_t* myDLL = create_dll();
	dll_push_front(myDLL,3);
	dll_push_front(myDLL,2);
	dll_push_front(myDLL,1);
	dll_push_back(myDLL,4);
	
	int expected = 4;
	int actual = dll_size(myDLL);
	
	if(expected == actual){
		printf("test_size PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	else{
		printf("test_size FAILED -- expected = %d, actual = %d\n",expected,actual);
	}
	
	dll_pop_front(myDLL);
	dll_pop_back(myDLL);

	
	expected = 2;
	actual = dll_size(myDLL);
	
	if(expected == actual){
		printf("test_size PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	else{
		printf("test_size FAILED -- expected = %d, actual = %d\n",expected,actual);
	}

	dll_pop_front(myDLL);
	dll_pop_back(myDLL);

	
	expected = 0;
	actual = dll_size(myDLL);
	
	if(expected == actual){
		printf("test_size PASSED -- expected = %d, actual = %d\n",expected,actual);
	}
	else{
		printf("test_size FAILED -- expected = %d, actual = %d\n",expected,actual);
	}
	
	free_dll(myDLL);
}

void test_free(){
	
	dll_t* myDLL = create_dll();
	dll_push_front(myDLL,3);
	dll_push_front(myDLL,2);
	dll_push_front(myDLL,1);
	dll_push_back(myDLL,4);

	free_dll(myDLL);
	
}

// ====================================================
// ================== Program Entry ===================
// ====================================================
int main(){
    //dll_t * dll = create_dll();
	//test_nodes();//PASSED 
	//test_create_dll();//PASSED
	//test_push_front();//PASSED
	//test_push_back();//PASSED
	//test_pop_front();//PASSED
	//test_pop_back();//PASSED
	//test_pop_back_and_front();//PASSED	
	//test_insert();//PASSED
	//test_insert_pop_back();//PASSED
	//test_insert_1();//PASSED	
	//test_insert_2();//PASSED
	//test_get();//PASSED	
	//test_remove();//PASSED
	//test_size();//PASSED	
	//test_free();//PASSED
	  
    return 0;
}



