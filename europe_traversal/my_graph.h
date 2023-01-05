// =================== Support Code =================
// Graph.
//
// - Implement each of the functions to create a working graph.
// - Do not change any of the function declarations
//   - (i.e. graph_t* create_graph() should not have additional arguments)
// - You should not have any 'printf' statements in your graph functions.
//   - (You may consider using these printf statements to debug, but they 
//      should be removed from your final version)
// ==================================================
#ifndef MYGRAPH_H
#define MYGRAPH_H

#include "my_dll.h"
#include <stdlib.h>
#include <assert.h>
// Create a graph data structure
typedef struct graph{
    int numNodes;
    int numEdges;
    dll_t* nodes;     //an array of nodes storing all of our nodes.
} graph_t;

typedef struct graph_node{
    int data;
    dll_t* inNeighbors;
    dll_t* outNeighbors;
    
    //added for traversals
    int visited;

} graph_node_t;

typedef struct graph_edge{
   graph_node_t* in;
   graph_node_t* out;
   
   int weight;

} graph_edge_t;


// Creates a graph
// Returns a pointer to a newly created graph.
// The graph should be initialized with data on the heap.
// The graph fields should also be initialized to default values.
// Returns NULL if we cannot allocate memory.
graph_t* create_graph(){
    // Modify the body of this function as needed.
    graph_t* myGraph= malloc(sizeof(graph_t));
    myGraph->nodes = create_dll();
    myGraph->numEdges = 0;
    myGraph->numNodes = 0;
    
    return myGraph;
}

// Returns the node pointer if the node exists.
// Returns NULL if the node doesn't exist or the graph is NULL
graph_node_t* find_node( graph_t * g, int value){
	if(g == NULL){
		return NULL;
	}
	//return NULL when graph is empty
	if(g->numNodes == 0){
		return NULL;
	}

	if(dll_empty(g->nodes) == 1){
		return NULL;
	}

	//iterates through each node in the graph node list and compares it to value
	int i;
	graph_node_t* temp;
	for(i = 0; i < g->numNodes; i++){
		temp = dll_get(g->nodes,i);
		if(temp->data == value){
			return temp;
		}
	}
	
    return NULL;
}

// Creates a graph node
// Note: This relies on your dll implementation.
graph_node_t* create_graph_node(int value){
    graph_node_t* graph_node = (graph_node_t*)malloc(sizeof(graph_node_t));
    
    if ( graph_node == NULL ) return NULL;
    
    graph_node->data = value;
    graph_node->inNeighbors = create_dll();
    graph_node->outNeighbors = create_dll();
    
    //added for traversals
    graph_node->visited = 0;
    
    return graph_node;
}

// Returns 1 on success
// Returns 0 on failure ( or if the node already exists )
// Returns -1 if the graph is NULL.
int graph_add_node(graph_t* g, int value){
    if ( g == NULL ) return -1;
    
    if (find_node(g, value) != NULL) return -1;
    
    graph_node_t * newNode = create_graph_node(value);
    if ( newNode == NULL ) return -1;
    
    assert(g->nodes);
    dll_push_back(g->nodes, newNode);
    g->numNodes++;
    
    return 1;
}


//TODO not sure if this does anything since already using dll_remove for g-nodes
//HELPER free graph node
void free_graph_node(graph_node_t* node){
	if(node == NULL){
		return;
	}
	free(node);
}


//HELPER FUNCTION -- called on list of GRAPH NODES
//Returns the index position of an item within a linked list 
//returns -1 upon failure(i.e. value not in list or list is NULL)
int dll_get_index(dll_t* l, int value){
	if(l == NULL){
		return -1;
	}

	if(dll_empty(l) == 1){
		return -1;
	}

	int counter;
	graph_node_t* tempNode = dll_get(l, 0);
	
	for(counter = 0; counter < dll_size(l); counter ++){
		tempNode = dll_get(l, counter);
		if(tempNode->data == value){
			return counter;
		}
	}
	return -1;
}

//TODO - added for HW9
//HELPER FUNCTION -- called on list of IN-NEIGHBOR EDGES 
//Returns the index position of an item within a linked list 
//returns -1 upon failure(i.e. value not in list or list is NULL)
int dll_get_index_in_neighbor(dll_t* l, int value){
	if(l == NULL){
		return -1;
	}

	if(dll_empty(l) == 1){
		return -1;
	}

	int counter;
	graph_edge_t* tempEdge = dll_get(l, 0);
	graph_node_t* tempNode = tempEdge->in;
	
	for(counter = 0; counter < dll_size(l); counter ++){
		tempEdge = dll_get(l, counter);
		//the out of an edge is an in-neighbor for a node that has that edge in its in-neighbor list
		tempNode = tempEdge->out;
		if(tempNode->data == value){
			return counter;
		}
	}
	return -1;
}

