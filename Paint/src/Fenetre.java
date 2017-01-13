import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/*
 * Notre classe Fenetre va hériter la classe JFrame ...
 */
public class Fenetre extends JFrame implements ComponentListener
{
	/*
	 * On crée un objet de type Fenetre qui pointe vers notre Fenetre ...
	 */
	Fenetre maFenetre = this;
	
	/*
	 * les panneaux
	 */
	private PanneauDessin panneau = new PanneauDessin();
	
	/*
	 * La barre de menu et d'outil
	 */
	private JMenuBar barre_menu = new JMenuBar();
	private JToolBar barre_outil = new JToolBar();
	
	/*
	 * Les menus
	 */
	private JMenu fichier = new JMenu("Fichier");
	private JMenu edition = new JMenu("Edition");
	private JMenu forme_pointeur = new JMenu("Forme du pointeur");
		
	/*
	 * les menus item(sous menu)
	 */
	
		//Du menu Fichier
		private JMenuItem ouvrir = new JMenuItem("Ouvrir");
		
		//private JMenuItem enregistrer = new JMenuItem("Enregistrer");
		//private JMenuItem enregistrer_sous = new JMenuItem("Enregistrer sous");
	
		private JMenuItem effacer = new JMenuItem("Nouveau");
		private JMenuItem quitter = new JMenuItem("Quitter");
		
		//Du menu Edition
		private JMenuItem pointeur_rond = new JMenuItem("Rond");
		private JMenuItem pointeur_carre = new JMenuItem("Carré");
		private JMenuItem couleur_pointeur = new JMenuItem("Couleur du pointeur");
	
	
	/*
	 * Les boutons
	 */
	private JButton bouton_rond = new JButton(new ImageIcon("images/rond.jpg"));
	private JButton bouton_carre = new JButton(new ImageIcon("images/carre.jpg"));
	private JButton bouton_gomme = new JButton(new ImageIcon("images/gomme.jpg"));
	private JButton bouton_cercle = new JButton(new ImageIcon("images/cercle.jpg"));
	private JButton bouton_arc = new JButton(new ImageIcon("images/arc.jpg"));
	private JButton bouton_rectangle = new JButton(new ImageIcon("images/rectangle.jpg"));
	private JButton agrandir = new JButton(new ImageIcon("images/plus.jpg"));
	private JButton reduire = new JButton(new ImageIcon("images/moins.jpg"));
	private JButton palette = new JButton(new ImageIcon("images/palette.jpg"));

	/*
	 * Les coordonnées de chaque forme ...
	 */
	private int rectangle_debutX,rectangle_debutY,rectangle_posX,rectangle_posY;
	private int cercle_origineX,cercle_origineY,cercle_rayon;
	private int arc_debutX,arc_debutY,arc_posX,arc_posY;
	
	/*
	 * Un compteur pour detécter le nombe de cliques necessaire
	 * pour dessiner chaque forme 
	 */
	private int nbr_clique = 0;
	
	/*
	 * ************** ETAPE IMPORTANTE *********************
	 * On définit des ArrayList qui sont des tableau pouvant contenir 
	 * plusieur types de données.
	 * On va y mettre les coordonnées des point necessaire pour dessiner 
	 * chaque forme dans le but de les redessinner automatiquement
	 * car le JPanel se repaint chaque fois qu'on redimentionne la fenetre,
	 * l'agrandir ou la réduire ... 
	 *  
	 */
	
		/*
		 * Origine_X et Origine_Y représentent les coordonnées
		 * des formes carré et rond ...(le point où on presse)
		 */
		private ArrayList origineForme_X = new ArrayList();
		private ArrayList origineForme_Y = new ArrayList();
		
		/*
		 * Pour le rectangle :
		 * rect_debutX : represente l'abcisse x du 1er point du rectangle
		 * rect_debutY : represente l'ordonné y du 1er point du rectangle
		 * rect_posX : represente l'abcisse x du dernier point du rectangle
		 * rect_posY : represente l'ordonné y du dernier point du rectangle
		 */
		private ArrayList rect_debutX = new ArrayList();
		private ArrayList rect_debutY = new ArrayList();
		private ArrayList rect_posX = new ArrayList();
		private ArrayList rect_posY = new ArrayList();
		
