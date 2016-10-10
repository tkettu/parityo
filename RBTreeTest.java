import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.io.*;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
  * The class RBTreeTest is the test class for RBTree
  * @author Tero Kettunen
  * @author Juhani Seppälä
  */
public class RBTreeTest {
  private static Random r;
  private static final int MAXN = 1000000; // Max input size for tests
  private static final int PERF_INCREMENT = 100000; // Size of increments (only for benchmarks)

  public static void main(String[] args) {
  
    RBTree<Integer> puu = new RBTree<Integer>();
    r = new Random();
    
    int N = 20; // Randomly generated tree size
    int K = 20; // Number of repeated runs
    int P = 0; // Flag for whether to run performance test (default = off)
    if(args.length > 0)
      N = Integer.valueOf(args[0]);
    if (args.length > 1)
      K = Integer.valueOf(args[1]);
    if (args.length > 2)
      P = Integer.valueOf(args[2]);

    boolean successAdd = testAdd(K, N);
    if (successAdd)
      System.out.println("PASS: add()");
    else
      System.out.println("FAIL: add()");

    boolean successRemove = testRemove(K, N);
    if (successRemove)
      System.out.println("PASS: remove()");
    else
      System.out.println("FAIL: remove()");

    boolean successUnion = testUnion(K, N);
    if (successUnion)
      System.out.println("PASS: union()");
    else
      System.out.println("FAIL: union()");

    boolean successIntersection = testIntersection(K, N);
    if (successIntersection)
      System.out.println("PASS: intersection()");
    else
      System.out.println("FAIL: intersection()");
    
    boolean successDifference = testDifference(K, N);
    if (successDifference)
      System.out.println("PASS: difference()");
    else
      System.out.println("FAIL: difference()");

    if (successAdd && successRemove && successUnion && successIntersection && successDifference)
      System.out.println("All tests OK!");
    else
      System.out.println("All tests not OK!");

    if (P == 1) {
      perfAdd(K);
      perfRemove(K);
      perfUnion(K);
      perfIntersection(K);
      perfDifference(K);
    }
  }  
  
  /*
   * add() test
   */
  private static boolean testAdd(int runs, int n) {
    boolean success = true;
    System.out.println("Testing add()... runs: " + runs);
    for (int i = 0; i < runs; i++) {
      RBTree<Integer> testTree = randomRBTree(n, new ArrayList<Integer>());
      if (!checkRBTree(testTree)) {
        success = false;
        break;
      }
    }
    return success;
  }

  /*
   * remove() test
   */
  private static boolean testRemove(int runs, int n) {
    boolean success = true;
    ArrayList<Integer> addedData;
    System.out.println("Testing remove()... runs: " + runs);
    for (int i = 0; i < runs; i++) {
      addedData = new ArrayList<Integer>();
      RBTree<Integer> testTree = randomRBTree(n, addedData);
      for (int j = 0; j < addedData.size() / 2; j++) {
        testTree.remove(addedData.get(j));
      }
      if (!checkRBTree(testTree)) {
        success = false;
        break;
      }
    }
    return success;
  }

  /*
   * union() test
   */
  private static boolean testUnion(int runs, int n) {
    boolean success = true;
    ArrayList<Integer> addedData;
    System.out.println("Testing union()... runs: " + runs);
    for (int i = 0; i < runs; i++) {
      addedData = new ArrayList<Integer>();
      RBTree<Integer> tree1 = randomRBTree(n, addedData);
      RBTree<Integer> tree2 = randomRBTree(n, addedData);
      RBTree<Integer> testTree = tree1.union(tree2);
      for (int j = 0; j < addedData.size() / 2; j++) {
        testTree.remove(addedData.get(j));
      }
      for (int j = 0; j < n / 2; j++) {
        testTree.add(r.nextInt(2 * n));
      }
      if (!checkRBTree(testTree)) {
        success = false;
        break;
      }
    }
    return success;
  }

