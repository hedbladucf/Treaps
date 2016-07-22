/* Oscar Hedblad
 * PID: o3415424
 * COP 3503, Fall 2013
 * Assignment #3 - Treaps.java
 * 
 * DESCRIPTION: An implementation of a treap - a binary tree combined with a heap that 
 * works with probabilistic data structure and implements tree rotations. This program will
 * calculate the size and height, as well as keeping track of the various traversals of a 
 * treap with respect to its data and priority.
 * */

// Importing packages
import java.io.*;
import java.util.*;
import java.util.HashSet;

// Creation of my node-class. This class simply creates a node object. 
class Node<AnyType extends Comparable<AnyType>>{
		
		AnyType data;
		int priority;
		
		Node<AnyType> left, right;
		// Constructor
		Node(AnyType data, int priority){
			this.data = data;
			this.priority = priority;
		}
}
/* The main treap class. This generic class is basically the core of the program
 * containing all necessary methods such as add(), remove() and a few more. */
public class Treap<AnyType extends Comparable<AnyType>> {
	
	private Node<AnyType> root;
	// Creates a HashSet to keep track of the priorities in the treap
	private HashSet<Integer> prio = new HashSet<Integer>();
/* Add function that uses a math random and multiplies it by the integer max value 
	in order to generate a random priority for a node */
	public void add(AnyType data){
		int priority;
		priority = (int)(Math.random()*(Integer.MAX_VALUE) + 1);
/* Checks if priority is already used and keep generating priority until a 
	unique one is found */
		while(prio.contains(priority)){
			priority = (int)(Math.random()*(Integer.MAX_VALUE) + 1);
		}
		// Adds priority to hashset
		prio.add(priority);
		root = add(root, data, priority);
	}
	//  Second add function. Works for given priorites
	public void add(AnyType data, int priority){
		root = add(root, data, priority);
	}
	
/* Add function. This generic function inserts node into right spot of treap, 
 * does left as well as right rotations */
	private Node<AnyType> add(Node<AnyType> root, AnyType data, int priority){
		if(root == null){
			// Return newly made node
			return new Node<AnyType>(data, priority);
		}
/* If the value is less than 0 root.data gets set to right child */
		else if(0 > data.compareTo(root.data)){
			root.left = add(root.left, data, priority);
/* If priority is greater than its left childs priority, rotate down using right rotation */
			if(root.priority > root.left.priority){
				root = rightRotation(root);
			}
		}
/* If the value is greater than 0 root.data gets set to left child */		
		else if(0 < data.compareTo(root.data)){
			root.right = add(root.right, data, priority);
/* If priority is greater than its right childs priority, rotate down using left rotation */
			if(root.priority > root.right.priority){
				root = leftRotation(root);
			}
		}
		// Else-statement to make sure duplicate functions does not get inserted
		else{
			
		}
		return root;
	}
	// Gets root and returns it
	public Node<AnyType> getRoot(){
		return root;
	}
	// Performs left rotation of nodes
private Node<AnyType> leftRotation(Node<AnyType> root){
		// Creates node to return as new root
		Node<AnyType> rightC = root.right;
		root.right = rightC.left;
		rightC.left = root;
		return rightC;
	}
   //Performs right rotation of nodes
	private Node<AnyType> rightRotation(Node<AnyType> root){
		// Creates node to return as new root
		Node<AnyType> leftC = root.left;
		root.left = leftC.right;
		leftC.right = root;
		return leftC;
		
	}
/* Remove function. Takes in data and rotates the targeted node enough so that it ends up
 * being a leaf node and then deletes it */
	private Node<AnyType> remove(Node<AnyType> root, AnyType data){
		if(root != null){
			// Recursively calls function until targeted node is found
			if(0 < data.compareTo(root.data)){
				root.right = remove(root.right, data);
			}
			else if(0 > data.compareTo(root.data)){
				root.left = remove(root.left, data);
			}
/* Conditional statements that covers cases where targeted node is already found */
			else{
				// Checks to see if left- and right child exists
				if(root.left == null && root.right == null){
					root = null;
				}
				// Checks to see if left child exists
				else if(root.left == null){
					root = leftRotation(root);
				}
				// Checks to see if right child exists
				else if(root.right == null){
					root = rightRotation(root);
				}
				// If right priority is less that the left priority, rotate left
				else if(root.right.priority < root.left.priority){
					root = leftRotation(root);
				}
				// If right priority is less that the left priority, rotate right
				else if(root.right.priority > root.left.priority){
					root = rightRotation(root);
				}
				// Recursive statement that keeps going as long as node is not a leaf node
				if(root != null){
					root = remove(root, data);
				}
			}
			
		}
		return root;
	}
	// Removes node containing data from treap
	public void remove(AnyType data){
		root = remove(root, data);
	}
	// True/false function that checks if data is in the treap
	public boolean contains(AnyType data)
    {
        return contains(root, data);
    }
/* A boolean (true/false) method that checks if the value being passed is present */
    private boolean contains(Node<AnyType> root, AnyType data)
    {
        if (root == null)
        {
            return false;
        }
        // If data < root.data then is calls the recursive contains-function again, 
        // but with left child.
        else if (data.compareTo(root.data) < 0)
        {
            return contains(root.left, data);
        }
        // If data > root.data then is calls the recursive contains-function again, 
        // but with right child.
        else if (data.compareTo(root.data) > 0)
        {
            return contains(root.right, data);
        }
        else
        {
            return true;
        }
    }
/* Size function. This function finds the size of the treap and returns its value, being 
 * the number of nodes in treap */
	private int size(Node<AnyType> root){
		if(root == null){
			return 0;
		}
		else{
			return size(root.left) + size(root.right) + 1;
		}
	}
	// Returns size of treap
	public int size(){
		return size(root);
	}
/* Height function. This function finds the height of the treap and returns its value, being 
 * the longest path from root to leaf node */
	private int height(Node<AnyType> root){
		if(root == null){
			return -1;
		}
		// Get left- and right height
		int leftH = height(root.left);
		int rightH = height(root.right);
		// Conditional if-else statement that determines which path is longer
		if(leftH > rightH){
			return leftH + 1;
		}
		else{
			return rightH + 1;
		}
	}
	// Returns height of treap
		public int height(){
			return height(root);
		}
		
/* My difficulty rating method. I found the previous assignment, GenericBST, quite
* challenging; because of my prior lack in knowledge of BST's. So this assignment 
* required a lot of thinking and conceptual understanding but has rewarded 
* me greatly in the process! */
	public static double difficultyRating(){
		return 4.5;
	}
	
/* My hours-spent function. As stated above it wasn't an easy assignment to complete,
 * which yielded many hours spent working on both the conceptual understanding of treaps,
 * as well as the assignment itself. */
	public static double hoursSpent(){
		return 16.0;
	}

}