package messagerie.serveur.filtre;

import java.io.Serializable;

/**
 * Interface définissant les actions dont doivent être capables les différents filtres.
 */
public interface IFiltre extends Serializable {
	/**
	 * Permet de vérifier si l'objet passé en paramètre doit être filtré.
	 * @param objet Objet à vérifier.
	 * @return Vrai si l'objet doit être filtré. Faux sinon.
	 */
	public boolean compare(Object objet);

	/**
	 * Permet de récupérer l'objet filtré.
	 * @return Objet filtré.
	 */
	public Object getObject();

	@Override
	public boolean equals(Object objet);

	@Override
	public int hashCode();
}