  /*
   * intersection() test
   */
  private static boolean testIntersection(int runs, int n) {
    boolean success = true;
    ArrayList<Integer> addedData;
    System.out.println("Testing intersection()... runs: " + runs);
    for (int i = 0; i < runs; i++) {
      addedData = new ArrayList<Integer>();
      RBTree<Integer> tree1 = randomRBTree(n, addedData);
      RBTree<Integer> tree2 = randomRBTree(n, addedData);
      RBTree<Integer> testTree = tree1.intersection(tree2);
      for (int j = 0; j < n / 2; j++) {
        testTree.add(r.nextInt(2 * n));
      }
      for (int j = 0; j < addedData.size() / 2; j++) {
        testTree.remove(addedData.get(j));
      }
      if (!checkRBTree(testTree)) {
        success = false;
        break;
      }
    }
    return success;
  }

  /*
   * difference() test
   */
  private static boolean testDifference(int runs, int n) {
    boolean success = true;
    ArrayList<Integer> addedData;
    System.out.println("Testing difference()... runs: " + runs);
    for (int i = 0; i < runs; i++) {
      addedData = new ArrayList<>();
      RBTree<Integer> tree1 = randomRBTree(n, addedData);
      RBTree<Integer> tree2 = randomRBTree(n, addedData);
      RBTree<Integer> testTree = tree1.difference(tree2);
      for (int j = 0; j < addedData.size() / 2; j++) {
        testTree.remove(addedData.get(j));
      }
      for (int j = 0; j < n / 2; j++) {
        testTree.add(r.nextInt(2 * n));
      }
      if (!checkRBTree(testTree)) {
        success = false;
        break;
      }
    }
    return success;
  }

  /**
    * The method perfAdd benchmarks the add operation averaging over the parameter-given
    * run count and saves the results to a file
    * @param runs The number of repeated runs to average over
    * @author Juhani Seppälä
    */
  private static void perfAdd(int runs) {
    long totalTime = 0;
    long totalMemory = 0;
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    Calendar now = Calendar.getInstance();
    String fileName = "results-add-" + timeStamp() + ".txt";
    Path file = Paths.get(fileName);
    try {
      Files.write(file, Arrays.asList("N\tTime(ms)\tMemory(MB)"), Charset.forName("UTF-8"), CREATE, WRITE, TRUNCATE_EXISTING);
    } catch (IOException ioe) {}
    for (int i = 0; i <= MAXN; i += PERF_INCREMENT) {
      totalMemory = 0;
      totalTime = 0;
      for (int j = 0; j < runs; j++) {
        long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long start = System.nanoTime();
        RBTree<Integer> t = randomRBTree(i, new ArrayList<Integer>());
        totalTime += (System.nanoTime() - start);
        long endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (endMem - startMem > 0)
          totalMemory += endMem - startMem;
      }
      System.out.println("Add average (N = " + i + ", runs = " + runs + "): " + df.format((double)(totalTime / runs) / 1000000) + " ms");
      System.out.println("Average memory (N = " + i + ", runs = " + runs + "): " + df.format((double)(totalMemory / runs) / 1000000) + " MB");
      List<String> line = Arrays.asList("" + i + "\t" + df.format((double)(totalTime / runs) / 1000000) + "\t" + df.format((double)(totalMemory / runs) / 1000000));
      try {
        Files.write(file, line, Charset.forName("UTF-8"), APPEND);
      } catch (IOException ioe) {}
    }
  }

