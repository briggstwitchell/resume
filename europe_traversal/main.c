#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "my_dll.h"
#include "my_graph.h"

int CITY_NAME_SIZE = 20;
int MAX_NUM_CITIES = 20;
//prints the strings in an array of strings
void printStringArray(char array[MAX_NUM_CITIES][CITY_NAME_SIZE], int size){
	if (array == NULL){
		return;
	}
	int i;
	printf("\n");
	for(i=0;i<size;i++){
		printf("%s ",array[i]);
	}	
	printf("\n");
}

//returns 1 if a string is already in a list of strings
//	meant to check for a city within the list of cities
int inCityList(char array[MAX_NUM_CITIES][CITY_NAME_SIZE], int size, char* str){
	if(array == NULL || str == NULL){
		return -1;
	}

	if(size == 0){
		return 0;
	}
	int i;
	for(i = 0; i<size; i++){
		if(strcmp(array[i],str)==0){
			return 1;
		}	
	}
	return 0;
}


//returns the index of a string in an array of strings -- returns -1 if null or can't find string in array
int getStringIndex(char array[MAX_NUM_CITIES][CITY_NAME_SIZE], int size, char* str){

	if(array == NULL || str == NULL){
		return -1;
	}

	if(size == 0){
		return -1;
	}
	
	int i;
	for(i = 0; i<size; i++){
		if(strcmp(array[i],str)==0){
			return i;
		}		
	}
	return -1;
}

//returns the index of a string in an array of strings -- returns -1 if null or can't find string in array
int getIntIndex(int array[], int size, int item){

	if(array == NULL || size == 0){
		return -1;
	}

	int i;
	for(i = 0; i<size; i++){
		if(array[i] == item){
			return i;
		}		
	}
	return -1;
}
void swapStrings(char string1[CITY_NAME_SIZE], char string2[CITY_NAME_SIZE]){
	char temp[CITY_NAME_SIZE];
	strncpy(temp,string1,CITY_NAME_SIZE);
	strcpy(string1, string2);
	strcpy(string2, temp);
	return;
}

void swapNums(int* a, int* b){
	int temp;
	temp =*a;
	*a = *b;
	*b = temp;
	return;
}

//sorts the first array passed in alphabetically and sorts the second array passed in to correspond with sorting
void bubbleSortParallelArrays(char stringArray[MAX_NUM_CITIES][CITY_NAME_SIZE],int* numArray, int size){
	if(stringArray == NULL || numArray == NULL || size == 0){
		return;
	}	

	int i;
	int j;
	for(i=0;i<size-1;i++){
		for(j=0;j<size-i-1;j++){
				if(*stringArray[j] > *stringArray[j+1]){
					
					swapStrings(stringArray[j],stringArray[j+1]);
					swapNums(&numArray[j],&numArray[j+1]);
				}
			}
		}
	}

void printIntArray(int* array, unsigned int size){
    // Note: 'unsigned int' is a datatype for storing positive integers.
    unsigned int i;
    	for(i = 0; i < size; i=i+1){
    		printf("%d ",array[i]);
               }
    	printf("\n");
    }

void printCitySelections(char array[MAX_NUM_CITIES][CITY_NAME_SIZE], int size, int selection){
	printf("\tEnter a number associated with one of the cities below:\n");
	int i;
	for(i=0;i<size;i++){
		if(i == selection){
			continue;
		}
		printf("\t\t%-12s %s %2d\n",array[i],"---",i+1);
	}	
	printf("\n\t\tTO QUIT ENTER -- %2d\n\n\t\t$> ",0);	
}

