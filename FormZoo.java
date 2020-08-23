import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class FormZoo extends JFrame  {

		private JButton ButtonCreateAnimal= new JButton ("Ajouter Animal");
		private JButton ButtonDeleteAnimal = new JButton("Supprimer un  Animal");
		private JButton ButtonSearchAnimal = new JButton("Rechercher un Animal");
		private JButton ButtonShowAnimals = new JButton("Afficher tout les Animeaux");
		private JButton ButtonMoveAnimal = new JButton("Déplacer un Animal");
		private JButton ButtonShowEnclosures = new JButton("Afficher tout les Enclots");
		private JButton ButtonDetailsEnclosure= new JButton("Afficher le contenu d'un Enclots");
		
		public static String UserInputAsString1;
		public static String UserInputAsString2;
		public static Integer UserInputAsInteger;
		public static boolean WorkedOrNot;
		
		
		public static String tableSpecies[];
		public static Integer tableEnclot[];
		
		
		public FormZoo(){
			
			//TABLE POUR CHOIX D'ESPECES (CONSTANT)
			int i=0;
			tableSpecies = new String[Species.existingSpeciesName.size()];
			for(String aKey : Species.existingSpeciesName.keySet()){
				tableSpecies[i]=aKey;
				i++;
			}
			
			//TABLE POUR CHOIX D'ENCLOT (CONSTANT)
			int j=0;
			tableEnclot = new Integer[Enclosure.existingEnclosure.size()];
			for(int aKey : Enclosure.existingEnclosure.keySet()){
				tableEnclot[j]=aKey;
				j++;
			}
			

			build();
		}
		private void build() {
			JPanel contenufenetre = new JPanel();
			
			contenufenetre.add(ButtonCreateAnimal);
			contenufenetre.add(ButtonDeleteAnimal);
			contenufenetre.add(ButtonSearchAnimal);
			contenufenetre.add(ButtonShowAnimals);	
			contenufenetre.add(ButtonMoveAnimal);
			contenufenetre.add(ButtonShowEnclosures);
			contenufenetre.add(ButtonDetailsEnclosure);
			
			this.setContentPane(contenufenetre);

			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.pack();	
			this.setLocationRelativeTo(null);

			
			ButtonCreateAnimal.addActionListener(new CustomActionListener());
			ButtonDeleteAnimal.addActionListener(new CustomActionListener());
			ButtonSearchAnimal.addActionListener(new CustomActionListener());
			ButtonShowAnimals.addActionListener(new CustomActionListener());
			ButtonMoveAnimal.addActionListener(new CustomActionListener());
			ButtonShowEnclosures.addActionListener(new CustomActionListener());
			ButtonDetailsEnclosure.addActionListener(new CustomActionListener());
			
		}
		
		public String[] updateAnimalTable() {
			int z=0;
			String[] tableAnimal = new String[Animal.existingAnimalName.size()];
			for(String aKey : Animal.tableLinkIdName.keySet()){
				tableAnimal[z]=aKey;
				z++;
			}
			return tableAnimal;
			
		}
		class CustomActionListener implements ActionListener{
			public void actionPerformed(ActionEvent eve){
				if(eve.getSource() == ButtonCreateAnimal){
					
					UserInputAsString1 = JOptionPane.showInputDialog("Nom de l'animal ?");	//CHOIX LIBRE
					
					UserInputAsString2= (String)JOptionPane.showInputDialog(null,			//CONSTRUCTION DES CHOIX
							"Merci de choisir une especes","Selection de l'especes",JOptionPane.QUESTION_MESSAGE,null,
							tableSpecies,tableSpecies[0]);
					
					UserInputAsInteger = (Integer)JOptionPane.showInputDialog(null,			//CONSTRUCTION DES CHOIX
							"Merci de choisir un enclot","Selection de l'enclot",JOptionPane.QUESTION_MESSAGE,null,
							tableEnclot,tableEnclot[0]);
					
					if((UserInputAsString1==null)||(UserInputAsString2==null)||(UserInputAsInteger==null)){WorkedOrNot=false;}//Si pas d'annulation..
					else {WorkedOrNot = Zoo.MyZoo.defineNewAnimal(UserInputAsString1, UserInputAsString2, UserInputAsInteger);}//..on crée un animal
					
					if(WorkedOrNot) {JOptionPane.showMessageDialog(null,"Animal crée avec succés","Information", JOptionPane.INFORMATION_MESSAGE);}//Message ok
					else {JOptionPane.showMessageDialog(null,"Erreur lors de la creation de cette Animal","Information", JOptionPane.ERROR_MESSAGE);}//Message probléme
				}
				
				if(eve.getSource() == ButtonDeleteAnimal){
					String[] AnimalTable = updateAnimalTable();//met a jour les animaux disponible
					
					if(AnimalTable.length == 0) {// si plus d'animaux
						JOptionPane.showMessageDialog(null,"Erreur : plus d'animaux présent dans le ZOO","Information", JOptionPane.ERROR_MESSAGE);
					}
					else {
						UserInputAsString1 = (String)JOptionPane.showInputDialog(null,
							"Merci de choisir un Animal","Selection de l'animal",JOptionPane.QUESTION_MESSAGE,null,
							AnimalTable,AnimalTable[0]);
						WorkedOrNot = Zoo.MyZoo.removeAnimal(UserInputAsString1);
						if(WorkedOrNot) {JOptionPane.showMessageDialog(null,"Animal retiré avec succés","Information", JOptionPane.INFORMATION_MESSAGE);}
						else {JOptionPane.showMessageDialog(null,"Erreur sur cette Animal: Anomalie sur les entrées saisies","Information", JOptionPane.ERROR_MESSAGE);}
					}
				}
				if(eve.getSource() == ButtonSearchAnimal){
					String[] AnimalTable = updateAnimalTable();
					UserInputAsString1 = (String)JOptionPane.showInputDialog(null,
							"Merci de choisir un Animal","Selection de l'animal",JOptionPane.QUESTION_MESSAGE,null,
							AnimalTable,AnimalTable[0]);
					
					JOptionPane.showMessageDialog(null,"Animal description :\n"
							+ "id :"+ Animal.LoadAnimal(UserInputAsString1).getAnimalID()+"\n"
							+ "Nom :"+ Animal.LoadAnimal(UserInputAsString1).getName()+"\n"
							+ "Enclot :"+ Animal.LoadAnimal(UserInputAsString1).getEnclosureID()+"\n"
							+ "Espece :"+ Animal.LoadAnimal(UserInputAsString1).getSpecies().getTaxon()+"\n"
							,"Information", JOptionPane.INFORMATION_MESSAGE);
				}
				
				if(eve.getSource() == ButtonMoveAnimal){
					String[] AnimalTable = updateAnimalTable();//met a jour les animaux disponible
					if(AnimalTable.length == 0) {// si plus d'animaux
						JOptionPane.showMessageDialog(null,"Erreur : plus d'animaux présent dans le ZOO","Information", JOptionPane.ERROR_MESSAGE);
					}
					UserInputAsString1 = (String)JOptionPane.showInputDialog(null,
							"Merci de choisir un Animal","Selection de l'animal",JOptionPane.QUESTION_MESSAGE,null,
							AnimalTable,AnimalTable[0]);
					UserInputAsInteger = (Integer)JOptionPane.showInputDialog(null,
							"Merci de choisir un enclot","Selection de l'enclot",JOptionPane.QUESTION_MESSAGE,null,
							tableEnclot,tableEnclot[0]);
					WorkedOrNot = Zoo.MyZoo.changeCage(UserInputAsString1, UserInputAsInteger);
					if(WorkedOrNot) {JOptionPane.showMessageDialog(null,"Animal deplacé avec succés","Information", JOptionPane.INFORMATION_MESSAGE);}
					else {JOptionPane.showMessageDialog(null,"Erreur sur le déplacement de l'Animal: Anomalie sur les entrées saisies","Information", JOptionPane.ERROR_MESSAGE);}
					
				}
				
				if(eve.getSource() == ButtonShowAnimals){
					new FormRecap();
					
				}
				if(eve.getSource() == ButtonShowEnclosures){
					int numberOfenclsoure = Enclosure.cnt;
					String message="Les enclos existant (par ID) :\n";
					for(int i=1;i<=numberOfenclsoure;i++) {
						message = message+"\t\t - "+i+"\n";
						
					}
					JOptionPane.showMessageDialog(null,message);  
					
				}
				
				if(eve.getSource() == ButtonDetailsEnclosure){
					UserInputAsInteger =  (Integer)JOptionPane.showInputDialog(null,
							"Merci de choisir un enclot","Selection de l'enclot",JOptionPane.QUESTION_MESSAGE,null,
							tableEnclot,tableEnclot[0]);
					System.out.print(UserInputAsInteger);
					new FormRecap(UserInputAsInteger);
					
				}
				
			}
				
				
				
		}	

	}


