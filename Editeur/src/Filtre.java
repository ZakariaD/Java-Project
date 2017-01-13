import java.io.File;
import javax.swing.filechooser.FileFilter;

/*
 * Notre classe Filtre va heriter la classe abstraite FileFilter ...
 */
public class Filtre extends FileFilter
{
	//On déclare l'extension et la description du fichier ...
	private String extension = null;
	private String description = null;

	/*
	 * On définit un constructeur avec paramètres ...
	 */
	public Filtre(String extension,String description)
	{
		//On initialise les 2 objets extension et description ...
		this.extension = extension;
		this.description = description;
	}
	
	/*
	 * Impléméntation obligatoire des méthodes accept()
	 * et getDescription ...
	 */
	
	//La méthode accept nous renseigne si le filtre
	//accepte le type de fichier donné en paramétre ...
	public boolean accept(File fichier) 
	{		
		return (/*si c'est un dossier */
				fichier.isDirectory() || 
				/*ou si le nom du fichier se
				 *  termine avec l'extension précisé 
				 *  lors de l'instanciation de l'objet*/ 
				fichier.getName().endsWith(this.extension));
	}

	//La méthode getDescription() retourne la description du
	//fichier par exemple dans bloc-note nous affiche
	//"Fichiers texte (*.txt)" ...
	public String getDescription() {
		
		return this.description;
	}
	

}