  /**
    * The method perfRemove benchmarks the remove operation averaging over the parameter-given
    * run count and saves the results to a file
    * @param runs The number of repeated runs to average over
    * @author Juhani Seppälä
    */
  private static void perfRemove(int runs) {
    long totalTime = 0;
    long totalMemory = 0;
    ArrayList<Integer> addedData;
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    String fileName = "results-remove-" + timeStamp() + ".txt";
    Path file = Paths.get(fileName);
    try {
      Files.write(file, Arrays.asList("N\tTime(ms)"), Charset.forName("UTF-8"), CREATE, WRITE, TRUNCATE_EXISTING);
    } catch (IOException ioe) {}
    for (int i = 0; i <= MAXN; i += PERF_INCREMENT) {
      totalMemory = 0;
      totalTime = 0;
      for (int j = 0; j < runs; j++) {
        addedData = new ArrayList<Integer>();
        RBTree<Integer> t = randomRBTree(i, addedData);
        long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long start = System.nanoTime();
        for (int z = 0; z < addedData.size() / 2; z++) {
          t.remove(addedData.get(z));
        }
        totalTime += System.nanoTime() - start;
        long endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (endMem - startMem > 0)
          totalMemory += endMem - startMem;
      }
      System.out.println("Remove average (N = " + i + ", runs = " + runs + "): " + df.format((double)(totalTime / runs) / 1000000) + " ms");
      List<String> line = Arrays.asList("" + i + "\t" + df.format((double)(totalTime / runs) / 1000000));
      try {
        Files.write(file, line, Charset.forName("UTF-8"), APPEND);
      } catch (IOException ioe) {}
    }
  }

  /**
    * The method perfUnion benchmarks the union operation averaging over the parameter-given
    * run count and saves the results to a file
    * @param runs The number of repeated runs to average over
    * @author Juhani Seppälä
    */
  private static void perfUnion(int runs) {
    long totalTime = 0;
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    String fileName = "results-union-" + timeStamp() + ".txt";
    Path file = Paths.get(fileName);
    try {
      Files.write(file, Arrays.asList("N\tTime(ms)"), Charset.forName("UTF-8"), CREATE, WRITE, TRUNCATE_EXISTING);
    } catch (IOException ioe) {}
    for (int i = 0; i <= MAXN; i += PERF_INCREMENT) {
      totalTime = 0;
      for (int j = 0; j < runs; j++) {
        RBTree<Integer> t1 = randomRBTree(i, new ArrayList<Integer>());
        RBTree<Integer> t2 = randomRBTree(i, new ArrayList<Integer>());
        long start = System.nanoTime();
        RBTree<Integer> test = t1.union(t2);
        totalTime += (System.nanoTime() - start);
      }
      System.out.println("Union average (N = " + i + ", runs = " + runs + "): " + df.format((double)(totalTime / runs) / 1000000) + " ms");
      List<String> line = Arrays.asList("" + i + "\t" + df.format((double)(totalTime / runs) / 1000000));
      try {
        Files.write(file, line, Charset.forName("UTF-8"), APPEND);
      } catch (IOException ioe) {}
    }
  }

  /**
    * The method perfIntersection benchmarks the intersection operation averaging over the parameter-given
    * run count and saves the results to a file
    * @param runs The number of repeated runs to average over
    * @author Juhani Seppälä
    */
  private static void perfIntersection(int runs) {
    long totalTime = 0;
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    String fileName = "results-intersect-" + timeStamp() + ".txt";
    Path file = Paths.get(fileName);
    try {
      Files.write(file, Arrays.asList("N\tTime(ms)"), Charset.forName("UTF-8"), CREATE, WRITE, TRUNCATE_EXISTING);
    } catch (IOException ioe) {}
    for (int i = 0; i <= MAXN; i += PERF_INCREMENT) {
      totalTime = 0;
      for (int j = 0; j < runs; j++) {
        RBTree<Integer> t1 = randomRBTree(i, new ArrayList<Integer>());
        RBTree<Integer> t2 = randomRBTree(i, new ArrayList<Integer>());
        long start = System.nanoTime();
        RBTree<Integer> test = t1.intersection(t2);
        totalTime += (System.nanoTime() - start);
      }
      System.out.println("Intersection average (N = " + i + ", runs = " + runs + "): " + df.format((double)(totalTime / runs) / 1000000) + " ms");
      List<String> line = Arrays.asList("" + i + "\t" + df.format((double)(totalTime / runs) / 1000000));
      try {
        Files.write(file, line, Charset.forName("UTF-8"), APPEND);
      } catch (IOException ioe) {}
    }
  }

