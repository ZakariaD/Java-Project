import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/*
 * Notre classe Editeur va heriter la classe JFrame ...
 */
public class Editeur extends JFrame
{
	/*
	 * Un pointeur vers la fenetre
	 */
	private Editeur monEditeur = this;
	
	/*
	 * La barre de menu, d'outil et d'état ...
	 */
	private JMenuBar barre_menu = new JMenuBar();
	private JToolBar barre_outil = new JToolBar();
	private JLabel label_barre_etat = new JLabel("| Total Lignes 1, Position Curseur 0 |");

	/*
	 * Les menus
	 */
	private JMenu fichier = new JMenu("Fichier");
	private JMenu edition = new JMenu("Edition");
	private JMenu format = new JMenu("Format");
	private JMenu affichage = new JMenu("Affichage");
	private JMenu help = new JMenu("?");
	
	/*
	 * les boutons de la barre d'outil ...
	 */
	private JButton bouton_nouveau = new JButton(new ImageIcon("images/nouveau.png"));
	private JButton bouton_ouvrir = new JButton(new ImageIcon("images/ouvrir.png"));
	private JButton bouton_enregistrer = new JButton(new ImageIcon("images/enregistrer.png"));
	private JButton bouton_enregistrer_sous = new JButton(new ImageIcon("images/enregistrer_sous.png"));
	private JButton bouton_couper = new JButton(new ImageIcon("images/couper.png"));
	private JButton bouton_copier = new JButton(new ImageIcon("images/copier.png"));
	private JButton bouton_coller = new JButton(new ImageIcon("images/coller.png"));
	private JButton bouton_supprimer = new JButton(new ImageIcon("images/supprimer.png"));
	private JButton bouton_about = new JButton(new ImageIcon("images/about.png"));

	/*
	 * Les sous-menu du menu Fichier
	 */
	private JMenuItem nouveau = new JMenuItem("Nouveau");
	private JMenuItem ouvrir = new JMenuItem("Ouvrir...");
	private JMenuItem enregistrer = new JMenuItem("Enregistrer");
	private JMenuItem enregistrer_sous = new JMenuItem("Enregistrer sous...");
	private JMenuItem quitter = new JMenuItem("Quitter");
	
	/*
	 * Les sous-menu du menu Edition
	 */
	private JMenuItem annuler = new JMenuItem("Annuler");
	private JMenuItem couper = new JMenuItem("Couper");
	private JMenuItem copier = new JMenuItem("Copier");
	private JMenuItem coller = new JMenuItem("Coller");
	private JMenuItem supprimer = new JMenuItem("Supprimer");
	private JMenuItem selectionner_tout = new JMenuItem("Selectionner tout");
	
	/*
	 * Les sous-menu de la popup menu (lors du clique sur le bouton
	 * droit de la souris) ...
	 */
	private JMenuItem annuler_up = new JMenuItem("Annuler");
	private JMenuItem couper_up = new JMenuItem("Couper");
	private JMenuItem copier_up = new JMenuItem("Copier");
	private JMenuItem coller_up = new JMenuItem("Coller");
	private JMenuItem supprimer_up = new JMenuItem("Supprimer");
	private JMenuItem selectionner_tout_up = new JMenuItem("Selectionner tout");
	
	/*
	 * Les sous-menu du menu Format
	 */
	private JCheckBoxMenuItem retour_automatique_a_la_ligne = new JCheckBoxMenuItem("retour automatique à la ligne");
	private JMenuItem couleur_fond = new JMenuItem("Couleur De Fond");
	private JMenuItem couleur_text = new JMenuItem("Couleur DU Texte");
	private JMenu couleur_selection = new JMenu("Couleur De La Selection");
	private JMenuItem couleur_texte_selection = new JMenuItem("Du Texte");
	private JMenuItem couleur_fond_selection = new JMenuItem("De Fond");
	private JMenuItem police = new JMenuItem("Police...");
	
	/*
	 * Les sous-menu du menu Affichage
	 */
	private JCheckBoxMenuItem check_barre_etat = new JCheckBoxMenuItem("Barre d'état");
	private JCheckBoxMenuItem check_barre_outil = new JCheckBoxMenuItem("Barre d'outil");

	/*
	 * Les sous-menu du menu ?
	 */
	private JMenuItem rubrique_aide = new JMenuItem("Rubriques d'aide");
	private JMenuItem a_propos = new JMenuItem("A Propos");
	
	/*
	 * La popup menu
	 */
	private JPopupMenu pop_up = new JPopupMenu();
	
	/*
	 * La zone de texte
	 */
	private JTextArea zone_texte = new JTextArea();
	
	/*
	 * Le scrollPane, on met dedans la zone de texte ...
	 */
	private JScrollPane scroll = new JScrollPane(zone_texte);
	
	
	/*
	 * Le layout principale
	 */
	private BorderLayout layout = new BorderLayout();
	
