import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//https://stackoverflow.com/questions/13212431/jpanel-vs-jframe-in-java
//		Voir https://stackoverflow.com/a/13212460  #David Kroukamp
//https://stackoverflow.com/questions/21375255/jpanel-positions-and-sizes-changes-according-to-screensize/21376596#21376596 
//   	Voir https://stackoverflow.com/a/21376596 #Paul Samsotha
//Utilisation efficasse de JOptionPane
//http://www.iro.umontreal.ca/~dift1170/A09/docPDF/chapit11.pdf
//		#M. Reid
//Site officiel d'oracle pour assimiler le "Layout Managers"
//https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
//De nombreux exemples pour comprendre Java
////https://www.javascan.com/1080/java-joptionpane-showconfirmdialog
//Cours de Mme EL ATTAR (CNAM)

public class FormInit extends JFrame {
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel bottomPanel;
	
	private JButton ButtonAddSpecies= new JButton ("Ajouter Especes");
	private JButton ButtonSetEnemy = new JButton("Definir Especes Incompatible");
	private JButton ButtonAddEnclosure = new JButton("Ajouter Enclos");
	private JButton ButtonShowEnclosures = new JButton("Nombre d'Enclos Existant");
	private JButton ButtonQuitAndContinu = new JButton("Start");
	private JButton ButtonOptimisation = new JButton("Optimisation Enclot/Especes");
	
	public static String UserInputAsString;
	public static boolean WorkedOrNot;
	

	public FormInit(){
		build();
	}
	private void build() {//Constructeur
		
		//Defini l'affichage du contenu des trois Panels
		leftPanel = new JPanel(new GridLayout(2,1,8,8));
		rightPanel = new JPanel(new GridLayout(3,1,4,4));
		bottomPanel = new JPanel(new FlowLayout());
		
		//Associe bouton et Panels
		leftPanel.add(ButtonAddSpecies);
		leftPanel.add(ButtonAddEnclosure);
		rightPanel.add(ButtonSetEnemy);
		rightPanel.add(ButtonShowEnclosures);
		rightPanel.add(ButtonOptimisation);
		bottomPanel.add(ButtonQuitAndContinu);
		
		leftPanel.setPreferredSize(rightPanel.getPreferredSize());// les boutons a gauche au meme dimmension qu'a droite
		
		//Defini la disposition des trois Panel
		this.setLayout(new BorderLayout());
		this.add(leftPanel, BorderLayout.LINE_START);
		this.add(rightPanel, BorderLayout.LINE_END);
		this.add(bottomPanel, BorderLayout.PAGE_END);
		
		
		this.pack();
		this.setLocationRelativeTo(null);// au Centre de l'�cran
		this.setVisible(true);
		
		//Associe bouton et evenements
		ButtonAddSpecies.addActionListener(new CustomActionListener());
		ButtonSetEnemy.addActionListener(new CustomActionListener());
		ButtonAddEnclosure.addActionListener(new CustomActionListener());
		ButtonQuitAndContinu.addActionListener(new CustomActionListener());
		ButtonShowEnclosures.addActionListener(new CustomActionListener());
		ButtonOptimisation.addActionListener(new CustomActionListener());
		
	}
	
