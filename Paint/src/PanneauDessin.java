import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/*
 * La classe PanneauDessin h�rite de la classe JPanel ...
 */
public class PanneauDessin extends JPanel
{
	private Color couleur_forme = Color.RED;
	private Graphics g;
	private boolean bool_default = false,bool_erase = false,bool_retablir = false,bool_rectangle = false,bool_cercle = false, bool_arc = false;
	private int rect_debutX,rect_debutY,rect_posX,rect_posY;
	private int cercle_origineX,cercle_origineY,cercle_rayon;
	private int arc_debutX,arc_debutY,arc_posX,arc_posY;

	private int posX,posY,valeur_epaisseur;
	private String nom_forme = "rond";
	private Fenetre fenetre;
	
	private ArrayList origineFormeX = new ArrayList(),origineFormeY = new ArrayList(),couleur = new ArrayList(),forme = new ArrayList(),epaisseur = new ArrayList();

	/*
	 * on cr�e des objet de type ArrayList qui vont nous permettre
	 * de stocker les rectangles, les cercle et les arcs dessin� 
	 * pour les afficher qu'on on redimentionne la fenetre ...
	 */
	private ArrayList rectPast_debutX = new ArrayList();
	private ArrayList rectPast_debutY = new ArrayList();
	private ArrayList rectPast_posX = new ArrayList();
	private ArrayList rectPast_posY = new ArrayList();
	
	private ArrayList arcPast_debutX = new ArrayList();
	private ArrayList arcPast_debutY = new ArrayList();
	private ArrayList arcPast_posX = new ArrayList();
	private ArrayList arcPast_posY = new ArrayList();
	
	private ArrayList cerclePast_origineX = new ArrayList();
	private ArrayList cerclePast_origineY = new ArrayList();
	private ArrayList cerclePast_rayon = new ArrayList();
	