//TODO - added for HW9
//HELPER FUNCTION -- called on list of OUT-NEIGHBOR EDGES 
//Returns the index position of an item within a linked list 
//returns -1 upon failure(i.e. value not in list or list is NULL)
int dll_get_index_out_neighbor(dll_t* l, int value){
	if(l == NULL){
		return -1;
	}

	if(dll_empty(l) == 1){
		return -1;
	}

	int counter;
	graph_edge_t* tempEdge = dll_get(l, 0);
	graph_node_t* tempNode = tempEdge->out;
	
	for(counter = 0; counter < dll_size(l); counter ++){
		tempEdge = dll_get(l, counter);
		//the in of edge is an out-neighbor for a node that has that edge in its out-neighbor list
		tempNode = tempEdge->in;
		if(tempNode->data == value){
			return counter;
		}
	}
	return -1;
}

//HELPER to free an edge struct that has been malloced
void free_edge(graph_edge_t* e){
	if(e == NULL){
		return;
	}
	free(e);
	return;
}

// The function removes the node from the graph along with any edges associated with it.
// That is, this node would have to be removed from all the in and out neighbor's lists that countain it.
// Returns 1 on success
// Returns 0 on failure ( or if the node doesn't exist )
// Returns -1 if the graph is NULL.
int graph_remove_node(graph_t* g, int value){
	if(g == NULL){
		return -1;
	}

	graph_node_t* n = find_node(g, value);

	graph_edge_t* tempEdge;
	graph_node_t* tempNode;
	int tempIndex;

	if(n != NULL){
		//removing the node from the in-neighbors' lists of the node's out-neighbors
		while(dll_size(n->outNeighbors) > 0){
			tempEdge = dll_pop_front(n->outNeighbors);
			tempNode = tempEdge->in;
			tempIndex = dll_get_index_in_neighbor(tempNode->inNeighbors, value);
			
			//removes the edge if it exists
			if(tempIndex > -1){	
				dll_remove(tempNode->inNeighbors,tempIndex);
				free_edge(tempEdge);
				g->numEdges --;
			}
		}			
		
		//removing the node from the out-neighbors' lists of the node's in-neighbors
		while(dll_size(n->inNeighbors) > 0){
			tempEdge = dll_pop_front(n->inNeighbors);
			tempNode = tempEdge->out;
			tempIndex = dll_get_index_out_neighbor(tempNode->outNeighbors, value);
			
			if(tempIndex > -1){
				dll_remove(tempNode->outNeighbors, tempIndex);
				free_edge(tempEdge);
				g->numEdges --;
			}
		}
		tempIndex = dll_get_index(g->nodes, value);
		dll_remove(g->nodes, tempIndex);
	free_dll(n->outNeighbors);
	free_dll(n->inNeighbors);
	free_graph_node(n);
	
	g->numNodes --;
	return 1;
	}

	return 0;
}
/*
ORIGINAL GRAPH REMOVE NODE
int graph_remove_node(graph_t* g, int value){
	if(g == NULL){
		return -1;
	}

	graph_node_t* n = find_node(g, value);

	graph_node_t* tempNode;
	int tempIndex;

	if(n != NULL){
		while(dll_size(n->outNeighbors) > 0){
			tempNode = dll_pop_front(n->outNeighbors);
			tempIndex = dll_get_index(tempNode->inNeighbors, value);
			
			if(tempIndex > -1){	
				dll_remove(tempNode->inNeighbors,tempIndex);
				g->numEdges --;
			}
		}			
		
		while(dll_size(n->inNeighbors) > 0){
			tempNode = dll_pop_front(n->inNeighbors);
			tempIndex = dll_get_index(tempNode->outNeighbors, value);
			if(tempIndex > -1){
				dll_remove(tempNode->outNeighbors, tempIndex);
				g->numEdges --;
			}
		}
		tempIndex = dll_get_index(g->nodes, value);
		dll_remove(g->nodes, tempIndex);
	free_graph_node(n);
	g->numNodes --;
	return 1;
	}

	return 0;
}
*/

