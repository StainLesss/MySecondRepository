
public class Zoo {
	public String Titre;
	static Zoo MyZoo;//L'objet Static
	
	
	//== UTILE POUR L'EXTENSION DU PROJET ==
	static int[][] SpeciesByQtyEnemy;//Tableau d'especes par ID et nombre/quantité(Entier) d'enemies
	//== voir plus bas ==
	
	
	//============ Constructeur ====================
	Zoo(){
		Titre = "Zoo d'amnéville";
		MyZoo = this;	
	}
	//===============================================	
	
	
	
	/*Entree : vide()
	 *Sortie : Si l'ajout a bien fonctionne
	 *Definit un nouvel enclot(Objet) au Zoo */
	public boolean defineNewEnclsure() {
		Enclosure instance  = new Enclosure();
		return true;
	}
	
	/*Entree : Un nom d'especes(String)
	 *Sortie : Un boleen Vrai si une nouvelle especes(Objet) a ete cree sans erreur (par exemple un Taxon déja crée)
	 *Crée une nouvelle instance d'especes qui, grace au constructeur, sera ajouter parmis les especes disponible (voir constructeur de l'espece */
	public boolean defineNewSpecies(String aTaxon) {
		//Si le taxon n'est pas disponible(i.e. deja cree) , ne pas initialiser
		if(isAnExistingSpecies(aTaxon)) {return false;}
		else {
			Species instance = new Species(aTaxon.toUpperCase());
			return true;
		}
	}
	
	
	/*Entree :Un nouveaux nom d'animal(String)
	 * 		  Un taxon (String)
	 * 	      Un numero de cage(Entier)
	 *Sortit :Si l'ajout a fonctionner(Booleen)
	 *Fonction vérifiant que les entrees soient juste puis 
	 *va en charger l'espece(Objet) a partir du Taxon(Entree;String) et crée un Animal(Objet) avec le constructeur
	 *Animal(Nom de l'animal(Entré) , L'especes(Objet) , Le numero de Cage (Entre)	 */
	public boolean defineNewAnimal(String name,String aSpecies,int cageID) {
		boolean isAdded;
		Enclosure instanceCage = Enclosure.loadEnclosure(cageID);
		
		//Si l'espece n'existe pas, ne pas initialiser
		if(!isAnExistingSpecies(aSpecies)) {return false;}
		//Si l'enclot n'existe pas, ne pas initialiser 
		if(!isAnExistingCage(cageID)) {return false;}
		//Si l'especes de l'animal fait partie de la liste noir de l'enclot, ne pas initialiser
		if(instanceCage.enemyList.contains( Species.loadSpecies(aSpecies).getID() )) {return false;}
		
		Animal instanceLivingThing = new Animal(name,Species.loadSpecies(aSpecies),cageID);
		
		isAdded = instanceCage.addAnimal(instanceLivingThing); //ajout de l'animal a l'enclot
		
		//Si l'animal a bien ete ajouter on met a jour l'enclot
		if(isAdded) {Enclosure.UpdateEnclosure(cageID, instanceCage);}
		
		return (isAdded);
		}
	
	
	/*Entree : Un nom d'animal(String)
	 *Sortie : Si l'ajout a fonctionner (booleen)
	 *Procedure qui va controler que l'entré de l'utilisateur est juste puis 
	 * a partir du nom de l'animal (Entree;String) va chargé l'animal(Objet). De cette animal nous allons pouvoir
	 *charger un numero d'enclot(Entier) servant a charger l'enclot(Objet) a mettre a jour
	 *On lui supprime alors l'animal puis on met a jour l'enclot  */
	public boolean removeAnimal(String aName) {
		//Si le nom de l'animal n'existe pas , ne pas initialiser
		if(!isAnExistingAnimal(aName)) {return false;}
		
		Animal instanceAnimal;
		instanceAnimal= Animal.LoadAnimal(aName);
		int idCageOfAnimal = instanceAnimal.getEnclosureID();
		
		Enclosure instanceEnclosure;
		instanceEnclosure = Enclosure.loadEnclosure(idCageOfAnimal);
		int idEnclosure = instanceEnclosure.getID();
		
		instanceEnclosure.removeAnimal(instanceAnimal);	// On enléve l'animal(Objet) a l'enclot
		Animal.RemoveAnimal(aName);					  	// On enléve le nom d'animal a la liste des nom
		
		Enclosure.UpdateEnclosure(idEnclosure, instanceEnclosure);//On met a jour l'enclot
		return true;
	}
	
