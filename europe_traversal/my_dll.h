// =================== Support Code =================
// Doubly Linked List ( DLL ).
//
//
//
// - Implement each of the functions to create a working DLL.
// - Do not change any of the function declarations
//   - (i.e. dll_t* create_dll() should not have additional arguments)
// - You should not have any 'printf' statements in your DLL functions. 
//   - (You may consider using these printf statements to debug,
//     but they should be removed from your final version)
//   - (You may write helper functions to help you debug your code such as print_list etc)
// ==================================================
#ifndef MYDLL_H
#define MYDLL_H

#include <stdlib.h>
// Create a node data structure to store data within
// our DLL. In our case, we will stores 'integers'
typedef struct node {
    void* data;
    struct node* next;
    struct node* previous;
} node_t;

// Create a DLL data structure
// Our DLL holds a pointer to the first node in our DLL called head,
// and a pointer to the last node in our DLL called tail.
typedef struct DLL {
    int count;              // count keeps track of how many items are in the DLL.
    node_t* head;           // head points to the first node in our DLL.
    node_t * tail;          //tail points to the last node in our DLL.
} dll_t;


//HELPER: function to create a new node
node_t* new_node(void* data, node_t* next, node_t* previous){
	//TODO changing sizeof(node_t*) to sizeof(node_t);
	node_t* n = (node_t*)malloc(sizeof(node_t));
	
	if(n == NULL){
		return NULL;
	}

	n->data = data;
	n->next = next;
	n->previous = previous;
	return n;
}

//HELPER: function to free a node
void free_node(node_t* n){
	if(n == NULL){
		exit(0);
	}
	
	//TODO NEED TO MAKE SURE DATA IS FREED SOMEWHERE
	//free(n->data);
	free(n);
}

// Creates a DLL
// Returns a pointer to a newly created DLL.
// The DLL should be initialized with data on the heap.
// (Think about what the means in terms of memory allocation)
// The DLLs fields should also be initialized to default values.
// Returns NULL if we could not allocate memory.
dll_t* create_dll(){
	
	//TODO changing sizeof(dll_t*) to sizeof(dll_t);
	dll_t* myDLL = (dll_t*)malloc(sizeof(dll_t));	
        
	if(myDLL == NULL){
		return NULL;
	}

	myDLL->count = 0;
	myDLL->head = NULL;
	myDLL->tail = NULL;

	return myDLL;
}

// DLL Empty
// Check if the DLL is empty
// Exits if the DLL is NULL.
// Returns 1 if true (The DLL is completely empty)
// Returns 0 if false (the DLL has at least one element enqueued)
int dll_empty(dll_t* l){
	if (l == NULL){
                exit(1);
        }

        if (l->count == 0){
                return 1;
        }

        else{return 0;}       
}

// push a new item to the front of the DLL ( before the first node in the list).
// Exits if DLL is NULL.
// Returns 1 on success
// Returns 0 on failure ( i.e. we couldn't allocate memory for the new node)
// (i.e. the memory allocation for a new node failed).
int dll_push_front(dll_t* l, void* item){

	//exits if dll passed in is null
	if (l == NULL){
                exit(1);
        }

        node_t* n;

	//if empty adds to tail, which is also the head
        if(dll_empty(l) == 1){
        	n = new_node(item,NULL,NULL);
        	l->tail = n;
        }

	//if there's a single item already, sets tail previous to new node
        if(l->count == 1){
        	n = new_node(item,l->tail,NULL);
        	l->tail->previous = n;
        }

	//if there's more than 1 item, sets former head previous to new node
        if(l->count > 1){
		n = new_node(item, l->head,NULL);
		l->head->previous = n;
	}
 	
	//returns 0 if failed
	//TODO this could cause an issue because head and tail changed prior
	if(n == NULL){
		exit(1);
        }

	//sets head to the new node and increments count, returns 1 on sucess
        l->head = n;
        l->count ++;

        return 1;
}

// push a new item to the end of the DLL (after the last node in the list).
// Exits if DLL is NULL.
// Returns 1 on success
// Returns 0 on failure ( i.e. we couldn't allocate memory for the new node)
// (i.e. the memory allocation for a new node failed).
int dll_push_back(dll_t* l, void* item){

	//exits if dll sent in is null
	if (l == NULL){
               exit(1);
        }

        node_t* n;

	//when dll is empty sets head to back node
        if(dll_empty(l) == 1){
        	n = new_node(item,NULL,NULL);
        	l->head = n;
        }

	//when pushing back when only 1 item, sets new node next to tail and head next to new node	
        if(l->count == 1){
	        n = new_node(item,NULL,l->tail);
        	l->head->next = n;
        }

	//if more than 1 item already in, sets new node previous to former tail, and former tail next to new node
        if(l->count > 1){
        n = new_node(item, NULL,l->tail);
        l->tail->next = n;
        }

	//exits if malloc fails
	//TODO this could cause an issue because head and tail changed prior
        if(n == NULL){
                exit(1);
        }

	//always sets new tail to new node (becauses added to end) and increments count
        l->tail = n;
        l->count ++;
	return 1;
}

// Returns the first item in the DLL and also removes it from the list.
// Exits if the DLL is NULL. 
// Returns NULL on failure, i.e. there is nothing to pop from the list.
// Assume no negative numbers in the list or the number zero.
void* dll_pop_front(dll_t* t){

	//exits if dll sent in is null
	if (t == NULL){
                exit(1);
        }

	//returns null if dll is popping from empty list
        if(dll_empty(t) == 1){
                return NULL;
        }

	//sets the node to be popped to the dll head and stores its contents
        node_t* temp = t->head;
        void* item = temp->data;

	//sets the head to the head's next node
        t->head = temp->next;

	//when list only has 1 item left, sets the head and tail to null
        if(t->count == 1){
                t->tail = NULL;
                t->head = NULL;
        }

	//frees the former head node
        //TODO this could cause a segmentation fault
	free_node(temp);

	//decrements and returns the contents of the node
        t->count --;
        return item;
}

