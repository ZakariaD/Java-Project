import java.io.File;
import javax.swing.filechooser.FileFilter;

/*
 * Notre classe Filtre va heriter la classe abstraite FileFilter ...
 */
public class Filtre extends FileFilter
{
	//On d�clare l'extension et la description du fichier ...
	private String extension = null;
	private String description = null;

	/*
	 * On d�finit un constructeur avec param�tres ...
	 */
	public Filtre(String extension,String description)
	{
		//On initialise les 2 objets extension et description ...
		this.extension = extension;
		this.description = description;
	}
	
	/*
	 * Impl�m�ntation obligatoire des m�thodes accept()
	 * et getDescription ...
	 */
	
	//La m�thode accept nous renseigne si le filtre
	//accepte le type de fichier donn� en param�tre ...
	public boolean accept(File fichier) 
	{		
		return (/*si c'est un dossier */
				fichier.isDirectory() || 
				/*ou si le nom du fichier se
				 *  termine avec l'extension pr�cis� 
				 *  lors de l'instanciation de l'objet*/ 
				fichier.getName().endsWith(this.extension));
	}

	//La m�thode getDescription() retourne la description du
	//fichier par exemple dans bloc-note nous affiche
	//"Fichiers texte (*.txt)" ...
	public String getDescription() {
		
		return this.description;
	}
	

}