		/*
		 * Pour le cercle :
		 * cercleX : represente l'abcisse x de l'origine du cercle
		 * cercleY : represente l'ordonné y de l'origine du cercle
		 * cercleRayon : represente le rayon du cercle
		 */
		private ArrayList cercleX = new ArrayList();
		private ArrayList cercleY = new ArrayList();
		private ArrayList cercleRayon = new ArrayList();

		/*
		 * Pour l'arc :
		 * ArcX : represente l'abcisse x de l'origine de l'arc
		 * ArcY : represente l'ordonné y de l'origine de l'arc
		 * arcRayon : represente le rayon de l'arc
		 */
		private ArrayList arcX = new ArrayList();
		private ArrayList arcY = new ArrayList();
		private ArrayList arcPosX = new ArrayList();
		private ArrayList arcPosY = new ArrayList();

		/*
		 * Autres ArrayList :
		 * couleur : stock la couleur de chaque dessin
		 * forme : stock le nom de la forme
		 * epaisseur : stock l'epaisseur de trait 
		 */
		private ArrayList couleur = new ArrayList();
		private ArrayList forme = new ArrayList();
		private ArrayList epaisseur = new ArrayList();
		
	/*
	 * la couleur de traçage
	 */
	private Color couleur_forme = Color.RED;
	
	/*
	 * Le nom de la forme est par défaut un rond
	 * on a 2 formes : rond et carré ...
	 */
	private String nom_forme = "rond";
	
	/*
	 * on défint l'épaisseur de traçage static car elle ne doit
	 * changer qu'au niveau des objets ...
	 */
	private static int valeur_epaisseur = 20;
	
	/*
	 * attribut boolean pour chaque forme permettant
	 * de savoir la forme à dessiner, le bool_default concerne
	 * la forme par défaut qui est le rond ...
	 */
	private boolean bool_default = true ,bool_cercle = false,bool_rectangle = false,bool_arc = false;
	