//HELPER FUNCTION
//prints the contents of the graph
void print_graph(graph_t* g){
	if(g == NULL){
		return;
	}
	
	if(dll_empty(g->nodes)){
		return;
	}
	
	graph_node_t* tempNode;
	graph_edge_t* tempEdge;
	int tempWeight;
	graph_node_t* tempNeighbor;	

	int i;
	int j;
	int tempData1;
	int tempData2;
	for(i = 0; i< dll_size(g->nodes); i++){
		tempNode = dll_get(g->nodes, i);
		tempData1 = tempNode->data;	
		printf("Node: %d",tempData1);
		
		//iterate through out-neighbors
		if(!(dll_empty(tempNode->outNeighbors))){
			printf("\n    Out-neighbors: ");
			for(j = 0; j < dll_size(tempNode->outNeighbors); j ++){
				tempEdge = dll_get(tempNode->outNeighbors,j);
				tempNeighbor = tempEdge->in;
				tempData2 = tempNeighbor->data;
				tempWeight = tempEdge->weight;
				if(j == dll_size(tempNode->outNeighbors) - 1){
					printf("%d:%d ",tempData2, tempWeight);
				}	
				else{
					printf("%d:%d,  ",tempData2, tempWeight);
				}
			}	
		}
		
		//iterate through in-neighbors
		if(!(dll_empty(tempNode->inNeighbors))){
			printf("\n    In-neighbors: ");
			for(j = 0; j < dll_size(tempNode->inNeighbors); j ++){
				tempEdge = dll_get(tempNode->inNeighbors, j);
				tempNeighbor = tempEdge->out;
				tempData2 = tempNeighbor->data;
				tempWeight = tempEdge->weight;
				if(j == dll_size(tempNode->inNeighbors) - 1){
					printf("%d:%d ",tempData2, tempWeight);
				}	
				else{
					printf("%d:%d, ",tempData2, tempWeight);
				}
			}	
		}
	printf("\n");	
	}
	return;
}

//HELPER function -- used on graph list of nodes
//Returns 1 if an edge exists (i.e. the neighbor is in the neighborList)
//Returns 0 if an edge doesn't exist (i.e. neighbor not in neighborList)
//Returns -1 if neighborList or neighbor are NULL 
int dll_has_graph_node(dll_t* l, graph_node_t* n){
	if(l == NULL){
		return -1;
	}
	if(n == NULL){
		return -1;
	}

	if(dll_size(l) < 1){
		return 0;
	}
	
	int i;
	graph_node_t* tempNode;
	int tempInt;
	for(i=0; i < dll_size(l); i++){
		tempNode = dll_get(l, i);	
		tempInt = tempNode->data;
		if(tempInt == n->data){
			return 1;
		}
	}
	return 0;
} 

//HELPER function -- used on graph node out-neighbor EDGE lists
//Returns 1 if an edge exists (i.e. the neighbor is in the neighborList)
//Returns 0 if an edge doesn't exist (i.e. neighbor not in neighborList)
//Returns -1 if neighborList or neighbor are NULL 
int dll_has_out_neighbor(dll_t* l, graph_node_t* n){
	if(l == NULL){
		return -1;
	}
	if(n == NULL){
		return -1;
	}

	if(dll_size(l) < 1){
		return 0;
	}
	
	int i;
	graph_edge_t* tempEdge;
	graph_node_t* tempNode;
	int tempInt;
	for(i=0; i < dll_size(l); i++){
		
		tempEdge = dll_get(l, i);
		tempNode = tempEdge->in;
		tempInt = tempNode->data;
		if(tempInt == n->data){
			return 1;
		}
	}
	return 0;
} 

//HELPER function -- used on graph node in-neighbor EDGE lists
//Returns 1 if an edge exists (i.e. the neighbor is in the neighborList)
//Returns 0 if an edge doesn't exist (i.e. neighbor not in neighborList)
//Returns -1 if neighborList or neighbor are NULL 
int dll_has_in_neighbor(dll_t* l, graph_node_t* n){
	if(l == NULL){
		return -1;
	}
	if(n == NULL){
		return -1;
	}

	if(dll_size(l) < 1){
		return 0;
	}
	
	int i;
	graph_edge_t* tempEdge;
	graph_node_t* tempNode;
	int tempInt;
	for(i=0; i < dll_size(l); i++){
		
		tempEdge = dll_get(l, i);
		tempNode = tempEdge->out;
		tempInt = tempNode->data;
		if(tempInt == n->data){
			return 1;
		}
	}
	return 0;
} 

//HELPER FUNCTION TO CREATE A GRAPH_EDGE struct
graph_edge_t* create_edge(graph_node_t* out, graph_node_t* in, int weight){
	
	if(in == NULL || out == NULL){
		return NULL;
	}
	
	graph_edge_t* myEdge = (graph_edge_t*)malloc(sizeof(graph_edge_t));
	myEdge->in = in;
	myEdge->out = out;
	myEdge->weight = weight;   

    return myEdge;
}

