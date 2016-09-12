import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

/**
  * Testi ohjelma luokkaa RBTree varten
  * @author Tero Kettunen
  * @author Juhani Seppälä
  *
  */
 
public class RBTreeTesti {

  public static void main(String[] args){
  
    RBTree<Integer> puu = new RBTree<Integer>();
    
    int N = 20; // Syötekoko
    int K = 20; // Toistot 
    if(args.length > 0)
      N = Integer.valueOf(args[0]);
    if (args.length > 1)
      K = Integer.valueOf(args[1]);

    boolean successAdd = testAdd(K, N);
    if (successAdd)
      System.out.println("PASS");
    else
      System.out.println("FAIL");

    boolean successRemove = testRemove(K, N);
    if (successRemove)
      System.out.println("PASS");
    else
      System.out.println("FAIL");

    boolean successUnion = testUnion(K, N);
    if (successUnion)
      System.out.println("PASS");
    else
      System.out.println("FAIL");

    boolean successIntersection = testIntersection(K, N);
    if (successIntersection)
      System.out.println("PASS");
    else
      System.out.println("FAIL");
    
    boolean successDifference = testDifference(K, N);
    if (successDifference)
      System.out.println("PASS");
    else
      System.out.println("FAIL");

    if (successAdd && successRemove && successUnion && successIntersection && successDifference)
      System.out.println("Testit OK!");
    else
      System.out.println("Kaikki testit EI OK!");
      
  }  
  
  /*
   * Add testi
   */
  private static boolean testAdd(int runs, int n) {
    boolean success = true;
    System.out.println("Testataan add... toistot: " + runs);
    for (int i = 0; i < runs; i++) {
      RBTree<Integer> tree = randomRBTree(n, new ArrayList<Integer>());
      if (!checkRBTree(tree)) {
//        System.out.println("FAIL");
        success = false;
      } else {
//        System.out.println("PASS");
      }
    }
    return success;
  }

  /*
   * Remove testi
   */
  private static boolean testRemove(int runs, int n) {
    boolean success = true;
    ArrayList<Integer> addedData;
    System.out.println("Testataan remove... toistot: " + runs);
    for (int i = 0; i < runs; i++) {
      addedData = new ArrayList<Integer>();
      RBTree<Integer> tree = randomRBTree(n, addedData);
//      BTreePrinter.printNode(tree.getRoot());
      for (int j = 0; j < addedData.size(); j++) {
//        System.out.println("Poistetaan... " + addedData.get(j) + "\n");
//        try {
//          Thread.sleep(200);
//        } catch (Exception e) {}
        tree.remove(addedData.get(j));
//        BTreePrinter.printNode(tree.getRoot());
        if (!checkRBTree(tree)) {
//          System.out.println("FAIL");
          success = false;
        } else {
//          System.out.println("PASS");
        }
      }
    }
    return success;
  }

  /*
   * Union testi
   */
  private static boolean testUnion(int runs, int n) {
    boolean success = true;
    System.out.println("Testataan union... toistot: " + runs);
    for (int i = 0; i < runs; i++) {
      RBTree<Integer> tree1 = randomRBTree(n, new ArrayList<Integer>());
      RBTree<Integer> tree2 = randomRBTree(n, new ArrayList<Integer>());
      RBTree<Integer> union = tree1.union(tree2);
      if (!checkRBTree(union)) {
//        System.out.println("FAIL");
        success = false;
      } else {
//        System.out.println("PASS");
      }
    }
    return success;
  }

  /*
   * Intersection testi
   */
  private static boolean testIntersection(int runs, int n) {
    boolean success = true;
    System.out.println("Testataan intersection... toistot: " + runs);
    for (int i = 0; i < runs; i++) {
      RBTree<Integer> tree1 = randomRBTree(n, new ArrayList<Integer>());
      RBTree<Integer> tree2 = randomRBTree(n, new ArrayList<Integer>());
      RBTree<Integer> intersection = tree1.intersection(tree2);
      if (!checkRBTree(intersection)) {
//        System.out.println("FAIL");
        BTreePrinter.printNode(intersection.getRoot());
        success = false;
      } else {
//        System.out.println("PASS");
      }
    }
    return success;
  }