// Returns the last item in the DLL, and also removes it from the list.
// Exits if the DLL is NULL. 
// Returns NULL on failure, i.e. there is noting to pop from the list.
// Assume no negative numbers in the list or the number zero.
void* dll_pop_back(dll_t* t){

	if (t == NULL){
                exit(1);
        }

        if(dll_empty(t) == 1){
                return NULL;
        }

        node_t* temp = t->tail;
        void* item = temp->data;

        t->tail = temp->previous;
        if(t->count == 1){
                t->tail = NULL;
                t->head = NULL;
        }

        free_node(temp);

        t->count --;
        return item;
}

// Inserts a new node before the node at the specified position.
// Exits if the DLL is NULL
// Returns 1 on success
// Retruns 0 on failure:
//  * we couldn't allocate memory for the new node
//  * we tried to insert at a negative location.
//  * we tried to insert past the size of the list
//   (inserting at the size should be equivalent as calling push_back).
int dll_insert(dll_t* l, int pos, void* item){
	
	//when operation fails
	if(l == NULL){
		exit(1);
	}
        if(pos > l->count){
		return 0;
	}
        if(pos < 0){
		return 0;
	}

	//inserts at beginning or end
        if(pos == l->count){
                dll_push_back(l, item);
                return 1;
        }
        if(pos == 0){
                dll_push_front(l, item);
                return 1;
        }

	//iterates through dll if not at pushing to front or back
        node_t* n;
	//temp1 set at head node, temp2 set at 1 node ahead of temp1
        node_t* temp1 = l->head;
        node_t* temp2 = l->head->next;

        int iterator = 0;

        while(iterator < pos){
                if(iterator + 1 == pos){
                        n = new_node(item, temp1->next, temp2->previous);
                        temp1->next = n;
                        temp2->previous = n;
                }
                else{
                        temp1 = temp2;
                        temp2 = temp1->next;
                }
                iterator ++;
        }

        if(n == NULL){return 0;}
        l->count++;
        return 1;
}

// Returns the item at position pos starting at 0 ( 0 being the first item )
// Exits if the DLL is NULL
// Returns NULL on failure:
//  * we tried to get at a negative location.
//  * we tried to get past the size of the list
// Assume no negative numbers in the list or the number zero.
void* dll_get(dll_t* l, int pos){

	if(l == NULL){
		exit(1);
	}
        if(pos < 0){
		return NULL;
	}
        if(pos > l->count){
		return NULL;
	}

        void* item;

        if(pos == 0){
                item = l->head->data;
                return item;
        }
        if(pos == l->count-1){
                item = l->tail->data;
                return item;
        }

        node_t* temp1 = l->head;
        node_t* temp2 = l->head->next;
        int iterator = 0;

        while(iterator < pos){
                if(iterator + 1 == pos){
                        item = temp2->data;
                        return item;
                }
                else{
                        temp1 = temp2;
                        temp2 = temp1->next;
                }
                iterator ++;
        }
        return NULL;

}
/*
//TODO NEW HELPER FUNCTION -- currently doesn't work 
//Returns a pointer to an item within the dll
//Returns NULL if the dll is null, the dll is empty, or the item is not in the dll
void* dll_find(dll_t* t, int item){
	if(t == NULL){
		return NULL;
	}	
	if(dll_empty(t) == 1){
		return NULL;
	}

	node_t* temp = t->head;

	while(temp->data->data != item && temp->next != NULL){
		temp = temp->next;
	}

	if(temp->data->data != item){
		temp = NULL;
	}

	return temp;
}
*/
// Removes the item at position pos starting at 0 ( 0 being the first item )
// Exits if the DLL is NULL
// Returns NULL on failure:
//  * we tried to remove at a negative location.
//  * we tried to remove get past the size of the list
// Assume no negative numbers in the list or the number zero.
void* dll_remove(dll_t* l, int pos){
	if(l == NULL){
		exit(1);
	}
        if(pos < 0){return NULL;}
        if(pos > l->count){return NULL;}
        if(dll_empty(l) == 1){return NULL;}

        void* item;

        if(pos == 0){
                item = dll_pop_front(l);
                return item;
        }
        if(pos == l->count-1){
                item = dll_pop_back(l);
                return item;
        }

        node_t* temp1 = l->head;
        node_t* temp2 = l->head->next;
        int iterator = 0;

        while(iterator < pos){
                if(iterator + 1 == pos){
                        item = temp2->data;
                        temp1->next = temp2->next;
                        temp2->next->previous = temp1;
                        free_node(temp2);
                        l->count --;
                        return item;
                }
                else{
                        temp1 = temp2;
                        temp2 = temp1->next;

                }
                iterator ++;
        }
        return NULL;
}

// DLL Size
// Exits if the DLL is NULL. -- CHANGED TO RETURNING -1 
// Queries the current size of a DLL
int dll_size(dll_t* t){
	if(t == NULL){
		return -1;
	}
	return t->count;
}

// Free DLL
// Exits if the DLL is NULL.
// Removes a DLL and all of its elements from memory.
// This should be called before the proram terminates.
void free_dll(dll_t* t){

	if (t == NULL){
		exit(1);
	}

        int iterator = 0;
        int length = t->count;

        while(iterator <length){
                dll_pop_front(t);
                iterator ++;
        }

        free(t);
}

#endif
