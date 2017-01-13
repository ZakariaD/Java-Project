import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 * Notre classe Police va hériter de la classe JFrame ... 
 */
public class Police extends JDialog
{

	/*
	 * Déclarations ...
	 */
	
	//Un pointeur vers la JDialogue Police ...
	private Police maPolice = this;
	
	//L'objet editeur de type Editeur ...
	private Editeur editeur;
	
	//Les différentes listes du panneau Police ...
	private JComboBox list_police;
	private JComboBox  list_style;
	private JComboBox  list_taille;
		
	//Les différents panneaux ...
	private JPanel panneau_police = new JPanel();
	private JPanel panneau_style = new JPanel();
	private JPanel panneau_taille = new JPanel();
	private JPanel panneau_bouton = new JPanel();
	private JPanel panneau_principal = new JPanel();
	
	//Les champs de texte pour voir les valeurs saisies ...
	private JTextField champ_police = new JTextField();
	private JTextField champ_style = new JTextField();
	private JTextField champ_taille = new JTextField();

	//On définit un JLabel pour voir l'aperçu du texte 
	//avant la confirmation ...
	private JLabel aperçu = new JLabel("AaBbYyZz");
	
	//On définit les autres JLabel ...
	private JLabel label_police = new JLabel("Police :");
	private JLabel label_style = new JLabel("Style :");
	private JLabel label_taille = new JLabel("Taille :");

	//Les donneés des diféerentes listes ...
	private String[] data_police = {"Times New Roman","Arial","Arial Black"
									,"Arial Narrow","MS Sans Serif",
									"Century Gothic","Comic Sans MS",
									"Courier"};
	private String[] data_style = {"Standard","Italique","Gras","Gras italique"};
	private String[] data_taille = {"8","9","10","11","12","14","16","18","20","22"
									,"24","26","28","36","48","72"};
	
	//On défint les layouts ...
	private GridLayout layout_police = new GridLayout(3,1);
	private GridLayout layout_style = new GridLayout(3,1);
	private GridLayout layout_taille = new GridLayout(3,1);

	//Les boutons
	private JButton bouton_ok = new JButton("OK");
	private JButton bouton_annuler = new JButton("Annuler");
	
	//Les objets statics ...
	private static String police,style,taille;
	
	//Un objet de type Font ... ...
	private Font font;
	
	//Constructeur par défaut ...
	public Police()
	{
	//vide ...
	}
	
	//Constructeur surchargé ...
	public Police(Editeur editeur)
	{
		//On initialise notre objet editeur ...
		this.editeur = editeur;
		
		//On récupère la police de la zone de texte ...
		this.font = this.editeur.getTextAreaFont();
		
		//On initialise les objets statics ...
		this.police = this.font.getName();
		this.taille = Integer.toString(this.font.getSize());
		this.style = data_style[0];
		
		//Si on a un autre style dans la zone de texte ...
		if(this.font.getStyle() == Font.BOLD)
		{
			this.style = "Gras";
		}
		if(this.font.getStyle() == Font.ITALIC)
		{
			this.style = "Italique";
		}
				
		//Configuration de la fenetre Police ...
		this.setTitle("Police");
		this.setSize(500,300);
		this.setLocationRelativeTo(null);
						
		//On fait appelle à la méthode traitementListes() ...
		traitementListes();
		
		//On fait appelle à la méthode traitementChamps() ...
		traitementChamps();
		
		//On fait appelle à la méthode traitementPanneaux() ...
		traitementPanneaux();
		
		//On apelle la meéthode traitementEvenement ...
		traitementEvenement();
		
		//On apelle la méthode formatTexte() ...
		formatTexte();
		
		this.getContentPane().add(panneau_principal,BorderLayout.CENTER);
		this.getContentPane().add(panneau_bouton,BorderLayout.SOUTH);
		this.setVisible(true);
			
	}
	
	/*
	 * la méthode traitementListes() ...
	 */
	private void traitementListes()
	{
		//Configuration de list_police ...
		list_police = new JComboBox (data_police);
		
		//Choix par défaut de la police...
		list_police.setSelectedItem(this.police);
		
		//Configuration de list_style ...
		list_style = new JComboBox (data_style);
		
		//Choix par défaut du style ...
		list_style.setSelectedItem(this.style);
		
		//Configuration de list_taille...
		list_taille = new JComboBox(data_taille);
		
		//Choix par défaut de la taille ...
		list_taille.setSelectedItem(this.taille);
	}
	
	/*
	 * La méthode traitementChamps ...
	 */
	private void traitementChamps()
	{
		//Texte par défaut du champ_police ...
		champ_police.setText(list_police.getSelectedItem().toString());
		
		//Texte par défaut du champ_style ...
		champ_style.setText(list_style.getSelectedItem().toString());
		
		//Texte par défaut du champ_taille ...
		champ_taille.setText(list_taille.getSelectedItem().toString());
	}
	
