import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

/**
  * Testi ohjelma luokkaa RBTree varten
  * @author Tero Kettunen
  *
  */
 
public class RBTreeTesti {

  public static void main(String[] args){
  
    RBTree<Integer> puu = new RBTree<Integer>();
    
    int N = 10; //Syötekoko
    if(args.length > 0)
      N = Integer.valueOf(args[0]);
    
    //Testataan isEmpty();
    
    if(puu.isEmpty())
      System.out.println("Puu on tyhja");
      
    Random R = new Random();
    
    //tulostetaan testipuu
	
	RBTree<Integer> testipuu = testTree();
	
	printTree(testipuu);
 //     BTreePrinter.printNode(testipuu.getRoot());
	
	if(checkRBTree(testipuu))
	  System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");

        printTree(testipuu);
       BTreePrinter.printNode(testipuu.getRoot());
	
	//Testataan add() ________________________________________
	
	System.out.println("\ntestataan add() testipuun datalla:");
	
	puu.add(2);
	puu.add(1);
	puu.add(11);
	puu.add(15);
	puu.add(14);
	puu.add(7);
	puu.add(4);
	puu.add(8);
	puu.add(5);
	
	puu.add(8);
	
	printTree(puu);
      BTreePrinter.printNode(puu.getRoot());

	if(checkRBTree(puu))
	  System.out.println("Puu on puna-musta!");
	else
	  System.out.println("Puu ei ole puna-musta!");

      System.out.println("Poisto: ");

      puu.remove(2);
      printTree(puu);
      BTreePrinter.printNode(puu.getRoot());
      if(checkRBTree(puu))
        System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");

      puu.remove(4);
      printTree(puu);
      BTreePrinter.printNode(puu.getRoot());
      if(checkRBTree(puu))
        System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");

      System.out.println("Union: ");
      RBTree<Integer> puu11 = new RBTree<Integer>();
      puu11.add(2);
      puu11.add(1);
      puu11.add(11);
      puu11.add(15);
      puu11.add(14);
      puu11.add(7);
      puu11.add(4);
      puu11.add(8);
      puu11.add(5);

      RBTree<Integer> puu12 = new RBTree<Integer>();
      puu12.add(6);
      puu12.add(3);
      puu12.add(2);
      puu12.add(50);
      puu12.add(22);
      puu12.add(1);
      puu12.add(16);
      puu12.add(7);
      puu12.add(30);

      RBTree<Integer> union = puu11.union(puu12);
      printTree(union);
      BTreePrinter.printNode(union.getRoot());
      if(checkRBTree(union))
        System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");

      union.add(31);
      printTree(union);
      BTreePrinter.printNode(union.getRoot());
      if(checkRBTree(union))
        System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");

      RBTree<Integer> intersect = puu11.intersection(puu12);
      printTree(intersect);
      BTreePrinter.printNode(intersect.getRoot());
      if(checkRBTree(intersect))
        System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");

      intersect.add(30);
      printTree(intersect);
      BTreePrinter.printNode(intersect.getRoot());
      if(checkRBTree(intersect))
        System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");
    
    //Testataan add() satunnaisilla luvuilla

    RBTree<Integer> puu2 = new RBTree<Integer>();
	
    System.out.println("\nLisataan");
    for (int i = 0; i<N;i++){
      Integer x = R.nextInt(2*N);
      System.out.print(x + " ");
      puu2.add(x);
    }

      if(checkRBTree(puu2))
        System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");   

      printTree(puu2);
      BTreePrinter.printNode(puu2.getRoot());

      System.out.println("\nPoistetaan");
      for (int i = 0; i < N / 2; i++){
        Integer x = R.nextInt(2*N);
        System.out.print(x + " ");
        puu2.remove(x);
      }

      if(checkRBTree(puu2))
        System.out.println("Puu on puna-musta!");
      else
        System.out.println("Puu ei ole puna-musta!");   

      printTree(puu2);
      BTreePrinter.printNode(puu2.getRoot());
	
  /*
	System.out.println();
	printTree(puu2);
	
	if(checkRBTree(puu2))
	  System.out.println("Puu on puna-musta!");
	else
	  System.out.println("Puu ei ole puna-musta!");
	
  
    //Testataan remove()
	
	int pois = 5;  //TODO tarkista lehdillä ja juurilla
	System.out.println("\nPoistetaan " + pois);
	
//	testipuu.remove(pois);
	
	printTree(testipuu);
    
    //Testataan search()
*/
  }
  
  /* Luo laillisen puna-mustan puun
  
       7(m)
    /       \
    2(p)      11(p) 
   /    \     /   \ 
  1(m)  5(m) 8(m) 14(m)
      /        \
    4(p)      15(p)
    
  (Cormen, Leiserson, Rivest s. 282, figure 13.4 (d))
  */
      
  
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
  private static boolean checkRBTree(RBTree puu){
    if(puu.isEmpty())
      return true;

    RBTreeNode juuri = puu.getRoot();

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
  private static boolean checkRBTreeApu(RBTreeNode node) {

    /*
     * Ominaisuus 3
     */
    if (node.getSentinel() && node.getColor() != 1) 
      return false;

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
}

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
}