	/*
	 * l'icone de la fenetre
	 */
	private Image icone;
	
	/*
	 * Les variables
	 */
	
	/*
	 *	La variable first_saving nous
	 *	renseigne si le fichier est enregistré 
	 *	pour la 1ère fois.
	 *	La variable file_saved nous renseigne si le
	 *	fichier est enregistrer ou pas.
	 */
	private boolean file_saved = true,first_saving = true;
	
	/*
	 * La variable titre_fichier contient le nom du fichier
	 */
	private String titre_fichier = "Sans Titre";
	
	/*
	 * L'attribut texte_initiale contient le texte déja présent 
	 * dans la zone de texte.
	 * L'attribut texte_modifie contient le texte ajouté 
	 * ou supprimer de la zone de texte.
	 * L'attribut chemein_fichier contient le chemin absolu du fichier
	 */
	private String texte_initial = null,texte_modifie = null,chemin_fichier = "Sans Titre";
	
	/*
	 * les messages
	 */
	private JOptionPane option = new JOptionPane();
	
	/*
	 * Les fichiers
	 */
	private File fichier_txt;
	private FileWriter ecrire_fichier = null;
	private FileReader lire_fichier = null;
	private JFileChooser choisir_fichier = null;
	
	/*
	 * On crée un objet de type Filtre qui va nous permettre de filtrer
	 * les dossiers et les fichiers dont l'extension est envoyée en
	 * parramètre au constructeur, et celà lors de l'ouverture
	 * des boîtes de dialogue ouvrir,enregistrer et enregistrer sous ...
	 */
	private Filtre filtre = new Filtre(".txt","Fichier Texte (*.txt)");
	
	/*
	 * On déclare les variable nbre_lignes ,nbre_colones
	 * et pos_curseur qui vont nous permettre d'obtenir 
	 * le nombre de lignes ,la position du curseur 
	 * et le nombre de colones dans la zone de texte ...
	 */
	private int nbre_lignes = 0,pos_curseur = 0;
	
	/*
	 * Le constructeur par défaut
	 */
	
