//----------------------------------------------------------------------------
// BinarySearchTree.java          by Dale/Joyce/Weems                Chapter 8
//
// Defines all constructs for a reference-based BST
//----------------------------------------------------------------------------     
//The 6 methods emailed to me by our professor; add, get, contains, inOrder, preOrder, and postOrder are written iteratively below.

public class BinarySearchTree<T extends Comparable<T>> 
             implements BSTInterface<T>

{
  protected BSTNode<T> root;      // reference to the root of this BST

  boolean found;   // used by remove
  
  // for traversals
  protected LinkedUnbndQueue<T> inOrderQueue;    // queue of info
  protected LinkedUnbndQueue<T> preOrderQueue;   // queue of info
  protected LinkedUnbndQueue<T> postOrderQueue;  // queue of info

  public BinarySearchTree()
  // Creates an empty BST object.
  {
    root = null;
  }

  public boolean isEmpty()
  // Returns true if this BST is empty; otherwise, returns false.
  {
    return (root == null);
  }

  private int recSize(BSTNode<T> tree)
  // Returns the number of elements in tree.
  {
    if (tree == null)    
      return 0;
    else
      return recSize(tree.getLeft()) + recSize(tree.getRight()) + 1;
  }

  public int size()
  // Returns the number of elements in this BST.
  {
    return recSize(root);
  }

  public int size2()
  // Returns the number of elements in this BST.
  {
    int count = 0;
    if (root != null)
    {
      LinkedStack<BSTNode<T>> hold = new LinkedStack<BSTNode<T>>();
      BSTNode<T> currNode;
      hold.push(root);
      while (!hold.isEmpty())
      {
        currNode = hold.top();
        hold.pop();
        count++;
        if (currNode.getLeft() != null)
          hold.push(currNode.getLeft());
        if (currNode.getRight() != null)
          hold.push(currNode.getRight());
      }
    }
    return count;
  }

  private boolean recContains(T element, BSTNode<T> tree)
  // Returns true if tree contains an element e such that 
  // e.compareTo(element) == 0; otherwise, returns false.
  {
    {

    BSTNode<T> myRoot = tree;
    while(myRoot != null) {
     
      if(element.compareTo(myRoot.getInfo()) < 0) {
        myRoot = myRoot.left;
      } else if (element.compareTo(myRoot.getInfo()) > 0) {
        myRoot = myRoot.right;
      } else {
        return true;
        }
      
       }
    return false;
      }
    }

  public boolean contains (T element)
  // Returns true if this BST contains an element e such that 
  // e.compareTo(element) == 0; otherwise, returns false.
  {
    return recContains(element, root);
  }
  
  private T recGet(T element, BSTNode<T> tree)
  // Returns an element e from tree such that e.compareTo(element) == 0;
  // if no such element exists, returns null.
  {

    BSTNode<T> myRoot = tree;
    while(myRoot != null) {
     
      if(element.compareTo(myRoot.getInfo()) < 0) {
        myRoot = myRoot.left;
      } else if (element.compareTo(myRoot.getInfo()) > 0) {
        myRoot = myRoot.right;
      } else {
        return myRoot.getInfo();
      }
      
    }
    return null;
  }
  //ABOVE IS THE ITERATIVE VERSION. compareTo checks and either assigns -1, 0, or 1, which is compared to see to go left or right


  public T get(T element)
  // Returns an element e from this BST such that e.compareTo(element) == 0;
  // if no such element exists, returns null.
  {
    return recGet(element, root);
  }

  private BSTNode<T> recAdd(T element, BSTNode<T> tree)
  // Adds element to tree; tree retains its BST property.
  {
    // if (tree == null)
    //   // Addition place found
    //   tree = new BSTNode<T>(element);
    // else if (element.compareTo(tree.getInfo()) <= 0)
    //   tree.setLeft(recAdd(element, tree.getLeft()));    // Add in left subtree
    // else
    //   tree.setRight(recAdd(element, tree.getRight()));   // Add in right subtree
    // return tree;

    BSTNode<T> myNode = new BSTNode<T>(element);
    if(tree == null) {
      return myNode;
    }
    BSTNode<T> parentNode = null, currentNode = tree;
    while(currentNode != null) {
      parentNode = currentNode;
      if(currentNode.info.compareTo(element) <= 0) {
        currentNode = currentNode.right;
      } else {
        currentNode = currentNode.left;
      }
    }
    if(parentNode.info.compareTo(element) <= 0) {
      parentNode.right = myNode;
    } else {
      parentNode.left = myNode;
    }
    return tree;
  }

  public void add (T element)
  // Adds element to this BST. The tree retains its BST property.
  {
    root = recAdd(element, root);
  }

  private T getPredecessor(BSTNode<T> tree)
  // Returns the information held in the rightmost node in tree
  {
    while (tree.getRight() != null)
      tree = tree.getRight();
    return tree.getInfo();
  }

  private BSTNode<T> removeNode(BSTNode<T> tree)
  // Removes the information at the node referenced by tree.
  // The user's data in the node referenced by tree is no
  // longer in the tree.  If tree is a leaf node or has only
  // a non-null child pointer, the node pointed to by tree is
  // removed; otherwise, the users data is replaced by its
  // logical predecessor and the predecessor's node is removed.
  {
    T data;

    if (tree.getLeft() == null)
      return tree.getRight();
    else if (tree.getRight() == null) 
      return tree.getLeft();
    else
    {
      data = getPredecessor(tree.getLeft());
      tree.setInfo(data);
      tree.setLeft(recRemove(data, tree.getLeft()));  
      return tree;
    }
  }

  private BSTNode<T> recRemove(T element, BSTNode<T> tree)
  // Removes an element e from tree such that e.compareTo(element) == 0
  // and returns true; if no such element exists, returns false. 
  {
    if (tree == null)
      found = false;
    else if (element.compareTo(tree.getInfo()) < 0)
      tree.setLeft(recRemove(element, tree.getLeft()));
    else if (element.compareTo(tree.getInfo()) > 0)
      tree.setRight(recRemove(element, tree.getRight()));
    else  
    {
      tree = removeNode(tree);
      found = true;
	 }
    return tree;
  }

  public boolean remove (T element)
  // Removes an element e from this BST such that e.compareTo(element) == 0
  // and returns true; if no such element exists, returns false. 
  {
    root = recRemove(element, root);
    return found;
  }

  private void inOrder(BSTNode<T> tree)
  // Initializes inOrderQueue with tree elements in inOrder order.
  {
    System.out.print("The tree in Inorder is: ");
    if(root == null) {
      return;
    }
    LinkedStack<BSTNode<T>> myStack = new LinkedStack<BSTNode<T>>();
    BSTNode<T> myNode = root;

    while(myNode != null) {
      myStack.push(myNode);
      myNode = myNode.left;
    }

    while(myStack.size() > 0) {
      myNode = myStack.top();
      myStack.pop();
      System.out.print(myNode.info + " ");
      if(myNode.right != null) {
        myNode = myNode.right;

        while(myNode != null) {
          myStack.push(myNode);
          myNode = myNode.left;
        }
      }
    }
  }

  private void preOrder(BSTNode<T> node)
  // Initializes preOrderQueue with tree elements in preOrder order.
  {

    if(node == null) {
      return;
    }
       System.out.print("The tree in Preorder is: ");
       LinkedStack<BSTNode<T>> nodeStack = new LinkedStack<BSTNode<T>>();
       nodeStack.push(root);

       while (nodeStack.isEmpty() == false) {

       BSTNode<T> mynode = nodeStack.top();
       nodeStack.pop();

       while(mynode != null){

       System.out.print(mynode.info + " ");

       if(mynode.right != null) {
         nodeStack.push(mynode.right);
        }
       mynode = mynode.left;
    }   
    }
  }

  private void postOrder(BSTNode<T> tree)
  // Initializes postOrderQueue with tree elements in postOrder order.
  {
    LinkedStack<BSTNode<T>> myStack = new LinkedStack<BSTNode<T>>();
    BSTNode<T> current = tree;
    myStack.push(current);
    System.out.print("The tree in Postorder is ");
    while(!myStack.isEmpty()) {
      BSTNode<T> next = myStack.top();
      if(next.left == null && next.right == null || next.left == tree || next.right == tree) {
        myStack.pop();
        System.out.print(next.info + " ");
        tree = next;
      } else {
        if(next.right != null) {
          myStack.push(next.right);
        }
        if(next.left != null) {
          myStack.push(next.left);
        }
      }
    }
  }

  public int reset(int orderType)
  // Initializes current position for an iteration through this BST
  // in orderType order. Returns current number of nodes in the BST.
  {
    int numNodes = size();

    if (orderType == INORDER)
    {
      inOrderQueue = new LinkedUnbndQueue<T>();
      inOrder(root);
    }
    else
    if (orderType == PREORDER)
    {
      preOrderQueue = new LinkedUnbndQueue<T>();
      preOrder(root);
    }
    if (orderType == POSTORDER)
    {
      postOrderQueue = new LinkedUnbndQueue<T>();
      postOrder(root);
    }
    return numNodes;
  }

  public T getNext (int orderType)
  // Preconditions: The BST is not empty
  //                The BST has been reset for orderType
  //                The BST has not been modified since the most recent reset
  //                The end of orderType iteration has not been reached
  //
  // Returns the element at the current position on this BST for orderType
  // and advances the value of the current position based on the orderType. 
  {
    if (orderType == INORDER)
      return inOrderQueue.dequeue();
    else
    if (orderType == PREORDER)
      return preOrderQueue.dequeue();
    else
    if (orderType == POSTORDER)
      return postOrderQueue.dequeue();
    else return null;

  }
}