	/*
	 * On a pas besoin d'un constructeur, la m�thode paintComponent
	 * s'appelle automatiquement apr�s chaque repaint de l'objet panneau ... 
	 */
	public void paintComponent(Graphics g)
	{		
		//Si c'est bool_default, c�d le rond ou le carr� ...
		if(bool_default)
		{
			/*
			 * On d�finit la couleur de remplissage ...
			 */
			g.setColor(couleur_forme);
			
			if(nom_forme.equals("rond"))
			{
				/*
				 * on paint un rond ...
				 */
				g.fillOval(posX, posY,valeur_epaisseur,valeur_epaisseur);
			}
			if(nom_forme.equals("carr�"))
			{
				/*
				 * on paint un carr� ...
				 */
				g.fillRect(posX, posY,valeur_epaisseur,valeur_epaisseur);
			}
		}
		
		//Si c'est bool_erase, on efface la fenetre ...
		if(bool_erase)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0,fenetre.getWidth(),fenetre.getHeight());
			bool_erase = false;
		}
		
		/*
		 * Si c'est bool_retablir, on r�afiche tout le contenu 
		 * du panneau avant le redimentionnement ...
		 */
		if(bool_retablir)
		{
			for(int j = 0;j<origineFormeX.size();j++)
			{
				/*
				 * On proc�de au cast car les objet retourn�s par 
				 * la m�thode get des objets de type ArrayList sont de
				 * type Object ... 
				 */
				
				g.setColor((Color)couleur.get(j));
				int a = (Integer) origineFormeX.get(j);
				int b = (Integer) origineFormeY.get(j);
				int e = (Integer) epaisseur.get(j);
				String str = (String)forme.get(j);
				
				if(str.equals("rond"))
				{
					g.fillOval(a, b,e,e);
				}
				if(str.equals("carr�"))
				{
					g.fillRect(a, b,e,e);
				}
				
			}
			this.bool_retablir = false;
			
		}
		
		/*
		 * Si c'est bool_rectangle, on dessine un rectangle sans
		 * supprimer le contenu d�j� pr�sent, on va proc�der 
		 * comme pour le cas de bool_retablir ... 
		 */
		if(bool_rectangle)
		{
			/*
			 * Ici,on va effacer le contenu de la fenetre 
			 * � chaque appelle de la m�thode repaint pour permettre
			 * dessiner un seul r�ctangle m�me si on d�place la souris,
			 * le rectangel suivra le mvt de la souris.
			 * mais on va utiliser les ArrayList pour afficher en m�me temps
			 * le contenu pr�c�dant ...
			 */
			g.setColor(Color.WHITE);
			g.fillRect(0, 0,fenetre.getWidth(),fenetre.getHeight());
			g.setColor(couleur_forme);
			
			//On dessine un rectangle ...
			g.drawRect(rect_debutX,rect_debutY,rect_posX,rect_posY);
			
			//On affiche les autres rectangles d�ssin�s ...
			for(int j = 0;j < rectPast_posX.size();j++)
			{
				int a = (Integer)rectPast_debutX.get(j);
				int b = (Integer)rectPast_debutY.get(j);
				int c = (Integer)rectPast_posX.get(j);
				int d = (Integer)rectPast_posY.get(j);
				
				g.drawRect(a,b,c,d);

			}
			
			//On affiche les cercles d�j� pr�sents ...
			for(int j = 0; j < cerclePast_rayon.size(); j++)
			{
				int x = (Integer)cerclePast_origineX.get(j);
				int y = (Integer)cerclePast_origineY.get(j);
				int r = (Integer)cerclePast_rayon.get(j);
				
				g.drawOval(x,y,r,r);

			}
			
			//On affiche les arcs d�j� presents ...
			for(int j = 0;j < arcPast_posX.size();j++)
			{
				int a = (Integer)arcPast_debutX.get(j);
				int b = (Integer)arcPast_debutY.get(j);
				int c = (Integer)arcPast_posX.get(j);
				int d = (Integer)arcPast_posY.get(j);
				
				g.drawArc(a,b,c,d,c,c);

			}
			//On affiche les formes d�j� presentes ...
			for(int j = 0;j<origineFormeX.size();j++)
			{
				g.setColor((Color)couleur.get(j));
				int a = (Integer) origineFormeX.get(j);
				int b = (Integer) origineFormeY.get(j);
				int e = (Integer) epaisseur.get(j);
				String str = (String)forme.get(j);
				
				if(str.equals("rond"))
				{
					g.fillOval(a, b,e,e);
				}
				if(str.equals("carr�"))
				{
					g.fillRect(a, b,e,e);
				}
			}
		}
		
		/*
		 * Si c'est bool_cercle, on dessine un cercle 
		 * comme pour le rectangle on va proc�der ...
		 */
		if(bool_cercle)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0,fenetre.getWidth(),fenetre.getHeight());
			g.setColor(couleur_forme);
			
			//On dessine un cercle(rond vide) ...
			g.drawOval(cercle_origineX,cercle_origineY,cercle_rayon,cercle_rayon);
		
			//On affiche les autres cercles dessin�s  ...
			for(int j = 0; j < cerclePast_rayon.size(); j++)
			{
				int x = (Integer)cerclePast_origineX.get(j);
				int y = (Integer)cerclePast_origineY.get(j);
				int r = (Integer)cerclePast_rayon.get(j);
				
				g.drawOval(x,y,r,r);

			}
			//On affiche les arcs d�j� presents ...
			for(int j = 0;j < arcPast_posX.size();j++)
			{
				int a = (Integer)arcPast_debutX.get(j);
				int b = (Integer)arcPast_debutY.get(j);
				int c = (Integer)arcPast_posX.get(j);
				int d = (Integer)arcPast_posY.get(j);
				
				g.drawArc(a,b,c,d,c,c);

			}
			
			//On affiche les rectangles d�j� pr�sents ...
			for(int j = 0;j < rectPast_posX.size();j++)
			{
				int a = (Integer)rectPast_debutX.get(j);
				int b = (Integer)rectPast_debutY.get(j);
				int c = (Integer)rectPast_posX.get(j);
				int d = (Integer)rectPast_posY.get(j);
				
				g.drawRect(a,b,c,d);

			}
			
			//On affiche les formes d�ja presentes ...
			for(int j = 0;j<origineFormeX.size();j++)
			{
				g.setColor((Color)couleur.get(j));
				int a = (Integer) origineFormeX.get(j);
				int b = (Integer) origineFormeY.get(j);
				int e = (Integer) epaisseur.get(j);
				String str = (String)forme.get(j);
				
				if(str.equals("rond"))
				{
					g.fillOval(a, b,e,e);
				}
				if(str.equals("carr�"))
				{
					g.fillRect(a, b,e,e);
				}
			}
		}
		
		/*
		 * Si c'est bool_arc, on dessine un arc
		 * comme pour le rectangle et le cercle ...
		 */
		if(bool_arc)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0,fenetre.getWidth(),fenetre.getHeight());
			g.setColor(couleur_forme);
			
			g.drawArc(arc_debutX,arc_debutY, arc_posX,arc_posY,arc_posX,arc_posX);
		
			//On affiche les autres arcs d�ssin�s ...
			for(int j = 0;j < arcPast_posX.size();j++)
			{
				int a = (Integer)arcPast_debutX.get(j);
				int b = (Integer)arcPast_debutY.get(j);
				int c = (Integer)arcPast_posX.get(j);
				int d = (Integer)arcPast_posY.get(j);
				
				g.drawArc(a,b,c,d,c,c);

			}
			
			//On affiche les cercles d�j� pr�sents ...
			for(int j = 0; j < cerclePast_rayon.size(); j++)
			{
				int x = (Integer)cerclePast_origineX.get(j);
				int y = (Integer)cerclePast_origineY.get(j);
				int r = (Integer)cerclePast_rayon.get(j);
				
				g.drawOval(x,y,r,r);

			}
			
			//On affiche les rectangles d�j� presents ...
			for(int j = 0;j < rectPast_posX.size();j++)
			{
				int a = (Integer)rectPast_debutX.get(j);
				int b = (Integer)rectPast_debutY.get(j);
				int c = (Integer)rectPast_posX.get(j);
				int d = (Integer)rectPast_posY.get(j);
				
				g.drawRect(a,b,c,d);

			}
			
			//On affiche les formes d�ja presentes ...
			for(int j = 0;j<origineFormeX.size();j++)
			{
				g.setColor((Color)couleur.get(j));
				int a = (Integer) origineFormeX.get(j);
				int b = (Integer) origineFormeY.get(j);
				int e = (Integer) epaisseur.get(j);
				String str = (String)forme.get(j);
				
				if(str.equals("rond"))
				{
					g.fillOval(a, b,e,e);
				}
				if(str.equals("carr�"))
				{
					g.fillRect(a, b,e,e);
				}
			}
		}
	}
	
	/*
	 * ************ LES SETTERS OU LES MUTATEURS ***************
	 */
	public void setBoolDefault(boolean bool)
	{
		this.bool_default = bool;
		
		this.bool_rectangle = false;
		this.bool_cercle = false;
		this.bool_arc = false;
		this.bool_retablir = false;
		this.bool_erase = false;
	}
	
	public void setPosXY(int a,int b)
	{
		this.posX = a;
		this.posY = b;
	}
	
	public void setEpaisseur(int valeur)
	{
		this.valeur_epaisseur = valeur;
	}
	
	public void setCouleurForme(Color couleur)
	{
		this.couleur_forme = couleur;
	}
	
	public void setForme(String forme)
	{
		this.nom_forme = forme;
	}
	
	public void setFenetre(Fenetre fenetre)
	{
		this.fenetre = fenetre;
		
		//On vide le contenu des ArrayList ...
		this.origineFormeX.clear();
		this.origineFormeY.clear();
		this.couleur.clear();
		this.forme.clear();
		
		this.rectPast_debutX.clear();
		this.rectPast_debutY.clear();
		this.rectPast_posX.clear();
		this.rectPast_posY.clear();
		
		this.arcPast_debutX.clear();
		this.arcPast_debutY.clear();
		this.arcPast_posX.clear();
		this.arcPast_posY.clear();

		this.cerclePast_origineX.clear();
		this.cerclePast_origineY.clear();
		this.cerclePast_rayon.clear();
		
		this.bool_erase = true;
		this.bool_rectangle = false;
		this.bool_retablir = false;
		this.bool_cercle = false;
		this.bool_arc = false;
		this.bool_default = false;
	}
	
	public void setRect(int a,int b, int c, int d)
	{
		this.bool_rectangle = true;
		
		this.bool_cercle = false;
		this.bool_arc = false;
		this.bool_default = false;
		this.bool_retablir = false;
		this.bool_erase = false;
		
		this.rect_debutX = a;
		this.rect_debutY = b;
		this.rect_posX = c;
		this.rect_posY = d;
	}
	
	public void setCercle(int a,int b, int c)
	{
		this.bool_cercle = true;

		this.bool_rectangle = false;
		this.bool_arc = false;
		this.bool_default = false;
		this.bool_retablir = false;
		this.bool_erase = false;
		
		this.cercle_origineX = a;
		this.cercle_origineY = b;
		this.cercle_rayon = c;
	}
	
	public void setArc(int a,int b, int c,int d)
	{
		this.bool_arc = true;
		
		this.bool_cercle = false;
		this.bool_rectangle = false;
		this.bool_default = false;
		this.bool_retablir = false;
		this.bool_erase = false;
		
		this.arc_debutX = a;
		this.arc_debutY = b;
		this.arc_posX = c;
		this.arc_posY = c;
	}
	
	public void setLastContentForme(ArrayList X,ArrayList Y,ArrayList c,ArrayList f,ArrayList e)
	{
			this.origineFormeX = X;
			this.origineFormeY = Y;
			this.couleur = c;
			this.forme = f;
			this.epaisseur = e;
			this.bool_retablir = true;
			this.bool_default = false;
	}
	
	public void setLastContentRect(ArrayList a, ArrayList b, ArrayList c,ArrayList d)
	{
		this.rectPast_debutX = a;
		this.rectPast_debutY = b;
		this.rectPast_posX = c;
		this.rectPast_posY = d;
	}
	public void setLastContentCercle(ArrayList x, ArrayList y, ArrayList r)
	{
		this.cerclePast_origineX = x;
		this.cerclePast_origineY = y;
		this.cerclePast_rayon = r;

	}
	public void setLastContentArc(ArrayList a, ArrayList b, ArrayList c,ArrayList d)
	{
		this.arcPast_debutX = a;
		this.arcPast_debutY = b;
		this.arcPast_posX = c;
		this.arcPast_posY = d;

	}
}	