	/*Entree : Nom d'especes (String)
	 *Sortie : Si ce nom existe en tant qu'especes possible(booleen)
	 *Renvoi vrai si le nom de l'especes existe déja parmi les especes deja cree, faux sinon*/
	private boolean isAnExistingSpecies(String speciesName) {
		speciesName = speciesName.toUpperCase();
		//Si l'espece n'existe pas, ne pas initialiser
		if(Species.existingSpeciesName.containsKey(speciesName)) {return true;}
		return false;
	}
	
	/*Entree : Nom d'animal (String)
	 *Sortie : Si ce nom existe parmi les animaux crée (booleen) 
	 *Renvoi vrai si le nom de l'animal existe déja parmi les especes deja cree, faux sinon */
	private boolean isAnExistingAnimal(String Name) {
		//Si l'animal n'existe pas, ne pas initialiser
		return Animal.existingAnimalName.contains(Name); //result;
	}
	
	/*Entree : Numero d'enclot (Entier)
	 *Sortie : Si le numero d'enclot demandé existe ou non (booleen)*/	
	private boolean isAnExistingCage(int aCageNo) {
		if ( (aCageNo > Enclosure.existingEnclosure.size()) || (aCageNo<0) ) {
			return false;
			
		}
		return true;
	}
	
	/*Entree : Une Espece A(String)
	 * 		   Une Espece B(String) 
	 *Sortie : Si le reglage d'especes incompatible a bien fonctionner(booleen)
	 *Procedure qui teste si les valeur de l'utilisateur peuvent etre utiliser puis
	 *Va depuis les noms A(String) et B(String) charger les especes(Objet) correspondantes
	 *puis definie les especes en enemie et enfin mettre a jours les especes*/
	public boolean setIncompatibleSpecies(String SpeciesA,String SpeciesB) {
		//Si l'espece A n'existe pas ou B n'existe pas, ne pas initialiser
		if(((!isAnExistingSpecies(SpeciesA))||(!isAnExistingSpecies(SpeciesB)))) {return false;}
		
		Species A;
		Species B;
		A = Species.loadSpecies(SpeciesA);
		B =  Species.loadSpecies(SpeciesB);
		A.addEnemy(B);//A enemie de B et B enemie de A
		Species.updateSpecies(A, SpeciesA);
		Species.updateSpecies(B, SpeciesB);
		return true;
	}
	
	/*Entree : Un nom d'animal(String)
	 *		   Une cage de destination(Entier)
	 *Sortie : Si le tranfert a fonctionne(Booleen)
	 *Procedure qui va charger l'animal(Objet) conscerne puis l'enclot d'origine(Objet) et l'enclot de destination(Objet)
	 *On vérifie que l'especes de l'animal n'est pas parmis la liste noir de l'enclot.
	 *Si c'est bon, on enleve l'animal de l'enclot d'origine, on l'ajoute a celui de destination et on met a jour les enclots	
	 *enfin on associe le nouvelle enclot de l'animal à l'animal lui même et enfin on met a jour l'animal*/
	public boolean changeCage(String aName,int aNewCageId) {
		Animal instanceAnimal;
		instanceAnimal = Animal.LoadAnimal(aName);
		
		int previousCageID = instanceAnimal.getEnclosureID();
		Enclosure A_instanceEnclosureOrigin = Enclosure.loadEnclosure(previousCageID);
		
		Enclosure B_instanceEnclosureDesitnation = Enclosure.loadEnclosure(aNewCageId);
		
		//Si l'espéces est parmi la liste noir de l'enclot alors on ne fair rien
		if(B_instanceEnclosureDesitnation.enemyList.contains(instanceAnimal.getSpecies().getID())) {return false;}
		
		A_instanceEnclosureOrigin.removeAnimal(instanceAnimal);
		B_instanceEnclosureDesitnation.addAnimal(instanceAnimal);
		
		Enclosure.UpdateEnclosure(previousCageID,A_instanceEnclosureOrigin);
		Enclosure.UpdateEnclosure(aNewCageId,B_instanceEnclosureDesitnation);

		instanceAnimal.setEnclosureID(aNewCageId);
		Animal.updateAnimal(instanceAnimal);
		return true;
	}
	
	
	// * * * ======== POUR L'EXTENSION DU PROJET ======== * * *
	
