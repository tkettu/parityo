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
      System.out.println("Puu on tyhjä");
      
    Random R = new Random();
    
    
    
    //Testataan add() satunnaisilla luvuilla
    
    System.out.println("Lisätään");
    for(int i = 0; i<N;i++){
      Integer x = R.nextInt(N);
      System.out.print(x + " ");
      puu.add(x);
    }
  
    //Testataan remove()
    
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
  private RBTree<Integer> testTree(){
    
    RBTree<Integer> puu = new RBTree<Integer>();
    
    puu.setRoot(RBTreeNode<Integer>(7,null,null,null,"b"));  
        //7 juureksi,           vanhempi, lapset null, väri musta
    
    RBTreeNode<Integer> juuri = puu.getRoot();
    
    // juuren lapset
    juuri.setLeftChild(new RBTreeNode<Integer>(2,juuri,null,null,"r"));
    juuri.setRightChild(new RBTreeNode<Integer>(11,juuri,null,null,"r"));
    
    //vasen haara
    RBTreeNode<Integer> vh = juuri.getLeftChild();
    
    vh.setLeftChild(new RBTreeNode<Integer>(1,vh,null,null,"b"));
    vh.setRightChild(new RBTreeNode<Integer>(5,vh,null,null,"b"));
    
    RBTreeNode<Integer> vho = vh.getRightChild();
    
    vho.setLeftChild(new RBTreeNode<Integer>(4,vho,null,null,"r"));
    
    //oikea haara
    RBTreeNode<Integer> oh = juuri.getRightChild();
    
    oh.setLeftChild(new RBTreeNode<Integer>(8,oh,null,null,"b"));
    oh.setRightChild(new RBTreeNode<Integer>(14,oh,null,null,"b"));
    
    RBTreeNode<Integer> oho = oh.getRightChild();
    
    oho.setLeftChild(new RBTreeNode<Integer>(15,oho,null,null,"r"));
    
    
    
  }
  
  
  private void printTree(RBTree puu){
    
    if(puu.isEmpty()){
      System.out.println("Puu on tyhjä");
    }else{
      int n = puu.size();
      RBTreeNode juuri = puu.getRoot();
      
      System.out.println(juuri.getColor() + " | ");// juuri.getElement());
      //System.out.println(" /    \\ ");  
      PrintTreeApu(juuri);
    }
  }
  
  private void PrintTreeApu(RBTreeNode node){
  
    RBTreeNode vl = node.getLeftChild();
    
    if(vl!=null){
      PrintTreeApu(vl);
    }
    
    System.out.println(node.getColor());
    
    RBTreeNode ol = node.getRightChild();
    
    if(ol!=null){
      PrintTreeApu(ol);
    } 
  }
  
  
}