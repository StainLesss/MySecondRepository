/* Project 	:	NFP136 
 * Author	:	PUYGRENIER Solann
 * Date 	:	11 June 20
 * 
 * 
 * 
 */

import java.util.HashMap;
import java.util.ArrayList;

public class Animal {
	
	private int enclosureID;
	private int animalID;
	private String name;
	private Species species;
	private static int cnt;		//cnt pour CouNTeur
	
	//Listes des noms de tous les animaux existants (chaque élément est unique)
	// L'appel updateExistingName() permet de re-générer les noms existants a partir 
	public static ArrayList<String> existingAnimalName= new ArrayList<String>();
	
	//Les deux HAshMap ci dessous sont tres important
	//On peut accéder a un Animal(Objet) à partir de son Nom ou ID
	//La suppression ou l'ajout d'un animal mettra à jour ces listes
	public static HashMap<String,Integer> tableLinkIdName = new HashMap<String,Integer>();		//<Name,ID_Animal>
	public static HashMap<Integer,Animal> existingAnimal = new HashMap<Integer,Animal>();		//<ID_Animal ,Animal>
	
	
	Animal(){
		
	}
	
	/*===========================  Constructeur ================================ 
	 * Un animal a forcémment
	 * Un nom
	 * Une espéce
	 * Un numéro de cage */
	Animal(String aName,Species aSpecies,int aCageID ){
		if (existingAnimalName.contains(aName)) {	//Si nom déja existant 
			System.out.println("Nom déja existant");
		}
		else {//Sinon on crée bien l'objet Animal
			cnt++;
			this.animalID = cnt;		//L'id a pour valeur son numéro de création
			this.enclosureID = aCageID;
			this.name = aName;
			this.species = aSpecies;
			existingAnimalName.add(aName);
			tableLinkIdName.put(aName,cnt); //Associe a un 	Nom(Clef/String) => un ID_Animal(Integer)
			existingAnimal.put(cnt,this);	//Associe a un 	ID_Animal(Clef/Integer)	=> un Animal(Objet)
			}
	}
	/*=========================================================================*/
	
	/*Entrée : un numéro d'animal/ ID Animal (entier)
	 *Sortie : l'animal correspondant(Animal)*/
	public static Animal LoadAnimal(int anID) {
		return existingAnimal.get(anID);
	}
	/*Entrée :un Nom (String)
	 *Sortie : l'animal correspondant(Animal)*/
	public static Animal LoadAnimal(String itsName) {
		int id = tableLinkIdName.get(itsName);
		return existingAnimal.get(id);
	}
	
	/*Entrée : ID animal (entier)
	 *Sortie : vide ()
	 *Supprime des éléments des deux HAshmap statiques correspondant a l'animal(ID/NOM/Animal) en question et empéchant ainsi un accés future  */
	public static void RemoveAnimal(int id) {
		String NameToDelete = Animal.LoadAnimal(id).getName();
		
		tableLinkIdName.remove(NameToDelete);
		existingAnimal.remove(id);
		Animal.updateExistingName();//Re-génére la ArrayListe des noms existants
	}
	/*Entrée : un Animal (Objet Animal)
	 *Sortie : vide ()
	 *Supprime des éléments des deux HAshmap statiques correspondant à l'animal(ID/NOM/Animal) en question et empéchant ainsi un accés future  */
	public static void RemoveAnimal(Animal anAnimal) {
		int IdToDelete = anAnimal.getAnimalID();
		String NameToDelete = anAnimal.getName();
		
		tableLinkIdName.remove(NameToDelete);
		existingAnimal.remove(IdToDelete);
		Animal.updateExistingName();//Re-génére la ArrayListe des noms existants
	}
	
	/*Entrée : un nom d'animal (String)
	 *Sortie : vide ()
	 *Supprime des éléments des deux HAshmap statiques correspondant à l'animal(ID/NOM/Animal) en question et empéchant ainsi un accés future  */
	public static void RemoveAnimal(String Name) {
		int IdToDelete = Animal.LoadAnimal(Name).getAnimalID();
		
		tableLinkIdName.remove(Name);
		existingAnimal.remove(IdToDelete);
		Animal.updateExistingName();//Re-génére la ArrayListe des noms existants
	}
	
	/*Entrée : vide()
	 *Sortie : vide()
	 *Re-génére les noms des animaux existant a partir de la Hasmap des Numéro d'animau(Clef/entier) => Animal (Objet) 
	 *A utliser aprés avoir supprimer un animal*/
	private static void updateExistingName() {
		existingAnimalName.clear();
		for(int key : existingAnimal.keySet() ) {
			existingAnimalName.add(Animal.LoadAnimal(key).getName());
		}
	}
	
	/*Entrée : Un Animal(Objet)
	 *Sortie : vide()
	 *A utiliser aprés un LoadAnimal(). Cela permet de mettre a jour un Animal(Objet) dont les 
	 *attributs auraient été modifier en mettant à jour les deux HAshmap.
	 */
	public static void updateAnimal(Animal UpdatedAnimal) {
		int id = tableLinkIdName.get(UpdatedAnimal.getName());
		existingAnimal.replace(id, UpdatedAnimal);
	}
	
	
	//======== Les getteurs de l'objet ==========
	public Species getSpecies() {return this.species;}
	public int getAnimalID() {return this.animalID;}
	public int getEnclosureID() {return this.enclosureID;}
	public String getName() {return this.name;}
	
	//======== Les setteur de l'objet ===========
	public void setEnclosureID(int EnclosureDestinationID) {this.enclosureID = EnclosureDestinationID;}
	public void setName(String rename) {this.name = rename;}
	public void setSpecies(Species aSpecies) {this.setSpecies(aSpecies);}
		
}
