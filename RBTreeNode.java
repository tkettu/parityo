/**
  * The class RBTreeNode is an abstract Node implementation for the RBTree class
  * @author Juhani Seppälä
  * 
  */
public class RBTreeNode<E> {
  private E element;
  private RBTreeNode<E> parent;
  private RBTreeNode<E> leftChild;
  private RBTreeNode<E> rightChild;
  private int color; // 0 = r, 1 = b
  private boolean sentinel;

    /**
    * The empty constructor for the class RBTreeNode constructs a new sentinel-node with the color set to black
    * and the sentinel-flag set to true
    * @param element Solmun hyötytiedoksi tuleva data
    * @author Juhani Seppälä
    *
    */
  public RBTreeNode() {
    sentinel = true;
    color = 1;
  }

  /**
    * The constructor for the class RBTreeNode, given an element, constructs a new node with the parameter-given
    * element, the sentinel-flag set to false and the default color of red
    * @param element Solmun hyötytiedoksi tuleva data
    * @author Juhani Seppälä
    *
    */
  public RBTreeNode(E element) {
    this.element = element;
    this.sentinel = false;
  }

  /**
    * Luokan RBTreeNode konstruktori muodostaa parametrina saadusta objektista uuden
    * solmun 
    * The constructor for the class RBTreeNode, given an element, parent, left child node, right child node and the color, constructs a new node
    * with all the parameter-given values as its attributes and the sentinel flag set to false
    * @author Juhani Seppälä
    * @param element Solmun hyötytiedoksi tuleva data
    *
    */
  public RBTreeNode(E element,
                        RBTreeNode<E> parent,
                        RBTreeNode<E> leftChild,
                        RBTreeNode<E> rightChild,
                        int color) {
    this.element = element;
    this.parent = parent;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
    this.color = color;
    this.sentinel = false;
  }

  /**
    * The method getElement returns the element of this node
    * @return The element of this node
    * @author Juhani Seppälä
    */
  public E getElement() {
    return element;
  }
  
  /**
    * The method getLeftChild returns the left child of this node
    * @return The left child of this node, or null, if no left child exists
    * @author Juhani Seppälä
    *
    */
  public RBTreeNode<E> getLeftChild() {
    return leftChild;
  }
  
  /**
    * The method getRightChild returns the left child of this node
    * @return The right child of this node, or null, if no right chil exists
    * @author Juhani Seppälä
    *
    */
  public RBTreeNode<E> getRightChild() {
    return rightChild;
  }
  
  /**
    * The method parent return the parent of this node
    * @return The parent of this node, or null, if no parent node exists
    * @author Juhani Sepäälä
    */
  public RBTreeNode<E> getParent() {
    return parent;
  }

  /**
    * The method setElement sets the parameter-given element as the element of this node
    * @param The element to be set as the element of this node
    * @author Juhani Seppälä
    *
    */
  public void setElement(E element) {
    this.element = element;
  }
  
  /**
    * The method setLeftChild set the parameter-given node as the left child of this node
    * @param node The node to be set as the left child of this node
    *
    */
  public void setLeftChild(RBTreeNode<E> node) {
    leftChild = node;
  }
  
  /**
    * The method setRightChild sets the parameter-given node as the right child of this node
    * @param node The node to be set as the right child of this node
    *
    */
  public void setRightChild(RBTreeNode<E> node) {
    rightChild = node;
  }
  
  /**
    * The method setParent sets the parameter-given node as the parent of this node
    * @param node The node to be added as the parent of this node
    *
    */
  public void setParent(RBTreeNode<E> node) {
    parent = node;
  }
  
  /**
    * The method setColor sets the parameter-given color as the color of this node
    * @param color The color to be set as the color of this node (0 = red, 1 = black)
    *
    */
  public void setColor(int color) {
    this.color =  color;
  }
  
  /**
    * The method getColor returns the color of this node
    * @return The color of this node (0 = red, 1 = black)
    *
    */
  public int getColor() {
    return color;
  }

  /**
    * The method setSentinel sets the parameter-given sentinel-flaq as the sentinel-flag of this node
    * @author Juhani Seppälä
    */
  public void setSentinel(boolean sentinel) {
    this.sentinel = sentinel;
  }

  /**
    * The method 
    */
  public boolean getSentinel() {
    return sentinel;
  }

  public String toString() {
    if (element != null)
      return String.valueOf(element);
    else
      return "-";
  }
  
} // class