// Returns 1 on success
// Returns 0 on failure ( or if the source or destination nodes don't exist, or the edge already exists )
// Returns -1 if the graph is NULL.
int graph_add_edge(graph_t * g, int source, int destination, int weight){
    // The function adds an edge from source to destination but not the other way.
    // Make sure you are not adding the same edge multiple times.
    // Make sure you modify the in and out neighbors appropriatelly. destination will be an out neighbor of source.
    // Source will be an in neighbor of destination.
	if(g == NULL){
		return -1;
	}

	graph_node_t* sourceNode = find_node(g, source);
	graph_node_t* destNode = find_node(g, destination);
	
	//checks to make sure both source and dest node are in the graph
	if(sourceNode == NULL || destNode == NULL){
		return 0;
	}

	//check to make sure edge doesn't already exist and adds it if it doesn't exist
	if(dll_has_out_neighbor(sourceNode->outNeighbors, destNode) == 0 && dll_has_in_neighbor(destNode->inNeighbors, sourceNode) == 0){
		//mallocs for new edge (contains edge node and weight)
		graph_edge_t* newEdge = create_edge(sourceNode, destNode, weight);
		
		dll_push_back(sourceNode->outNeighbors, newEdge);
		dll_push_back(destNode->inNeighbors, newEdge);	
		g->numEdges ++;
		return 1;	
	} 
	else{
		return 0;
	}
}

// Returns 1 on success
// Returns 0 on failure ( or if the source or destination nodes don't exist )
// Returns -1 if the graph is NULL.
int contains_edge( graph_t * g, int source, int destination){
	if(g == NULL){
		return -1;
	}

	graph_node_t* sourceNode = find_node(g, source);
	graph_node_t* destNode = find_node(g, destination);

	//continuing if both graph nodes exist
	if(dll_has_graph_node(g->nodes, sourceNode) == 1 && dll_has_graph_node(g->nodes, destNode) == 1){

		//returning 1 if dest node is an out-neighbor of source and source node is an in-neighbor of dest
		if(dll_has_out_neighbor(sourceNode->outNeighbors, destNode) == 1 && dll_has_in_neighbor(destNode->inNeighbors, sourceNode)){
			return 1;
		} 	
		return 0;
	}
	return 0;
}

// Returns 1 on success
// Returns 0 on failure ( or if the source or destination nodes don't exist, or the edge doesn't exists )
// Returns -1 if the graph is NULL.
int graph_remove_edge(graph_t * g, int source, int destination){
    //The function removes an edge from source to destination but not the other way.
    //Make sure you remove destination from the out neighbors of source.
    //Make sure you remove source from the in neighbors of destination.
	if(g == NULL){
		return -1;
	}

	graph_node_t* sourceNode = find_node(g, source);
	graph_node_t* destNode = find_node(g, destination);


	if((sourceNode != NULL || destNode != NULL) && contains_edge(g, source, destination) == 1){

		int tempIndexOut = dll_get_index_out_neighbor(sourceNode->outNeighbors, destination);
		int tempIndexIn = dll_get_index_in_neighbor(destNode->inNeighbors, source);
	
		//removing the edge struct from the lists of the graph nodes
		dll_remove(sourceNode->outNeighbors, tempIndexOut);
		graph_edge_t* edge = dll_remove(destNode->inNeighbors, tempIndexIn);
		free_edge(edge);
		g->numEdges --;
		return 1;
		
	}
	return 0;	
}

//HELPER printing a list of out-neighbors
void print_out_neighbors(dll_t* l){

	if(l == NULL){
		return;
	}
	else{
		int i;
		graph_edge_t* tempEdge;
		graph_node_t* tempNode;
		for(i=0; i<dll_size(l); i++){
			tempEdge = dll_get(l, i);
			tempNode = tempEdge->in;
			if(i == dll_size(l)-1){
				printf("%d:%d ",tempNode->data,tempEdge->weight);
			}
			else{
				printf("%d:%d, ",tempNode->data,tempEdge->weight);
			}
		}
		printf("\n");
	}
}

//HELPER printing a list of in-neighbors
void print_in_neighbors(dll_t* l){

	if(l == NULL){
		return;
	}
	else{
		int i;
		graph_edge_t* tempEdge;
		graph_node_t* tempNode;
		for(i=0; i<dll_size(l); i++){
			tempEdge = dll_get(l, i);
			tempNode = tempEdge->out;
			if(i == dll_size(l)-1){
				printf("%d:%d ",tempNode->data,tempEdge->weight);
			}
			else{
				printf("%d:%d, ",tempNode->data,tempEdge->weight);
			}
		}
		printf("\n");
	}
}

