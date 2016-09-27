/**
  * The class RBTree is a Red-Black Tree implementation based on Cormen, Leiserson, Rivest (CLR)
  * @author Tero Kettunen
  * @author Juhani Seppälä
  */
import java.util.ArrayList;


public class RBTree<E extends Comparable<E>> {
  
  private int size;
  private RBTreeNode<E> root;
  
  /**
    * The default constructor for the class RBTree creates an empty tree with no root and zero size
    */
  public RBTree(){

  }

  /**
    * The method isEmpty returns whether the tree is empty
    * @author Juhani Seppälä
    * @return True if this tree is empty, 0 otherwise
    */
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
    * The method size returns the size of this tree
    * @author Tero Kettunen
    * @return Nykyinen puun solmujen lukumäärä
    */
  public int size(){
    return size;
  }
  
  /** 
    * The method setSize sets the size of this tree
    * @author Tero Kettunen
    * @param newSize The new size of this tree
    */
  public void setSize(int newSize){
    size = newSize;
  }

  /**
   * The method getRoot returns the root node of this tree
   * @author Tero Kettunen
   * @return The root of this tree
   */
  public RBTreeNode<E> getRoot(){
    return root;  
  }
  
  /**
    * The method setRoot sets the root of this tree to be the parameter-given node
    * @author Juhani Seppälä
    * @param node The node to be set as the root of this tree
    */
  public void setRoot(RBTreeNode<E> node){
    root = node;
  }

