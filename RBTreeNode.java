/**
  * Luokka RBTreeNode on RBTree:n solmutyyppi
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
    * Luokan RBTreeNode konstruktori muodostaa sentinel-solmun ilman tietokenttää
    * @param element Solmun hyötytiedoksi tuleva data
    *
    */
  public RBTreeNode(RBTreeNode<E> parent){
    this.parent = parent;
    sentinel = true;
    color = 1;
  }

  /**
    * Luokan RBTreeNode konstruktori muodostaa parametrina saadusta objektista uuden
    * solmun
    * @param element Solmun hyötytiedoksi tuleva data
    *
    */
  public RBTreeNode(E element){
    this.element = element;
    this.sentinel = false;
  }

  /**
    * Luokan RBTreeNode konstruktori muodostaa parametrina saadusta objektista uuden
    * solmun
    * @param element Solmun hyötytiedoksi tuleva data
    *
    */
  public RBTreeNode(E element,
                        RBTreeNode<E> parent,
                        RBTreeNode<E> leftChild,
                        RBTreeNode<E> rightChild,
                        int color){
    this.element = element;
    this.parent = parent;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
    this.color = color;
    this.sentinel = false;
  }

  public E getElement() {
    return element;
  }
  
  /**
    * Metodi getLeftChild palauttaa tarkasteltavan solmun vasemman lapsen
    * @return Solmun vasen lapsi tai null, jos solmulla ei ole vasenta lasta
    *
    */
  public RBTreeNode<E> getLeftChild(){
    if (leftChild == null) {
      RBTreeNode<E> sentinelNode = new RBTreeNode<E>(this);
      leftChild = sentinelNode;
    }
    return leftChild;
  }
  
  /**
    * Metodi getRightChild palauttaa tarkasteltavan solmun oikean lapsen
    * @return Solmun oikea lapsi tai null, jos solmula ei ole oikeaa lasta
    *
    */
  public RBTreeNode<E> getRightChild(){
    if (rightChild == null) {
      RBTreeNode<E> sentinelNode= new RBTreeNode<E>(this);
      rightChild = sentinelNode;
    }
    return rightChild;
  }
  
  /**
    * Metodi getParent palauttaa tarkasteltavan solmun vanhemman
    * @return Solmun vanhempi tai null, jos solmu on puun juurisolmu
    */
  public RBTreeNode<E> getParent(){
    return parent;
  }

  /**
    * Metodi setElement asettaa parametrina saadun objektin tämän solmun hyötytiedoksi
    * @param element Solmun uusi hyötytieto
    *
    */
  public void setElement(E element) {
    this.element = element;
  }
  
  /**
    * Metodi setLeftChild asettaa parametrina saadun solmun tämän solmun vasemmaksi
    * lapseksi
    * @param node Vasemmaksi lapseksi asetettava solmu
    *
    */
  public void setLeftChild(RBTreeNode<E> node){
    leftChild = node;
    if (node != null)
      node.setParent(this);
  }
  
  /**
    * Metodi setRightChild asettaa parametrina saadun solmun tämän solmun oikeaksi
    * lapseksi
    * @param node Oikeaksi lapseksi asetettava solmu
    *
    */
  public void setRightChild(RBTreeNode<E> node){
    rightChild = node;
    if (node != null)
      node.setParent(this);
  }
  
  /**
    * Metodi setParent asettaa parametrina saadun solmun tämän solmun vanhemmaksi
    * @param node Vanhemmaksi asetettava solmu
    *
    */
  public void setParent(RBTreeNode<E> node){
    parent = node;
  }
  
  /**
    * Metodi setColor asettaa parametrina saadun värin tämän solmun väriksi
    * @param color Solmun väriksi asettava väri ("0|1")
    *
    */
  public void setColor(int color){
    this.color =  color;
  }
  
  /**
    * Metodi getColor palauttaa tarkasteltavan solmun värin
    * @return Solmun väri ("0|1")
    *
    */
  public int getColor(){
    return color;
  }

  public void setSentinel(boolean sentinel) {
    this.sentinel = sentinel;
  }

  public boolean getSentinel() {
    return sentinel;
  }

  public String toString() {
    return String.valueOf(element);
  }
  
} // class