import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/*
 * La classe About hérite de JDialog ...
 */
public class About extends JDialog
{
	//On crée un objet de type Editeur ...
	private Editeur editeur; 
	
	//On crée un pointeur vers la fenetre ...
	private About about = this;
	
	//L'icone ...
	private ImageIcon img;
	
	//On crée 2 JPanel ...
	private JPanel panneau_name = new JPanel();
	private JPanel panneau_auteur = new JPanel();
	private JPanel panneau_home_page = new JPanel();
	private JPanel panneau_center = new JPanel();
	private JPanel panneau_south = new JPanel();
	
	//Les label ...
	private JLabel label_name;
	private	JLabel label_auteur = new JLabel("Auteur : ");
	private	JLabel label_name_auteur = new JLabel("Zakaria Dahani");
	private	JLabel label_home_page = new JLabel("Home Page : ");
	private JLabel label_emi = new JLabel("EMI");
	
	//Le bouton ...
	private JButton bouton_ok = new JButton("OK");
		
	//Constructeur par défaut ...
	public About()
	{
		//vide ..
	}
	
	//Constructeur surchargé ...
	public About(Editeur editeur)
	{
		//On initialise notre objet editeur ...
		this.editeur = editeur;
		
		//Configuration de la fenetre About ...
		this.setTitle("A Propos");
		this.setSize(500,300);
		this.setLocationRelativeTo(null);
		
		//On apelle la méthode traitementPanneaux() ...
		traitementPanneaux();
		
		//On apelle la méthode traitementEvenement() ...
		traitementEvenement();
		
		this.setVisible(true);
						
	}

	/*
	 * La méthode traitementPanneaux() ...
	 */
	private void traitementPanneaux()
	{
		img = new ImageIcon("images/icone.png");
		label_name = new JLabel("DEditeur V1.0",img,JLabel.LEFT);
		
		panneau_auteur.add(label_auteur);
		panneau_auteur.add(label_name_auteur);
		
		panneau_home_page.add(label_home_page);
		panneau_home_page.add(label_emi);
		
		panneau_center.setLayout(new GridLayout(3,1));
		panneau_center.add(panneau_auteur);
		panneau_center.add(panneau_home_page);
		
		panneau_south.add(bouton_ok);
		
		this.getContentPane().add(label_name,BorderLayout.NORTH);
		this.getContentPane().add(panneau_south,BorderLayout.SOUTH);
		this.getContentPane().add(panneau_center,BorderLayout.CENTER);
	}
	
	/*
	 * la méthode traitementEvenement() ...
	 */
	private void traitementEvenement()
	{
		//Lors du clique sur le bouton OK ...
		//On utilise une classe anonyme ...
		bouton_ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event_ok)
			{
				//On cache notre fenetre About ...
				about.setVisible(false);
				
			}
			
		});
	}
	
}