	public Editeur()
	{
		//Titre de la fenetre ...
		this.setTitle(titre_fichier+" - DNote");
		
		//Taille de la fenetre ...
		this.setSize(600,400);
		
		//On centre la fenetre ...
		this.setLocationRelativeTo(null);
		
		//par défaut on ne fait rien lors du clique sur 
		//la croix de fermeture de fenetre ...
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//On permet le redimentionnement de la fenetre ...
		this.setResizable(true);
		
		//On définit le layout pricipale de la fenetre ...
		this.setLayout(layout);
		
		//on crée une icone à notre fenetre.
		//L'apelle de la méthode read() lève 
		//une exception que l'on doit capturer avec les blocs
		//try{}catch{} ...
		try
		{
			icone = ImageIO.read(new File("images/icone.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}	
		
		//On met l'icone à notre fenetre ...
		this.setIconImage(icone);
		
		// On appelle la méthode traitementMenu ...
		traitementMenu();
		
		// On appelle la méthode traitementEvenement ...
		traitementEvenement();
		
		//On initialise l'objet texte_initial de type String ...
		texte_initial = zone_texte.getText();
		
		//On définit la police par défaut de la zone de texte ...
		zone_texte.setFont(new Font("Times New Roman",Font.BOLD,22));
		
		//On précise l'alignement de la barre d'état ...
		this.label_barre_etat.setHorizontalAlignment(JLabel.CENTER);
		
		//on ajoute la zone de texte liée au scroll à la
		//fenetre avec comme position centrer...
		this.getContentPane().add(scroll,BorderLayout.CENTER);
		
		//On ajoute la barre d'état en position south ...
		this.getContentPane().add(label_barre_etat,BorderLayout.SOUTH);
		
		//Par défaut on rend la barre d'état invisible ...
		this.label_barre_etat.setVisible(false);
		
		//On ajute la barre d'outil en position north ...
		this.getContentPane().add(barre_outil,BorderLayout.NORTH);

		this.setJMenuBar(barre_menu);
		
		//On affiche notre fenetre ...
		this.setVisible(true);
	}
	
	/*
	 * La methode isTextSelected() étudie 
	 * l'état du text dans la zone de texte
	 * s'il est selectionné ou pas pour changer l'état 
	 * du sous menu edition et le popup menu
	 */
	private void isTextSelected()
	{
		if(zone_texte.getSelectedText() == null)
		{
			//On désactive les boutons de la popup menu ...
			couper_up.setEnabled(false);
			copier_up.setEnabled(false);
			supprimer_up.setEnabled(false);
			
			//On désactive les boutons de la barre d'état ...
			bouton_couper.setEnabled(false);
			bouton_copier.setEnabled(false);
			bouton_supprimer.setEnabled(false);

			//On désactive les boutons de la menu edition ...
			couper.setEnabled(false);
			copier.setEnabled(false);
			supprimer.setEnabled(false);
		}
		else
		{
			//On active les boutons de la popup menu ...
			couper_up.setEnabled(true);
			copier_up.setEnabled(true);
			supprimer_up.setEnabled(true);
			
			//On active les boutons de la barre d'état ...
			bouton_couper.setEnabled(true);
			bouton_copier.setEnabled(true);
			bouton_supprimer.setEnabled(true);
			
			//On active les boutons de la menu edition ...
			couper.setEnabled(true);
			copier.setEnabled(true);
			supprimer.setEnabled(true);
		}
	}
	
	/*
	 * La methode traitementMenu() configure la barre de menu 
	 * en ajoutant des sous menu aux différents menus.
	 * De plus on ajoute les Mnemonics aux différents menus.
	 *  
	 */
	private void traitementMenu()
	{
		/*
		 * Configuration des sous-menu
		 */
	
			//Menu Fichier
			fichier.add(nouveau);
			fichier.add(ouvrir);
			fichier.add(enregistrer);
			fichier.add(enregistrer_sous);
			fichier.addSeparator();
			fichier.add(quitter);
			
			//Menu Edition
			edition.add(annuler);
			edition.addSeparator();
			edition.add(couper);
			edition.add(copier);
			edition.add(coller);
			edition.add(supprimer);
			edition.addSeparator();
			edition.add(selectionner_tout);
			
					
			//Menu Format
			format.add(retour_automatique_a_la_ligne);
			format.add(couleur_fond);
			format.add(couleur_text);
			couleur_selection.add(couleur_texte_selection);
			couleur_selection.add(couleur_fond_selection);
			format.add(couleur_selection);
			format.add(police);
			
			//Menu Affichage
			affichage.add(check_barre_etat);
			affichage.add(check_barre_outil);
			
			//Menu ?
			help.add(rubrique_aide);
			help.addSeparator();
			help.add(a_propos);
			
		/*
		 * Configuration de la barre de menu
		 */
			barre_menu.add(fichier);
			barre_menu.add(edition);
			barre_menu.add(format);
			barre_menu.add(affichage);
			barre_menu.add(help);
			
		/*
		 * Configuration de la barre d'outil ...
		 */
			
			bouton_nouveau.setToolTipText("Nouveau");
			barre_outil.add(bouton_nouveau);
			
			bouton_ouvrir.setToolTipText("Ouvrir");
			barre_outil.add(bouton_ouvrir);
			
			bouton_enregistrer.setToolTipText("Enregistrer");
			barre_outil.add(bouton_enregistrer);
			
			bouton_enregistrer_sous.setToolTipText("Enregistrer Sous");
			barre_outil.add(bouton_enregistrer_sous);
			
			bouton_couper.setToolTipText("Couper");
			bouton_couper.setEnabled(false);
			barre_outil.add(bouton_couper);
			
			bouton_copier.setToolTipText("Copier");
			bouton_copier.setEnabled(false);
			barre_outil.add(bouton_copier);
			
			bouton_coller.setToolTipText("Coller");
			barre_outil.add(bouton_coller);
			
			bouton_supprimer.setToolTipText("Supprimer");
			bouton_supprimer.setEnabled(false);
			barre_outil.add(bouton_supprimer);
			
			bouton_about.setToolTipText("A Propos");
			barre_outil.add(bouton_about);

		/*
		 * Les Mnemonics <=> Alt+caratère
		 */
			fichier.setMnemonic('F');
			edition.setMnemonic('E');
			format.setMnemonic('O');
			affichage.setMnemonic('A');
			help.setMnemonic('H');
			
		/*
		 * Les accélérateurs ...
		 */
			nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));
			ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));
			enregistrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
			enregistrer_sous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK+KeyEvent.SHIFT_MASK));
			annuler.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_MASK));
			couper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_MASK));
			copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_MASK));
			coller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.CTRL_MASK));
			selectionner_tout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_MASK));

		//La popup menu
			pop_up.add(couper_up);
			pop_up.add(copier_up);
			pop_up.add(coller_up);
			pop_up.add(supprimer_up);
			pop_up.addSeparator();
			pop_up.add(selectionner_tout_up);
			
	//***********Fin de la methode traitementMenu*********//
	}
	
	/*
	 * La methode traitementEvenement() affecte 
	 * des événements à un certains nombre d'actions
	 * faites sur la fenetre.
	 */
	private void traitementEvenement()
	{
		/*
		 * Configuration des événements
		 */
			//menu Fichier
			nouveau.addActionListener(new MenuFichierListener());
			ouvrir.addActionListener(new MenuFichierListener());
			enregistrer.addActionListener(new MenuFichierListener());
			enregistrer.setEnabled(false);
			enregistrer_sous.addActionListener(new MenuFichierListener());
			quitter.addActionListener(new MenuFichierListener());
			
			//menu Edition
			annuler.addActionListener(new MenuEditionListener());
			couper.addActionListener(new MenuEditionListener());
			copier.addActionListener(new MenuEditionListener());
			coller.addActionListener(new MenuEditionListener());
			supprimer.addActionListener(new MenuEditionListener());
			selectionner_tout.addActionListener(new MenuEditionListener());
			
			//La popup menu
			annuler_up.addActionListener(new MenuEditionListener());
			couper_up.addActionListener(new MenuEditionListener());
			copier_up.addActionListener(new MenuEditionListener());
			coller_up.addActionListener(new MenuEditionListener());
			supprimer_up.addActionListener(new MenuEditionListener());
			selectionner_tout_up.addActionListener(new MenuEditionListener());
			
			//La barre d'outil ...
			bouton_nouveau.addActionListener(new MenuFichierListener());
			bouton_ouvrir.addActionListener(new MenuFichierListener());
			bouton_enregistrer.addActionListener(new MenuFichierListener());
			bouton_enregistrer_sous.addActionListener(new MenuFichierListener());
			bouton_enregistrer.setEnabled(false);
			
			bouton_couper.addActionListener(new MenuEditionListener());
			bouton_copier.addActionListener(new MenuEditionListener());
			bouton_coller.addActionListener(new MenuEditionListener());
			bouton_supprimer.addActionListener(new MenuEditionListener());

			//menu Format
			retour_automatique_a_la_ligne.addActionListener(new MenuFormatListener());
			couleur_fond.addActionListener(new MenuFormatListener());
			couleur_text.addActionListener(new MenuFormatListener());
			couleur_texte_selection.addActionListener(new MenuFormatListener());
			couleur_fond_selection.addActionListener(new MenuFormatListener());
			police.addActionListener(new MenuFormatListener());
			
			//menu Affichage
			check_barre_etat.addActionListener(new MenuAffichageListener());
			check_barre_outil.addActionListener(new MenuAffichageListener());

			//menu ?
			rubrique_aide.addActionListener(new MenuHelpListener());
			a_propos.addActionListener(new MenuHelpListener());
			bouton_about.addActionListener(new MenuHelpListener());
			
		//Lors du clique sur la croix pour fermer la fenetre
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent event) 
			{
				//On rénitialise l'objet texte_modifie ...
				texte_modifie = zone_texte.getText();
			
				//Si le contenu de la zone de texte n'a pas changé ...
				if(file_saved)
				{
					System.exit(0);
				}
				//Sinon, on affiche une boîte de dialogue pour 
				//Confirmer ou non notre choix ...
				else
				{
					int retour = JOptionPane.showConfirmDialog(null,"Le texte du fichier "+chemin_fichier+" a été\nmodifié.\n\nVoulez-vous enregistrer les modifications ?","Editeur",JOptionPane.YES_NO_CANCEL_OPTION);	
		
					//Si on clique sur le bouton OK de la boite de dialogue.
					//On enregistre les données puis on ferme la fenetre ...		
					if(retour == JOptionPane.OK_OPTION)	
					{
						enregistrerFichier();
						System.exit(0);
					}
		
					//Si on clique sur le bouton NON de la boite de dialogue.
					//On ferme la fenetre ...
					if(retour == JOptionPane.NO_OPTION)
					{
						System.exit(0);
					}
					
					//Si on clique sur le bouton ANNULER de la boite de dialogue ...
					if(retour == JOptionPane.CANCEL_OPTION)
					{
						//On ne fait rien
					}
				}
			}
		});
		
		//Lorsqu'un événement clavier se produit 
		//sur la zone de texte ...
		zone_texte.addKeyListener(new KeyAdapter()
		{	
			//Lors du clique sur le clavier, càd quand on 
			//ajoute un caractère dans la zone de texte ...
			public void keyTyped(KeyEvent event) 
			{
				//Si retour_automatique_a_la_ligne est cochée ...
				if(retour_automatique_a_la_ligne.isSelected())
				{
					//Si la longueur du texte dans la zone de texte
					//est un multiple de 50, on fait un retour à la ligne ...
					if((zone_texte.getText().length() % 50) == 0
						&& zone_texte.getText().length() !=0)
					{
						//On fait un saut de ligne à partir de la fin du texte...
						zone_texte.append("\n");
					}
				}
				//On recupère le texte modifié de la zone de texte ...
				texte_modifie = zone_texte.getText();
			
				//Chaque fois qu'on tape un nouveau caractère
				//Le fichier texte est non enregistré ...
				file_saved = false;
			
				//On change le titre de la fenetre
				//quand le fichier est non enregistré ...
				monEditeur.setTitle("* "+titre_fichier+" - DNote");
		
				//On détermine le nombre de lignes
				//atteint dans la zone de texte ...
				nbre_lignes = zone_texte.getLineCount();
				
				//On détermine la position du curseur
				//dans la zone de texte ...
				pos_curseur = zone_texte.getCaretPosition();
				
				label_barre_etat.setText("| Total Lignes "+nbre_lignes+", Position Curseur "+pos_curseur+" |");

				//On active les objets corespondant à l'enregistrement ...
				enregistrer.setEnabled(true);
				bouton_enregistrer.setEnabled(true);
			}
		});
		
		//Lorsqu'un événement de la souris se produit
		//sur la zone de texte ...
		zone_texte.addMouseListener(new MouseAdapter()
		{
			//Quand on clique de la souris dans la zone de texte ...
			public void mouseClicked(MouseEvent event)
			{
				//On récupère le nombre de lignes ...
				nbre_lignes = zone_texte.getLineCount();
				
				//On récupère la position du curseur ...
				pos_curseur = zone_texte.getCaretPosition();
				
				//On change le texte de notre label ...
				label_barre_etat.setText("| Total Lignes "+nbre_lignes+", Position Curseur "+pos_curseur+" |");

			}
			
			//Quand on relache l'un des bouton de la souris ...
			public void mouseReleased(MouseEvent event)
			{
				//On apelle la methode isTextSelected()
				//pour vérifier si on a selectionné du texte ...
				//************* ETAPE IMPORTANTE *************
				isTextSelected();
				
				//Si on a relaché le bouton droit de la souris
				if(event.isPopupTrigger())
				{
					
				//On affiche une menu popup dans la 
				//position correspondante aux coordonnées
				//du lieu de clique ...
					pop_up.show(zone_texte, event.getX(),event.getY());
				}
			}
		});
		
	//********Fin de la methode traitementEvenement********//	
	}
	
	/*
	 * Configuration des classes internes
	 */
	
	//La classe interne MenuFichierListener 
	//implémentation de l'interface ActionListener ...
	public class MenuFichierListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_fichier)
		{
			//la méthode getSource() retourn un objet 
			//de type Object ...
			Object objet = action_fichier.getSource();
			
			//On fait des test pour trouver 
			//l'objet qui a déclenché l'événement ...
			
			//Si c'est l'objet nouveau ...
			if(objet == nouveau || objet == bouton_nouveau)
			{
				//si le fichier texte est enregistré ...
				if(file_saved)
				{
					zone_texte.setText("");
				}
				//sinon, on affiche une boîte de dialogue ...
				else
				{
					//On affiche une boîte de dialgue pour confirmer notre choix ...
					int val = option.showConfirmDialog(null, "Le texte du fichier "+titre_fichier+" a été modifié.\nVoulez-vous enregistrer les modifications ?",titre_fichier+"- Editeur de texte", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
					
					//si on a cliqué sur le bouton OK ...
					if(val == JOptionPane.OK_OPTION)
					{
						//Apelle de la methode enregistrerFichier() ...
						enregistrerFichier();
						
						//On vide la zone de texte aprés l'enregistrement ...
						zone_texte.setText("");
						
						//On rénitialise notre variable titre_fichier ...
						titre_fichier = "Sans Titre";
						
						//On met un titre par défaut à la fenetre ...
						monEditeur.setTitle(titre_fichier+" - DNote");
					}
					
					//Si on a cliqué sur le bouton NON ...
					if(val == JOptionPane.NO_OPTION)
					{
						//On vide la zone de texte sans avoir enregistré le fichier ...
						zone_texte.setText("");
						
						//On rénitialise notre variable titre_fichier ...
						titre_fichier = "Sans Titre";
						
						//On met un titre par défaut à la fenetre ...
						monEditeur.setTitle(titre_fichier+" - DNote");
						
						//On rénitialise nos variables texte_initial et texte_modifie ...
						texte_initial = "";
						texte_modifie = "";
						
						//Un nouveau fichier => il n'est pas encore enregistré ...  
						file_saved = true;
						first_saving = true;
						
						//On désactive les objets corespondant à l'enregistrement ...
						enregistrer.setEnabled(false);
						bouton_enregistrer.setEnabled(false);
					}
					
					//Si on a cliqué sur le bouton ANNULER ...
					if(val == JOptionPane.CANCEL_OPTION)
					{
						//On ne fait rien
					}
				}
			}
			
			//Si c'est l'objet ouvrir ...
			if(objet == ouvrir || objet == bouton_ouvrir)
			{
				//On instancie notre objet choisir_fichier ...
				choisir_fichier = new JFileChooser();
				
				//On lui ajoute un filtre de selection ...
				choisir_fichier.addChoosableFileFilter(filtre);
				
				//On affiche la boîte de dialogue correspondante
				//à l'ouverutre fichier ou dossier tout en précisent 
				//son objet parent puis on met son 
				//retour dans une variable de type entier  
				//pour y effectuer des tests ...
				int retour = choisir_fichier.showOpenDialog(monEditeur);
				
				//Si on a cliqué sur le bouton OUVRIR de la boîte de
				//dialogue ...
				if(retour == JFileChooser.APPROVE_OPTION)
				{
					//la méthode getSelectedFile() retourne
					//un objet de type File - c'est l'adresse de 
					//l'emplacement du fichier - que l'on met 
					//dans le fichier_txt ...
					fichier_txt = choisir_fichier.getSelectedFile();
					
					//on metle nom du fichier_txt dans titre_fichier
					//qui est le titre de la fenetre ...
					titre_fichier = fichier_txt.getName();
					
					//on met le chemin absolu du fichier_txt dans
					//chemin_fichier ...
					chemin_fichier = fichier_txt.getAbsolutePath();
					
					//on déclare un objet chaine de type String
					//avec un contenu vide ...
					String chaine = "";
					
					//on déclare un compteur ...
					int i = 0;
					
					//l'instanciation d'un objet FileReader lève
					//une exception que l'on doit capturer
					//avec les blocs try{}catch{} ...
					try
					{
						//on instancie notre objet lire_fichier ...
						lire_fichier = new FileReader(fichier_txt);
						
						//la boucle va nous permettre de lire le 
						//contenu du fichier ouvert.
						//la méthode read() lit l'objet lire_fichier
						//caratère par caratère et retourn un entier 
						//que l'on met dans l'entier i
						//elle retourne -1 si la lecture de lobjet
						//est terminé ce qui arrêtera la boucle ...
						
						while((i=lire_fichier.read()) != -1)
						{
							//on met à jour l'objet chaine
							//caratère par caratère 
							//chaque caratère est obtenu en castant
							//l'entier de retour i ...
							chaine += (char)i;
						}
						
						//le contenu de la zone de texte sera
						//l'objet chaine ...
						zone_texte.setText(chaine);
						
						//On met à jour le titre de la fenetre 
						//qui ne sera que le nom du fichier ouvert ...
						monEditeur.setTitle(titre_fichier+" - DNote");
						
						//on rénitialise texte_initial et texte_modifie ...
						texte_initial = chaine;
						texte_modifie = chaine;
						
						//Le fichier ouvert est déjà enregistré ...
						file_saved = true;
						first_saving = false;
						
						//On désactive les objets corespondant à l'enregistrement ...
						enregistrer.setEnabled(false);
						bouton_enregistrer.setEnabled(false);
						
						//On ferme notre fichier ...
						lire_fichier.close();
					}
					catch(FileNotFoundException e)
					{
						e.printStackTrace();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			
			//Si c'est l'objet enregistrer ...
			if(objet == enregistrer || objet == bouton_enregistrer)
			{
				//apelle de la méthode enregistrerFichier() ...
				enregistrerFichier();
			}

			//Si c'est l'objet enregistrer_sous ...
			if(objet == enregistrer_sous || objet == bouton_enregistrer_sous)
			{
				//apelle de la méthode enregistrerFichierSous() ...
				enregistrerFichierSous();
			}

			//Si c'est l'objet quitter ...
			if(objet == quitter)
			{
				//on quitte le programme ...
				System.exit(0);
			}
		}
	}
	
	//La classe interne MenuEditionListener 
	//implémentation de l'interface ActionListener ...
	public class MenuEditionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_edition)
		{
			//la méthode getSource() retourn un objet 
			//de type Object ...
			Object objet = action_edition.getSource();
			
			//On fait des test pour trouver 
			//l'objet qui a déclenché l'événement ...
			
			//si c'est l'objet annuler ou annuler_up ...
			if(objet == annuler || objet == annuler_up)
			{
				zone_texte.setText(texte_initial);
			}
			
			//si c'est l'objet couper ou couper_up ...
			if(objet == couper || objet == couper_up || objet == bouton_couper)
			{
				//apelle de la méthode cut() ...
				zone_texte.cut();
				
				//On apelle la méthode isTextSelected() ...
				isTextSelected();
				
				//On change le titre de la fenetre
				//quand le fichier est non enregistré ...
				monEditeur.setTitle("* "+titre_fichier+" - DNote");
				
				//On active les objets corespondant à l'enregistrement ...
				enregistrer.setEnabled(true);
				bouton_enregistrer.setEnabled(true);
			}

			//si c'est l'objet copier ou copier_up ...
			if(objet == copier || objet == copier_up || objet == bouton_copier)
			{
				//apelle de la méthode copie() ...
				zone_texte.copy();
				
				//On apelle la méthode isTextSelected() ...
				isTextSelected();
			}

			//si c'est l'objet coller ou coller_up ...
			if(objet == coller || objet == coller_up || objet == bouton_coller)
			{				
				//apelle de la méthode paste() ...
				zone_texte.paste();
				
				//On apelle la méthode isTextSelected() ...
				isTextSelected();
				
				//On change le titre de la fenetre
				//quand le fichier est non enregistré ...
				monEditeur.setTitle("* "+titre_fichier+" - DNote");
				
				//On active les objets corespondant à l'enregistrement ...
				enregistrer.setEnabled(true);
				bouton_enregistrer.setEnabled(true);
			}

			//si c'est l'objet coller ou coller_up ...
			if(objet == supprimer || objet == supprimer_up || objet == bouton_supprimer)
			{
				//on remplace le texte selectionné 
				//par une chaine vide ... 
				zone_texte.replaceSelection("");
				
				//On apelle la méthode isTextSelected() ...
				isTextSelected();
				
				//On change le titre de la fenetre
				//quand le fichier est non enregistré ...
				monEditeur.setTitle("* "+titre_fichier+" - DNote");
				
				//On active les objets corespondant à l'enregistrement ...
				enregistrer.setEnabled(true);
				bouton_enregistrer.setEnabled(true);
			}

			//si c'est l'objet selectionner_tout ou selectionner_tout_up ...
			if(objet == selectionner_tout || objet == selectionner_tout_up)
			{
				//apelle de la méthode selectAll() ...
				zone_texte.selectAll();
				
				//On apelle la méthode isTextSelected() ...
				isTextSelected();
			}
		}
	}
	
	//La classe interne MenuFormatListener 
	//implémentation de l'interface ActionListener ...
	public class MenuFormatListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_format)
		{
			//la méthode getSource() retourn un objet 
			//de type Object ...
			Object objet = action_format.getSource();
			
			if(objet == retour_automatique_a_la_ligne)
			{
				if(retour_automatique_a_la_ligne.isSelected() && check_barre_etat.isSelected())
				{
					//On rend invisible la barre d'état ...
					label_barre_etat.setVisible(false);
				}
				if((!retour_automatique_a_la_ligne.isSelected())&& check_barre_etat.isSelected())
				{
					//On rend visible la barre d'état ...
					label_barre_etat.setVisible(true);
				}
			}
			
			//On fait des test pour trouver 
			//l'objet qui a déclenché l'événement ...
			if(objet == couleur_fond)
			{
				//On affiche la boîte à couleurs prédéfinit
				//dans java, qui lors du clique sur lebouton OK
				//retourn un objet de type Color ...
				Color couleur_de_fond = JColorChooser.showDialog(monEditeur,"Choisir Une Couleur", Color.BLACK);
				
				//On met à jour la couleur de fond de 
				//la zone de texte ...
				zone_texte.setBackground(couleur_de_fond);
			}
			if(objet == couleur_text)
			{
				//On affiche la boîte à couleurs prédéfinit
				//dans java, qui lors du clique sur lebouton OK
				//retourn un objet de type Color ...
				Color couleur_texte_zone_texte = JColorChooser.showDialog(monEditeur,"Choisir Une Couleur", Color.BLACK);
				
				//On met à jour la couleur de la zone de texte ...
				zone_texte.setForeground(couleur_texte_zone_texte);
			}
			if(objet == couleur_texte_selection)
			{
				//On affiche la boîte à couleurs prédéfinit
				//dans java, qui lors du clique sur lebouton OK
				//retourn un objet de type Color ...
				Color couleur_texte_selectionne = JColorChooser.showDialog(monEditeur,"Choisir Une Couleur", Color.BLACK);
				
				//On met à jour la couleur de la zone de texte ...
				zone_texte.setSelectedTextColor(couleur_texte_selectionne);
			}
			if(objet == couleur_fond_selection)
			{
				//On affiche la boîte à couleurs prédéfinit
				//dans java, qui lors du clique sur lebouton OK
				//retourn un objet de type Color ...
				Color couleur_fond_selectionne = JColorChooser.showDialog(monEditeur,"Choisir Une Couleur", Color.BLACK);
				
				//On met à jour la couleur de la zone de texte ...
				zone_texte.setSelectionColor(couleur_fond_selectionne);
			}
			if(objet == police)
			{
				//On crée un objet de type Police ...
				new Police(monEditeur);
			}
		}
	}
	
	//La classe interne MenuAffichageListener 
	//implémentation de l'interface ActionListener ...
	public class MenuAffichageListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_affichage)
		{
			//la méthode getSource() retourn un objet 
			//de type Object ...
			Object objet = action_affichage.getSource();
			
			//On fait des test pour trouver 
			//l'objet qui a déclenché l'événement ...
			if(objet == check_barre_etat)
			{
				//Si la check_barre_etat est cochée...
				if(check_barre_etat.isSelected())
				{
					//On affiche la barre d'état ...
					label_barre_etat.setVisible(true);
				}
				//Si non,càd la check_barre_etat est décochée ...
				else
				{
					//On l'a rend invisible ...
					label_barre_etat.setVisible(false);
				}
			}
			if(objet == check_barre_outil)
			{
				//Si la check_barre_outil est cochée...
				if(check_barre_outil.isSelected())
				{
					//On affiche la barre d'outil ...
					barre_outil.setVisible(true);
				}
				//Si non,càd la check_barre_outil est décochée ...
				else
				{
					//On l'a rend invisible ...
					barre_outil.setVisible(false);
				}
			}
		}
	}
	
	//La classe interne MenuHelpListener 
	//implémentation de l'interface ActionListener ...
	public class MenuHelpListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_help)
		{
			//la méthode getSource() retourn un objet 
			//de type Object ...
			Object objet = action_help.getSource();
			
			//On fait des test pour trouver 
			//l'objet qui a déclenché l'événement ...
			if(objet == rubrique_aide)
			{
			
			}
			if(objet == a_propos || objet == bouton_about)
			{
				//On crée un objet de type About() ...
				new About(monEditeur);
			}
		}
	}
	
	//La méthode enregistrerFichier() ...
	private void enregistrerFichier()
	{
		//Si c'est le 1er enregistrement ... 
		if(first_saving)
		{
			//apelle de la méthode enregistrerFichierSous() ...
			enregistrerFichierSous();
		}
		
		//si le fichier n'est pas enregistré ...
		if(!first_saving && !file_saved)
		{
			//l'instanciation d'un objet FileWriter lève
			//une exception que l'on doit capturer
			//avec les blocs try{}catch{} ...
			try
			{
				//On instancie notre objet ecrire_fichier ...
				ecrire_fichier = new FileWriter(fichier_txt);
				
				//on écrit dedans le contenu de la zone de texte ...
				ecrire_fichier.write(zone_texte.getText());
				
				//le fichier est enregisré ...
				first_saving = false;
				file_saved = true;

				//On désactive les objets corespondant à l'enregistrement ...
				enregistrer.setEnabled(false);
				bouton_enregistrer.setEnabled(false);
				
				//mise à jour du titre de la fenetre ...
				monEditeur.setTitle(titre_fichier+" - DNote");
				
				//on met le contenu de la zone de texte dans les 
				//objet texte_initial et texte_modifie ...
				texte_initial = zone_texte.getText();
				texte_modifie = zone_texte.getText();
				
				//On ferme le fichier ecrire_fichier ...
				ecrire_fichier.close();
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
		}
				
	}
	
	//La méthode enregistrerFichierSous() ...
	private void enregistrerFichierSous()
	{
		//On instancie notre objet choisir_fichier ...
		choisir_fichier = new JFileChooser();
		
		//On donne un titre à la boîte de dialogue
		//enregistre sous ...
		choisir_fichier.setDialogTitle("Enregistrer Sous");

		//On lui ajoute un filtre de selection ...
		choisir_fichier.addChoosableFileFilter(filtre);
		
		//On affiche la boîte de dialogue correspondante
		//à l'enregistrement du fichier ou dossier tout en précisent 
		//son objet parent puis on met son 
		//retour dans une variable de type entier  
		//pour y effectuer des tests ...
		int retour = choisir_fichier.showSaveDialog(monEditeur);
		
		//Si on a cliqué sur le bouton ENREGISTRER de la boîte de
		//dialogue ...
		if(retour == JFileChooser.APPROVE_OPTION)
		{
			//la méthode getSelectedFile() retourne
			//un objet de type File - c'est l'adresse de 
			//l'emplacement du fichier - que l'on met 
			//dans le fichier_txt ...
			fichier_txt = choisir_fichier.getSelectedFile();
			
			//On rénitialise le titre de la fenetre ...
			titre_fichier = fichier_txt.getName();

			//l'instanciation d'un objet FileWriter lève
			//une exception que l'on doit capturer
			//avec les blocs try{}catch{} ...
			try
			{
				//on instancie notre objet ecrire_fichier ...
				ecrire_fichier = new FileWriter(fichier_txt);
				
				//on écrit dedans le contenu de la zone de texte ...
				ecrire_fichier.write(zone_texte.getText());
								
				//le fichier est enregisré ...
				first_saving = false;
				file_saved = true;

				//On désactive les objets corespondant à l'enregistrement ...
				enregistrer.setEnabled(false);
				bouton_enregistrer.setEnabled(false);

				
				//mise à jour du titre de la fenetre ...
				monEditeur.setTitle(titre_fichier+" - DNote");
				
				//on met le contenu de la zone de texte dans les 
				//objet texte_initial et texte_modifie ...
				texte_initial = zone_texte.getText();
				texte_modifie = zone_texte.getText();
				
				//On ferme le fichier ecrire_fichier ...
				ecrire_fichier.close();
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	//On définit les accesseurs qui va nous permettre respectivement de 
	//mettre à jour la police de la zone_texte et retourner la police
	//de la zone de texte ...
	
	//Le mutateur ...
	public void setTextAreaFont(Font font)
	{
		zone_texte.setFont(font);
	}
	
	//L'accesseur ...
	public Font getTextAreaFont()
	{
		return zone_texte.getFont();
	}
/*
 * Fin de la class Editeur ...
 */

}