// Returns dll_t* of all the in neighbors of this node.
// Returns NULL if thte node doesn't exist or if the graph is NULL.
dll_t* getInNeighbors( graph_t * g, int value ){
	if(g == NULL){
		return NULL;
	}

	int valueIndex = dll_get_index(g->nodes, value);
	if(valueIndex != -1 ){
		graph_node_t* node = dll_get(g->nodes, valueIndex);
		dll_t* inNeighbors = node->inNeighbors;
		return inNeighbors;
	}
	else{
		return NULL;
	}	
}

// Returns the number of in neighbors of this node.
// Returns -1 if the graph is NULL or the node doesn't exist.
int getNumInNeighbors( graph_t * g, int value){
	if(g == NULL){
		return -1;
	}
	
	dll_t* inNeighbors = getInNeighbors(g, value);

	if(inNeighbors != NULL){
		return dll_size(inNeighbors);
	}
	else{
		return -1;
	}
}

// Returns dll_t* of all the out neighbors of this node.
// Returns NULL if thte node doesn't exist or if the graph is NULL.
dll_t* getOutNeighbors( graph_t * g, int value ){
	if(g == NULL){
		return NULL;
	}

	int valueIndex = dll_get_index(g->nodes, value);
	if(valueIndex != -1 ){
		graph_node_t* node = dll_get(g->nodes, valueIndex);
		dll_t* outNeighbors = node->outNeighbors;
		return outNeighbors;
	}
	else{
		return NULL;
	}	
}

// Returns the number of out neighbors of this node.
// Returns -1 if the graph is NULL or the node doesn't exist.
int getNumOutNeighbors( graph_t * g, int value){
	if(g == NULL){
		return -1;
	}
	
	dll_t* outNeighbors = getOutNeighbors(g, value);

	if(outNeighbors != NULL){
		return dll_size(outNeighbors);
	}
	else{
		return -1;
	}
}

// Returns the number of nodes in the graph
// Returns -1 if the graph is NULL.
int graph_num_nodes(graph_t* g){
	if(g ==	NULL){
		return -1;
	}	
	if(g->nodes != NULL){
		return g->numNodes;
	}
	else{
		return -1;
	}
}

// Returns the number of edges in the graph,
// Returns -1 on if the graph is NULL
int graph_num_edges(graph_t* g){
	if(g == NULL){
		return -1;
	}
	
	return g->numEdges;	
}

//removes a node from the in-neighbor edge list of the node's out-neighbors
void node_remove_out_neighbors(graph_node_t* n){
	if(n == NULL || n->outNeighbors == NULL){
		return;
	}
	int i;
	graph_edge_t* tempEdge;
	graph_node_t* tempNode;
	int index;	

	for(i = 0; i<dll_size(n->outNeighbors); i++){
		tempEdge = dll_get(n->outNeighbors, i);
		tempNode = tempEdge->in;
		
		//removing this n from the list of inNeighbors of the ith node in its outNeighbors
		index = dll_get_index_in_neighbor(tempNode->inNeighbors, n->data);
		dll_remove(tempNode->inNeighbors, index);
		free_edge(tempEdge);
	}
	return;
}

//removes a node from the out-neighbors edge list of the node's in-neighbors
void node_remove_in_neighbors(graph_node_t* n){
	if(n == NULL || n->inNeighbors == NULL){
		return;
	}
	int i;
	graph_edge_t* tempEdge;
	graph_node_t* tempNode;
	int index;	

	for(i = 0; i<dll_size(n->inNeighbors); i++){
		tempEdge = dll_get(n->inNeighbors, i);
		tempNode = tempEdge->out;
		
		//removing this n from the list of outNeighbors of the ith node in its inNeighbors
		index = dll_get_index_out_neighbor(tempNode->outNeighbors, n->data);
		dll_remove(tempNode->outNeighbors, index);
		free_edge(tempEdge);
	}
	return;
}


// Free graph
// Removes a graph and ALL of its elements from memory.
// This should be called before the program terminates.
// Make sure you free all the dll's too.
void free_graph(graph_t* g){
	if(g == NULL){
		return;
	}

	if(graph_num_nodes(g)>=0 && g->nodes != NULL){
		//printf("graph_num_nodes = %d\n",graph_num_nodes(g));
		graph_node_t* tempNode;
		int i;
		for(i=0;i<graph_num_nodes(g);i++){
			tempNode = dll_get(g->nodes, i);
			
			node_remove_out_neighbors(tempNode);
			node_remove_in_neighbors(tempNode);
			free_dll(tempNode->outNeighbors);
			free_dll(tempNode->inNeighbors);
			free_graph_node(tempNode);
		}
		free_dll(g->nodes);
	}
	free(g);		
}

