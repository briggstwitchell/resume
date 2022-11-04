//Briggs Twitchell
//10/6/2022
//HW3 - Creating a Doubly Linked List

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

// Create a node data structure to store data within
// our DLL. In our case, we will stores 'integers'
typedef struct node {
	int data;
	struct node* next;
  	struct node* previous;
} node_t;

//HELPER: function to create a new node
node_t* new_node(int data, node_t* next, node_t* previous){
	node_t* n = (node_t*)malloc(sizeof(node_t*));
	n->data = data;

	if (n == NULL){
		return NULL;
	}	

	n->next = next;
	n->previous = previous;
	return n;
}


//HELPER: function to free a node
void free_node(node_t* n){
	if (n == NULL){
		exit(0);
	}
	
	free(n);
}


// Create a DLL data structure
// Our DLL holds a pointer to the first node in our DLL called head,
// and a pointer to the last node in our DLL called tail.
typedef struct DLL {
	int count;		// count keeps track of how many items are in the DLL.
	node_t* head;		// head points to the first node in our DLL.
    node_t * tail;          //tail points to the last node in our DLL.
} dll_t;

// Creates a DLL
// Returns a pointer to a newly created DLL.
// The DLL should be initialized with data on the heap.
// (Think about what the means in terms of memory allocation)
// The DLLs fields should also be initialized to default values.
// Returns NULL if we could not allocate memory.
dll_t* create_dll(){
	dll_t* myDLL=(dll_t*)malloc(sizeof(dll_t*));	
	
	if (myDLL == NULL){
		return NULL;
	}

	myDLL->count = 0;
	myDLL->head = NULL;
	myDLL->tail = NULL;	
	
	return myDLL;
}

// DLL Empty
// Check if the DLL is empty
// Returns -1 if the dll is NULL.
// Returns 1 if true (The DLL is completely empty)
// Returns 0 if false (the DLL has at least one element enqueued)
int dll_empty(dll_t* t){
	if (t == NULL){
		return -1;
	}
	
	if (t->count == 0){
		return 1;
	}
	
	else{return 0;}
}

// push a new item to the front of the DLL ( before the first node in the list).
// Returns -1 if DLL is NULL.
// Returns 1 on success
// Returns 0 on failure ( i.e. we couldn't allocate memory for the new node)
// (i.e. the memory allocation for a new node failed).
int dll_push_front(dll_t* t, int item){
	if (t == NULL){
		return -1;
	}
	
	node_t* n;

	if(dll_empty(t) == 1){
	n = new_node(item,NULL,NULL);
	t->tail = n;
	}

	if(t->count == 1){
	n = new_node(item,t->tail,NULL);
	t->tail->previous = n;
	}

	if(t->count > 1){
	n = new_node(item, t->head,NULL);
	t->head->previous = n;//added
	}	
	
	if(n == NULL){
		return 0;
	}
	
	t->head = n;

	t->count ++;
	
	return 1;
}

// push a new item to the end of the DLL (after the last node in the list).
// Returns -1 if DLL is NULL.
// Returns 1 on success
// Returns 0 on failure ( i.e. we couldn't allocate memory for the new node)
// (i.e. the memory allocation for a new node failed).
int dll_push_back(dll_t* t, int item){
	
	if (t == NULL){
		return -1;
	}
	
	node_t* n;


	if(dll_empty(t) == 1){
	n = new_node(item,NULL,NULL);
	t->head = n;
	}

	if(t->count == 1){
	n = new_node(item,NULL,t->tail);
	t->head->next = n;
	}

	if(t->count > 1){
	n = new_node(item, NULL,t->tail);
	t->tail->next = n;
	}	
	
	if(n == NULL){
		return 0;
	}
	
	t->tail = n;

	t->count ++;

	return 1; 
}

// Returns the first item in the DLL and also removes it from the list.
// Returns -1 if the DLL is NULL. 
// Returns 0 on failure, i.e. there is noting to pop from the list.
// Assume no negative numbers in the list or the number zero.
int dll_pop_front(dll_t* t){
	if (t == NULL){
		return -1;
	}

	if(dll_empty(t) == 1){
		return 0;
	}

	node_t* temp = t->head;	
	int item = temp->data;

	t->head = temp->next;
	if(t->count == 1){
		t->tail = NULL;
		t->head = NULL;
	}	

	free_node(temp);

	t->count --;
	return item;
}