	/*
	 * La méthode traitementPanneaux() ...
	 */
	private void traitementPanneaux()
	{
		//Pour les 3 panneaux, on attribue un layout ...
		panneau_police.setLayout(layout_police);
		panneau_style.setLayout(layout_style);
		panneau_taille.setLayout(layout_taille);
		
		//On remplit le panneau_police ...
		panneau_police.add(label_police);
		panneau_police.add(champ_police);
		panneau_police.add(list_police);

		//On remplit le panneau_style ...
		panneau_style.add(label_style);
		panneau_style.add(champ_style);
		panneau_style.add(list_style);
		
		//On remplit le panneau_taille ...
		panneau_taille.add(label_taille);
		panneau_taille.add(champ_taille);
		panneau_taille.add(list_taille);
		
		//On remplit le panneau_pricipal ...
		panneau_principal.add(panneau_police);
		panneau_principal.add(panneau_style);
		panneau_principal.add(panneau_taille);
		
		//On définit les dimensions de l'aperçu ...
		aperçu.setPreferredSize(new Dimension(300,100));
		
		//On définit le type de bordure pour l'aperçu...
		aperçu.setBorder(BorderFactory.createTitledBorder("Aperçu"));
		
		//On définit l'alignement de l'aperçu ...
		aperçu.setHorizontalAlignment(JLabel.CENTER);
		
		//On ajoute l'aperçu au panneau_principal ...
		panneau_principal.add(aperçu);
		
		//On remplit le panneau_bouton ...
		panneau_bouton.add(bouton_ok);
		panneau_bouton.add(bouton_annuler);

		
	}
	
	/*
	 * La méthode traitementEvenement() ...
	 */
	private void traitementEvenement()
	{
		//Quand on choisit une police ou un style ou une taille ...
		list_police.addActionListener(new PoliceListener());
		list_style.addActionListener(new StyleListener());
		list_taille.addActionListener(new TailleListener());
		
		//Quand on clique sur le bouton_ok ou bouton_annuler ...
		bouton_ok.addActionListener(new OkListener());
		bouton_annuler.addActionListener(new AnnulerListener());

	}
	
	/*
	 * La méthode formatTexte() ...
	 */
	private void formatTexte()
	{ 
		if(style.equals("Standard"))
		{
			aperçu.setFont(new Font(police,Font.TRUETYPE_FONT,Integer.valueOf(taille).intValue()));
		}
		if(style.equals("Italique"))
		{
			aperçu.setFont(new Font(police,Font.ITALIC,Integer.valueOf(taille).intValue()));

		}
		if(style.equals("Gras"))
		{
			aperçu.setFont(new Font(police,Font.BOLD,Integer.valueOf(taille).intValue()));

		}
		if(style.equals("Gras italique"))
		{
			//???? ...
		}
	}
	
	/*
	 * Définition de la classe interne PoliceListener
	 * qui implemente l'interface ActionListener ...
	 */
	private class PoliceListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent event_police) 
		{
			//On obtient le nom de la police ...
			police = list_police.getSelectedItem().toString();
			
			//Texte par défaut du champ_police ...
			champ_police.setText(police);
			
			//On apelle la méthode formatTexte() ...
			formatTexte();
		}
		
	}
		
	/*
	 * Définition de la classe interne PoliceListener
	 * qui implemente l'interface ActionListener ...
	 */
	private class StyleListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent event_style) 
		{
			//On obtient le nom du style ...
			style = list_style.getSelectedItem().toString();
			
			//Texte par défaut du champ_police ...
			champ_style.setText(style);
			
			//On apelle la méthode formatTexte() ...
			formatTexte();
			
		}
	}
	
	/*
	 * Définition de la classe interne PoliceListener
	 * qui implemente l'interface ActionListener ...
	 */
	private class TailleListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent event_taille) 
		{
			taille = list_taille.getSelectedItem().toString();
			
			//Texte par défaut du champ_police ...
			champ_taille.setText(taille);
			
			//On apelle la méthode formatTexte() ...
			formatTexte();
			
		}
	
	}
	
	/*
	 * Définition de la classe interne OkListener
	 * qui implemente l'interface ActionListener ...
	 */
	private class OkListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent event_ok) 
		{
			//Si on cliqué sur le bouton OK
			//On met à jour la police de la zone de texte ...
			editeur.setTextAreaFont(aperçu.getFont());
			
			//On cache notre JDialogue Police ...
			maPolice.setVisible(false);
		}
	
	}
	
	/*
	 * Définition de la classe interne AnnulerListener
	 * qui implemente l'interface ActionListener ...
	 */
	private class AnnulerListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent event_annuler) 
		{
			//Si on a cliqué sur le bouton ANNULER
			//On cache notre JDialogue Police ...
			maPolice.setVisible(false);
		}
	
	}
	
}
