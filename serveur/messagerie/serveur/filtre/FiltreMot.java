package messagerie.serveur.filtre;

public class FiltreMot implements IFiltre {
	private String mot;
	
	public FiltreMot(String mot) {
		this.mot = mot;
	}

	@Override
	public boolean compare(Object objet) {
		if (object instanceof String)
			return ((String)object).equals(this.mot)
		return false;
	}
}