/* ============================================================
 * LAB09 FUNCTIONS
 * =========================================================== */

//HELPER FUNCTION -- reset visited nodes
void graph_reset_visited(graph_t* g){
//	printf("entering reset visited function\n");
	if(g == NULL){
		return;
	}
	
	if(dll_empty(g->nodes)){
		return;
	}
	
	graph_node_t* tempNode = dll_get(g->nodes, 0);

	int i;
	for(i = 0; i< dll_size(g->nodes); i++){
		tempNode = dll_get(g->nodes, i);
		tempNode->visited = 0;
	}
	return;
}

// returns 1 if we can reach the destination from source
// returns 0 if it is not reachable
// returns -1 if the graph is NULL (using BFS)
int graph_is_reachable(graph_t * g, int source, int dest){
	if(g == NULL){
		return -1;
	}

	//not reachable if no nodes or no edges between nodes
	if(graph_num_nodes(g) == 0 || graph_num_edges(g) == 0){
		return 0;
	}
	
	//not reachable if source or dest node not in graph
	if(dll_get_index(g->nodes,source) == -1 || dll_get_index(g->nodes,dest) == -1){
		return 0;
	}

	//return true if there's a direct edge between source and dest -- (short circuit)
	if(contains_edge(g,source,dest)){
		return 1;
	}

	int index = dll_get_index(g->nodes, source);
	graph_node_t* tempNode = dll_get(g->nodes, index);
	graph_edge_t* tempEdge;
	graph_node_t* tempNode2;

	dll_t* q = create_dll();
	dll_push_back(q, tempNode);

	int i;

	while(!(dll_empty(q))){
		
		//get index[0] of the queue and set as current node
		tempNode = dll_get(q,0);

		//check if the current node's value is the dest value -- set reachable to 1 and return it
		if(tempNode->data == dest){
			graph_reset_visited(g);
//			printf("found data; resetting visited\n");
			free_dll(q);
			return 1;
		}

		//mark current node as visited
		tempNode->visited = 1;

		//push all unvisited outNeighbors onto queue
		for(i=0;i<dll_size(tempNode->outNeighbors);i++){
			tempEdge = dll_get(tempNode->outNeighbors, i);
			tempNode2 = tempEdge->in;
			if(!(tempNode2->visited)){
				dll_push_back(q,tempNode2);
			}
		}	

		//pop front of queue
		dll_pop_front(q);
	}

	//if iteration is completed without matching to dest then return 0
	graph_reset_visited(g);
	free_dll(q);
	return 0;
}

//helper function to use DFS to find a cycle given a source node
int dfs_find_cycle(graph_node_t* source){

	if(source == NULL){
		return 0;
	}
	
	if(source->visited){
		return 1;
	}

	source->visited = 1;
	
	int size = dll_size(source->outNeighbors);

	if(size == 0){
		source->visited = 0;
		return 0;
	}

	int i;
	graph_node_t* tempNode;
	graph_edge_t* tempEdge;
	int cycle = 0;

	for(i=0;i<size;i++){
		tempEdge = dll_get(source->outNeighbors, i);
		tempNode = tempEdge->in;
		cycle = dfs_find_cycle(tempNode);
		if(cycle == 1){
			return 1;
		}
		tempNode->visited = 0;
	}

	return cycle;
}

// returns 1 if there is a cycle in the graph
// returns 0 if no cycles exist in the graph
// returns -1 if the graph is NULL 
// You may use either BFS or DFS to complete this task.
int graph_has_cycle(graph_t * g){
	
	if(g == NULL){
		return -1;
	}

	if(g->nodes == NULL || dll_empty(g->nodes)){
		return 0;
	}
	
	graph_node_t* temp;
	int i;
	for(i = 0;i<dll_size(g->nodes);i++){
		temp = dll_get(g->nodes,i);
//		printf("%d visited == %d\n",temp->data,temp->visited);		
		
		if(dfs_find_cycle(temp)){
//			printf("dfs_find_cycle(temp) == 1\n");
			return 1;
		}
		graph_reset_visited(g);
	}
	graph_reset_visited(g);
	return 0;
}