  /**
    * The method height calculates and returns the height of the parameter-given node
    * @param node The node of interest
    * @return The height of the parameter-given node
    * @author Juhani Seppälä
    */
  public int getHeight(RBTreeNode<E> node) {
    if (node.getSentinel())
      return  -1;
    return 1 + Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));
  }
  
  /**
    * The method add inserts the parameter-given element into the tree if the element is not already in the tree
    * @author Juhani Seppälä
    * @param data The element to be inserted into the tree
    * @return Node that was added and constructed from the paratemer-given element, or null, if the element was a duplicate
    */
  public RBTreeNode<E> add(E data) {

    RBTreeNode<E> y = null;
    RBTreeNode<E> x = root;
    RBTreeNode<E> z = new RBTreeNode<E>(data);

    while (x != null && !x.getSentinel()) {
      y = x;
      if (z.getElement().compareTo(y.getElement()) < 0)
        x = y.getLeftChild();
      else if (z.getElement().compareTo(y.getElement()) > 0)
        x = y.getRightChild();
      else
        return null;
    }
    z.setParent(y);
    if (y == null)
      setRoot(z);
    else {
      if (z.getElement().compareTo(y.getElement()) < 0)
        y.setLeftChild(z);
      else
        y.setRightChild(z);
    }
    z.setColor(0);
    RBTreeNode<E> sentinelLeft = new RBTreeNode<E>();
    RBTreeNode<E> sentinelRight = new RBTreeNode<E>();
    sentinelLeft.setParent(z);
    sentinelRight.setParent(z);
    z.setLeftChild(sentinelLeft);
    z.setRightChild(sentinelRight);
    size++;
    addFixup(z);
    return z;
  }

  /**
  * The method RBTreeAddFixup repairs the Red-black Tree property violations in the tree starting from the parameter-given node
  * @author Juhani Seppälä
  * @param node Node from which the repair procedure starts
  */
  private void addFixup(RBTreeNode<E> z) {
    while (z.getParent() != null && z.getParent().getParent() != null && z.getParent().getColor() == 0) {
      if (z.getParent() == z.getParent().getParent().getLeftChild()) {
        RBTreeNode<E> y = z.getParent().getParent().getRightChild();
        if (y.getColor() == 0) {
          z.getParent().setColor(1);
          y.setColor(1);
          z.getParent().getParent().setColor(0);
          z = z.getParent().getParent();
        } else {
          if (z == z.getParent().getRightChild()) {
            z = z.getParent();
            leftRotate(z);
          }
          z.getParent().setColor(1);
          z.getParent().getParent().setColor(0);
          rightRotate(z.getParent().getParent());
        }
      } else if (z.getParent() == z.getParent().getParent().getRightChild()) {
        RBTreeNode<E> y = z.getParent().getParent().getLeftChild();
        if (y.getColor() == 0) {
          z.getParent().setColor(1);
          y.setColor(1);
          z.getParent().getParent().setColor(0);
          z = z.getParent().getParent();
        } else {
          if (z == z.getParent().getLeftChild()) {
              z = z.getParent();
              rightRotate(z);
          }
          z.getParent().setColor(1);
          z.getParent().getParent().setColor(0);
          leftRotate(z.getParent().getParent());
        }
      }
    }
    root.setColor(1);
  }
  
  /**
    * The method remove removes the parameter-given element from the tree if it exists in the tree
    * @author Tero Kettunen
    * @author Juhani Seppälä
    * @param data The element to be removed from this tree
    * @return The node representing the parameter-given element, or null, if the element was not in this tree
    */
  public RBTreeNode<E> remove(E data){
  
    RBTreeNode<E> z = search(data);
    RBTreeNode<E> y;    
    RBTreeNode<E> x;
    
    if (z == null)
      return null;

    if (z.getLeftChild().getSentinel() || z.getRightChild().getSentinel())
      y = z;
    else {
      y = successor(z);
    }

    if (y.getLeftChild() != null && !y.getLeftChild().getSentinel()) 
      x = y.getLeftChild();
    else
      x = y.getRightChild();
    
    x.setParent(y.getParent());

    if (y.getParent() == null)
      root = x;
    else {
      if (y == y.getParent().getLeftChild())
        y.getParent().setLeftChild(x);
      else
        y.getParent().setRightChild(x);
    }

    if (y != z)
      z.setElement(y.getElement());
    if (y.getColor() == 1)
      removeFixup(x);
    return y;
  }

  /**
    * The method removeFixup repairs the Red-Black Tree property violations in the tree starting from the parameter-given node
    * in the case of the remove operation
    * @author Juhani Seppälä
    * @param x The node that may violate the Red-Black Tree properties of this tree 
    */
  private void removeFixup(RBTreeNode<E> x) {
    while (x  != root && x.getColor() == 1) {
        if (x == x.getParent().getLeftChild()) {
            RBTreeNode<E> w = x.getParent().getRightChild();
            if (w.getColor() == 0) {
                w.setColor(1);
                x.getParent().setColor(0);
                leftRotate(x.getParent());
                w = x.getParent().getRightChild();
            }
            if (w.getLeftChild().getColor() == 1 && w.getRightChild().getColor() == 1) {
                w.setColor(0);
                x = x.getParent();
            } else  {
                if (w.getRightChild().getColor() == 1) {
                    w.getLeftChild().setColor(1);
                    w.setColor(0);
                    rightRotate(w);
                    w = x.getParent().getRightChild();
                }
                w.setColor(x.getParent().getColor());
                x.getParent().setColor(1);
                w.getRightChild().setColor(1);
                leftRotate(x.getParent());
                x = root;
            }
        } else {
            RBTreeNode<E> w = x.getParent().getLeftChild();
            if (w.getColor() == 0) {
                w.setColor(1);
                x.getParent().setColor(0);
                rightRotate(x.getParent());
                w = x.getParent().getLeftChild();
            }
            if (w.getRightChild().getColor() == 1 && w.getLeftChild().getColor() == 1) {
                w.setColor(0);
                x = x.getParent();
            } else  {
                if (w.getLeftChild().getColor() == 1) {
                    w.getRightChild().setColor(1);
                    w.setColor(0);
                    leftRotate(w);
                    w = x.getParent().getLeftChild();
                }
                w.setColor(x.getParent().getColor());
                x.getParent().setColor(1);
                w.getLeftChild().setColor(1);
                rightRotate(x.getParent());
                x = root;
            }
        }
    } // while
    x.setColor(1);
  } // method

  /**
    * The method leftRotate performs the left rorate operation on the subtree denoted by by the parameter-given node
    * @author Tero Kettunen
    * @param node The node from which the left rotate operation is to be performed
    */
  private void leftRotate(RBTreeNode<E> x) {
    RBTreeNode<E> y = x.getRightChild();  //otetaan talteen noden oikea lapsi y
    x.setRightChild(y.getLeftChild()); // y:n vasen alipuu noden oikeaksi alipuuksi
    if (y.getLeftChild() != null)
      y.getLeftChild().setParent(x);

    y.setParent(x.getParent());      // noden vanhempi y:n vanhemmaksi
    
    if (x.getParent() == null) {       // Jos node on juuri
      setRoot(y);         
    } else {
      if (x == x.getParent().getLeftChild())
        x.getParent().setLeftChild(y);
      else
        x.getParent().setRightChild(y);
    }
    y.setLeftChild(x);
    x.setParent(y);
  }

  /**
    * The method rightRotate performs the right rorate operation on the subtree denoted by by the parameter-given node
    * @author Tero Kettunen
    * @param node The node from which the left rotate operation is to be performed
    */
  private void rightRotate(RBTreeNode<E> x) {
    RBTreeNode<E> y = x.getLeftChild();  //otetaan talteen noden vasen lapsi y
    x.setLeftChild(y.getRightChild()); // y:n oikea alipuu noden vasemmaksi alipuuksi
    if (y.getRightChild() != null)
      y.getRightChild().setParent(x);

    y.setParent(x.getParent());      // noden vanhempi y:n vanhemmaksi
    
    if (x.getParent() == null) {       // Jos node on juuri
      setRoot(y);             
    } else {
      if (x == x.getParent().getRightChild())
        x.getParent().setRightChild(y);
      else
        x.getParent().setLeftChild(y);
    }
    y.setRightChild(x);
    x.setParent(y);
  }

  /**
    * The method search searches and returns the node representing the parameter-given element from the tree
    * @author Juhani Seppälä
    * @param data The element to be searched from this tree
    * @return The node representing the parameter-given element, or null if the element was not in this tree
    */
  public RBTreeNode<E> search(E data) {
    if (size == 0)
      return null;
    RBTreeNode<E> n = root;
    while (n != null && !n.getSentinel()) {
      if (data.compareTo(n.getElement()) == 0)
        return n;
      if (data.compareTo(n.getElement()) < 0)
        n = n.getLeftChild();
      else
        n = n.getRightChild();
    }
    return null;
  }

  /**
    * The method successor returns the next largest element in this tree after the parameter-given node
    * @author Juhani Seppälä
    * @param node The node from which the successor is to be searched
    * @return The next largest node after the parameter-given node
    */
  public RBTreeNode<E> successor(RBTreeNode<E> x) {
    if (x.getRightChild() != null && !x.getRightChild().getSentinel())
      return min(x.getRightChild());
    else {
      RBTreeNode<E> y = x.getParent();
      while (y != null && x == y.getRightChild()) {
        x = y;
        y = x.getParent();
      }
      return y;
    }
  }

  /**
    * The method predecessor returns the next smallest element in this tree after the parameter-given node
    * @author Juhani Seppälä
    * @param node The node from which the predecessor is to be searched
    * @return The predecessor node of the parameter-given node, or null, if no predecessor exists
    */
  public RBTreeNode<E> predecessor(RBTreeNode<E> node) {
    if (node.getLeftChild() != null) {
      node = node.getLeftChild();
      while (node.getRightChild() != null) {
        node = node.getRightChild();
      }
      return node;
    }
    else {
      while (node.getParent() != null) {
        if (node.getParent().getRightChild() != null
          && node.getParent().getRightChild() == node) {
          return node.getParent();
        }
        else if (node.getParent().getParent() != null
          && node.getParent().getParent().getRightChild() != null
          && node.getParent().getParent().getRightChild() == node.getParent()) {
          return node.getParent().getParent();
        }
        else {
          node = node.getParent();
        }
      }
    }
    return null;
  }

  /**
    * The method min searches and returns the smallest node in the subtree denoted by the parameter-given node
    * @author Juhani Seppälä
    * @param node root node of the subtree from which the smallest element is to be searched
    * @return The smallest element within the subtree denoted by parameter-given subtree root
    */
  public RBTreeNode<E> min(RBTreeNode<E> node) {
    while (node.getLeftChild() != null && !node.getLeftChild().getSentinel())
      node = node.getLeftChild();
    return node;
  }

  /** 
    * The method union forms and returns an union from this tree and the parameter-given tree
    * @author Juhani Seppälä
    * @param t The tree with which the union is to be formed
    * @return The tree representing the union of the elements of this tree and the parameter-given tree
    */
  public RBTree<E> union(RBTree<E> t) {
    if (t.isEmpty())
      return this;

    ArrayList<E> list1 = getOrderedListData();
    ArrayList<E> list2 = t.getOrderedListData();
    RBTree<E> tree = treeFromList(listUnion(list1, list2));
    return tree;
  }
  
  /** 
    * The method intersection forms and returns the intersection from this tree and the parameter-given tree
    * @author Tero Kettunen
    * @author Juhani Seppälä
    * @param t The tree with which the intersection is to be formed
    * @return The tree representing the intersection of the elements of this tree and the parameter-given tree
    */
  public RBTree<E> intersection(RBTree<E> t) {
    RBTree<E> newTree = new RBTree<E>();

    if (!isEmpty() && !t.isEmpty()) {
      ArrayList<E> list1 = getOrderedListData();
      ArrayList<E> list2 = t.getOrderedListData();
      newTree = treeFromList(listIntersect(list1, list2));
    }
    return newTree;
  }

  /** 
    * The method difference forms and returns the difference from this tree and the parameter-given tree
    * @author Juhani Seppälä
    * @param t The tree with which the difference is to be formed
    * @return The tree representing the difference of the elements of this tree and the parameter-given tree
    */  
  public RBTree<E> difference(RBTree<E> t) {
    if (t.isEmpty())
      return this;

    ArrayList<E> list1 = getOrderedListData();
    ArrayList<E> list2 = t.getOrderedListData();
    RBTree<E> tree = treeFromList(listDifference(list1, list2));
    return tree;
  }

  /**
    * The method getOrderedListData forms and returns the elements stored in this tree in an ordered list
    * @author Juhani Seppälä
    */
  public ArrayList<E> getOrderedListData() {
    ArrayList<E> data = new ArrayList<E>();
    RBTreeNode<E> node = root;
    if (node != null)
      data = inorderAddBranch(node, data);
    
    return data;
  }

    /**
     * The method inorderAddBranch adds the elements of the subtree denoted by the parameter-given node into the parameter-given list (in-order)
     * @param node The root of the subtree to be added to the list
     * @param data The list to be appended with the elements of the subtree denoted by the parameter-given node
     * @return The elements of the subtree denoted by the parameter-given node (for convenience)
     * @author Juhani Seppälä
     */
  private ArrayList<E> inorderAddBranch(RBTreeNode<E> node, ArrayList<E> data) {
    if (node.getLeftChild() != null && !node.getLeftChild().getSentinel())
      inorderAddBranch(node.getLeftChild(), data);

    data.add(node.getElement());

    if (node.getRightChild() != null && !node.getRightChild().getSentinel())
      inorderAddBranch(node.getRightChild(), data);

    return data;
  }

  /**
    * The method treeFromList forms and returns a balanced RBTree from the elements in the parameter-given list
    * @param list The list of the elements to be formed into a new tree
    * @return A balanced RBTree representation of the parameter-given list of elements
    * @author Juhani Seppälä
    */
  public RBTree<E> treeFromList(ArrayList<E> list) {
    RBTree<E> tree = new RBTree<E>();
    RBTreeNode<E> root = treeFromListNode(list, 0, list.size() - 1);

    tree.setRoot(root);
    tree.setSize(list.size());

    colorBST(tree);

    return tree;
  }

  /**
    * The method treeFromListNode is the recursion of the method treeFromList
    * @param list The list of elements to be formed into a new tree
    * @param start The beginning index used to calculate the middle point for this recursion step
    * @param end The end index used to calculate the middle point for this recursion step
    * @return The root of the new all-black, balanced RBTree
    * @author Juhani Seppälä
    */
  private RBTreeNode<E> treeFromListNode(ArrayList<E> list, int start, int end) {
    if (start > end) 
      return null;

    int pivot = start + (end - start) / 2;

    RBTreeNode<E> node = new RBTreeNode<E>(list.get(pivot));

    node.setColor(1);
    node.setLeftChild(treeFromListNode(list, start, pivot - 1));
    node.setRightChild(treeFromListNode(list, pivot + 1, end));

    if (node.getLeftChild() == null) {
      RBTreeNode<E> sentinelLeft = new RBTreeNode<E>();
      sentinelLeft.setParent(node);
      node.setLeftChild(sentinelLeft);
    } else {
      node.getLeftChild().setParent(node);
    }
    
    if (node.getRightChild() == null) {
      RBTreeNode<E> sentinelRight = new RBTreeNode<E>();
      sentinelRight.setParent(node);
      node.setRightChild(sentinelRight);
    } else {
      node.getRightChild().setParent(node);
    }
    return node;
  }

  /**
    * The method colorBST sets the color of the deepest nodes in the balanced RBTree to red
    * @param tree The tree to be colored
    * @author Juhani Seppälä
    */
  private void colorBST(RBTree<E> tree) {
    if (tree.size()  == 0)
      return;

    RBTreeNode<E> root = tree.getRoot();
    int height = getHeight(root);
    colorBSTNode(root, height, 0);
  }

  /**
    * The method coorBSTNode is the recursion of the method colorBST
    * @param node The node to be processed for this recursion step
    * @param height The precalculated height of this tree
    * @param level The current depth of recursion
    * @author Juhani Seppälä
    */
  private void colorBSTNode(RBTreeNode<E> node, int height, int level) {
    if (node == null || node.getSentinel())
      return;

    if (level == height && node.getParent() != null) {
      node.setColor(0);
    }

    colorBSTNode(node.getLeftChild(), height, level + 1);
    colorBSTNode(node.getRightChild(), height, level + 1);
  }

  /**
    * The method listUnion forms and returns the union of the parameter-given lists in a new list
    * @param list1 The first list used to construct the union
    * @param list2 The second list used to construct the union
    * @return The intersection of the elements in the parameter-given lists
    * @author Juhani Seppälä
    */
  private ArrayList<E> listUnion(ArrayList<E> list1, ArrayList<E> list2) {
    int i = 0, j = 0;
    ArrayList<E> newList = new ArrayList<E>();

    while (i < list1.size() && j < list2.size()) {
      if (list1.get(i).compareTo(list2.get(j)) < 0) {
        if (newList.size() == 0 || !newList.get(newList.size() - 1).equals(list1.get(i)))
          newList.add(list1.get(i));
        i++;
      } else {
        if (newList.size() == 0 || !newList.get(newList.size() - 1).equals(list2.get(j)))
          newList.add(list2.get(j));
        j++;
      }
    }
    return newList;
  }

  /**
    * The mehod listIntersect forms and returns the intersection of the parameter-given lists in a new list
    * @param list1 The first list used to construct the interserction
    * @param list2 The second list used to construct the intersection
    * @return The intersection of the elements in the parameter-given lists
    * @author Juhani Seppälä
    */
  private ArrayList<E> listIntersect(ArrayList<E> list1, ArrayList<E> list2) {
    int i = 0, j = 0;
    ArrayList<E> newList = new ArrayList<E>();

    while (i < list1.size() && j < list2.size()) {
      if (list1.get(i).compareTo(list2.get(j)) < 0)
        i++;
      else if (list1.get(i).compareTo(list2.get(j)) > 0)
        j++;
      else {
        newList.add(list1.get(i));
        i++;
        j++;
      }
    }
    return newList;
  }

  /**
    * The method listDifference modifies the first parameter-given list to be the difference of the parameter-given lists
    * @param list1 The first list used to construct the difference
    * @param list2 The second list used to construct the difference
    * @return The first parameter-given list representing the difference of the parameter-given lists (for convenience)
    * @author Juhani Seppälä
    */
  private ArrayList<E> listDifference(ArrayList<E> list1, ArrayList<E> list2) {
    int i = 0, j = 0;
    while (i < list1.size() && j < list2.size()) {
      if (list1.get(i).compareTo(list2.get(j)) < 0) {
        i++;
      }
      else if (list1.get(i).compareTo(list2.get(j)) > 0) {
        j++;
      }
      else {
        list1.set(i, null);
        i++;
        j++;
      }
    }
    ArrayList<E> newList = new ArrayList<E>();
    for (i = 0; i < list1.size(); i++) {
      if (list1.get(i) != null)
        newList.add(list1.get(i));
    }
    return newList;
  }
} // class