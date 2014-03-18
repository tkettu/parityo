/**
	* Luokka BRTree
	* @author Tero Kettunen
	* @author Juhani Seppälä
	* 
	*/
public class RBTree<E> {
	
	private int size = 0;
	private BRTreeNode<E> root;
	

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
	}
	
	/**
		* Metodi setRoot asettaa parametrina saadun solmun puun juureksi
		* @author Juhani Seppälä
		* @param node Juureksi lisättävä solmu
		*
		*/
	public void setRoot(BRTreeNode<E> node){
	}

	/**
		* Metodi add muodostaa parametrina annetusta objektista uuden solmun ja lisää
		* sen puuhun ja kutsuu puun tasapainottavaa metodia fixBRTree
		* @author Juhani Seppälä
		* @param data Puuhun lisättävä objekti
		*
		*/
	public void add(E data){
	}
	
	/**
		* Metodi remove poistaa parametrina annetun objektin puusta ja kutsuu puun tasapainottavaa
		* metodia fixRBTree
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
	}

	/** 
		* Metodi union muodostaa yhdisteen kutsuttavasta ja parametrina saadusta puusta
		* @author Juhani Seppälä
		* @param t yhdisteeseen tuleva puu
		* @return Kahden puun joukko-opillista yhdistettä kuvaava uusi puu
		*
		*/
	public RBTree<E> union(RBTree<E> t) {
	}
	
	/** 
		* Metodi intersection muodostaa leikkauksen kutsuttavasta ja parametrina saadusta puusta
		* @author Tero Kettunen
		* @param t leikkaukseen tuleva puu
		* @return Kahden puun joukko-opillista leikkausta kuvaava uusi puu
		*
		*/
	public BRTree<E> intersection(BRTree<E> t) {
	}

	/** 
		* Metodi difference muodostaa leikkauksen kutsuttavasta ja parametrina saadusta puusta
		* @author Juhani Seppälä
		* @param t erotukseen tuleva puu
		* @return Kahden puun joukko-opillista erotusta kuvaava uusi puu
		*
		*/	
	public BRTree<E> difference(BRTree<E> t) {
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
	}

	/**
		* Metodi rightRotate suorittaa parametrina annetun solmun perusteella puulle oikean käännön
		* @author Tero Kettunen
		* @param node Solmu, josta alkaen oikea kääntö suoritetaan
		*
		*/
	private void rightRotate(E e) {
	}
	
}
