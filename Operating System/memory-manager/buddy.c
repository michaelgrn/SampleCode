
/**
 * Implementation of a memory allocator based on the Buddy System.
 * See Knuth Art of Computer Programming, vol. 1, page 442.
 * Each available block starts with a header that consists
 * of a tag (free/reserved), kval (size of block 2^kval), next
 * and previous pointers. Each reserved block has the tag and kval
 * field only. All allocations are done in powers of two. All requests
 * are rounded up to the next power of two.
 *
 * @author  TBD
 *
 */

#include "buddy.h"
#include <errno.h>
int initialized = 0;

/* the header for an available block */
struct block_header {
	short tag;
	short kval;
	struct block_header *next;
	struct block_header *prev;
};
const int RESERVED = 0;
const int FREE = 1;
const int UNUSED = -1;


/* supports memory upto 2^(MAX_KVAL-1) (or 32 GB) in size */
#define  MAX_KVAL  35
 int newMax = MAX_KVAL;

/* default memory allocation is 512MB */
const size_t DEFAULT_MAX_MEM_SIZE = 512*1024*1024;
const int findLogTwo(size_t size);
const int powTwo(int);
const void *removeNode(int);
const struct block_header* split(struct block_header *toSplit);
const int addNode(struct block_header *toAdd);
const int removeSpecificNode(struct block_header *buddy);

/* A static structure stores the table of pointers to the lists in the buddy system.  */
struct pool {
	void *start; // pointer to the start of the memory pool
	int lgsize;  // log2 of size
	size_t size; // size of the pool, same as 2 ^ lgsize
	/* the table of pointers to the buddy system lists */
	struct block_header avail[MAX_KVAL];
} pool;



const int buddy_init(size_t size) {
    void* brk;
    int logSize = 0;
    int x = 0;
    size_t newSize = 0;

  //  fprintf(stderr, "%d\n", newSize);
    if(size < 1 << 7){
	  newSize = 1 <<7;
	  logSize = 7;
	  brk = sbrk(newSize);
	  initialized = TRUE;
    }else if(size >= (1L<<35)){
	  newSize = 1L << 35;
	  logSize = 35;
	  brk = sbrk(newSize);
	  initialized = TRUE;
    }else{
	  logSize = findLogTwo(size);
	  newSize = powTwo(logSize);
	  brk = sbrk(newSize);
	  initialized = TRUE;
    }
    pool.start = brk;
    pool.lgsize = logSize;
    newMax = logSize;
    pool.size = newSize;

    struct block_header* local = brk;
    local->next = local;
    local->prev = local;
    local->kval = newMax;
    pool.avail[newMax].tag =1;
    pool.avail[newMax].kval = newMax;
    pool.avail[newMax].next = pool.avail[newMax].prev = local;

    //printf("Location of local %p and brk %p\n", local, brk);


    for(x = 0; x < newMax-1; x++){
       pool.avail[x].next = pool.avail[x].prev = NULL;
    }

    return TRUE;
}


void *malloc(size_t size)
{
  if(size == 0){
     size = 1;
  }
  int blockSearch = newMax;
  struct block_header *removedBlock;
  int logSize = 0;
  int currentBlock= -1;
  if(initialized == FALSE){
      buddy_init(DEFAULT_MAX_MEM_SIZE);
  }
  size_t toSearchFor = size + sizeof(struct block_header);
  logSize = findLogTwo(toSearchFor);
  //newSize = powTwo(logSize);

  if(logSize > blockSearch){
      return NULL;
  }

  while(blockSearch >= logSize){

    if(pool.avail[blockSearch].next !=NULL){
	currentBlock = blockSearch;
	//printf("Here");
	//printf("Current block kval = %d", pool.avail[blockSearch].next.kval);
    }
    blockSearch--;
  }

  if(currentBlock == -1){
      errno = ENOMEM;
      return NULL;
  }else{
    //remove from list
  
    removedBlock = (struct block_header*)removeNode(currentBlock);
  //  printf("%p\n", removedBlock);
  }
  //void* thisLocale = removedBlock;
 // printf("%p\n", thisLocale);
  //Split Required?
  while(removedBlock->kval !=logSize){
 //   printf("split\n");
	removedBlock = (struct block_header*)split(removedBlock);

  }
  //split

   
   void *location  = (removedBlock) +1;// + sizeof(struct block_header));
   return location;

}