	class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent eve){
			
			if(eve.getSource() == ButtonAddSpecies) {
				UserInputAsString = JOptionPane.showInputDialog("Taxon de l'espece ?(i.e. Especes)");
				//Si le bouton cancel est utilise ou pas d'information entree mais valide, informer l'utilisateur et ne sera pas initialiser
				if((UserInputAsString==null)||(UserInputAsString.length()==0)) {WorkedOrNot=false;}
				else{WorkedOrNot = Zoo.MyZoo.defineNewSpecies(UserInputAsString);}
				//Si l'especes a bien ete ajoute(Taxon valide entree), informer l'utilisateur et initialiser. Sinon inform� l'utilisateur de l'echec
				if(WorkedOrNot) {JOptionPane.showMessageDialog(null,"Esp�ces cr�e avec succ�s","Information", JOptionPane.INFORMATION_MESSAGE);}
				else {JOptionPane.showMessageDialog(null,"Erreur lors de la creation d'une nouvelle esp�ce","Information", JOptionPane.ERROR_MESSAGE);}
			}
			
			if (eve.getSource() == ButtonSetEnemy) {
				String TaxonA = JOptionPane.showInputDialog("Espece 1");
				String TaxonB = JOptionPane.showInputDialog("Espece 2");
				//Si le bouton cancel est utiliser pour l'une ou l'autre des especes, informer l'utilisateur et ne sera pas initialiser
				if((TaxonA ==null)||(TaxonB==null)) {WorkedOrNot = false;}
				else {WorkedOrNot=Zoo.MyZoo.setIncompatibleSpecies(TaxonA, TaxonB);}
				//Si l'especes a bien ete ajouter, informer l'utilisateur et initialiser. Sinon inform� l'utilisateur de l'echec
				if(WorkedOrNot) {JOptionPane.showMessageDialog(null,"Les "+TaxonA+" et "+TaxonB+" sont incompatible","Information", JOptionPane.INFORMATION_MESSAGE);}
				else {JOptionPane.showMessageDialog(null,"Anomali lors de l'ajout d'une r�gle d'incompatibilit�","Information", JOptionPane.ERROR_MESSAGE);}
			}
			
			if (eve.getSource() == ButtonAddEnclosure) {
				WorkedOrNot=Zoo.MyZoo.defineNewEnclsure();
				//Si ... Sinon mis en pr�ventif, un enclot sera forcement ajoute et une erreur sur cette aspect peut dificilement se produire
				if(WorkedOrNot) {JOptionPane.showMessageDialog(null,"Enclot ajouter","Information", JOptionPane.INFORMATION_MESSAGE);}
				else {JOptionPane.showMessageDialog(null,"Anomali lors de l'ajout d'un Enclot","Information", JOptionPane.ERROR_MESSAGE);}
			}
			
			if(eve.getSource() == ButtonShowEnclosures){
				//Liste les enclots vides et en informe l'utilisateur
				int numberOfenclsoure = Enclosure.cnt;
				String message="Les enclos existant (par ID) :\n";
				for(int i=1;i<=numberOfenclsoure;i++) {
					message = message+"\t\t - "+i+"\n";
					
				}
				JOptionPane.showMessageDialog(null,message);  
			}
			
			if (eve.getSource() == ButtonQuitAndContinu) {
				//quite la configuration des especes, enclot et enemie et lance la deuxi�me interface de gestion de zoo 
				dispose();
				new FormZoo();
			}
			
			//======== * * * * POUR L'EXTENSION DU PROJET * * * * ========

			if (eve.getSource() == ButtonOptimisation) {
				
				//Simulation
				Zoo.MyZoo.CreateTableSpeciesByQtyEnemy();//Initialise table
				Zoo.MyZoo.SortSpeciesByQtyEnemy();		//Table decroissante du nombre d'enemie par especes
				int rg = Zoo.MyZoo.SpeciesByQtyEnemy.length;
				String tipsForUser="";
				
				if (Enclosure.cnt == 0 ) {Zoo.MyZoo.defineNewEnclsure();}//forc�ment 1 enclot
				
				for(int i=0;i<rg;i++) {									//Pour chaque esp�ces pr�sente
					for(int j=1;j<=Enclosure.cnt;j++) {					//On essaye de mettre dans l'un des enclots
						
						WorkedOrNot = Zoo.MyZoo.defineNewAnimal(		//On cr�e un animal (temporaire)
								Integer.toString(i),
								Species.loadSpecies(Zoo.MyZoo.SpeciesByQtyEnemy[i][0]).getTaxon(),
								j);
						//Si on a r�ussi a mettre une especes dans un enclot on passe a l'esepces suivante
						if(WorkedOrNot == true) {	
							//On garde cette information pour plus tard
							tipsForUser = tipsForUser+Species.loadSpecies(Zoo.MyZoo.SpeciesByQtyEnemy[i][0]).getTaxon() +" dans l'enclot "+j+"\n";
							break;}
						}
					//Si on a pas d'enclot disponible pour cette especes, on cr�e un enclot, on l'ajoute et on passe a l'esepces suivante
					if(WorkedOrNot == false) {
						Zoo.MyZoo.defineNewEnclsure();
						Zoo.MyZoo.defineNewAnimal(
								Integer.toString(i),
								Species.loadSpecies(Zoo.MyZoo.SpeciesByQtyEnemy[i][0]).getTaxon(),
								Enclosure.cnt);//Animal sera suppirmer apres
						//On garde cette information pour plus tard
						tipsForUser = tipsForUser+Species.loadSpecies(Zoo.MyZoo.SpeciesByQtyEnemy[i][0]).getTaxon() +" dans l'enclot "+Enclosure.cnt+"\n";
						
					}
				}
				JOptionPane.showMessageDialog(null,"Nombres d'enclots : "+Enclosure.cnt+
						"\nPour une optimisation des enclot, il faut r�partire les especes dans les enclots tel que :\n"+tipsForUser//On informe l'utilisateur
						,"Information", JOptionPane.INFORMATION_MESSAGE);
				for(int i=0;i<rg;i++) {	//On supprime tout les animaux
					Zoo.MyZoo.removeAnimal(Integer.toString(i));
				}
			}
		}	
	}
}
	