	/*
	 * Le constructeur par défaut
	 */
	public Fenetre()
	{
		/*
		 * Configuration de la fenetre ...
		 */
		this.setTitle("DPaint");
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.panneau.setFenetre(maFenetre);

		/*
		 * Configuration de la barre de menu
		 */
		fichier.add(ouvrir);
		//fichier.add(enregistrer);
		//fichier.add(enregistrer_sous);

		fichier.add(effacer);
		fichier.addSeparator();
		fichier.add(quitter);
		
		barre_menu.add(fichier);
		
		forme_pointeur.add(pointeur_rond);
		forme_pointeur.add(pointeur_carre);
		edition.add(forme_pointeur);
		edition.addSeparator();
		edition.add(couleur_pointeur);

		barre_menu.add(edition);
		
		
		
		/*
		 * Configuration de la barre d'outil
		 */
		bouton_rond.setToolTipText("Disque");
		bouton_carre.setToolTipText("Carré");
		bouton_gomme.setToolTipText("Gomme");
		bouton_cercle.setToolTipText("Dessiner Cercle");
		bouton_rectangle.setToolTipText("Dessiner Rectangle");
		bouton_arc.setToolTipText("Dessiner Arc");
		palette.setToolTipText("Palette de couleurs");
		reduire.setToolTipText("Diminuer l'épaisseur");
		agrandir.setToolTipText("Augmenter l'épaisseur");

		barre_outil.add(bouton_rond);
		barre_outil.add(bouton_carre);
		barre_outil.add(bouton_gomme);
		barre_outil.add(bouton_cercle);
		barre_outil.add(bouton_arc);
		barre_outil.add(bouton_rectangle);
		barre_outil.add(agrandir);
		barre_outil.add(reduire);
		barre_outil.add(palette);
		

		/*
		 * Les mnemonic
		 */
		fichier.setMnemonic('F');
		edition.setMnemonic('E');

		/*
		 * Gestion des événements
		 */
		
		/*
		 * Pour le JPanel, lorsqu'un événement de la souris se produit ...
		 */
		panneau.addMouseListener(new MouseAdapter() //CLASSE ANONYME ...
		{
			//Lorsqu'on presse (clique) avec la souris ...
			public void mousePressed(MouseEvent event)
				{
					//Si la forme à dessiner est le rond ...
					if(bool_default)
					{
						/*
						 * on retourne les coordonnées du point où on 
						 * a presser 
						 */
						int posX = event.getX();
						int posY = event.getY(); 
						
						/*
						 * On stock les données concerant chaque ArrayList
						 */
						origineForme_X.add(posX);
						origineForme_Y.add(posY);
						couleur.add(couleur_forme);
						forme.add(nom_forme);
						epaisseur.add(valeur_epaisseur);
						
						/*
						 * On appelle les setter(ou les mutateurs) de 
						 * l'objet panneau 
						 */
						
						/*
						 * le setter setBoolDefault va permettre à la 
						 * methode paintComponent de l'objet panneau de 
						 * dessiner ou non la forme par défaut ...
						 */
						panneau.setBoolDefault(true);
						
						/*
						 * On appelle le setter setEpaisseur de l'objet
						 * panneau pour changer la valeur de l'attribut
						 * valeur_epaisseur définit dans l'objet panneau ...
						 */
						panneau.setEpaisseur(valeur_epaisseur);
						
						/*
						 * On appelle le setter setPosXY pour lui envoyer 
						 * les valeur de posX et posY ...
						 */
						panneau.setPosXY(posX,posY);
						
						/*
						 * On appelle la méthode repaint() de l'objet 
						 * panneau (par héritage de la classe JPanel)
						 * pour redessiner l'objet panneau et prendre
						 * en considération les modifications faites et
						 * donc l'affichage de la forme ... 
						 * 
						 */
						panneau.repaint();
					}
				}
		//Lors du clique de la souris ...
		public void mouseClicked(MouseEvent event)
			{
				//si c'est le rectangle ...
				if(bool_rectangle)
				{
					//on incrémente le nombre de cliques ...
					nbr_clique++;
					
					/*
					 * le 1er clique indique le 1er point du rectangle
					 * il nous reste donc qu'un 2éme clique pour le dessiner
					 * complétement ...
					 */
					if(nbr_clique == 1)
					{
						//coordonnées du point de début ...
						rectangle_debutX = event.getX();
						rectangle_debutY = event.getY();
						
						//On définit une position de fin par défaut ...
						rectangle_posX = event.getX();
						rectangle_posY = event.getY();
						
						/*
						 * on stock les donneés concernant le rectangle
						 * dans l'ArrayList corespondant ...
						 */
						rect_debutX.add(rectangle_debutX);
						rect_debutY.add(rectangle_debutY);		

						/*
						 * On appelle le setter setRect pour lui envoyer
						 * les coordonnées de début et une autre
						 * position de la souris ...
						 */
						panneau.setRect(rectangle_debutX,rectangle_debutY,rectangle_posX,rectangle_posY);
						
						/*
						 * On appelle la méthode repaint() de l'objet 
						 * panneau (par héritage de la classe JPanel)
						 * pour redessiner l'objet panneau et prendre
						 * en considération les modifications faites et
						 * donc l'affichage du rectangle... 
						 * 
						 */
						panneau.repaint();
					}
					
					/*
					 * si le nombre de clique est 2, c'est qu'on a
					 * fini le dessin de notre rectangle ...
					 */
					if(nbr_clique == 2)
					{
						//on remet le nombre de clique à 0 ...
						nbr_clique = 0;
						
						//On redéfinit les nouvelles positions ...
						rectangle_posX = event.getX();
						rectangle_posY = event.getY();
						
						//on stock les nouvelles positions ...
						rect_posX.add(rectangle_posX);
						rect_posY.add(rectangle_posY);
				
						/*
						 * on appelle le setter setRectPast de 
						 * l'objet panneau et lui envoyer les ArrayList concernant
						 * le rectangle en paramètre.
						 * 
						 */
						panneau.setLastContentRect(rect_debutX, rect_debutY, rect_posX, rect_posY);
						

					}
				}
				/*
				 * si c'est le cercle , on fait de mêmeque pour
				 * le rectangle ...
				 */
				if(bool_cercle)
				{
					nbr_clique++;
					
					if(nbr_clique == 1)
					{
						cercle_origineX = event.getX();
						cercle_origineY = event.getY();
						cercle_rayon = event.getX();
						//On peut aussi faire cercle_rayon = event.getY();
						
						cercleX.add(cercle_origineX);
						cercleY.add(cercle_origineY);

						panneau.setCercle(cercle_origineX,cercle_origineY,cercle_rayon);
						panneau.repaint();
					}
					if(nbr_clique == 2)
					{
						nbr_clique = 0;
						cercleRayon.add(cercle_rayon);

						panneau.setLastContentCercle(cercleX,cercleY,cercleRayon);
					}
				}
				
				/*
				 * si c'est l'arc, on fait de même que pour le rectangle
				 * et le cercle ...
				 */
				if(bool_arc)
				{
					nbr_clique++;
					if(nbr_clique == 1)
					{
						arc_debutX = event.getX();
						arc_debutY = event.getY();
						arc_posX = event.getX();
						arc_posY = event.getY();
						
						arcX.add(arc_debutX);
						arcY.add(arc_debutY);

						panneau.setArc(arc_debutX,arc_debutY,arc_posX,arc_posY);
						panneau.repaint();
					}
					if(nbr_clique == 2)
					{
						
						nbr_clique = 0;
						
						arc_posX = event.getX();
						arc_posY = event.getY();
						
						arcPosX.add(arc_posX);
						arcPosY.add(arc_posY);
						
						panneau.setLastContentArc(arcX,arcY,arcPosX,arcPosY);
					}
				}
			}
		//Fin de la gestion des évenement de la souris sur le JPanel ... 
		});
					
		this.addComponentListener(this);
		
		//On définit les ActionListener ...
		panneau.addMouseMotionListener(new MoveListener());
		
		pointeur_rond.addActionListener(new FormeListener());
		pointeur_carre.addActionListener(new FormeListener());
		agrandir.addActionListener(new FormeListener());
		reduire.addActionListener(new FormeListener());
		couleur_pointeur.addActionListener(new CouleurListener());
		
		
		bouton_rond.addActionListener(new FormeListener());
		bouton_carre.addActionListener(new FormeListener());
		
		bouton_cercle.addActionListener(new FormeListener());
		bouton_rectangle.addActionListener(new FormeListener());
		bouton_arc.addActionListener(new FormeListener());
		bouton_gomme.addActionListener(new CouleurListener());
		palette.addActionListener(new CouleurListener());

		effacer.addActionListener(new DeleteListener());
		quitter.addActionListener(new QuitterListener());
		
		//Layout de la fenetre ...
		this.setLayout(new BorderLayout());
		
		//On ajoute la barre de menu ...
		this.setJMenuBar(barre_menu);
		
		/*
		 * On met un arrière-plan blanc pour le contentPane de 
		 * l'objet fenetre ...		
		 */
		this.getContentPane().setBackground(Color.WHITE);
		
		/*
		 * On met nos objets dans le contentPane de l'objet fenetre
		 * en utilisant les position prédéfinit dans le BorderLayout ...
		 */
		this.getContentPane().add(barre_outil,BorderLayout.NORTH);
		this.getContentPane().add(panneau,BorderLayout.CENTER);

		//On affiche la fenetre ...
		this.setVisible(true);
	}
	