	/*Entree : vide()
	 *Sortie : vide()
	 *Initialise le tableau (Static) a deux dimmension(Entier)
	 *puis on y place pour chaque especes son Id especes(Entier) et la quantité/le nombre d'enemie(Entier)
	 *Le tableau n'est pas trier mais le sera via un appel ultérieur (  SortSpeciesByQtyEnemy()  ) */
	public void CreateTableSpeciesByQtyEnemy() {
		/*Initialise le tableau (static) des n especes existante en créant un tableau de deux dimensions
		 *	Numero 						1		2		...		n
		 * 	ID							ID1		ID2		...		IDn
		 * 	Quantité d'énemie			Qty1	Qty2	...		Qtyn
		 * 
		 */
		int QtyEnemie;
		int n = Species.existingSpeciesID.size();
		int cnt=0;
		
		SpeciesByQtyEnemy = new int[n][2];
		for(int aKey : Species.existingSpeciesID.keySet()) {
			QtyEnemie = Species.loadSpecies(aKey).loadIncompatibleList().size();
			SpeciesByQtyEnemy[cnt][0]=Species.existingSpeciesID.get(aKey).getID();	//ID
			SpeciesByQtyEnemy[cnt][1]=QtyEnemie;									//Qty
			cnt++;
		}
	}
	/*Entree : vide()
	 *Sortie : vide()
	 *Trie le tableau(Static) de maniere decroisante en metant les especes au plus petit nombre d'enemeie a la fin du tableau
	 *Cela permet d'obtenir un tableaux avec les especes au plus grand nombre d'enemie en tete du tableau*/	
	public void SortSpeciesByQtyEnemy() { 
		/* [ TRIE PAR SELECTION ]
		 * Trie le tableau (static) de maniére décroissante : les espéces aux plus grandes quantités d'enemies se retrouve en haut du classement
		 * On obtient un tableau telle que 
		 *      pour j et k des entiers appartenant a n on a pour tout j>k,  Aj>Ak avec Qty(Aj)>Qty(Ak)
		 *	Rang 						A1		A2		...		An
		 * 	ID							ID_A1	ID_A2	...		ID_n
		 * 	Quantité d'énemie			Qty(A1)	Qty(A2)	...		Qty(An)
		 */
		int n = Species.existingSpeciesID.size();
		int place,qty;
		for(int i=n-1;i>=0;i--) {				//On permute en partant de la fin le minimum du tableau
			qty = SpeciesByQtyEnemy[i][1];
			place= placeMin(i);
			permut(place,i);	
		}		
	}
		
	/*Entree : un indice du tableau(Entier) au dessus du quel on arrete de comparer
	 *Sortie : l'indice(Entier) du minimum dans le tableau(Static) SpeciesByQtyEnemy de la classe Zoo
	 *Renvoi la place de la quanité mimimum dans l'interval [0;ni] du tableau(Static)*/
	private int placeMin(int ni) {
		int min,ind;
		min = SpeciesByQtyEnemy[0][1];
		ind = 0;
		for(int i=0;i<=ni;i++) {
			if (SpeciesByQtyEnemy[i][1]<=min) {
				ind = i;
				min = SpeciesByQtyEnemy[i][1];		
			}
		}
		return ind;
	}
	
	/*Entree : un indice i du tableau(Entier)
	 *  	   un second indice ind tableau(Entier)
	 *Sortie : vide()
	 *Permute les valeures i(Entier) et ind(Entier) du tableau SpeciesByQtyEnemy de la classe Zoo*/
	private void permut(int i,int ind) {
			int[]tmp = new int[2];
			
			tmp[0]=SpeciesByQtyEnemy[ind][0];
			tmp[1]=SpeciesByQtyEnemy[ind][1];
			
			SpeciesByQtyEnemy[ind][0]= SpeciesByQtyEnemy[i][0];
			SpeciesByQtyEnemy[ind][1]= SpeciesByQtyEnemy[i][1];
			
			SpeciesByQtyEnemy[i][0]=tmp[0];
			SpeciesByQtyEnemy[i][1]=tmp[1];
	}	
		
}
