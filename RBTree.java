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
    return size == 0;
  }
  
  /**
    * Metodi setRoot asettaa parametrina saadun solmun puun juureksi
    * @author Juhani Seppälä
    * @param node Juureksi lisättävä solmu
    *
    */
  public void setRoot(RBTreeNode<E> node){
    root = node;
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
    * @return true, jos lisättiin; muuten false
    *
    */
  public boolean add(E data) {
    RBTreeNode<E> n = this.getRoot();
    if (n == null) {
      RBTreeNode<E> node = new RBTreeNode<E>(data);
      node.setColor(0);
      root = node;
      size++;
      return true;
    }
    while (n != null) {
      E nE = n.getElement();
      if (nE.equals(data))
        return false;
      else if (nE.compareTo(data) < 0) {
        if (n.getRightChild() == null) {
          RBTreeNode<E> node = new RBTreeNode<E>(data);
          node.setColor(0);
          n.setRightChild(node);
          RBTreeAddFixup(node);
          size++;
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
          size++;
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
    * @return true, jos data poistettiin; muuten false
    */
  public boolean remove(E data){
  
    RBTreeNode<E> rn = search(data);  //etsitään oikea poistettava solmu, jos on olemassa
    RBTreeNode<E> y;  //apusolmu    
    RBTreeNode<E> x;  //apusolmu
    
    if(rn==null)
      return false;
    
    if (rn.getLeftChild()==null || rn.getRightChild()==null) {
      y = rn;
    } else {
      y = successor(rn);
    }
    
    if (y.getLeftChild() != null) {
      x = y.getLeftChild();
    } else {
      x = y.getRightChild();
    }
    
    x.setParent(y.getParent());
    
    if (y.getParent() == null) {
      setRoot(x);
    } else if(y == y.getParent().getLeftChild()) {
      y.getParent().setLeftChild(x);
    } else {
      y.getParent().setRightChild(x);
    }
    
    if (y != rn) {
      rn.setElement(y.getElement());
    }
    
    if (y.getColor()==1) {    //jos y on musta
      RBTreeRemoveFixup(x);
    }
    size--;
    return true;  
  }
  
  /**
    * Metodi search etsii ja palauttaa puusta etsittävän solmun
    * @author Juhani Seppälä
    * @param data Puusta etsittävä objekti
    * @return etsittävä solmu tai null, jos solmua ei löytynyt
    *
    */
  public RBTreeNode<E> search(E data) {
    if (size == 0)
      return null;
    RBTreeNode<E> n = this.getRoot();
    while (n != null) {
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
    * Metodi successor palauttaa parametrina annetun solmun seuraajan puussa
    * (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node solmu, jonka seuraajaa etsitään
    * @return parametrina annetun solmun seuraaja puussa
    *
    */
  public RBTreeNode<E> successor(RBTreeNode<E> node) {
    if (node.getRightChild() != null)
      return min(node.getRightChild());
    else {
      RBTreeNode<E> n = node.getParent();
      while (n != null && node == n.getRightChild()) {
        node = n;
        n = n.getParent();
      }
      return n;
    }
  }

  /**
    * Metodi min hakee minimiä parametrina annetusta solmusta alkaen
    * (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node solmu (tai alipuun juuri), josta alkaen etsitään minimiä
    * @return (ali)puun minimialkio
    *
    */
  public RBTreeNode<E> min(RBTreeNode<E> node) {
    while (node.getLeftChild() != null)
      node = node.getLeftChild();
    return node;
  }

  /** 
    * Metodi union muodostaa yhdisteen kutsuttavasta ja parametrina saadusta puusta
    * @author Juhani Seppälä
    * @param t yhdisteeseen tuleva puu
    * @return Kahden puun joukko-opillista yhdistettä kuvaava uusi puu
    *
    */
  public RBTree<E> union(RBTree<E> t) {
    return t; //Kääntöä varten (TK)
  }
  
  /** 
    * Metodi intersection muodostaa leikkauksen kutsuttavasta ja parametrina saadusta puusta
    * @author Tero Kettunen
    * @param t leikkaukseen tuleva puu
    * @return Kahden puun joukko-opillista leikkausta kuvaava uusi puu
    *
    */
  public RBTree<E> intersection(RBTree<E> t) {
    return t; //Kääntöä varten (TK)
  }

  /** 
    * Metodi difference muodostaa erotuksen kutsuttavasta ja parametrina saadusta puusta
    * @author Juhani Seppälä
    * @param t erotukseen tuleva puu
    * @return Kahden puun joukko-opillista erotusta kuvaava uusi puu
    *
    */  
  public RBTree<E> difference(RBTree<E> t) {
    return t; //Kääntöä varten (TK)
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
    * Metodi setSize asettaa puun solmujen lukumäärän
    * @author Tero Kettunen
    * @param newSize puun solmujen uusi lukumäärä
    *
    */

  public void setSize(int newSize){
    this.size = newSize;
  }
  /**
    * RBTreeRemoveFixup tarkastaa solmujen värit sekä tasapainottaa puun poisto-operaation tapauksessa
    * kutsuen kääntöoperaatioiden metodeja leftRotate sekä rightRotate (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node solmu, joka saattaa rikkoa puun tasapainoa
    */
  private void RBTreeRemoveFixup(RBTreeNode<E> node) {
    while (node != root && node.getParent() != null && node.getColor() == 1) {
      if (node == node.getParent().getLeftChild()) {
        RBTreeNode<E> w = node.getParent().getRightChild();
        if (w.getColor() == 0) {
          w.setColor(1);
          node.getParent().setColor(0);
          leftRotate(node.getParent());
          w = node.getParent().getRightChild();
        }
        if (w.getLeftChild().getColor() == 1 && w.getRightChild().getColor() == 1) {
          w.setColor(0);
          node = node.getParent();
        }
        else if (w.getRightChild().getColor() == 1) {
          w.getLeftChild().setColor(1);
          w.setColor(0);
          rightRotate(w);
          w = node.getParent().getRightChild();
        }
        w.setColor(node.getParent().getColor());
        node.getParent().setColor(1);
        w.getRightChild().setColor(1);
        leftRotate(node);
        node = root;
      } else {
        RBTreeNode<E> w = node.getParent().getLeftChild();
        if (w.getColor() == 0) {
          w.setColor(1);
          node.getParent().setColor(0);
          leftRotate(node.getParent());
          w = node.getParent().getLeftChild();
        }
        if (w.getRightChild().getColor() == 1 && w.getLeftChild().getColor() == 1) {
          w.setColor(0);
          node = node.getParent();
        }
        else if (w.getLeftChild().getColor() == 1) {
          w.getRightChild().setColor(1);
          w.setColor(0);
          rightRotate(w);
          w = node.getParent().getLeftChild();
        }
        w.setColor(node.getParent().getColor());
        node.getParent().setColor(1);
        w.getLeftChild().setColor(1);
        leftRotate(node);
        node = root;  
      }
    } // while
  } // method

  /**
    * Metodi RBTreeAddFixup tarkastaa solmujen värit sekä tasapainottaa puun lisäysoperaation tapauksessa
    * kutsuen kääntöoperaatioiden metodeja leftRotate sekä rightRotate (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node Solmu, johon korjaustoimi kohdistuu
    *
    */
  private void RBTreeAddFixup(RBTreeNode<E> node) {
    while (node != root && node.getParent() != null && node.getParent().getColor() == 0) {
      if (node.getParent() == node.getParent().getParent().getLeftChild()) {
        RBTreeNode<E> y = node.getParent().getParent().getRightChild();
        if (y.getColor() == 0) {
          node.getParent().setColor(1);
          y.setColor(1);
          node.getParent().getParent().setColor(0);
          node = node.getParent().getParent();
        } else if (node == node.getParent().getRightChild()) {
          node = node.getParent();
          leftRotate(node);
        }
        node.getParent().setColor(1);
        node.getParent().getParent().setColor(0);
        rightRotate(node.getParent().getParent());
      } else {
        RBTreeNode<E> y = node.getParent().getParent().getLeftChild();
        if (y.getColor() == 0) {
          node.getParent().setColor(1);
          y.setColor(1);
          node.getParent().getParent().setColor(0);
          node = node.getParent().getParent();
        }
        else if (node == node.getParent().getLeftChild()) {
          node = node.getParent();
          leftRotate(node);
        }
        node.getParent().setColor(1);
        node.getParent().getParent().setColor(0);
        rightRotate(node.getParent().getParent());
      }
    }
    root.setColor(1);
  }

  /**
    * Metodi leftRotate suorittaa parametrina annetun solmun perusteella puulle vasemman käännön
    * @author Tero Kettunen
    * @param node Solmu, josta alkaen vasen kääntö suoritetaan
    *
    */
  private void leftRotate(RBTreeNode<E> node) {
    RBTreeNode<E> y = node.getRightChild();  //otetaan talteen noden oikea lapsi y
    node.setRightChild(y.getLeftChild()); // y:n vasen alipuu noden oikeaksi alipuuksi
    y.getLeftChild().setParent(node);
    y.setParent(node.getParent());      // noden vanhempi y:n vanhemmaksi
    
    if (node.getParent()==null) {       // Jos node on juuri
      setRoot(y);             
    } else if(node==node.getParent().getLeftChild()) { //Jos node on vasen lapsi
      node.getParent().setLeftChild(y);
    } else {                      //Jos se on oikea lapsi
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
    node.setLeftChild(y.getRightChild()); // y:n oikea alipuu noden vasemmaksi alipuuksi
    y.getRightChild().setParent(node);
    y.setParent(node.getParent());      // noden vanhempi y:n vanhemmaksi
    
    if (node.getParent()==null) {       // Jos node on juuri
      setRoot(y);             
    } else if (node==node.getParent().getRightChild()) { //Jos node on oikea lapsi
      node.getParent().setRightChild(y);
    } else {                      //Jos se on vasen lapsi
      node.getParent().setLeftChild(y);
    }
    
    y.setRightChild(node);
    node.setParent(y);
  }
  
} // class