	//********* LES CLASSES INTERNES ************
	
	/*
	 * Gestion des évenements concernant les déplacements de la souris ...
	 */
	public class MoveListener implements MouseMotionListener
	{
		/*
		 * Lorsqu'on se déplace tout en maintenant le clique 
		 * elle ne concerne que les formes carré et rond...
		 */
		public void mouseDragged(MouseEvent event)
		{
			//si c'est le rond ou le carré ...
			if(bool_default)
			{
				int posX = event.getX();
				int posY = event.getY();
				
				//On stock les données dans les ArrayList ...
				origineForme_X.add(posX);
				origineForme_Y.add(posY);
				couleur.add(couleur_forme);
				forme.add(nom_forme);
				epaisseur.add(valeur_epaisseur);
				
				panneau.setBoolDefault(true);
				panneau.setEpaisseur(valeur_epaisseur);
				panneau.setPosXY(posX,posY);
				panneau.repaint();
			}
		}
		
		/*
		 * Lorsqu'on se déplace avec la souris 
		 * evénement concernant le rectangle,le cercle et l'arc ...
		 */
		public void mouseMoved(MouseEvent event) 
		{
			if(bool_rectangle && nbr_clique == 1)
			{				
				rectangle_posX = event.getX();
				rectangle_posY = event.getY();
				
				//On affiche un libellé qui suit le mouvement de la souris ...
				panneau.setToolTipText("X : "+rectangle_posX+" ; Y : "+rectangle_posY);
				panneau.setRect(rectangle_debutX,rectangle_debutY,rectangle_posX,rectangle_posY);
				panneau.repaint();
			}
			if(bool_cercle && nbr_clique == 1)
			{
				cercle_rayon = event.getX();
				panneau.setToolTipText("Diamètre : "+cercle_rayon);
				panneau.setCercle(cercle_origineX,cercle_origineY,cercle_rayon);
				panneau.repaint();
			}
			if(bool_arc && nbr_clique == 1)
			{
				arc_posX = event.getX();
				arc_posY = event.getY();
				
				panneau.setArc(arc_debutX,arc_debutY,arc_posX,arc_posY);
				panneau.repaint();
			}
			
		}
	//Fin de la classe intenre MoveListener ...
	}
	