void *calloc(size_t nmemb, size_t size)
{
	size_t toCalloc = nmemb * size;
	//size_t kval = findLogTwo(toCalloc + sizeof(struct block_header));
	void* ptr = malloc(toCalloc);
	//call malloc with nmemb * size
	if(ptr != NULL)
	    memset(ptr, 0, toCalloc);
	return ptr;
}

void *realloc(void *ptr, size_t size)
{
 /*
  void* toReturn;
  if(ptr == NULL){
    toReturn = buddyMalloc(size);
    return toReturn;
  }
  
  if(size == 0){
    buddyFree(ptr);
    return NULL;
  }
  
  struct block_header *toRealloc = ptr;
  toRealloc = toRealloc - 1;
  //fprintf(stderr,"here\n");

  //check to see if Realloc is necessary
  //fprintf(stderr,"here\n");
  //fprintf(stderr,"%ld size\n", size);
  toReturn = buddyMalloc(size);
  if(toReturn == NULL){
    fprintf(stderr,"Not enuf space");
    return NULL;
  }
  //check to see if Malloc worked
  //fprintf(stderr,"here\n");
  struct block_header *ofReturned = toReturn;
  ofReturned = ofReturned - 1;

  fprintf(stderr,"kval powtwo of old block %ld, kval powtwo of new block %ld\n", toRealloc->kval, ofReturned->kval);
*/
  
  void* toReturn = malloc(size);
  free(ptr);
  return toReturn;
}


void free(void *ptr)
{
  //printf("%p address to free\n", ptr);
  int loop = 1;
  struct block_header *buddy = NULL;
  struct block_header *toFree = ptr;
  toFree = toFree - 1;
  while(loop){
      if(toFree->kval == newMax){
    	toFree->tag = 1;
	addNode(toFree);
	return;
  }
  void* address = (void *)(toFree) - (long int)(pool.start);
  
  if((long int)(address) % powTwo(toFree->kval+1) == 0){
      address = (powTwo(toFree->kval)) + address + (long int)(pool.start);
  }else{
      address = (address - (powTwo(toFree->kval))) + (long int)(pool.start);
  }
 // fprintf(stderr,"%p address to free, tag %d , kval %d, next %p, prev %p\n", toFree, toFree->tag, toFree->kval, toFree->next, toFree->prev);
  buddy = address;
 // fprintf(stderr,"%p address to buddy, tag %d , kval %d, next %p, prev %p\n", buddy, buddy->tag, buddy->kval, buddy->next, buddy->prev);
  
  

  if(buddy->tag == 0){
       	toFree->tag = 1;
	addNode(toFree);
	return;
  }
  
  if(buddy->tag == 1 && buddy->kval != toFree->kval){
        toFree->tag = 1;
	addNode(toFree);
	return;
  }
  struct block_header *next = buddy->next;
  struct block_header *prev = buddy->prev;
  removeSpecificNode(buddy);
  
 // fprintf(stderr, "%p buddy, %p free\n", buddy, toFree);
  if((long int)buddy<(long int)toFree){
    int kval = toFree->kval;
    int tag = toFree->tag;

    fprintf(stderr,"switch\n");
    toFree = buddy;
    toFree->kval = kval;
    toFree->tag = tag;
    toFree->next = next;
    toFree->prev = prev;
   
  }
    
    
  toFree->kval = toFree->kval+1;
  }
}


void printBuddyLists()
{
}

