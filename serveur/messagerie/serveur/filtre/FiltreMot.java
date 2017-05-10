package messagerie.serveur.filtre;

/**
 * Classe permettant de filtrer un mot particulier.
 */
public class FiltreMot implements IFiltre {
	public final static long serialVersionUID = 211L;

	/**
	 * Mot à filtrer.
	 */
	private String mot;

	/**
	 * Création d'un filtre.
	 * @param mot Mot à filtrer.
	 */
	public FiltreMot(String mot) {
		this.mot = mot;
	}
	
	@Override
	public Object getObject() { return this.mot; }

	@Override
	public boolean compare(Object objet) {
		if (objet instanceof String)
			return ((String)objet).equals(this.mot);
		return false;
	}

	@Override
	public boolean equals(Object objet) {
		if (objet == this)
			return true;

		if (objet.getClass() != this.getClass())
			return false;

		return ((FiltreMot)objet).mot.equals(this.mot);
	}

	@Override
	public int hashCode() {
		return (this.mot.hashCode() % 512) * 
				this.mot.hashCode();
	}
}
