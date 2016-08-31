/**
  * Luokka RBTree on Puna-musta -puun ADT-toteutus
  * @author Tero Kettunen
  * @author Juhani Seppälä
  * 
  */
import java.util.ArrayList;


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
   * @author Tero Kettunen
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
  public RBTreeNode<E> add(E data) {
    
    RBTreeNode<E> n = root;
    if (n == null) {
      RBTreeNode<E> node = new RBTreeNode<E>(data);
      node.setColor(0);
      root = node;
	RBTreeAddFixup(node); //TK 
      size++;
      return node;
    }
    while (n != null && !n.getSentinel()) {
      E nE = n.getElement();
      if (nE.equals(data))
        return null;
      else if (nE.compareTo(data) < 0) {
        if (n.getRightChild() == null) {
          RBTreeNode<E> node = new RBTreeNode<E>(data);
          node.setColor(0);
          n.setRightChild(node);
          node.setParent(n);	//TK
          RBTreeAddFixup(node);
          size++;
          return node;
        }
        else
          n = n.getRightChild();
      }
      else {
        if (n.getLeftChild() == null) {
          RBTreeNode<E> node = new RBTreeNode<E>(data);
          node.setColor(0);
          n.setLeftChild(node);
          node.setParent(n);	//TK
          RBTreeAddFixup(node);
          size++;
          return node;
        }
        else
          n = n.getLeftChild();
      }
    }
    return null;
    
    /*
    RBTreeNode<E> z = new RBTreeNode<E>(data);
    RBTreeNode<E> x = getRoot();
    RBTreeNode<E> y = null;
    while (x != null) {
      y = x;
      if (z.getElement().compareTo(x.getElement()) < 0)
        x = x.getLeftChild();
      else
        x = x.getRightChild();
    }
    if (y = null)
    if (z.getElement().compareTo(y.getElement()) < 0)
      y.setLeftChild(z);
    else
      y.setRightChild(z);

    z.setParent(y);
    z.setColor(0);
    RBTreeAddFixup(z);
    size++;
    return z;
    */

    /*
    if (isEmpty()) {
      RBTreeNode<E> z = new RBTreeNode<E>(data);
      z.setColor(0);
      setRoot(z);
      RBTreeAddFixup(z);
      return z;
    }

    RBTreeNode<E> z = getRoot();
    RBTreeNode<E> parent = null;
    while (z != null) {
      parent = z;
      if (data.compareTo(z.getElement()) < 0)
        z = z.getLeftChild();
      else if (data.compareTo(z.getElement()) > 0)
        z = z.getRightChild();
      else
        return null;
    }
    if (parent == null) {
      z = new RBTreeNode<E>(data);
      setRoot(z);
    } else {
      z.setParent(parent);
      z.setElement(data);
      z.setColor(0);
    }
    z.setColor(0);
    RBTreeAddFixup(z);
    return z;
    */
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
  * Metodi predecessor palauttaa parametrina annetun solmun edeltäjän puussa
  * @author Juhani Seppälä
  * @param node solmu jonka edeltäjää etsitään
  * @return edeltäjäsolmu tai null jollei edeltäjää ole
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
    * Metodi min hakee minimiä parametrina annetusta solmusta alkaen
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
    * Metodi union muodostaa uuden yhdisteen kutsuttavasta ja parametrina saadusta puusta
    * @author Juhani Seppälä
    * @param t yhdisteeseen tuleva puu
    * @return Kahden puun joukko-opillista yhdistettä kuvaava tasapainoinen uusi puu
    *
    */
  public RBTree<E> union(RBTree<E> t) {
    if (t.isEmpty())
      return this;

    ArrayList<E> list1 = getOrderedListData();
    ArrayList<E> list2 = t.getOrderedListData();
    return treeFromList(listUnion(list1, list2));
  }
  
  /** 
    * Metodi intersection muodostaa leikkauksen kutsuttavasta ja parametrina saadusta puusta
    * @author Tero Kettunen
    * @author Juhani Seppälä
    * @param t leikkaukseen tuleva puu
    * @return Kahden puun joukko-opillista leikkausta kuvaava uusi puu
    *
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
    * Metodi difference muodostaa erotuksen kutsuttavasta ja parametrina saadusta puusta
    * @author Juhani Seppälä
    * @param t erotukseen tuleva puu
    * @return Kahden puun joukko-opillista erotusta kuvaava uusi puu
    *
    */  
  public RBTree<E> difference(RBTree<E> t) {
    if (t.isEmpty())
      return this;

    ArrayList<E> list1 = getOrderedListData();
    ArrayList<E> list2 = t.getOrderedListData();
    return treeFromList(listDifference(list1, list2));
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
    node.setColor(1);
  } // method

  /**
    * Metodi RBTreeAddFixup tarkastaa solmujen värit sekä tasapainottaa puun lisäysoperaation tapauksessa
    * kutsuen kääntöoperaatioiden metodeja leftRotate sekä rightRotate (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node Solmu, johon korjaustoimi kohdistuu
    *
    */
  private void RBTreeAddFixup(RBTreeNode<E> z) {
    System.out.println("AddFixup called with elem: " + z + " p: " + z.getParent());

    while (z.getParent() != null && z.getParent().getParent() != null && z.getParent().getColor() == 0) {
      RBTreeNode<E> y = null;
      if (z.getParent() == z.getParent().getParent().getLeftChild()) {
        y = z.getParent().getParent().getRightChild();
        if (y != null && y.getColor() == 0) {
          z.getParent().setColor(1);
          y.setColor(1);
          z = z.getParent().getParent();
          z.setColor(0);
        } else {
          if (z == z.getParent().getRightChild()) {
            z = z.getParent();
            leftRotate(z);
          }
          z.getParent().setColor(1);
          z.getParent().getParent().setColor(0);
          rightRotate(z.getParent().getParent());
        }
      } else {
        y = z.getParent().getParent().getLeftChild();
        if (y != null && y.getColor() == 0) {
          z.getParent().setColor(1);
          y.setColor(1);
          z = z.getParent().getParent();
          z.setColor(0);
        } else {
          if (z == z.getParent().getLeftChild()) {
            z = z.getParent();
            leftRotate(z);
          }
          z.getParent().setColor(1);
          z.getParent().getParent().setColor(0);
          System.out.println("rotate: " + z.getParent().getParent());
          leftRotate(z.getParent().getParent());
        }
      }
    }
    /*
    while (node != root && node.getParent() != null
                                  && node.getParent().getParent() != null
                                  && node.getParent().getColor() == 0) {

      if (node.getParent() == node.getParent().getParent().getLeftChild()) {
        RBTreeNode<E> y = node.getParent().getParent().getRightChild();
        if (y.getColor() == 0) {
          node.getParent().setColor(1);
          y.setColor(1);
          node.getParent().getParent().setColor(0);
          node = node.getParent().getParent();
        } else {
          if (node == node.getParent().getRightChild()) {
            node = node.getParent();
            leftRotate(node);
          }
          node.getParent().setColor(1);
          node.getParent().getParent().setColor(0);
          rightRotate(node.getParent().getParent());
        }
      } else if (node.getParent() == node.getParent().getParent().getRightChild()){
        RBTreeNode<E> y = node.getParent().getParent().getLeftChild();
        if (y != null && y.getColor() == 0) {
          node.getParent().setColor(1);
          y.setColor(1);
          node.getParent().getParent().setColor(0);
          node = node.getParent().getParent();
        }
        else {
          if (node == node.getParent().getLeftChild()) {
            node = node.getParent();
            leftRotate(node);
          }
          node.getParent().setColor(1);
          node.getParent().getParent().setColor(0);
          rightRotate(node.getParent().getParent());
        }
      }
    }
    */
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

  /**
    * Metodi getOrderedListData muodostaa ja palauttaa järjestetyn listan puun solmujen datakentistä.
    * @author Juhani Seppälä
    *
    */
  public ArrayList<E> getOrderedListData() {
    ArrayList<E> data = new ArrayList<E>();
    RBTreeNode<E> node = root;
    if (node != null)
      data = inorderAddBranch(node, data);
    
    return data;
  }

    /**
     * Metodi inorderAddBranch lisää parametrina annetun puun solmujen datakentät sisäjärjestyksessä  parametrina annettuun listaan.
     * @author Juhani Seppälä
     *
     */
  private ArrayList<E> inorderAddBranch(RBTreeNode<E> node, ArrayList<E> data) {
    if (node.getLeftChild() != null)
      inorderAddBranch(node.getLeftChild(), data);

    data.add(node.getElement());

    if (node.getRightChild() != null)
      inorderAddBranch(node.getRightChild(), data);

    return data;
  }

  /**
    * Metodi treeFromList palauttaa kelvollisen RBTreen parametrina annetusta järjestetystä listasta.
    * @author Juhani Seppälä
    *
    */
  private RBTree<E> treeFromList(ArrayList<E> list) {
    RBTree<E> tree = new RBTree<E>();
    root = treeFromListNode(list, 0, list.size() - 1);

    tree.setRoot(root);

    return tree;
  }

  /**
    * Metodi treeFromListNode on treeFromList -metodin rekursio, joka rekursion päättyessä tasapainoisen puun juurisolmun
    * @author Juhani Seppälä
    */
  private RBTreeNode<E> treeFromListNode(ArrayList<E> list,  int start, int end) {
    if (start > end)
      return null;

    int pivot = (start + end) / 2;

    RBTreeNode<E> node = new RBTreeNode<E>(list.get(pivot));
    node.setColor(1);

    node.setLeftChild(treeFromListNode(list, start, pivot - 1));
    node.setRightChild(treeFromListNode(list, pivot + 1, end));

    return node;
  }

  /**
    * Metodi listUnion muodostaa ja palauttaa yhdisteen kahdesta parametrina annetusta järjestetystä listasta
    * @author Juhani Seppälä
    */
  private ArrayList<E> listUnion(ArrayList<E> list1, ArrayList<E> list2) {
    int i = 0, j = 0;
    ArrayList<E> newList = new ArrayList<E>();

    while (i < list1.size() && j < list2.size()) {
      if (list1.get(i).compareTo(list2.get(j)) < 0) {
        i++;
        if (!newList.get(newList.size() - 1).equals(list1.get(i)))
          newList.add(list1.get(i));
      }
      else {
        j++;
        if (!newList.get(newList.size() - 1).equals(list2.get(j)))
          newList.add(list2.get(j));
      }
    }
    return newList;
  }

  /**
    * Metodi listIntersect muodostaa ja palauttaa leikkauksen kahdesta parametrina annetusta järjestetystä listasta
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
    * Metodi listDifference muodostaa ja palauttaa listan, jossa on parametrina annettujen järjestettyjen listojen joukko-opillinen erotus (tuhoaa ensimmäisen parametrin)
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
