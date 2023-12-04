package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 * @author Christian Lapnow
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    tail = new Node();
    head = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    // TODO Auto-generated method stub
    return size;
  }
  
  @Override
  public boolean add(E item)
  {
      if(size == 0)
      {
          Node node = new Node();
          head.next = node;
          tail.previous = node;
          node.next = tail;
          node.previous = head;

          node.addItem(item);
      }
      else if(tail.previous.count == nodeSize)
      {
          Node node = new Node();
          Node tempNode = tail.previous;
          tempNode.next = node;
          tail.previous = node;
          node.previous = tempNode;
          node.next = tail;

          node.addItem(item);
      }
      else
      {
          tail.previous.addItem(item);
      }
      size++;
      return true;
  }

  @Override
  public void add(int pos, E item)
  {
      NodeInfo nodeInfo = find(pos);
      Node node = nodeInfo.node;
      int offset = nodeInfo.offset;

      if(head.next == tail)
          add(item);
      else if(offset == 0)
      {
          if(node.previous != head && node.previous.count < nodeSize)
          {
              node.previous.addItem(item);
              size++;
          }
          else if (node == tail)
              add(item);
      }
      else if(node.count < nodeSize)
      {
          node.addItem(offset, item);
          size++;
      }
      else
      {
          int split = nodeSize / 2;
          Node nodeFirst = new Node();
          for(int i = 0; i < split; i++)
          {
              nodeFirst.addItem(node.data[split]);
              node.removeItem(split);
          }
          Node nodeSecond = node.next;
          node.next = nodeFirst;
          nodeFirst.previous = node;
          nodeFirst.next = nodeSecond;
          nodeSecond.previous = nodeFirst;
          if(offset <= nodeSize / 2)
              node.addItem(offset, item);
          else
              nodeFirst.addItem((offset - nodeSize / 2), item);
          size++;
      }
  }
  private NodeInfo find(int pos)
  {
      int position = 0;
      Node node = head.next;
      while(node != tail)
      {
          if(node.count + position <= pos)
          {
              position += node.count;
              node = node.next;
              continue;
          }
          NodeInfo nodeInfo = new NodeInfo(node, pos - position);
          return nodeInfo;
      }
      return null;
  }

  @Override
  public E remove(int pos)
  {
      NodeInfo nodeInfo = find(pos);
      Node node = nodeInfo.node;
      int offset = nodeInfo.offset;
      E removed = node.data[offset];

      if (node.next == tail && node.count == 1)
      {
          Node before = node.previous;
          before.next = node.next;
          node.next.previous = before;
      }
      else if(node.next == tail || node.count > nodeSize / 2)
          node.removeItem(offset);
      else
      {
          node.removeItem(offset);
          Node after = node.next;
          if (after.count > nodeSize / 2)
          {
              node.addItem(after.data[0]);
              after.removeItem(0);
          }
          else if (after.count <= nodeSize / 2)
          {
              for (int i = 0; i < after.count; i++)
                  node.addItem(after.data[i]);
              node.next = after.next;
              after.next.previous = node;
              after = null;
          }
      }
      size--;
      return removed;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
      E[] arr = (E[]) new Comparable[size];
      int nodeIndex = 0;
      Node node = head.next;
      while (node != tail)
      {
          for (int i = 0; i < node.count; i++)
          {
              arr[nodeIndex] = node.data[i];
              nodeIndex++;
          }
          node = node.next;
      }
      insertionSort(arr, new Comparator<E>()
      {

          @Override
          public int compare(E o1, E o2) {
              return o1.compareTo(o2);
          }
      });
      head.next = tail;
      tail.previous = head;
      size = 0;
      for (int i = 0; i < arr.length; i++)
      {
          add(arr[i]);
      }
  }

  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
      E[] arr = (E[]) new Comparable[size];
      int nodeIndex = 0;
      Node node = head.next;
      while(node != tail)
      {
          for(int i = 0; i < node.count; i++)
          {
              arr[nodeIndex] = node.data[i];
              nodeIndex++;
          }
          node = node.next;
      }
      bubbleSort(arr);
      head.next = tail;
      tail.previous = head;
      size = 0;
      for(int i = 0; i < arr.length; i++)
      {
          add(arr[i]);
      }
  }
  
  @Override
  public Iterator<E> iterator()
  {
    // TODO Auto-generated method stub
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }
  private class NodeInfo
  {
      public Node node;
      public int offset;

      public NodeInfo(Node node, int offset)
      {
          this.node = node;
          this.offset = offset;
      }
  }

  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	// constants you possibly use ...   
	  
	// instance variables ...
      final int NONE = -1; //if next or previous hasn't been called yet
      final int PREVIOUS = 0; //if previous is called
      final int NEXT = 1; //if next is called
	  private int position;
      private int last; //either NONE, PREVIOUS, or NEXT which saves last action
      public E[] StoutListArray;
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
        position = 0;
        last = NONE;
        makeArray();
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
        position = pos;
        last = NONE;
        makeArray();
    }

    @Override
    public boolean hasNext()
    {
        if(position < size)
            return true;
        else
            return false;
    }

    @Override
    public E next()
    {
        last = NEXT;
        return StoutListArray[position++];
    }

      @Override
      public boolean hasPrevious()
      {
          if(position > 0)
              return true;
          else
              return false;
      }

      @Override
      public E previous() {
        last = PREVIOUS;
        position--;
          return StoutListArray[position];
      }

      @Override
      public int nextIndex() {
          return position;
      }

      @Override
      public int previousIndex() {
          return position - 1;
      }

      @Override
    public void remove()
    {
        if(last == PREVIOUS)
        {
            StoutList.this.remove(position);
            makeArray();
        }
        else if(last == NEXT)
        {
            StoutList.this.remove(position - 1);
            makeArray();
            position = Math.max(position - 1, 0);
        }
        last = NONE;
    }

      @Override
      public void set(E e)
      {
          if(last == PREVIOUS)
          {
              NodeInfo nodeInfo = find(position);
              nodeInfo.node.data[nodeInfo.offset] = e;
              StoutListArray[position] = e;
          }
          else if(last == NEXT)
          {
              NodeInfo nodeInfo1 = find(position - 1);
              nodeInfo1.node.data[nodeInfo1.offset] = e;
              StoutListArray[position - 1] = e;
          }
          else
              throw new IllegalStateException();
      }

      @Override
      public void add(E e)
      {
          StoutList.this.add(position, e);
          position++;
          makeArray();
      }

      public void makeArray()
      {
          StoutListArray = (E[]) new Comparable[size];
          Node node = head.next;
          int arrayIndex = 0;
          while(node != tail)
          {
              for(int i = 0; i < node.count; i++)
              {
                  StoutListArray[arrayIndex] = node.data[i];
                  arrayIndex++;
              }
              node = node.next;
          }
      }

      // Other methods you may want to add or override that could possibly facilitate
    // other operations, for instance, addition, access to the previous element, etc.
    // 
    // ...
    // 
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
      for (int i = 0; i < arr.length - 1; i++)
      {
          E e = arr[i + 1];
          int j = i;
          while (j >= 0 && comp.compare(arr[j], e) > 0)
          {
              arr[j + 1] = arr[j];
              j--;
          }
          arr[j + 1] = e;
      }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
      for(int i = 0; i < arr.length - 1; i++)
      {
          for(int j = 0; j < arr.length - i - 1; j++)
          {
              if(arr[j].compareTo(arr[j + 1]) < 0)
              {
                  E e = arr[j];
                  arr[j] = arr[j + 1];
                  arr[j + 1] = e;
              }
          }
      }
  }
}