package messagerie.serveur.filtre;

public class FiltreMot implements IFiltre {
	private String mot;
	
	public FiltreMot(String mot) {
		this.mot = mot;
	}

	@Override
	public boolean compare(Object objet) {
		if (objet instanceof String)
			return ((String)objet).equals(this.mot);
		return false;
	}
}