//helper function print DFS path from source to destination node 
int dfs_print(graph_node_t* source, int dest){

	if(source == NULL || source->visited == 1){
		return 0;
	}
	
	printf("(%d)",source->data);
	source->visited = 1;
	
	int size = dll_size(source->outNeighbors);
	if(size == 0 && source->data != dest){
		return 0;
	}

	if(source->data == dest){
		return 1;
	}

	int i;
	int reached = 0;
	graph_edge_t* tempEdge;
	graph_node_t* tempNode;
	
	for(i=0;i<size;i++){
		tempEdge = dll_get(source->outNeighbors, i);
		tempNode = tempEdge->in;
		if(!(tempNode->visited)){
			printf("--%d-->",tempEdge->weight);
			reached = dfs_print(tempNode,dest);
		}
		if(reached){
			break;
		}			
	
		//temp->visited = 0;
	}
	return reached;
}

// prints any path from source to destination if there 
// exists a path from the source to the destination.
// Note: Consider using one of the other functions to help you
//       determine if a path exists first
// (Choose either BFS or DFS, typically DFS is much simpler)
//
// Returns 1 if there is a path from source to destination
// Returns 0 if there is not a path from a source to destination
// Returns -1 if the graph is NULL
int graph_print_path(graph_t * g, int source, int dest){
	if(g == NULL){
		return -1;
	}

	if(!(graph_is_reachable(g,source,dest))){
		return 0;
	}
	
	graph_node_t* sourceNode = find_node(g, source);

	if(sourceNode == NULL){
		return 0;
	}	
	
	printf("\n");
	dfs_print(sourceNode, dest);	
	printf("\n");
	graph_reset_visited(g);
	return 1;

}

/* ============================================================
 * HW9 FUNCTIONS
 * =========================================================== */

int graph_get_edge_weight(graph_t* g, int source, int destination){
	return -1;
}

/* =========================================================== */
//DISTANCE HELPER STRUCT AND FUNCTIONS
typedef struct distance{
	graph_node_t* node;
	int cost;
	graph_node_t* from;
}distance_t;

distance_t* create_distance(graph_node_t* node, int cost, graph_node_t* from){
	distance_t* myDistance = (distance_t*)malloc(sizeof(distance_t));
	myDistance->node = node;
	myDistance->cost = cost;
	myDistance->from = from;
	return myDistance;
	}

void free_distance(distance_t* d){
	if(d == NULL){
		return;
	}
	free(d);
}
/* =========================================================== */
//DLL HELPER FUNCTIONS
//--------------------

//return the min distance from a list of distances
int dll_min_distance_index(dll_t* l){
	distance_t* tempDistance;
	int i;
	int minIndex = 0;
	int min = 999999;
	for(i=0;i<dll_size(l);i++){
		tempDistance = dll_get(l,i);
		if(tempDistance->cost < min){
			min = tempDistance->cost;
			minIndex = i;
		}
	}

	return minIndex;
}

//returns a nodes current distance when passed in a node
distance_t* t_get_distance(dll_t* t, graph_node_t* node){
	
	if(t==NULL || node == NULL){
		return NULL;
	}

	int i;
	distance_t* tempDistance;
	graph_node_t* tempNode;
//	printf("t size = %d\n",dll_size(t));
	for(i=0;i<dll_size(t);i++){
		tempDistance = dll_get(t,i);
//		printf("tempDistance->node = %d\n",tempDistance->node->data);
		if(tempDistance == NULL || tempDistance->node == NULL){
			continue;
		}		

		tempNode = tempDistance->node;
		if(tempNode->data == node->data){
			return tempDistance;
		}
	}
//	printf("exiting for loop\n");
	return NULL;
}

//prints a table that has been created via dijkstras shortest path algorithm
void print_shortest_path_table(dll_t* t){

	if(t == NULL){
		return;
	}	

	int i;
	distance_t* tempDistance;
	int tempNodeData;
	int tempFromNodeData;
	int tempCost;

	for(i=0;i<dll_size(t);i++){
		tempDistance = dll_get(t, i);
		tempNodeData = tempDistance->node->data;
		tempCost = tempDistance->cost;		

		printf("Node: %d, Cost: %d",tempNodeData,tempCost);
		if(tempDistance->from != NULL){
			tempFromNodeData = tempDistance->from->data;
			printf(", From: %d\n",tempFromNodeData);
		}
		else{
			printf(", From: NULL\n");
		}

	}
	return;
}

//free the malloced memory for a shortest path table created through the dijkstras algorithm
void free_shortest_path_table(dll_t* t){

	if(t == NULL){
		return;
	}	

	int i;
	distance_t* tempDistance;

	for(i=0;i<dll_size(t);i++){
		tempDistance = dll_get(t, i);
		if(tempDistance != NULL){
			free_distance(tempDistance);
		}		
	}
	free_dll(t);
	return;
}