  /*
   * Difference testi
   */
  private static boolean testDifference(int runs, int n) {
    boolean success = true;
    System.out.println("Testataan difference... toistot: " + runs);
    for (int i = 0; i < runs; i++) {
      RBTree<Integer> tree1 = randomRBTree(n, new ArrayList<Integer>());
      RBTree<Integer> tree2 = randomRBTree(n, new ArrayList<Integer>());
      RBTree<Integer> difference = tree1.difference(tree2);
      if (!checkRBTree(difference)) {
//        System.out.println("FAIL");
        success = false;
      } else {
//        System.out.println("PASS");
      }
    }
    return success;
  }
  
  // Puun alkioiden tulostus sisäjärjestyksessä
  private static void printTree(RBTree puu){
    
    if(puu.isEmpty()){
      System.out.println("Puu on tyhjä");
    }else{
      int n = puu.size();
      RBTreeNode juuri = puu.getRoot();
      System.out.println("\nJuuri on " + juuri.getElement()); 
      System.out.println("Vari (0=red, 1= black) |  elementti");
      
      PrintTreeApu(juuri);
    }
  }
  
  private static void PrintTreeApu(RBTreeNode node){
  
    RBTreeNode vl = node.getLeftChild();
    
    if(vl!=null && !vl.getSentinel()){
      PrintTreeApu(vl);
    }
    
    System.out.println(node.getColor() + " | " + node.getElement() );
    
    RBTreeNode ol = node.getRightChild();
    
    if(ol!=null && !ol.getSentinel()){
      PrintTreeApu(ol);
    } 
  }

  /*
   * Metodi randomRBTree muodostaa ja palauttaa parametrina annetun kokoisen RBTreen
   */
  private static RBTree<Integer> randomRBTree(int n, ArrayList<Integer> addedData) {
//    System.out.println("Luodaan satunnainen puu, koko: " + n);
    Random R = new Random();
    RBTree<Integer> tree = new RBTree<Integer>();
    for (int i = 0; i<n; i++){
      Integer x = R.nextInt(2 * n);
      tree.add(x);
      addedData.add(x);
    }
    return tree;
  }
  
  /*
    Metodi minHeight laskee ja palauttaa lyhimmän yksinkertaisen polun parametrina annetusta solmusta lehteen.
  */
  private static int minHeight(RBTreeNode node) {
    if (node == null || node.getSentinel())
      return 0;
    return 1 + Math.min(minHeight(node.getLeftChild()), minHeight(node.getRightChild()));
  }

  /*
    Metodi maxHeight laskee ja palauttaa pisimmän yksinkertaisen polun parametrina annetusta solmusta lehteen.
  */
  private static int maxHeight(RBTreeNode node) {
    if (node == null || node.getSentinel())
      return 0;
    return 1 + Math.max(maxHeight(node.getLeftChild()), maxHeight(node.getRightChild()));
  }
  
  /*
    Tarkistetaan, toteuttaako puu puna-mustan puun vaatimukset:
      1) Solmu on joko musta tai punainen
      2) Juuri on musta
      3) Sentinel-solmut (lehdet) mustia
      4) Jos solmu on punainen, kummatkin lapset mustia
      5) Jokaiselle solmulle: kaikki yksinkertaiset polut solmusta lehtiin sisältävät yhtä monta mustaa solmua (solmua itseään ei lasketa, sentinel lasketaan)
  */
  private static boolean checkRBTree(RBTree<Integer> puu) {
    if(puu.isEmpty())
      return true;

    RBTreeNode<Integer> juuri = puu.getRoot();

    if (!checkInOrderList(puu.getOrderedListData()))
      return false;

    /*
     * Ominaisuus 2
     */
    if(juuri.getColor() != 1)
      return false;

    /*
     * Ominaisuus 5 (tasapainoisuus)
     */
    if (maxHeight(juuri) > minHeight(juuri) * 2)
      return false;

    return checkRBTreeApu(juuri);
  }
  