const int removeSpecificNode(struct block_header *buddy){
     int kval = buddy->kval;
     if(pool.avail[kval].next == buddy && pool.avail[kval].prev == buddy){
       //fprintf(stderr,"1\n");
            pool.avail[kval].next = NULL;
	    pool.avail[kval].prev = NULL;
     }else if(pool.avail[kval].next == buddy && pool.avail[kval].prev != buddy){
       // fprintf(stderr,"here2\n");
       pool.avail[kval].next = buddy->next;
       pool.avail[kval].next->prev =  pool.avail[kval].prev;
       pool.avail[kval].prev->next = pool.avail[kval].next;
      
     }else if(pool.avail[kval].prev == buddy && pool.avail[kval].next != buddy){
       // fprintf(stderr,"here3\n");
       pool.avail[kval].prev = buddy->prev;
       pool.avail[kval].prev->next = pool.avail[kval].next;
       pool.avail[kval].next->prev = pool.avail[kval].prev;
     }else{
       buddy->prev->next = buddy->next;
       // fprintf(stderr,"here4\n");
       buddy->next->prev = buddy->prev;
     }
     buddy->tag = 0;
     
     return 1;
  
}

const int findLogTwo(size_t size){
  //for init round up

  signed int y = size;
  signed int toXor = size;
  signed int z = 1;
  signed int a = 0;
  int x = 0;
  while(y != 1){

    y = y >> 1L;
    x+=1;
    /*fprintf(stderr,"y = %d x = %d \n",y, x);*/
  }
  sleep(1);
  for(a = 0; a < x; a++){
    z *= 2;
  }
 /* printf("x = %d z = %d toXor = %d\n", x, z, toXor);*/
  //if xor toXor and z == zero don't round, otherwise round
  if((toXor ^ z) != 0L){
    x = x + 1;
  }

return x;

}

const int powTwo(int toPow){
    signed int toReturn;
    toReturn = (1L<<toPow);
  //  printf("%d\n",toReturn);
    return toReturn;
}

const void* removeNode(int toRemove){
  //printf("Here address toremove %p\n", pool.avail[toRemove].next);
  struct block_header *toReturn = pool.avail[toRemove].next;

  if(pool.avail[toRemove].next == pool.avail[toRemove].prev){
     pool.avail[toRemove].next = NULL;
     pool.avail[toRemove].prev = NULL;
  }else{
    pool.avail[toRemove].next = toReturn->next;
    toReturn->prev->next = toReturn->next;
    toReturn->next->prev = toReturn->prev;
  }

  toReturn->tag = 0;
  //printf("Here %p header in toremove\n", toReturn);
  return (void *)toReturn;

}

const struct block_header* split(struct block_header *toSplit){
  int j = toSplit->kval - 1;
  //printf("%p address to split and kval%d\n", toSplit, j);
  //printf("%p address to split and kval%d\n", toSplit, j);
//create left
  struct block_header* left = toSplit;
  left->kval = j;
  left->next = NULL;
  left->prev = NULL;
  left->tag = 0;
  void *location = ((void*)toSplit) + (1L<<j);
  struct block_header* right = location;
  //printf("%p\n",right);
  right->kval = j;
  right->tag = 1;

  addNode(right);
  //fprintf(stderr,"%p left %p right and kval %d\n", left, right, right->kval);
  return left;


}

const int addNode(struct block_header *toAdd){
  int location = toAdd->kval;
 // printf("%p to add\n", toAdd);
  if(pool.avail[location].next == NULL){
    toAdd->next = toAdd;
    toAdd->prev = toAdd;
    pool.avail[location].next = toAdd;
    pool.avail[location].prev = toAdd;

  }else if(pool.avail[location].next == pool.avail[location].prev){

    pool.avail[location].prev = toAdd;
    pool.avail[location].next->next = toAdd;
    pool.avail[location].next->prev = toAdd;
    toAdd->next = pool.avail[location].next;
    toAdd->prev = pool.avail[location].next;
  }else{
      pool.avail[location].prev->next = toAdd;
      pool.avail[location].next->prev = toAdd;
      toAdd->next = pool.avail[location].next;
      toAdd->prev = pool.avail[location].prev;
      pool.avail[location].prev = toAdd;

  }
  return 1;

}