  /**
    * The method perfDifference benchmarks the dífference operation averaging over the parameter-given
    * run count and saves the results to a file
    * @param runs The number of repeated runs to average over
    * @author Juhani Seppälä
    */
  private static void perfDifference(int runs) {
    long totalTime = 0;
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    String fileName = "results-difference-" + timeStamp() + ".txt";
    Path file = Paths.get(fileName);
    try {
      Files.write(file, Arrays.asList("N\tTime(ms)"), Charset.forName("UTF-8"), CREATE, WRITE, TRUNCATE_EXISTING);
    } catch (IOException ioe) {}
    for (int i = 0; i <= MAXN; i += PERF_INCREMENT) {
      totalTime = 0;
      for (int j = 0; j < runs; j++) {
        RBTree<Integer> t1 = randomRBTree(i, new ArrayList<Integer>());
        RBTree<Integer> t2 = randomRBTree(i, new ArrayList<Integer>());
        long start = System.nanoTime();
        RBTree<Integer> test = t1.difference(t2);
        totalTime += (System.nanoTime() - start);
      }
      System.out.println("Difference average (N = " + i + ", runs = " + runs + "): " + df.format((double)(totalTime / runs) / 1000000) + " ms");
      List<String> line = Arrays.asList("" + i + "\t" + df.format((double)(totalTime / runs) / 1000000));
      try {
        Files.write(file, line, Charset.forName("UTF-8"), APPEND);
      } catch (IOException ioe) {}
    }
  }
  
  /**
    * The method printTree prints the parameter-given tree (pre-order)
    * @param tree The tree to be printed
    * @author Tero Kettunen
    */
  private static void printTree(RBTree tree){
    
    if(tree.isEmpty()){
      System.out.println("The tree is empty");
    }else{
      int n = tree.size();
      RBTreeNode root = tree.getRoot();
      System.out.println("\nThe root is " + root.getElement()); 
      System.out.println("Color (0=red, 1= black) |  element");
      
      PrintTreeHelper(root);
    }
  }
  
  /**
    * The method PrintTreeHelper is the recursion helper method of the method printTree
    * @param node The node to be handled
    * @author Tero Kettunen
    */
  private static void PrintTreeHelper(RBTreeNode node){
  
    RBTreeNode vl = node.getLeftChild();
    
    if(vl!=null && !vl.getSentinel()){
      PrintTreeHelper(vl);
    }
    
    System.out.println(node.getColor() + " | " + node.getElement() );
    
    RBTreeNode ol = node.getRightChild();
    
    if(ol!=null && !ol.getSentinel()){
      PrintTreeHelper(ol);
    } 
  }

  /**
    * The method randomRBTree generates and returns a new random tree based on the parameter-given size and
    * adds the added data to the parameter given list
    * @param n The size of the randomly generated tree
    * @param addedData The list to be appended with the nodes that were added to the tree
    * @return New random tree of size parameter n 
    */
  private static RBTree<Integer> randomRBTree(int n, ArrayList<Integer> addedData) {
    RBTree<Integer> tree = new RBTree<Integer>();
    for (int i = 0; i < n; i++){
      Integer x = r.nextInt(2 * n);
      RBTreeNode<Integer> node = tree.add(x);
      if (node != null)
        addedData.add(x);
    }
    return tree;
  }
  
  /**
    * The method minHeight calculates and returns the shortest path from the parameter-given node to a leaf
    * @param node The node for which the minimal height is to be calculated
    * @return The length of the shortest path from this node to a leaf node
    */
  private static int minHeight(RBTreeNode<Integer> node) {
    if (node == null || node.getSentinel())
      return 0;
    return 1 + Math.min(minHeight(node.getLeftChild()), minHeight(node.getRightChild()));
  }

  /**
    * The method maxHeight calculates and returns the longest path from the parameter-given node to a leaf
    * @param node The node for which the maximal height is to be calculated
    * @return The length of the longest path from this node to a leaf node
    */
  private static int maxHeight(RBTreeNode<Integer> node) {
    if (node == null || node.getSentinel())
      return  0;
    return 1 + Math.max(maxHeight(node.getLeftChild()), maxHeight(node.getRightChild()));
  }

