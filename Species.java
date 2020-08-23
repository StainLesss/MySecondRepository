/* Project 	:	NFP136 
 * Author	:	PUYGRENIER Solann
 * Date 	:	11 June 20
 * 
 * 
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Species {
	 private int id;
	 private String  taxon;	
	 private ArrayList<Species> notCompatible; 			// Liste des Especes(Objet) avec qui elle ne peut être melanger
	 private ArrayList<Integer> idSpeciesnotCompatible;	// Liste des ID d'especes(Entier) avec qui elle ne peut etre melanger
	 
	 static int cnt;	//cnt pout CouNTeur d'especes
	 
	 //Les deux HAshMap ci dessous sont tres importante, elles listent toutes les especes existante
	 //On peut accéder a une Espece(Objet) à partir de son Nom(String) ou ID d'especes(Entier)
	 //Si on a le nom de l'espece on peut obtenir son ID(Entier) ou l'especes elle meme (Objet)
	 public static HashMap<String,Integer> existingSpeciesName = new HashMap<String,Integer>();	
	 public static HashMap<Integer,Species> existingSpeciesID = new HashMap<Integer,Species>();	
	 
	 /*===========================  Constructeur ================================ 
	  * Une espece a forcement un taxon i.e. un nom d'especes */
	public Species(String aTaxon) {
		String UpperCaseTaxon = aTaxon.toUpperCase();		// Formate tout les noms en majuscule (esthetique)
		boolean testAlreadyCreatedTaxon=false;
		
		for(String Key :existingSpeciesName.keySet()) {//On va parcourir tout les noms existant pour vérifier si aTaxon peut etre ajoute
			if (Key.equals(UpperCaseTaxon)) {testAlreadyCreatedTaxon = true;}
		}
		if (testAlreadyCreatedTaxon==false){  	// Si le nom d'espece n'est pas déjà cree <=> est libre
			cnt++;
			this.taxon = UpperCaseTaxon;						
			this.id =cnt;
			this.notCompatible = new ArrayList<Species>();
			this.idSpeciesnotCompatible = new ArrayList<Integer>();
			existingSpeciesID.put(id,this);
			existingSpeciesName.put(UpperCaseTaxon, id);
		}
		else {System.out.println("Pas de nouvelle espece ajouté :"+aTaxon.toUpperCase()+" déjà existante");}
	}
	/*===========================================================================*/
	
	/*Entree : Une espece (Objet)
	 *Sortie : vide()
	 *Ajoute un enemie especes(Objet) et ID especes (Entier) a la Liste des especes incompatible 
	 *De plus, la rivalité est réciproque : L'enemi voit sa liste mise a jour aussi */
	public void addEnemy(Species aSpecies) {
		//L'instance d'Especes(Objet) est mise a jour
		this.notCompatible.add(aSpecies);
		this.idSpeciesnotCompatible.add(aSpecies.getID());
		//L'instance de l'Especes enemie(Objet) est mise a jour
		aSpecies.notCompatible.add(this);
		aSpecies.idSpeciesnotCompatible.add(this.getID());
	}
	
	/*Entree : un numero d'espece (Entier)
	 *Sortie : l'espece correspondante(Objet)*/	
	public static Species loadSpecies(int anId) {
		return existingSpeciesID.get(anId);
	}
	
	/*Entree : un nom d'espece (String)
	 *Sortie : l'espece correspondante(Objet)*/	
	public static Species loadSpecies(String aName) {
		int anId = existingSpeciesName.get(aName.toUpperCase());
		return existingSpeciesID.get(anId);
	}
	
	/*Entree : Une Especes(Objet) différente de l'original
	 * 		   un nom d'espece (String)
	*Sortie : vide()
	*Permet de mettre l'espece (Objet) correpondante a la Clef ID especes (Entier) dans la HashMap */	
	public static void updateSpecies(Species SpeciesWithUpdate,String aName) {
		int targetId = existingSpeciesName.get(aName.toUpperCase());//Convertit le nom en ID
		existingSpeciesID.replace(targetId, SpeciesWithUpdate);
	}
	
	/*Entree : Une Especes(Objet) différente de l'original
	 * 		   un id d'espece (Entier)
	*Sortie : vide()
	*Permet de mettre a jour la HashMap tel que la Clef (ID espece|Entier) sois l'espece (Objet) en entrée */	
	public static void updateSpecies(Species SpeciesWithUpdate,int targetId) {
		existingSpeciesID.replace(targetId, SpeciesWithUpdate);
	}
	
	/*Entree : vide()
	 *Sortie : Une Liste(ArrayList) des id d'especes incompatible*/
	public ArrayList<Integer> loadIncompatibleList(){
		return this.idSpeciesnotCompatible;
	}
	
	//========== Les getteur =============
	public int getID() {return this.id;}
	public String getTaxon() {return this.taxon;}
	//======= Les setteur ===============
	public void setTaxon(String AnewTaxon) {this.taxon = AnewTaxon;}
	
}