	/*
	 * la classe intenre FormeListener implémente
	 *  l'interface ActionListener...
	 */
	public class FormeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_forme)
		{
			/*
			 * L'objet objet contient la source qui a produit l'évenement ...
			 */
			Object objet = action_forme.getSource();
				
			if(objet == pointeur_rond || objet == bouton_rond)
			{
				couleur_forme = Color.RED;
				panneau.setCouleurForme(couleur_forme);
				panneau.setForme("rond");
				nom_forme = "rond";
				bool_default = true;
				
				bool_cercle = false;
				bool_rectangle = false;
				bool_arc = false;
			}
			if(objet == pointeur_carre || objet == bouton_carre)
			{
				couleur_forme = Color.RED;
				panneau.setCouleurForme(couleur_forme);
				panneau.setForme("carré");
				nom_forme = "carré";
				bool_default = true;
				
				bool_cercle = false;
				bool_rectangle = false;
				bool_arc = false;
			}
			if(objet == bouton_cercle)
			{
				panneau.setForme("cercle");
				nom_forme = "cercle";
				bool_cercle = true;
				
				bool_default = false;
				bool_rectangle = false;
				bool_arc = false;
			}
			if(objet == bouton_rectangle)
			{
				panneau.setForme("rectangle");
				nom_forme = "rectangle";
				bool_rectangle = true;
				
				bool_default = false;
				bool_cercle = false;
				bool_arc = false;
			}
			if(objet == bouton_arc)
			{
				panneau.setForme("arc");
				nom_forme = "arc";
				bool_arc = true;
				
				bool_default = false;
				bool_cercle = false;
				bool_rectangle = false;
			}
			if(objet == agrandir)
			{
				valeur_epaisseur ++;
			}
			if(objet == reduire)
			{
				if(valeur_epaisseur > 2)
				{
					valeur_epaisseur --;
				}
				
			}
		}
	//Fin de la classe FormeListener ...
	}
	
	/*
	 * La classe interne CouleurListener implemente l'interface
	 * ActionListener ...
	 */
	public class CouleurListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_couleur)
		{
			Object objet = action_couleur.getSource();
			
			if(objet == couleur_pointeur)
			{
				//Affichage de la boite à couleur ...
				Color chosir_couleur = JColorChooser.showDialog(maFenetre,"Choisir Une Couleur", couleur_forme);
				panneau.setCouleurForme(chosir_couleur);
				couleur_forme = chosir_couleur;
			}
			if(objet == bouton_gomme)
			{
				panneau.setCouleurForme(Color.WHITE);
				couleur_forme = Color.WHITE;
				panneau.setForme(nom_forme);
				bool_default = true;
				
				bool_cercle = false;
				bool_rectangle = false;
				bool_arc = false;
			}
			if(objet == palette)
			{
				//affichage de la boite à couleur ...
				Color chosir_couleur = JColorChooser.showDialog(maFenetre,"Choisir Une Couleur", couleur_forme);
				panneau.setCouleurForme(chosir_couleur);
				couleur_forme = chosir_couleur;
			}
		}
	//Fin de la classe CouleurListener ...
	}
	
	/*
	 * La classe intenre DeleteListener implemente l'interface
	 * ActionListener ...
	 */
	public class DeleteListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_delete)
		{
			panneau.setFenetre(maFenetre);
			panneau.repaint();
		}
	}
	
	/*
	 * La classe interne QuitterListener implemente l'interface
	 * ActionListener ...
	 */
	public class QuitterListener implements ActionListener
	{
		public void actionPerformed(ActionEvent action_quitter)
		{
			//On ferme la fenetre ...
			System.exit(0);
		}
	}
	
	//Lorsqu'on redimentionne la fenetre ...
	public void componentResized(ComponentEvent arg0) 
	{
		
			panneau.setLastContentForme(origineForme_X, origineForme_Y,couleur,forme,epaisseur);
			panneau.repaint();
	}
	
	public void componentHidden(ComponentEvent arg0) 
	{
		// TODO Auto-generated method stub

	}
	
	public void componentMoved(ComponentEvent arg0)
	{
		
		
	}
	
	public void componentShown(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub

	}
}