// Returns the last item in the DLL, and also removes it from the list.
// Returns a -1 if the DLL is NULL. 
// Returns 0 on failure, i.e. there is noting to pop from the list.
// Assume no negative numbers in the list or the number zero.
int dll_pop_back(dll_t* t){
	
	if (t == NULL){
		return -1;
	}

	if(dll_empty(t) == 1){
		return 0;
	}

	node_t* temp = t->tail;	
	int item = temp->data;

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
// Returns -1 if the list is NULL
// Returns 1 on success
// Retruns 0 on failure:
//  * we couldn't allocate memory for the new node
//  * we tried to insert at a negative location.
//  * we tried to insert past the size of the list
//   (inserting at the size should be equivalent as calling push_back).
int dll_insert(dll_t* t, int pos, int item){
	
	//==================
	if(t == NULL){return -1;}
	if(pos > t->count){return 0;}
	if(pos < 0){return 0;}
	//==================
	
	//==================
	if(pos == t->count){
		dll_push_back(t, item);
		return 1;
	}	
	
	if(pos == 0){
		dll_push_front(t, item);
		return 1;
	}
	//==================

	node_t* n;
	node_t* temp1 = t->head;
	node_t* temp2 = t->head->next;	

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
	
	//==================
	if(n == NULL){return 0;}
	//==================
	t->count++;
	return 1;
}

// Returns the item at position pos starting at 0 ( 0 being the first item )
// Returns -1 if the list is NULL
//  (does not remove the item)
// Returns 0 on failure:
//  * we tried to get at a negative location.
//  * we tried to get past the size of the list
// Assume no negative numbers in the list or the number zero.
int dll_get(dll_t* t, int pos){
	
	if(t == NULL){return -1;}
	if(pos < 0){return -1;}
	if(pos > t->count){return-1;}

	int item;

	//return item if at head or tail
	if(pos == 0){
		item = t->head->data;
		return item;
	}
	if(pos == t->count-1){
		item = t->tail->data;
		return item;
	}

	//set up iterator variables
	node_t* temp1 = t->head;
	node_t* temp2 = t->head->next;	
	int iterator = 0;

	//iterate through list until temp2 is at position
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
	return -1;
}

// Removes the item at position pos starting at 0 ( 0 being the first item )
// Returns -1 if the list is NULL
// Returns 0 on failure:
//  * we tried to remove at a negative location.
//  * we tried to remove get past the size of the list
// Assume no negative numbers in the list or the number zero.
int dll_remove(dll_t* t, int pos){
	if(t == NULL){return -1;}
	if(pos < 0){return -1;}
	if(pos > t->count){return-1;}
	if(dll_empty(t) == 1){return -1;}

	int item;

	//return item if at head or tail
	if(pos == 0){
		item = dll_pop_front(t);
		return item;
	}
	if(pos == t->count-1){
		item = dll_pop_back(t);
		return item;
	}

	//set up iterator variables
	node_t* temp1 = t->head;
	node_t* temp2 = t->head->next;	
	int iterator = 0;

	//iterate through list until temp2 is at position
	while(iterator < pos){
		if(iterator + 1 == pos){
			item = temp2->data;
			temp1->next = temp2->next;
			temp2->next->previous = temp1;//NOT SURE IF THIS LINE WILL WORK
			free_node(temp2);
			t->count --;
			return item;
		}
		else{
			temp1 = temp2;
			temp2 = temp1->next;
			
		}
		iterator ++;
	}
	return -1;
}

// DLL Size
// Returns -1 if the DLL is NULL.
// Queries the current size of a DLL
int dll_size(dll_t* t){
	if(t == NULL){return -1;}
	return t->count;
}

// Free DLL
// Removes a DLL and all of its elements from memory.
// This should be called before the proram terminates.
void free_dll(dll_t* t){
	if (t == NULL){return;}

	int iterator = 0;
	int length = t->count;	

	while(iterator <length){
		dll_pop_front(t);
		iterator ++;	
	}
	
	free(t);	
}

#endif
