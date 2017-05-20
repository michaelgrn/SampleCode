import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 
 * This is a doubly linked list that implements the IndexUnsortedList
 * and uses the LinearNode object. 
 * 
 * @author Michael Green
 *
 * @param <T> the element contained in the double linked list. 
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T>{

	protected LinearNode<T> head;
	protected LinearNode<T> tail;
	private int size;
	private int modCount;
	
	/**
	 * 
	 * Constructor for the doubly linked list. 
	 * Starts as an empty list, user must add
	 * elements later. 
	 * 
	 */
	public IUDoubleLinkedList(){
		head = tail = null;
		size = 0;
		modCount = 0;
	}
	public LinearNode<T> getHead(){
		return head;
	}
	
	@Override
	public void addToFront(T element) {
		
		LinearNode<T> newNode = new LinearNode<T>(element);
		
		if(isEmpty()){
			head = tail = newNode;
		}else{
			newNode.setNext(head);
			head.setPrev(newNode);
			head = newNode;
		}
		
		size++;
		modCount++;
		
	}

	@Override
	public void addToRear(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		
		if(isEmpty()){
			head = tail = newNode;
		}else{
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
		
		size++;
		modCount++;
			
	}

	@Override
	public void add(T element) {
		addToRear(element);
		
	}

	@Override
	public void addAfter(T element, T target) {
		if(isEmpty() || !contains(target)){
			throw new NoSuchElementException();
		}
		
		add(indexOf(target) + 1, element);
				
	}

	@Override
	public void add(int index, T element) {
		
		if(index < 0 || index > size){
			throw new IndexOutOfBoundsException();
		}
		
		if(index == 0){
			
			addToFront(element);
	
		}else if(index == size){
		
			addToRear(element);
		
		}else{	
			
			LinearNode<T> current = head;
			LinearNode<T> newNode = new LinearNode<T>(element);
			
			for(int i  = 0; i < index; i++){
				current = current.getNext();
			}
			
			newNode.setNext(current);
			newNode.setPrev(current.getPrev());
			current.getPrev().setNext(newNode); 
			current.setPrev(newNode);
			
			size++;
			modCount++;
			
		}
		
	}

	@Override
	public T removeFirst() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		
		T retVal = head.getElement();
		
		if(head != tail){
		head = head.getNext();
		head.setPrev(null);
		}else{
			head = tail = null;
			
		}
		size--;
		modCount++;
		
		return retVal;
	}

	@Override
	public T removeLast() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		
		T retVal = tail.getElement();
		if(tail != head){
		tail = tail.getPrev();
		tail.setNext(null);
		}else{
			head = tail = null;
		}
		
		size--;
		modCount++;
		
		return retVal;
	}

	@Override
	public T remove(T element) {
		LinearNode<T> current = head;
		
		if(current == null || !contains(element)){
			throw new NoSuchElementException();
		}
		
		while(current != null && !current.getElement().equals(element)){
			
			current = current.getNext();
		}
		
		T retVal = current.getElement();
		
		if(head == tail){
			head = tail = null;
		}else{
		
			if(current == head){
				head = head.getNext();
				
				if(head != null){
					head.setPrev(null);
				}
			
			}else{
				
				current.getPrev().setNext(current.getNext());
			}
			
			if(current == tail){
				tail = tail.getPrev();
				
				if(tail != null){
					tail.setNext(null);
				}
			
			}else{
				
				current.getNext().setPrev(current.getPrev());
		
			}
		}
	
		size--;
		modCount++;
		
		
		return retVal;
	}

	@Override
	public T remove(int index) {
		
		if ( index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> current = head;
		
		for(int x = 0; x <index; x++){
			current = current.getNext();
		}
		if(current == head){
			head = head.getNext();
		
			if(head != null){
				head.setPrev(null);
			}
		
		}else{
			
			current.getPrev().setNext(current.getNext());
		}
		
		if(current == tail){
			tail = tail.getPrev();
			
			if(tail != null){
				tail.setNext(null);
			}
		
		}else{
			
			current.getNext().setPrev(current.getPrev());
	
		}
		
		size--;
		modCount++;
				
		return current.getElement();
			
	}

	@Override
	public void set(int index, T element) {
		if ( index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> current = head;
		LinearNode<T> newNode = new LinearNode<T>(element);
		for(int x = 0; x <index; x++){
			current = current.getNext();
		}
		
		if(head != tail){
		if(current == head){
			newNode.setNext(head.getNext());
			if(head != null){
				newNode.setPrev(null);
			}
			head = newNode;
		}else{
			
			current.getPrev().setNext(newNode);
			newNode.setNext(current.getNext());
		}
		
		if(current == tail){
			newNode.setPrev(tail.getPrev());
			if(tail != null){
				newNode.setNext(null);
			}
			tail = newNode;
		}else{
			
			current.getNext().setPrev(newNode);
			newNode.setPrev(current.getPrev());
		
		}
		}else{
			head = tail =newNode;
		}
		modCount++;
		
	}

	@Override
	public T get(int index) {
		if ( index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}

		ListIterator<T> lit = listIterator(index);
		
		return lit.next();
	}

	@Override
	public int indexOf(T element) {
		
		ListIterator<T> lit = listIterator();
		for (int i = 0; i < size; i++){
			if(lit.next().equals(element)){
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public T first() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		
		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		LinearNode<T> current = head;
		while(current != null){
			if(current.getElement().equals(target)){
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		
		return (tail == null || head == null || size() == 0);
	}

	@Override
	public int size() {
		
		return size;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		
		str.append("[");
		
		if(size != 0){
			Iterator<T> it = iterator();
			
			while(it.hasNext() == true){
				str.append(it.next().toString()+ ", ");
			}
			str.delete(str.length()-2, str.length());
		}
		str.append("]");
		
		return str.toString();
		
	}

	@Override
	public Iterator<T> iterator() {
		
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		
		return new DLLIterator(startingIndex);
	}

	/**
	 * 
	 * ListIterator for IUDoubleLinkedList. You can have it start 
	 * at the begining of the list or read in a starting index.
	 * 
	 * @author Michael Green
	 *
	 */
	private class DLLIterator implements ListIterator<T>{
		
		private LinearNode<T> nextNode;
		private LinearNode<T> lastReturned;
		private int nextIndex;
		private int iterModCount;
		
		
		
		/**
		 * Constructor for the Double Linked List Iterator, passes in a zero
		 * into the overloaded DLLIterator method, starting the iterator at
		 * the beginning of the list. 
		 */
		public DLLIterator(){
			this(0);
		}
		
		/**
		 * 
		 * Overloaded constructor for the Double Linked List Iterator. 
		 * Allows the user to pass in a starting index for the list and
		 * throws an IndexOutOfBounds exception if that index is not
		 * contained with in the list. 
		 * 
		 * @param startingIndex The index the iterator starts at
		 * @throws IndexOutOfBoundsException if the starting index is not contained in the list
		 */
		public DLLIterator(int startingIndex){
			if(startingIndex < 0 || startingIndex > size){
				throw new IndexOutOfBoundsException();
			}
			lastReturned = null;
			nextNode = head;
			nextIndex = 0;
			iterModCount = modCount;
			for(int i = 0; i < startingIndex; i++){
				nextNode = nextNode.getNext();
				nextIndex++;
			}
			
		}
		
		@Override
		public boolean hasNext() {
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			
			return (nextNode != null);
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			
			T retVal = nextNode.getElement();
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			
			
			return retVal;
		}

		@Override
		public boolean hasPrevious() {
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			
			return (nextNode != head);
		}

		@Override
		public T previous() {
			if(!hasPrevious()){
				throw new NoSuchElementException();
			}
			if(nextNode == null){

				nextNode = tail;
		
			}else{
				
				nextNode = nextNode.getPrev();
			
			}
			
			nextIndex--;
			lastReturned = nextNode;
			
			return nextNode.getElement();
		}

		@Override
		public int nextIndex() {
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			return nextIndex-1;
		}

		@Override
		public void remove() {
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(lastReturned == null){
				throw new IllegalStateException();
			}
			
			if(lastReturned == head){
				head = head.getNext();
				if(head != null){
					head.setPrev(null);
				}
			}else if(lastReturned == tail){
				tail=tail.getPrev();
				if(tail != null){
					tail.setNext(null);
			}
			
			}else{
				lastReturned.getNext().setPrev(lastReturned.getPrev());				
				lastReturned.getPrev().setNext(lastReturned.getNext());
			}
						
			if(nextNode != lastReturned){
				nextIndex--;
			}else{
				nextNode = nextNode.getNext();
			}
			
			size--;
			modCount++;
			iterModCount++;
			lastReturned = null;
		}

		@Override
		public void set(T e) {
			if (iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(lastReturned == null){
				throw new IllegalStateException();
			}
			lastReturned.setElement(e);
			
			iterModCount++;
			modCount++;
		}

		@Override
		public void add(T e) {
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			LinearNode<T> newNode = new LinearNode<T>(e);
			
			if(isEmpty()){
				head = tail = newNode;
			
			}else if(nextNode == head){
				newNode.setNext(head);
				head.setPrev(newNode);
				head = newNode;
								
			}else if(nextNode == null){
				tail.setNext(newNode);
				newNode.setPrev(tail);
				tail = newNode;
			
			}else{
				newNode.setNext(nextNode);
				newNode.setPrev(nextNode.getPrev());
				newNode.getPrev().setNext(newNode);
				nextNode.setPrev(newNode);;
			}
			
			nextIndex++;
			size++;
			modCount++;
			iterModCount++;
			lastReturned = null;
		}
	}
}
