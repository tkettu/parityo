/**
  * Luokka RBTreeNode on RBTree:n solmutyyppi
  * @author Juhani Seppälä
  * 
  */
public class RBTreeNode<E extends Comparable<E>> {
  private E element;
  private RBTreeNode<E> parent;
  private RBTreeNode<E> leftChild;
  private RBTreeNode<E> rightChild;
  private String color;


  /**
    * Luokan RBTreeNode konstruktori muodostaa parametrina saadusta objektista uuden
    * solmun
    * @param element Solmun hyötytiedoksi tuleva data
    *
    */
  public void RBTReeNode(E element){
  }

  /**
    * Luokan RBTreeNode konstruktori muodostaa parametrina saadusta objektista uuden
    * solmun
    * @param element Solmun hyötytiedoksi tuleva data
    *
    */
  public void RBTReeNode(E element,
                        RBTreeNode<E> parent,
                        RBTreeNode<E> leftChild,
                        RBTreeNode<E> rightChild,
                        String color){
  }
  
  /**
    * Metodi getLeftChild palauttaa tarkasteltavan solmun vasemman lapsen
    * @return Solmun vasen lapsi tai null, jos solmulla ei ole vasenta lasta
    *
    */
  public RBTreeNode<E> getLeftChild(){
    return leftChild;
  }
  
  /**
    * Metodi getRightChild palauttaa tarkasteltavan solmun oikean lapsen
    * @return Solmun oikea lapsi tai null, jos solmula ei ole oikeaa lasta
    *
    */
  public RBTreeNode<E> getRightChild(){
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
  }
  
  /**
    * Metodi setRightChild asettaa parametrina saadun solmun tämän solmun oikeaksi
    * lapseksi
    * @param node Oikeaksi lapseksi asetettava solmu
    *
    */
  public void setRightChild(RBTreeNode<E> node){
    rightChild = node;
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
    * @param color Solmun väriksi asettava väri ("r|b")
    *
    */
  public void setColor(String color){
    this.color =  color;
  }
  
  /**
    * Metodi getColor palauttaa tarkasteltavan solmun värin
    * @return Solmun väri ("r|b")
    *
    */
  public String getColor(){
    return color;
  }
  
}