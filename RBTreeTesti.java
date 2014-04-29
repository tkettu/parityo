import java.util.Random;

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
    
    //Testataan add() satunnaisilla luvuilla
    
	RBTree<Integer> puu2 = new RBTree<Integer>();
	
    System.out.println("\nLisataan");
    for(int i = 0; i<N;i++){
      Integer x = R.nextInt(N);
      System.out.print(x + " ");
      puu.add(x);
    }
	
	System.out.println();
	printTree(puu);
	
  
    //Testataan remove()
	
	int pois = 5;  //TODO tarkista lehdillä ja juurilla
	System.out.println("\nPoistetaan " + pois);
	
	testipuu.remove(pois);
	
	printTree(testipuu);
    
    //Testataan search()
    
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
  
  
  private static void printTree(RBTree puu){
    
    if(puu.isEmpty()){
      System.out.println("Puu on tyhjä");
    }else{
      int n = puu.size();
      RBTreeNode juuri = puu.getRoot();
       System.out.println();
      System.out.println("Vari (0=red, 1= black) |  elementti");
      
      PrintTreeApu(juuri);
    }
  }
  
  private static void PrintTreeApu(RBTreeNode node){
  
    RBTreeNode vl = node.getLeftChild();
    
    if(vl!=null){
      PrintTreeApu(vl);
    }
    
    System.out.println(node.getColor() + " | " + node.getElement() );
    
    RBTreeNode ol = node.getRightChild();
    
    if(ol!=null){
      PrintTreeApu(ol);
    } 
  }
  
  
}