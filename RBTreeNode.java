/**
	* Luokka RBTreeNode on RBTree:n solmutyyppi
	* @author Juhani Seppälä
	* 
	*/
public class RBTreeNode<E> {


	/**
		* Luokan RBTreeNode konstruktori muodostaa parametrina saadusta objektista uuden
		* solmun
		* @param element solmun hyötytiedoksi tuleva data
		*
		*/
	public RBTReeNode(E element){
	}
	
	/**
		* Metodi getLeftChild palauttaa tarkasteltavan solmun vasemman lapsen
		* @return Solmun vasen lapsi tai null, jos solmulla ei ole vasenta lasta
		*
		*/
	public RBTreeNode<E> getLeftChild(){
	}
	
	/**
		* Metodi getRightChild palauttaa tarkasteltavan solmun oikean lapsen
		* @return Solmun oikea lapsi tai null, jos solmula ei ole oikeaa lasta
		*
		*/
	public RBTreeNode<E> getRightChild(){
	}
	
	/**
		* Metodi getParent palauttaa tarkasteltavan solmun vanhemman
		* @return Solmun vanhempi tai null, jos solmu on puun juurisolmu
		*/
	public RBTreeNode<E> getParent(){
	}
	
	/**
		* Metodi setLeftChild asettaa parametrina saadun solmun tämän solmun vasemmaksi
		* lapseksi
		* @param node Vasemmaksi lapseksi asetettava solmu
		*
		*/
	public void setLeftChild(RBTreeNode<E> node){
	}
	
	/**
		* Metodi setRightChild asettaa parametrina saadun solmun tämän solmun oikeaksi
		* lapseksi
		* @param node Oikeaksi lapseksi asetettava solmu
		*
		*/
	public void setRightChild(RBTreeNode<E> node){
	}
	
	/**
		* Metodi setParent asettaa parametrina saadun solmun tämän solmun vanhemmaksi
		* @param node Vanhemmaksi asetettava solmu
		*
		*/
	public void setParent(RBTreeNode<E> node){
	}
	
	/**
		* Metodi setColor asettaa parametrina saadun värin tämän solmun väriksi
		* @param color Solmun väriksi asettava väri ("r|b")
		*
		*/
	public void setColor(String color){
	}
	
	/**
		* Metodi getColor palauttaa tarkasteltavan solmun värin
		* @return Solmun väri ("r|b")
		*
		*/
	public String getColor(){
	}
	
}