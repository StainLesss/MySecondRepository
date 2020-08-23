/* Project 	:	NFP136 
 * Author	:	PUYGRENIER Solann
 * Date 	:	11 June 20
 * 
 * 
 * 
 */

import java.util.LinkedList;
import java.util.HashMap;
import java.util.LinkedHashSet; //Une liste sans dupliquata



public class Enclosure {
	
	int enclosureID;
	static int cnt;
	LinkedList<Animal> residentList;	//Liste(LinkedList) des d'animaux(Animal) contenue dans l'enclot
	LinkedList<String> nameList; 		//Liste(LinkedList) des nom d'animaux(String) contenue dans l'enclot
	LinkedList<Species> speciesList; 	//Liste(LinkedList) des Especes(Objet) contenue dans l'enclot
	LinkedHashSet<Integer> enemyList;	//id des Especes (Entier) incompatible sans duplicata <=> liste noire de l'enclot
	
	//Liste(HashMap) permetant d'acceder avec un Id d'enclot(entier) a un enclot (Objet)
	static HashMap<Integer,Enclosure> existingEnclosure = new HashMap<Integer,Enclosure>();
	
	
	/*===========================  Constructeur ================================ */
	Enclosure(){
		cnt++;
		this.enclosureID=cnt;
		residentList = new LinkedList<Animal>();	
		nameList = new LinkedList<String>();
		speciesList =new LinkedList<Species>();
		enemyList = new LinkedHashSet<Integer>();
		existingEnclosure.put(enclosureID,this);
	}
	/* ============================================================================*/
	
	/*Entree : Un Animal(Objet)
	 *Sortie : Un booleen Vrai si l'animal a ete ajoute, faux sinon
	 *La fonction va mettre a jour les differents attributs de l'enclot (i.e. Liste de l'enclot) */
	public boolean addAnimal(Animal anAnimal){
		this.updateEnemyListe();
		if(this.enemyList.contains(anAnimal.getSpecies().getID())) {//regarde si l'animal est sur la liste noire des animaux interdit a cette enclot
			System.out.println("Espece incompatible avec l'Enclot No:"+this.enclosureID);
			return false;	//Permetra de verifier a plus haut niveaux qu'un animal n'a pas été crée
		}
		else {
			this.residentList.add(anAnimal);
			this.nameList.add(anAnimal.getName());
			this.speciesList.add(anAnimal.getSpecies());
			this.enemyList.addAll(anAnimal.getSpecies().loadIncompatibleList());	//Ajoute toutes les ID d'espéces incompatible de cette animal a l'enclot
			return true;	//Permetra de vérifier a plus haut niveaux qu'un animal a été crée
		}	
	}
	
	/*Entree : Un Animal(Objet)
	 *Sortie : Un booleen Vrai si l'animal a ete supprimé, faux sinon
	 *La fonction va mettre a jour les differentes attributs de l'enclot (i.e. Liste de l'enclot)
	 *La liste des enemie va etre regenere en utilsant les especes pdéja présente dans l'enclot*/
	public boolean removeAnimal(Animal anAnimal) {
		
		if(this.nameList.contains(anAnimal.getName())) {
			this.residentList.remove(anAnimal);
			this.nameList.remove(anAnimal.getName());
			this.speciesList.remove(anAnimal.getSpecies());
			this.updateEnemyListe();// re genere la liste des Id d'especes incompatible
			return true;
		}
		else {
			return false;
		}
	}

	/*Entree : vide()
	 *Sortie : vide() 
	 re-genere la liste noir (static LinkedHashSet) de toutes les ID d'especes(Entier) qui ne peuvent pas cohabiter dans l'enclot.
	 */
	public void updateEnemyListe() {		
		this.enemyList.clear();
		for(int i=0;i<this.speciesList.size();i++) {//Pour chaque espece dans l'enclot
			this.enemyList.addAll(this.speciesList.get(i).loadIncompatibleList());
		}
	}
	
	/*Entree : ID enclot (Entier)
	 *Sortie : Enclot(Objet)
	 *Permet a partir d'un ID (Entier) l'obtenir un Enclot(Objet)*/
	public static Enclosure loadEnclosure(int idEnclosure) {
		return existingEnclosure.get(idEnclosure);
	}
	/*Entree : ID enclot (Entier)
	 *         un enclot(Objet)
	 *Sortie : vide()
	 *Permet de remplacer/Mettre a jour l'enclot(Objet) en entrée par celui existant dans la HashMap a la même ID d'enclot*/
	public static void UpdateEnclosure(int idEnclosure,Enclosure anUpdateEnclosure) {
		existingEnclosure.replace(idEnclosure,anUpdateEnclosure);
	}
	
	/*Entree : enclot (Objet)
	 *Sortie : vide()
	 *Permet de remplacer/Mettre a jour l'enclot(Objet) en entrée par celui existant dans la HashMap au même ID d'enclot*/
	public static void UpdateEnclosure(Enclosure anUpdateEnclosure) {
		anUpdateEnclosure.getID();
		existingEnclosure.replace(anUpdateEnclosure.getID(),anUpdateEnclosure);
	}
	
	//getteur
	public int getID() {
		return this.enclosureID;
	}
	
	
}
	



