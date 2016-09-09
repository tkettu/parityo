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
    z.setLeftChild(new RBTreeNode<E>(z));
    z.setRightChild(new RBTreeNode<E>(z));
    size++;
    addFixup(z);
    return z;
  }

  /**
  * Metodi RBTreeAddFixup tarkastaa solmujen värit sekä tasapainottaa puun lisäysoperaation tapauksessa
  * kutsuen kääntöoperaatioiden metodeja leftRotate sekä rightRotate (Cormen, Leiserson, Rivest)
  * @author Juhani Seppälä
  * @param node Solmu, johon korjaustoimi kohdistuu
  *
  */
  private void addFixup(RBTreeNode<E> z) {
 //   System.out.println("AddFixup called with elem: " + z + " p: " + z.getParent());

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
      } else {
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
    * Metodi remove poistaa parametrina annetun objektin puusta ja kutsuu tasapainotusmetodia
    * RBTreeRemoveFixup
    * @author Tero Kettunen
    * @author Juhani Seppälä
    * @param data Puusta poistettava objekti
    * @return true, jos data poistettiin; muuten false
    */
  public RBTreeNode<E> remove(E data){
  
    RBTreeNode<E> z = search(data);  //etsitään oikea poistettava solmu, jos on olemassa
    RBTreeNode<E> y;  //apusolmu    
    RBTreeNode<E> x;  //apusolmu
    
    if (z == null)
      return null;
    
    if (z.getLeftChild().getSentinel() || z.getRightChild().getSentinel())
      y = z;
    else
      y = successor(z);
    
    if (y.getLeftChild() != null && !y.getLeftChild().getSentinel()) 
      x = y.getLeftChild();
    else
      x = y.getRightChild();
    
    x.setParent(y.getParent());
    
    if (y.getParent() == null)
      setRoot(x);
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
    * RBTreeRemoveFixup tarkastaa solmujen värit sekä tasapainottaa puun poisto-operaation tapauksessa
    * kutsuen kääntöoperaatioiden metodeja leftRotate sekä rightRotate (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node solmu, joka saattaa rikkoa puun tasapainoa
    */
  private void removeFixup(RBTreeNode<E> x) {
    while (x != root && x.getColor() == 1) {
        if (x == x.getParent().getLeftChild()) {
            RBTreeNode<E> w = x.getParent().getRightChild();
            if (w.getColor() == 0) {
                w.setColor(1);
                x.getParent().setColor(0);
                leftRotate(x.getParent());
            }
            if (w.getLeftChild().getColor() == 1 &&
                w.getRightChild().getColor() == 1) {

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
    * Metodi leftRotate suorittaa parametrina annetun solmun perusteella puulle vasemman käännön
    * @author Tero Kettunen
    * @param node Solmu, josta alkaen vasen kääntö suoritetaan
    *
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
    * Metodi rightRotate suorittaa parametrina annetun solmun perusteella puulle oikean käännön
    * @author Tero Kettunen
    * @param node Solmu, josta alkaen oikea kääntö suoritetaan
    *
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
      if (x == x.getParent().getLeftChild())
        x.getParent().setLeftChild(y);
      else
        x.getParent().setRightChild(y);
    }
    y.setRightChild(x);
    x.setParent(y);
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
    * Metodi successor palauttaa parametrina annetun solmun seuraajan puussa
    * (Cormen, Leiserson, Rivest)
    * @author Juhani Seppälä
    * @param node solmu, jonka seuraajaa etsitään
    * @return parametrina annetun solmun seuraaja puussa
    *
    */
  public RBTreeNode<E> successor(RBTreeNode<E> node) {
    if (node.getRightChild() != null && !node.getSentinel())
      return min(node.getRightChild());
    else {
      RBTreeNode<E> succ = node.getParent();
      while (succ != null && !succ.getSentinel() && node == succ) {
        node = succ;
        succ = node.getParent();
      }
      return succ;
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
    while (!node.getLeftChild().getSentinel() && !node.getSentinel())
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
    if (node.getLeftChild() != null && !node.getLeftChild().getSentinel())
      inorderAddBranch(node.getLeftChild(), data);

    data.add(node.getElement());

    if (node.getRightChild() != null && !node.getRightChild().getSentinel())
      inorderAddBranch(node.getRightChild(), data);

    return data;
  }

  /**
    * Metodi treeFromList palauttaa kelvollisen RBTreen parametrina annetusta järjestetystä listasta.
    * @author Juhani Seppälä
    *
    */
  public RBTree<E> treeFromList(ArrayList<E> list) {
    RBTree<E> tree = new RBTree<E>();
    RBTreeNode<E> root = treeFromListNode(list, 0, list.size() - 1);

    tree.setRoot(root);
    tree.setSize(list.size());

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

    node.setLeftChild(treeFromListNode(list, start, pivot - 1));
    node.setRightChild(treeFromListNode(list, pivot + 1, end));

    // Todelliset lehtisolmut voidaan asettaa tasapainoisessa puussa yksinkertaisesti punaiseksi, muut mustiksi.
    if (node.getLeftChild() == null && node.getRightChild() == null)
      node.setColor(0);
    else
      node.setColor(1);

    // Laitetaan sisäiset lehtisolmut (kuten add).
    if (node.getLeftChild() == null) {
      RBTreeNode<E> sentinelLeft = new RBTreeNode<E>(node);
      node.setLeftChild(sentinelLeft);
    }
    
    if (node.getRightChild() == null) {
      RBTreeNode<E> sentinel = new RBTreeNode<E>(node);
      node.setRightChild(sentinel);
    }

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