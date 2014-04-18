/**
  * Luokka BRTree
  * @author Tero Kettunen
  * @author Juhani Seppälä
  * 
  */
public class RBTree<E extends Comparable<E>> {
  
  private int size = 0;
  private RBTreeNode<E> root;
  

  /**
    * Luokan BRTree konstruktori luo uuden tyhjän puun
    *
    */
  public RBTree(){
  }

  /**
    * Metodi isEmpty palauttaa tiedon siitä, onko tutkittava puu tyhjä
    * @author Juhani Seppälä
    * @return Palauttaa true, jos puu on tyhjä
    */
  public boolean isEmpty() {
	 return true;	//Kääntöä varten (TK)
  }
  
  /**
    * Metodi setRoot asettaa parametrina saadun solmun puun juureksi
    * @author Juhani Seppälä
    * @param node Juureksi lisättävä solmu
    *
    */
  public void setRoot(RBTreeNode<E> node){
  }
  
  /**
   * Metodi getRoot palauttaa puun juuren
   * @author
   * @return puun juuri
   */
  public RBTreeNode<E> getRoot(){
	 return root;	
  }
  
   
  /**
    * Metodi add muodostaa parametrina annetusta objektista uuden solmun ja lisää
    * sen puuhun ja kutsuu solmulle tasapainotusmetodia RBTreeAddFixup
    * @author Juhani Seppälä
    * @param data Puuhun lisättävä objekti
    *
    */
  public boolean add(E data) {
    RBTreeNode<E> n = this.getRoot();
    if (n == null) {
      RBTreeNode<E> node = new RBTreeNode<E>(data);
      node.setColor(0);
      root = node;
      return true;
    }
    while (n != null) {
      E nE = n.getElement();
      if (nE.equals(data))
        return false;
      else if (nE.compareTo(data) == -1) {
        if (n.getRightChild() == null) {
          RBTreeNode<E> node = new RBTreeNode<E>(data);
          node.setColor(0);
          n.setRightChild(node);
          RBTreeAddFixup(node);
          return true;
        }
        else
          n = n.getRightChild();
      }
      else {
        if (n.getLeftChild() == null) {
          RBTreeNode<E> node = new RBTreeNode<E>(data);
          node.setColor(0);
          n.setLeftChild(node);
          RBTreeAddFixup(node);
          return true;
        }
        else
          n = n.getLeftChild();
      }
    }
    return false;
  }
  
  /**
    * Metodi remove poistaa parametrina annetun objektin puusta ja kutsuu tasapainotusmetodia
    * RBTreeRemoveFixup
    * @author Tero Kettunen
    * @param data Puusta poistettava objekti
    *
    */
  public void remove(E data){
  }
  
  /**
    * Metodi search etsii ja palauttaa puusta parametrina annetun objektin
    * @author Juhani Seppälä
    * @param data Puusta etsittävä objekti
    *
    */
  public E search(E data){
	 return data;	//Kääntöä varten (TK)
  }

  /** 
    * Metodi union muodostaa yhdisteen kutsuttavasta ja parametrina saadusta puusta
    * @author Juhani Seppälä
    * @param t yhdisteeseen tuleva puu
    * @return Kahden puun joukko-opillista yhdistettä kuvaava uusi puu
    *
    */
  public RBTree<E> union(RBTree<E> t) {
	 return t;	//Kääntöä varten (TK)
  }
  
  /** 
    * Metodi intersection muodostaa leikkauksen kutsuttavasta ja parametrina saadusta puusta
    * @author Tero Kettunen
    * @param t leikkaukseen tuleva puu
    * @return Kahden puun joukko-opillista leikkausta kuvaava uusi puu
    *
    */
  public RBTree<E> intersection(RBTree<E> t) {
	 return t;	//Kääntöä varten (TK)
  }

  /** 
    * Metodi difference muodostaa erotuksen kutsuttavasta ja parametrina saadusta puusta
    * @author Juhani Seppälä
    * @param t erotukseen tuleva puu
    * @return Kahden puun joukko-opillista erotusta kuvaava uusi puu
    *
    */  
  public RBTree<E> difference(RBTree<E> t) {
	 return t;	//Kääntöä varten (TK)
  }
  
  /** 
    * Metodi size palauttaa kokonaislukuna tarkasteltavan puun solmujen lukumäärän
    * @author Tero Kettunen
    * @return Nykyinen puun solmujen lukumäärä
    *
    */
  public int size(){
    return size;
  }

  /**
    * RBTreeRemoveFixup tarkastaa solmujen värit sekä tasapainottaa puun poisto-operaation tapauksessa
    * kutsuen joko metodia leftRotate tai rightRotate (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node solmu, joka saattaa rikkoa puun tasapainoa
    */
  private void RBTreeRemoveFixup(RBTreeNode<E> node) {
  }

  /**
    * Metodi RBTreeAddFixup tarkastaa solmujen värit sekä tasapainottaa puun lisäysoperaation tapauksessa
    * kutsuen joko metodia leftRotate tai rightRotate (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node Solmu, johon korjaustoimi kohdistuu
    *
    */
  private void RBTreeAddFixup(RBTreeNode<E> node) {
  }

  /**
    * Metodi leftRotate suorittaa parametrina annetun solmun perusteella puulle vasemman käännön
    * @author Tero Kettunen
    * @param node Solmu, josta alkaen vasen kääntö suoritetaan
    *
    */
  private void leftRotate(RBTreeNode<E> node) {
	RBTreeNode<E> y = node.getRightChild();  //otetaan talteen noden oikea lapsi y
	node.setRightChild(y.getLeftChild());	// y:n vasen alipuu noden oikeaksi alipuuksi
	y.getLeftChild().setParent(node);
	y.setParent(node.getParent());			// noden vanhempi y:n vanhemmaksi
	
	if(node.getParent()==null){				// Jos node on juuri
	  setRoot(y);							
	}else if(node==node.getParent().getLeftChild()){ //Jos node on vasen lapsi
	  node.getParent().setLeftChild(y);
	}else{											//Jos se on oikea lapsi
	  node.getParent().setRightChild(y);
	}
	
	y.setLeftChild(node);
	node.setParent(y);
  }

  /**
    * Metodi rightRotate suorittaa parametrina annetun solmun perusteella puulle oikean käännön
    * @author Tero Kettunen
    * @param node Solmu, josta alkaen oikea kääntö suoritetaan
    *
    */
  private void rightRotate(RBTreeNode<E> node) {
    RBTreeNode<E> y = node.getLeftChild();  //otetaan talteen noden vasen lapsi y
	node.setLeftChild(y.getRightChild());	// y:n oikea alipuu noden vasemmaksi alipuuksi
	y.getRightChild().setParent(node);
	y.setParent(node.getParent());			// noden vanhempi y:n vanhemmaksi
	
	if(node.getParent()==null){				// Jos node on juuri
	  setRoot(y);							
	}else if(node==node.getParent().getRightChild()){ //Jos node on oikea lapsi
	  node.getParent().setRightChild(y);
	}else{											//Jos se on vasen lapsi
	  node.getParent().setLeftChild(y);
	}
	
	y.setRightChild(node);
	node.setParent(y);
  }
  
}