//returns a nodes current distance when passed in a node's data
distance_t* t_get_distance_from_int(dll_t* t, int data){
	
	if(t==NULL){
		return NULL;
	}

	int i;
	distance_t* tempDistance;
	graph_node_t* tempNode;
	for(i=0;i<dll_size(t);i++){
		tempDistance = dll_get(t,i);
		if(tempDistance == NULL || tempDistance->node == NULL){
			continue;
		}		

		tempNode = tempDistance->node;
		if(tempNode->data == data){
			return tempDistance;
		}
	}
	return NULL;
}

//reconstructs a shortest path and sends it into a linked list based on the destination node passed in
dll_t* construct_shortest_path(dll_t* t, int destination){
	if(t == NULL){
		return NULL;
	}
	distance_t* tempDist = t_get_distance_from_int(t, destination);
	if(tempDist == NULL){
		return NULL;
	}
	if(tempDist->node == NULL){
		return NULL;
	}

	dll_t* path = create_dll();
	dll_push_front(path, tempDist);
	graph_node_t* tempNode = tempDist->from;	

	while(tempNode != NULL){
		tempDist = t_get_distance(t,tempNode);
		dll_push_front(path, tempDist);
		tempNode = tempDist->from;
	}
	return path;			
}

//prints a shortest path
void print_shortest_path(dll_t* path){
	if(path == NULL){
		printf("path == NULL\n");
		return;
	}
	int totalCost;	
	int i;
	distance_t* tempDist;
	
	printf("\n");
	for(i=0;i<dll_size(path);i++){
		tempDist = dll_get(path,i);
		printf("Node: %d, Cost: %d\n",tempDist->node->data,tempDist->cost);
		if(i == dll_size(path) - 1){
			totalCost = tempDist->cost;
		}
	}
	printf("\nTotal cost = %d\n",totalCost);

}

/* =========================================================== */

dll_t* dijkstras(graph_t* g, int source){

	//returns without printing if g is NULL or source not in graph
	if(g == NULL || find_node(g,source) == NULL){
		return NULL;
	}

	// =======================
	//initialize queue and table
	dll_t* q = create_dll();
	dll_t* t = create_dll();

	distance_t* tempDistance;
	graph_node_t* tempNode;
	
	//iterate through all graph nodes and add distances to both the queue and the table
	//	(sets cost to infinite, except for source node distance)
	int i;
	int numNodes = graph_num_nodes(g);
	for(i = 0;i<numNodes;i++){
		tempNode = dll_get(g->nodes,i);
		if(tempNode->data == source){
			tempDistance = create_distance(tempNode,0,NULL);
			dll_push_back(q, tempDistance);
			dll_push_back(t, tempDistance);
		}
		else{
			tempDistance = create_distance(tempNode,9999,NULL);
			dll_push_back(q,tempDistance);
			dll_push_back(t,tempDistance);
		}
	}
	// =======================

	distance_t* minDistance;
	int minIndex;


	dll_t* tempOutNeighbors;
	graph_edge_t* tempEdge;
	graph_node_t* tempNeighbor;	

	//distance_t* tempDistance;
	int tempDistanceNum;
	distance_t* currentDistance;

	int tempWeight;
	while(!(dll_empty(q))){
		
		//remove the smallest distance node from the queue and gets its out neighbors
		minIndex = dll_min_distance_index(q);
		minDistance = dll_remove(q, minIndex);
		tempNode = minDistance->node;
		tempOutNeighbors = getOutNeighbors(g,tempNode->data);

		//iterate through the smallest node's out neighbors and updates cost if smaller than current cost
		for(i=0;i<dll_size(tempOutNeighbors);i++){
			//get smallest node to out neighbor edge info
			tempEdge = dll_get(tempOutNeighbors,i);
			tempWeight = tempEdge->weight;
			tempNeighbor = tempEdge->in;
			
			//get the distance to the smallest node, add weight of edge, and compare to current cost to get to that node
			tempDistance = t_get_distance(t,tempNode);
			tempDistanceNum = tempDistance->cost + tempWeight;
			currentDistance = t_get_distance(t, tempNeighbor);
			
			if(currentDistance == NULL){
				printf("currentDistance == NULL\n");
			}	
		
	
			if(tempDistanceNum < currentDistance->cost){
				currentDistance->cost = tempDistanceNum;
				currentDistance->from = tempNode;
			}
		}
	}
	free_dll(q);
	return t;
}


#endif
