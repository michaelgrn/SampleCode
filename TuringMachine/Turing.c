#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFSIZE 100



int main(int argc, char **argv){
	int count = 0; //counter
	int countState = 0; //counter
	int countTransition = 0; //counter
	int language = 1; //number of symbols in the language including blank
	int numStates = 0; //used to determine number of states in language
	int tapeSize = 100; //used for dynamically growing the tape
	int oldSize; //used for dynamically growing the tape
	int* tape = NULL;//(int *)malloc(tapeSize * sizeof(int)); // stock tape, can be dynamically grown
	char *inputString; // input string
	char*** TM = NULL;// unitialized turing machine


	if(argc == 2){

		FILE *fp = fopen(argv[1], "r");
		char buff[BUFSIZE]; /* a buffer to hold what you read in */

		/* This while loop reads in the fext file */

		while(fgets(buff, BUFSIZE - 1, fp) != NULL){

			//reads the first line of text file to get number of states

			if(count == 0){
				numStates = atoi(buff)-1;
				count++;
			}

			//reads the second line of the text file to get number of symbols

			else if(count ==1){
				language += atoi(buff);
				count++;
				TM = (char ***)malloc(numStates*sizeof(char**));
				for (int i = 0; i< numStates; i++) {
					TM[i] = (char **) malloc(language*sizeof(char *));
					for (int j = 0; j < language; j++) {
						TM[i][j] = (char *)malloc(10*sizeof(char));
					}
				}


				//reads the following lines to get transitions for each state

			}else if(count > 1 && count < (2+(numStates*language))){
				strcpy(TM[countState][countTransition],buff);
				countTransition++;
				count++;
				if(countTransition == language){
					countTransition = 0;
					countState++;
				}

				//reads the last line of the file to get the input string

			}else if(count == (2+(numStates*language))){
				inputString = (char *)malloc(strlen(buff) * sizeof(char));
				strcpy(inputString, buff);
			}
		}

	}else{

		printf("Error");
		return 0;

	}


	//populates the tape with blanks
	tape = (int *)malloc(tapeSize * sizeof(int));
	for(int i=0; i < 101; i++){
		tape[i] = '0';
	}



	//populates the tape with input inputString
	for(int i = 0; i < (strlen(inputString)-1); i++){

		if(inputString[i] != '\0'){
			tape[i*2] = (int)(inputString[i]);
		}

		//makes sure that tape is large enough to contain input string.
		//if not, dynamically grows it

		if(i == tapeSize/2){
			oldSize = tapeSize;
			tapeSize = tapeSize * 2;
			tape = (int *)realloc(tape, tapeSize * sizeof(int));
			for(int j =oldSize; j < tapeSize; j++){
				tape[j] = '0';
			}
		}
	}


	int currentState = 0;
	long visitedEntry = 0;
	int headPosition = 0;
	int input;
	int leftMost=0;
	int rightMost=0;
	char currentTransition[3];

  //reads the tape. Ends if in end state or past tape limit
	while(currentState != numStates && visitedEntry < 10000000){

		input = (tape[headPosition]);
		input = input - 48; //converts from char to int

		currentTransition[0] = TM[currentState][input][0];
		currentTransition[1] = TM[currentState][input][2];
		currentTransition[2] = TM[currentState][input][4];

		if(currentTransition[1] == 0){
			currentTransition[1] = 48;
		}
		tape[headPosition] = (TM[currentState][input][2]);
		currentState = currentTransition[0] - 48;

		//moves the head left or right depending on what side of the tape the
		//head is on.
		if(headPosition == 0){
			if(currentTransition[2] == 'L'){
				headPosition = 1;
				if(leftMost < headPosition){
					leftMost = headPosition;
				}
			}else{
				headPosition = 2;
				if(rightMost < headPosition){
					rightMost = headPosition;
				}
			}
		}else if(headPosition == 1){
			if(currentTransition[2] == 'L'){
				headPosition = 3;
				if(leftMost < headPosition){
					leftMost = headPosition;
				}
			}else{
				headPosition = 0;
				if(rightMost < headPosition){
					rightMost = headPosition;
				}
			}
		}else if((headPosition%2) == 0){
			if(currentTransition[2] == 'L'){
				headPosition = headPosition - 2;
			}else{
				headPosition = headPosition + 2;
				if(rightMost < headPosition){
					rightMost = headPosition;
				}
			}
		}else if((headPosition%2) == 1){
			if(currentTransition[2] == 'L'){
				headPosition = headPosition + 2;
				if(leftMost < headPosition){
					leftMost = headPosition;
				}
			}else{
				headPosition = headPosition - 2;
			}
		}
		//dynamically grows tape size
		if(headPosition > ((tapeSize/2)-4)){
			oldSize = tapeSize;
			tapeSize = tapeSize * 2;
			tape = (int *)realloc(tape, tapeSize * sizeof(int));
			for(int j =oldSize; j < tapeSize; j++){
				tape[j] = '0';
			}
		}
	}
	//on end state, print out tape, left most first
  if(currentState == numStates){
		int numInList = 0;
		int sum = 0;
		for(int i = tapeSize-1; i > -1; i = i-2){
			if((tape[i]-48) != 0 && tape[i] !=0){
				printf("%d", (tape[i]-48));
				sum += (tape[i]-48);
			}
		}

		//on end state, print out tape, right side
		for(int i = 0; i <= tapeSize; i = i+2){
			if((tape[i]-48) != 0 && tape[i] !=0){
				printf("%d", (tape[i]-48));
				sum += (tape[i]-48);
			}
		}
	}
	
	//frees memory
	free(inputString);
	free(tape);

	for (int i = 0; i< numStates; i++) {

		for (int j = 0; j < language; j++) {
			free(TM[i][j]);
		}
		free(TM[i]);
	}
	free(TM);
	printf("\n");
	return 0;

}