  /**
    * The method blackHeight calculates and returns the number black nodes on the path from the parameter-given node to the leaves
    * @param node The node for which black-height is to be calculated
    * @return The number of black nodes on the path from parameter-given node to the leaves, or -1, if all paths dit not have same number of black nodes
    * @author Juhani Seppälä
    */
  private static int blackHeight(RBTreeNode<Integer> node) {
    int bh = 0;
    int bhl = 0;
    int bhr = 0;
    if (node.getColor() == 1)
      bh++;
    if (node.getLeftChild() != null)
      bhl = blackHeight(node.getLeftChild());
    else
      bhl = 1;
    if (node.getRightChild() != null)
      bhr = blackHeight(node.getRightChild());
    else
      bhr = 1;

    if (bhl != bhr)
      return -1;
    bh += bhl;
    return bh;
  }
  
  /**
    * The method checkRBTree validates the tree based on certain expected properties of Red-Black Trees:
    * 1) A node is either red or black
    * 2) The root is black
    * 3) A sentinel (nil) node is black
    * 4) If a node is red, both its children are black
    * 5) For each node: all simple paths from it to leaves contain same number of black nodes
    */
  private static boolean checkRBTree(RBTree<Integer> testTree) {
    if(testTree.isEmpty())
      return true;

    RBTreeNode<Integer> root = testTree.getRoot();

    if (!checkInOrderList(testTree.getOrderedListData())) {
      System.out.println("Not in order");
      return false;
    }

    /*
     * Property 2
     */
    if(root.getColor() != 1)
      return false;

    /*
     * General tree balance property
     */
    if (maxHeight(root) > minHeight(root) * 2) {
      System.out.println("Not balanced");
      return false;
    }

    /*
     * Property 5
     */
    if (blackHeight(root) == -1) {
      System.out.println("Black-height error");
      return false;
    }

    return checkRBTreeHelper(root);
  }
  
  /*
   * CheckRBTree recursion. Properties 3 and 4
   */
  private static boolean checkRBTreeHelper(RBTreeNode<Integer> node) {

    /*
     * Property 3
     */
    if (node.getSentinel() && node.getColor() != 1) 
      return false;

    if (!node.getSentinel()) {
      if (node.getLeftChild() == null || node.getRightChild() == null)
        return false;
    }

    /*
     * Property 4
     */
    if (node.getColor() == 0) {
      if (node.getLeftChild() != null && node.getLeftChild().getColor() == 0)
        return false;
      if (node.getRightChild() != null && node.getRightChild().getColor() == 0)
        return false;
    }

    if (node.getLeftChild() != null && !checkRBTreeHelper(node.getLeftChild()))
      return false;

    if (node.getRightChild() != null && !checkRBTreeHelper(node.getRightChild()))
      return false;

    return true; 
  }

  /**
     * The method checkInOrderList checks that a parameter-given list is in order
     * @param list The list to be checked
     * @return True if the parameter-given list was in order
     * @author Juhani Seppälä
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

  /**
    * The method timeStamp is a helper method for file timestamp generation using the Calendar class
    * @return The current time in the following (padded) format: yyyy-mm-dd-hh:mm:ss
    * @author Juhani Seppälä
    */
  private static String timeStamp() {
    Calendar now = Calendar.getInstance();
    String year = "" + now.get(Calendar.YEAR);
    int m = now.get(Calendar.MONTH) + 1;
    String month = "" + m;
    String day = "" + now.get(Calendar.DAY_OF_MONTH);
    String hour = "" + now.get(Calendar.HOUR_OF_DAY);
    String minute = "" + now.get(Calendar.MINUTE);
    String second = "" + now.get(Calendar.SECOND);

    month = String.format("%2s", month).replace(" ", "0");
    day = String.format("%2s", day).replace(" ", "0");
    hour = String.format("%2s", hour).replace(" ", "0");
    minute = String.format("%2s", minute).replace(" ", "0");
    second = String.format("%2s", second).replace(" ", "0");

    return year + "-" + month + "-" + day + "-" + hour + ":" + minute + ":" + second;
  }
  
} // class
