import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

//import FormAnimalRecap.CustomActionListener;

public class FormRecap extends JFrame {
	
	public FormRecap(){    
		  String[] columns = { "Enclos ID", "Especes", "Animal"};
	      Object[][] data = refreshGeneralData();
	      
	      JTable table = new JTable(data, columns);
	      table.setFillsViewportHeight(true);
	      JScrollPane scrollPane = new JScrollPane(table);
	      
	      JLabel lblHeading = new JLabel("Liste du Zoo");
	      lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
	      
	      JButton ButtonRefresh= new JButton ("Rafraichir");
	      ButtonRefresh.addActionListener(new CustomActionListener());
	      
	      JPanel panneau = new JPanel();
	      panneau.setLayout(new BorderLayout());
	      panneau.add(lblHeading, BorderLayout.PAGE_START);
	      panneau.add(scrollPane, BorderLayout.CENTER);
	      panneau.add(ButtonRefresh,BorderLayout.SOUTH);
	 	     
	      setContentPane(panneau);
	      setSize(1000,500);
	      setVisible(true);     
	   }
	public FormRecap(int EnclotNumber) {
		Enclosure instanceEnclosure = Enclosure.loadEnclosure(EnclotNumber);
		
		
		String[] columns = { "Nom", "Especes", "ID Animal"};
	    Object[][] data = refreshEnclosure(instanceEnclosure);
	      
	    JTable table = new JTable(data, columns);
	    table.setFillsViewportHeight(true);
	    JScrollPane scrollPane = new JScrollPane(table);
		JLabel lblHeading = new JLabel("Liste de  L'enclot"+EnclotNumber);
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));
	    JPanel panneau = new JPanel();
	    panneau.setLayout(new BorderLayout());
	    panneau.add(lblHeading, BorderLayout.PAGE_START);
	    panneau.add(scrollPane, BorderLayout.CENTER);
 
	      setContentPane(panneau);
	      setSize(1000,500);
	      setVisible(true);     
		
	}

	  public Object[][] refreshEnclosure(Enclosure TargetEnclosure){
		  int lines = TargetEnclosure.residentList.size();
		  Object[][] data = new Object[lines][3];
		  for (int i=1;i<=lines;i++) {
	    	  data[i-1][0] = TargetEnclosure.residentList.get(i-1).getName();
			  data[i-1][1] =TargetEnclosure.residentList.get(i-1).getSpecies().getTaxon();
			  data[i-1][2] = TargetEnclosure.residentList.get(i-1).getAnimalID();
	      }
		  
		  return data;
	  }
	  public Object[][] refreshGeneralData(){
		  int lines = Animal.existingAnimal.size();
		  Object[][] data = new Object[lines][3];
		  
		  
		  /*for (int i=1;i<=lines;i++) {
	    	  Animal instanceAnimal = Animal.LoadAnimal(i);
	    	  data[i-1][0] = instanceAnimal.getEnclosureID();
			  data[i-1][1] =instanceAnimal.getSpecies().getTaxon();
			  data[i-1][2] = instanceAnimal.getName();
	      }*/
		  int i=0;
		  for(int aKey : Animal.existingAnimal.keySet()) {
			  Animal instanceAnimal = Animal.existingAnimal.get(aKey);
			  data[i][0] = instanceAnimal.getEnclosureID();
			  data[i][1] =instanceAnimal.getSpecies().getTaxon();
			  data[i][2] = instanceAnimal.getName();
			  i++;
		  }
		  
		  return data;
	  }
	  
	  
	  class CustomActionListener implements ActionListener{
		  public void actionPerformed(ActionEvent eve){
			  dispose();
			  new FormRecap();
			  
		  }
			  
	  }

}