  /*
   * CheckRBTree rekursio. Ominaisuuksien 3 ja 4 tarkistus.
   */
  private static boolean checkRBTreeApu(RBTreeNode<Integer> node) {

    /*
     * Ominaisuus 3
     */
    if (node.getSentinel() && node.getColor() != 1) 
      return false;

    if (!node.getSentinel()) {
      if (node.getLeftChild() == null || node.getRightChild() == null)
        return false;
    }

    /*
     * Ominaisuus 4
     */
    if (node.getColor() == 0) {
      if (node.getLeftChild() != null && node.getLeftChild().getColor() == 0)
        return false;
      if (node.getRightChild() != null && node.getRightChild().getColor() == 0)
        return false;
    }

    if (node.getLeftChild() != null && !checkRBTreeApu(node.getLeftChild()))
      return false;

    if (node.getRightChild() != null && !checkRBTreeApu(node.getRightChild()))
      return false;

    return true; 
  }

  /*
   * Listan järjestys
   */
  private static boolean checkInOrderList(ArrayList<Integer> list) {
    if (list.size() <= 1)
      return true;

    Iterator<Integer> iterator = list.iterator();
    Integer previous = iterator.next();
    while (iterator.hasNext()) {
      Integer next = iterator.next();
      if (next.compareTo(previous) < 0)
        return false;
      previous = next;
    }
    return true;
  }

 //TODO, tarkista toimiiko edes
  private static RBTree<Integer> testTree(){
    
    RBTree<Integer> puu = new RBTree<Integer>();
    
    puu.setRoot(new RBTreeNode<Integer>(7,null,null,null,1));  
        //7 juureksi,           vanhempi, lapset null, väri musta
    
    RBTreeNode<Integer> juuri = puu.getRoot();
    
    // juuren lapset
    juuri.setLeftChild(new RBTreeNode<Integer>(2,juuri,null,null,0));
    juuri.setRightChild(new RBTreeNode<Integer>(11,juuri,null,null,0));
    
    //vasen haara
    RBTreeNode<Integer> vh = juuri.getLeftChild();
    
    vh.setLeftChild(new RBTreeNode<Integer>(1,vh,null,null,1));
    vh.setRightChild(new RBTreeNode<Integer>(5,vh,null,null,1));
    
    RBTreeNode<Integer> vho = vh.getRightChild();
    
    vho.setLeftChild(new RBTreeNode<Integer>(4,vho,null,null,0));
    
    //oikea haara
    RBTreeNode<Integer> oh = juuri.getRightChild();
    
    oh.setLeftChild(new RBTreeNode<Integer>(8,oh,null,null,1));
    oh.setRightChild(new RBTreeNode<Integer>(14,oh,null,null,1));
    
    RBTreeNode<Integer> oho = oh.getRightChild();
    
    oho.setRightChild(new RBTreeNode<Integer>(15,oho,null,null,0));
    
  puu.setSize(10);
    return puu;
    
  }

} // class

class BTreePrinter {

    public static <E extends Comparable<?>> void printNode(RBTreeNode<E> root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private  static <E extends Comparable<?>> void printNodeInternal(List<RBTreeNode<E>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<RBTreeNode<E>> newNodes = new ArrayList<RBTreeNode<E>>();
        for (RBTreeNode<E> node : nodes) {
            if (node != null && !node.getSentinel()) {
                if (node.getColor() == 0)
                  System.out.print("r");
                else
                  System.out.print("b");

                System.out.print(node.getElement());

                newNodes.add(node.getLeftChild());
                newNodes.add(node.getRightChild());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeftChild() != null && !nodes.get(j).getLeftChild().getSentinel())
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).getRightChild() != null && !nodes.get(j).getRightChild().getSentinel())
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <E extends Comparable<?>> int maxLevel(RBTreeNode<E> node) {
        if (node == null || node.getSentinel())
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.getLeftChild()), BTreePrinter.maxLevel(node.getRightChild())) + 1;
    }

    private static <E> boolean isAllElementsNull(List<E> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
} // class