int main() {

		//Read data from datafile city.dat
		
		//Build an adjacency matrix based on the city.dat datafile
		
		//Create an alphabetized list of cities for selection by the user

		//Display the cities from which to select using a number that your
		//program assigns from the alphabetized list of cities that is
		//dynamically created from your city.dat file.  Example:
		//     Please select an origin city
		//     Enter a number associated with one of the cities below:
		//     Amsterdam    ---  1
		//     Belgrade     ---  2
		//     Bern         ---  3
		//     Genoa        ---  4
		//     Hamburg      ---  5
		//     Lisbon       ---  6
		//     Madrid       ---  7
		//     Munich       ---  8
		//     Naples       ---  9
		//     Paris        --- 10
		//     Warsaw       --- 11
		//
		//     TO QUIT ENTER --  0
		//
		//     $>
		
	//Have the user select an origin city, displaying the above list
	
	//Have the user select a destination city, removing the origin 
	//city from the list
	
	//Apply Dijkstra's Algorithm to determine the optimal (shortest) path

	//Report three items:
	//     1. the names of the two cities, such as:
	//          ORIGIN:      Lisbon
	//          DESTINATION: Warsaw
	//     2. the length of the optimal path, such as:
	//          LENGTH:      1629
	//     3. the list of the cities visited along the optimal path, like:
	//          PATH CITIES: Lisbon
	//                       Madrid
	//                       Genoa
	//                       Trieste
	//                       Vienna
	//                       Warsaw

	//Continue to ask for two cities to map until the user selects 0

	//OBTAINING FILE INFORMATION
	char fileName[30] = "city.dat";
	//printf("Please enter the file name containing the city data\n");
	//scanf("%s",&fileName);

	//creates file handle and closes if unable to read file
	FILE* file_pointer = fopen(fileName,"r");
	if (file_pointer == NULL){
		printf("can't read file\n");
		exit(1);
	}	

	//reads file line by line and stores in array if it finds a new city
	int max_expected_string = 200; 
	char lineString[max_expected_string];

	//initialize token variables to split line by space
	char* str;
	int tokenCounter;

	//initializes the variables for the list of cities
	char cityArray[MAX_NUM_CITIES][CITY_NAME_SIZE];
	int cityArraySize = 0;	
	int weight;

	//temp ints to represent source and dest node values	
	int source;
	int dest;

	//initialize the graph
	graph_t* city_graph = create_graph();

	while(!(feof(file_pointer))){
		fgets(lineString,max_expected_string,file_pointer);
		
		//iterates through line twice and adds city if not in cityArray
		str = strtok(lineString, " ");
		for(tokenCounter = 0; tokenCounter<3; tokenCounter++){
			if(tokenCounter == 2 && str != NULL){
				weight = atoi(str);
				continue;
			}	
		
			if(inCityList(cityArray,cityArraySize,str)==0){
				strcpy(cityArray[cityArraySize],str);
				graph_add_node(city_graph,cityArraySize);
				cityArraySize ++;
			}
			if(tokenCounter == 0){
				source = getStringIndex(cityArray, cityArraySize, str);
			}
			else{
				dest = getStringIndex(cityArray, cityArraySize, str);
			}
			str = strtok(NULL," ");
		}
		if(source >= 0 && dest >= 0 && weight > 0){
			graph_add_edge(city_graph, source, dest, weight);	
			graph_add_edge(city_graph, dest, source, weight);	
		} 
	}	
	fclose(file_pointer);

	//create array of ints that corresponds to cityArray of strings
	int i;
	int cityNumArray[cityArraySize];
	for(i=0;i<cityArraySize;i++){
		cityNumArray[i] = i;
	}
	
	//sorting the string array and its corresponding number array alphabetically
	bubbleSortParallelArrays(cityArray,cityNumArray,cityArraySize);
	
	//initialize variables for running the user interface
	dll_t* shortestPathTable;
	dll_t* path;
	int userSelection = -1;

	distance_t* tempDistance;
	int tempInt;

	//running the program until the user indicates to exit
	while(userSelection != 0){
  
		printf("\n\tPlease select and origin city\n");
		printCitySelections(cityArray, cityArraySize,-1);
		scanf("%d",&userSelection);
	
		source = userSelection-1;
		if(userSelection == 0){
			break;
		}

		printf("\n\tPlease select a destination city\n");
		printCitySelections(cityArray, cityArraySize,source);
		scanf("%d",&userSelection);
	
		dest = userSelection-1;

		shortestPathTable = dijkstras(city_graph, cityNumArray[source]);
		path = construct_shortest_path(shortestPathTable, cityNumArray[dest]);
		
		tempDistance = dll_get(path,dll_size(path)-1);
		tempInt = tempDistance->cost;

		printf("\n\t\tORIGIN: %15.10s",cityArray[source]);
		printf("\n\t\tDESTINATION: %10.10s",cityArray[dest]);
		printf("\n\t\tLENGTH: %15d",tempInt);
		printf("\n\t\tPATH CITIES: %10.10s\n",cityArray[source]);
		
		for(i=1;i<dll_size(path);i++){
			tempDistance = dll_get(path,i);
			tempInt = tempDistance->node->data;		
			printf("\t\t%23.10s\n",cityArray[getIntIndex(cityNumArray,cityArraySize,tempInt)]);
		}

		free_shortest_path_table(shortestPathTable);
		free_dll(path);		
	}
	
	free_graph(city_graph);
    
	return EXIT_SUCCESS;
}
